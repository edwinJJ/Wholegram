package net.nigne.wholegram.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.service.BoardService;



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
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView signUp(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		model.addAttribute( "sessionId", user_id );
		
		return new ModelAndView("upload");
	}
	
	//TODO formdata로 업로드 구성예정
	@ResponseBody
	@RequestMapping(value = "/uploadw", method = RequestMethod.POST)
	public ModelAndView uploadAjax2(Locale locale, Model model) {
		return new ModelAndView("upload");
	}
	
	//Ajax로 파일 및 태그 업로드 
	@ResponseBody
	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	public String uploadAjax(@RequestBody Map<String,Object> param,HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();	
		String uploadPath = "";
		osSetting(OS);
		try{
			String rsq = param.get("dataurl").toString(); // json의 dataurl를 String타입으로 변환
			String type = rsq.substring(rsq.indexOf("/")+1,rsq.indexOf(";")); // dataurl의 확장자 타입을 분리
			uploadPath = createUploadPath(type);
			System.out.println(uploadPath);
			byte[] imagedata = DatatypeConverter.parseBase64Binary(rsq.substring(rsq.indexOf(",") + 1)); // dataurl의 data영역을 byte타입으로 변환
			UUID uid = UUID.randomUUID(); // 임의의 ID를 부여
			Path path = Paths.get(PATH+uploadPath+uid+"."+type); // 파일을 저장 할 위치 및 파일 이름을 지정
			Files.write(path, imagedata); // path에 쓰여진 값을 토대도 파일 작성
			
			if(compareToDataType(type)){
				FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(PATH+uploadPath+uid+"."+type); // 동영상을 프레임단위로 자름..
				System.out.println(PATH+uploadPath+uid+"."+type);
				grabber.start();    
				Java2DFrameConverter paintConverter = new Java2DFrameConverter(); // Frame -> bufferedImage로 변환하기 위한 컨버터 생성
				ImageIO.write(resizeImage(paintConverter.getBufferedImage(grabber.grab(),1),295,295),"png", new File(destination+uid+".png")); // bufferedImage를 이미지로 저장
				grabber.stop();  // for문이 없으므로 해당 동영상의 첫프레임을 이미지로 가져옴
			}
			
			BoardVO vo =new BoardVO();
			vo.setUser_id((String) session.getAttribute("user_id")); // 로그인 된 아이디로 글 작성
			vo.setContent(param.get("content").toString()); // 글 내용을 저장
			vo.setMedia(URLPATH+uploadPath+uid+"."+type); // 파일이 저장된 위치 저장
			vo.setMedia_type(param.get("type").toString());
			vo.setMedia_thumnail(URLPATH+"video_thumnail//"+uid+".png");
			if(param.get("atag")!= EMPTY)
				vo.setTag(param.get("atag").toString()); // 사람 태그를 하였을 경우 저장
			bs.BoardUP(vo);
			
			return "true";
		}catch(Exception e){
			e.printStackTrace();
			return e.toString();
		}
	}
	
	public static void osSetting(String OS){ // OS 버젼별로 경로 변경 및 디렉토리가 없을경우 디렉토리생성
		if(OS.indexOf("win") >= 0){
			PATH = "C:\\item\\"; 
			destination="C:\\item\\video_thumnail\\";
			File video = new File(PATH+"video");
			File image = new File(PATH+"image");
			File videoThumnail = new File(PATH+"video_thumnail");
			if(!video.exists()){
				video.mkdir();
				image.mkdir();
				videoThumnail.mkdir();
			}
		}
		
		if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ){
			PATH ="/home/pi/upload/";
			URLPATH="/resources/upload/";
			destination="/home/pi/upload/video_thumnail/";
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
	
	public static String createUploadPath(String type){ // upload폴더 내부에 저장될 위치 결정
		String path = "image/";
		for (String d : VIDEOTYPE){
			if(type.toLowerCase().equals(d))
				path = "video/";
		}
		return path;
	}
	
	public static boolean compareToDataType(String type){ // 현재 업로드 되고 있는 데이터가 동영상인지 아닌지 구별 동영상이 들어오면 true 아니면 false를 리턴
		boolean flag = false;
		
		for (String d : VIDEOTYPE){
			if(type.toLowerCase().equals(d))
				flag = true;
		}
		return flag;
	}
	
	// 동영상에서 읽어온 이미지를 resizing
	public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
		float w = new Float(width) ;
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
		
		BufferedImage resizedImage = new BufferedImage(wi,he,BufferedImage.TYPE_INT_RGB);
		Graphics2D rImage = (Graphics2D) resizedImage.getGraphics();
		rImage.drawImage(image.getScaledInstance(wi,he,image.SCALE_AREA_AVERAGING),0,0,wi,he,null);
		return Scalr.rotate(resizedImage, Rotation.CW_90);
		}
//	 TODO 제거예정
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(MultipartFile file,@RequestParam("dataurl")String rsq,@RequestParam("content")String cnt,@RequestParam("atag")String atag,Model model) throws IOException {
		System.out.println(file.getOriginalFilename());
		
		byte[] imagedata = DatatypeConverter.parseBase64Binary(rsq.substring(rsq.indexOf(",") + 1));
		System.out.println(imagedata.length);
		UUID uid = UUID.randomUUID();
		System.out.println(atag);
		Path path = Paths.get(PATH+uid+file.getOriginalFilename());
		Files.write(path, imagedata);
		return rsq;
		
	}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test(Locale locale, Model model) {
	
		return new ModelAndView("test");
	}
	
}
