package net.nigne.wholegram.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.FollowVO;
import net.nigne.wholegram.domain.NoticeVO;
import net.nigne.wholegram.service.BoardService;
import net.nigne.wholegram.service.FollowService;
import net.nigne.wholegram.service.NoticeService;



@RestController
@RequestMapping("/")
public class UploadController {
	public static String PATH = "C:\\item\\";
	public static String URLPATH = "/resources/upload/";
	public static String destination="";
	private static String OS = System.getProperty("os.name").toLowerCase();
	public static final String EMPTY = null;
	public static final String VIDEOTYPE[] = {"mp4","avi"};
	public static final String IMAGETYPE[] = {"jpg","jpeg","gif","png"};
	@Inject
	private BoardService bs;
	@Inject
	private NoticeService ns;
	@Inject
	private FollowService fs;
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView signUp(Locale locale, Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
	      String user_id = (String)session.getAttribute("user_id");
	      ModelAndView mav = new ModelAndView();
	      mav.addObject("sessionId", user_id);
	      mav.setViewName("upload");
	      return mav;
	}
	
	//TODO formdata업로드 처리 예정
	@ResponseBody
	@RequestMapping(value = "/uploadw", method = RequestMethod.POST)
	public ModelAndView uploadAjax2(Locale locale, Model model) {
		System.out.println("test");
		return new ModelAndView("upload");
	}
	
	//Ajax로 파일 업로드 처리 
	@ResponseBody
	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	public String uploadAjax(@RequestBody Map<String,Object> param, HttpServletRequest request) throws Exception{
		
		System.out.println("entry : " + param.entrySet());
		
		HttpSession session = request.getSession();	
		String uploadPath = "";
		String test =param.get("content").toString();																	// 업로드 게시물 글 작성 내용
		String temp=test;
		List<String> ls = new ArrayList<String>();
		osSetting(OS);																									// OS를 불러와서 OS에 맞게 경로 셋팅 및 폴더가 없을 경우 생성
		String user_id = (String)session.getAttribute("user_id");
		try{
			String rsq = param.get("dataurl").toString(); 																// json에서 dataurl값을 String으로 불러옴
			String type = rsq.substring(rsq.indexOf("/")+1,rsq.indexOf(";"));											// dataurl에서 타입을 따로 불러옴
			uploadPath = createUploadPath(type);																		// 업로드할 파일의 디렉토리 경로 변경
			
			byte[] imagedata = DatatypeConverter.parseBase64Binary(rsq.substring(rsq.indexOf(",") + 1)); 				/* dataurl에서 data부분을 byte로 변환 -> 모든 파일이 dataurl로 변환시킬 때 타입이 Base64로 변환된다. 그렇기 때문에 parseBase64Binary를 사용해서 binary타입으로 변환 하여 byte 변수로 받는다. 
																														   dataurl 내용은 [data:image/png;base64,data내용...]  -> [data:확장자;base64,실제데이터내용...] 이런 형식으로 되어있다. */
			
			UUID uid = UUID.randomUUID(); 									  											// 랜덤으로 파일이름 생성
			Path path = Paths.get(PATH+uploadPath+uid+"."+type); 			  											// Java.nio를 이용하여 업로드할 파일 저장경로를 path타입으로 지정
			
			if(param.get("content").toString().indexOf("@")!=-1 || param.get("content").toString().indexOf("#")!=-1){	//@와 #가 있을시 알맞게 처리
				test =param.get("content").toString();
				test = test.replaceAll("@", " @").replaceAll("#"," #").replaceFirst(" ","");							// replaceFirst : 글 내용중에 맨앞에 공백이 생겨서 제거해줌, replaceAll : @ or #이 있을경우 앞에 한칸 띄어줌
				String[] test2 = test.split(" ");																		// 공백 단위로 문자열 잘라줌
				
				temp = "";
				for (String s : test2) {
					if (s.indexOf("@") != -1 || s.indexOf("#") != -1) { 												// 글 내용중에 @ or # 있을경우
						if (s.indexOf("@") != -1) { 																	// @가 있을경우
							if (!user_id.equals(s)) { 																	// 유저 자신이 아닐경우
								ls.add(s.substring(1)); 																// List<String> ls변수에 유저 아이디 추가
							}
							s = s.replace(s, "<a href=/" + s.substring(1) + ">" + s + " </a>");							// @내용을 a태그로 감싸준다
							temp += s;
						} else {																						// #이 있을경우
							s = s.replace(s, "<a href=/hash/" + URLEncoder.encode(s, "UTF-8") + ">" + s + " </a>");		// #내용을 a태그로 감싸준다
							temp += s;
						}
					} else {																							// 글 내용중에 @ or # 없을경우 -> 즉, 그냥 단순한 텍스트 내용일 경우
						temp += s + " ";
					}
				}
			}
			
			// TODO 존재하지 않는 user를 언급했을경우 예외처리 해주기
			
			BoardVO vo =new BoardVO();
			vo.setUser_id((String) session.getAttribute("user_id")); 	// 로그인 된 아이디를 받아옴
			vo.setContent(temp); 										// 변환된 글 내용을 입력
			vo.setMedia(URLPATH+uploadPath+uid+"."+type); 				// 파일이 저장된 경로
			vo.setMedia_type(param.get("type").toString());				// 파일 타입
			vo.setMedia_thumnail(URLPATH+"video_thumnail//"+uid+".png");// 동영상 파일일 경우 썸네일 저장 경로

			if(param.get("atag")!= EMPTY){								// 사진에 유저를 태그한게 있을경우
				vo.setTag(param.get("atag").toString()); 				// atag에는 유저id를 a태그로 감싼내용이 들어있다. (a태그에 속성 설정도 같이 되어있음)
			}
			
			bs.BoardUP(vo);												// 게시물 업로드 (DB 등록)
			Files.write(path, imagedata); 								// 해당 경로로 이미지 or 동영상을 저장(path에 저장되어있는 경로로 파일을 생성한다고 볼 수 있음)
			boolean Rotate = false;			  							// 썸네일 이미지를 회전시킬지 안시킬지 결정하는 함수
			
			if(compareToDataType(type)){	  // 동영상일 경우
				FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(PATH+uploadPath+uid+"."+type); // 해당경로에 있는 동영상을 불러옴
				grabber.start();  																   // 프레임 캡쳐 시작
				int wd = grabber.grabImage().imageWidth;										   // 캡쳐된 이미지의 width 구함
				int he = grabber.grabImage().imageHeight;										   // 캡쳐된 이미지의 height 구함

				if(wd == he){
					Rotate = true;																   // 이미지의 높이가 같은 경우 true
				}
				System.out.println(Rotate);
				Java2DFrameConverter paintConverter = new Java2DFrameConverter(); // Frame -> bufferedImage로 변환
				ImageIO.write(resizeImage(paintConverter.getBufferedImage(grabber.grabImage(),1),295,295,type,Rotate),"png", new File(destination+uid+".png")); // bufferedImage瑜� �씠誘몄�濡� ���옣
				grabber.stop();  // 한 프레임 캡쳐 종료
 			}else{							//	동영상이 아닐 경우
				BufferedImage buf = ImageIO.read(new File(PATH+uploadPath+uid+"."+type)); //해당경로에 있는 파일을 불러옴
				ImageIO.write(resizeImage(buf,200,200,type,false), "png",new File(destination+uid+".png")); // 썸네일 이미지를 만들어 자장함
			}
			int board_num = bs.getBoardNum(vo);		// 알림을 클릭하였을때 동작하기 위한 변수
			if(param.get("content").toString().indexOf("@")!=-1){ // 게시물에 작성된 내용중에 유저를 언급하였을 경우 notice테이블에 입력됨
				NoticeVO nvo = new NoticeVO();
				nvo.setMedia(URLPATH+"video_thumnail//"+uid+".png");
				nvo.setBoard_num(board_num);
				nvo.setOther_id(user_id);
				nvo.setFlag(4);
				nvo.setRefer_content(temp);
				nvo.setUser_id2(ls);
				ns.insertFromUpload(nvo);
			}
			if(param.get("atag").toString()!="" && param.get("atag").toString()!=null){ // 사진 업로드 할때 사진에 태그된 아이디를  notice테이블에 입력
				System.out.println(param.get("atag").toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
				String atagTemp = param.get("atag").toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", " "); // 모든 태그들을 제거
				String[] tagArr =  atagTemp.split(" ");						//	단어별로 나눔
				List<String> list = putNotice(tagArr,user_id,6);
				NoticeVO nvo = new NoticeVO();
				nvo.setMedia(URLPATH+"video_thumnail//"+uid+".png");
				nvo.setBoard_num(board_num);
				nvo.setOther_id(user_id);
				nvo.setFlag(6);
				nvo.setRefer_content(temp);
				nvo.setUser_id2(list);
				ns.insertFromUpload(nvo);
			}
			return "true";
		}catch(Exception e){
			e.printStackTrace();
			return e.toString();
		}
	}

	// 팔로잉한 유저 목록 가져오기
	@RequestMapping(value = "/getFollowingUser", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> GetFollowingAjax(HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession();					
		
		ResponseEntity<Map<String,Object>> entity = null;
		String user_id = (String)session.getAttribute("user_id"); // 현재 접속한 유저 아이디를 가져옴
		List<FollowVO> ls = fs.getMyFollwerList(user_id); 		  // 나를 팔로워 하고 있는 목록 불러옴
		Map<String,Object> map = new HashMap<>();
		map.put("list", ls);
		
		try{
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.toString();
		}
		return entity;
	}
	
	public static boolean find(List<String> buf,String idx){ 		// 아이디가 중복되어서 언급되거나 태그된 경우를 체크
		boolean flag = false;
		
		for(String s:buf){
			if(s.equals(idx))
				flag = true;
		}
		
		return flag;
	}
	public static void osSetting(String OS){ 						// OS 에 따라 경로를 설정해줌
		if(OS.indexOf("win") >= 0){		     						// OS가 윈도우일 경우
			PATH = "C:\\item\\"; 
			destination="C:\\item\\video_thumnail\\";				// 동영상 프레임캡쳐 할때 사용
			File video = new File(PATH+"video");					// 동영상 파일 저장소
			File image = new File(PATH+"image");					// 이미지 파일 저장소
			File videoThumnail = new File(PATH+"video_thumnail");	// 동영상 파일 프레임캡쳐 저장소
			
			if(!video.exists()){									// 디렉토리 경로가 없을 경우 아래와 같이 경로(폴더)를 생성해줌. -> video or image or videoThumnail 아무거나 상관없다. 어차피 폴더가 없으면 다 같이 없거나, 있으면 다 같이 있거나 둘중 하나니깐
				video.mkdir();
				image.mkdir();
				videoThumnail.mkdir();
			}
		}
		
		if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ){  // OS가 Unix or linux일 경우
			PATH ="/home/test/upload/";
			URLPATH="/resources/upload/";
			destination="/home/test/upload/video_thumnail/";
			File video = new File(PATH+"video");
			File image = new File(PATH+"image");
			File videoThumnail = new File(PATH+"video_thumnail");
			if(!video.exists()){
				video.mkdir();
				image.mkdir();
				videoThumnail.mkdir();
			}
		}
	}
	public static String createUploadPath(String type){ // upload 경로를 변경해줌
		String path = "image/";
		for (String d : VIDEOTYPE){
			if(type.toLowerCase().equals(d))
				path = "video/";
		}
		return path;
	}
	
	public List<String> putNotice(String[] idx,String user_id,int flag){ // 리스트에다가 notice될 아이디값을 입력받고 리턴
		List<String> ls = new ArrayList<String>();
		for(String s:idx){
			if(!"".equals(s)){
				if(!user_id.equals(s)){
					ls.add(s);
				}
					
			}
		}
		return ls;
	}
	
	public static boolean compareToDataType(String type){ // 들어온 타입이 비디오일 경우 true 아니면 false로 처리
		boolean flag = false;
		
		for (String d : VIDEOTYPE){
			if(type.toLowerCase().equals(d))
				flag = true;
		}
		return flag;
	}
	
	/* 썸네일 이미지를 만들어주는 역할*/
	public static BufferedImage resizeImage(BufferedImage image, int width, int height, String type,boolean flag) {
		float w = new Float(width) ;		// 이미지의 높이 넓이를 불러옴
		float h = new Float(height) ;

		if ( w <= 0 && h <= 0 ) {
		w = image.getWidth();
		h = image.getHeight();
		} else if ( w <= 0 ) {
		w = image.getWidth() * ( h / image.getHeight() ); 
		} else if ( h <= 0 ) {
		h = image.getHeight() * ( w / image.getWidth() ); 
		}

		int wi = (int) w;
		int he = (int) h;
		
		BufferedImage resizedImage = new BufferedImage(wi,he,BufferedImage.TYPE_INT_RGB); // 이미지를 불러옴
		Graphics2D rImage = (Graphics2D) resizedImage.getGraphics();					  // 이미지를 2D화 시킴
		rImage.drawImage(image.getScaledInstance(wi,he,image.SCALE_AREA_AVERAGING),0,0,wi,he,null); // 이미지 썸네일 만들기
		
		if(compareToDataType(type) ) {
			System.out.println(compareToDataType(type));
			if(flag){												//원본 이미지의 가로 세로가 같은 경우
				System.out.println("2"+flag);
				return resizedImage;								// 회전없이 이미지 저장
				
			}else													//원본 이미지의 가로 세로가 다른경우
				return Scalr.rotate(resizedImage, Rotation.CW_90);  // 이미지를 90도 회전
		} else {
			if(wi == he)
				return resizedImage;
			else
				return Scalr.rotate(resizedImage, Rotation.CW_90);
		}
	}
	/* TODO �젣嫄곗삁�젙
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(MultipartFile file,@RequestParam("dataurl")String rsq,@RequestParam("content")String cnt,@RequestParam("atag")String atag,Model model) throws IOException {
		System.out.println(file.getOriginalFilename());
		
		byte[] imagedata = DatatypeConverter.parseBase64Binary(rsq.substring(rsq.indexOf(",") + 1));
		System.out.println(imagedata.length);
		UUID uid = UUID.randomUUID();
		System.out.println(atag);
		Path path = Paths.get(PATH+uid+file.getOriginalFilename());
		Files.write(path, imagedata);
		return rsq;[
		
	}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test(Locale locale, Model model) {
	
		return new ModelAndView("test");
	}*/
	
}
