package net.nigne.wholegram.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.NoticeVO;
import net.nigne.wholegram.service.BoardService;
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
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView signUp(Locale locale, Model model) {
	
		return new ModelAndView("upload");
	}
	
	//TODO formdata濡� �뾽濡쒕뱶 援ъ꽦�삁�젙
	@ResponseBody
	@RequestMapping(value = "/uploadw", method = RequestMethod.POST)
	public ModelAndView uploadAjax2(Locale locale, Model model) {
		System.out.println("test");
		return new ModelAndView("upload");
	}
	
	//Ajax濡� �뙆�씪 諛� �깭洹� �뾽濡쒕뱶 
	@ResponseBody
	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	public String uploadAjax(@RequestBody Map<String,Object> param,HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();	
		String uploadPath = "";
		String test =param.get("content").toString();
		String temp=test;
		List<String> ls = new ArrayList<String>();
		List<String> compare = new ArrayList<String>();
		osSetting(OS);
		try{
			String rsq = param.get("dataurl").toString(); // json�쓽 dataurl瑜� String���엯�쑝濡� 蹂��솚
			String type = rsq.substring(rsq.indexOf("/")+1,rsq.indexOf(";")); // dataurl�쓽 �솗�옣�옄 ���엯�쓣 遺꾨━
			uploadPath = createUploadPath(type);
			System.out.println(uploadPath);
			byte[] imagedata = DatatypeConverter.parseBase64Binary(rsq.substring(rsq.indexOf(",") + 1)); // dataurl�쓽 data�쁺�뿭�쓣 byte���엯�쑝濡� 蹂��솚
			UUID uid = UUID.randomUUID(); // �엫�쓽�쓽 ID瑜� 遺��뿬
			Path path = Paths.get(PATH+uploadPath+uid+"."+type); // �뙆�씪�쓣 ���옣 �븷 �쐞移� 諛� �뙆�씪 �씠由꾩쓣 吏��젙
			
			if(param.get("content").toString().indexOf("@")!=-1 || param.get("content").toString().indexOf("#")!=-1){
				test =param.get("content").toString();
				test = test.replaceAll("@", " @").replaceAll("#"," #").replaceFirst(" ","");
				String[] test2 = test.split(" ");
				temp=test;
 				for(String s:test2){
					if(s.indexOf("@")!=-1 || s.indexOf("#")!=-1){
						if(!find(compare,s)){
							System.out.println(s);
							System.out.println(find(compare,s));
							if(s.indexOf("@")!=-1 ){
								temp = temp.replaceAll(s, "<a href=/"+s.substring(1)+">"+s+" </a>");
								ls.add(s.substring(1));
								compare.add(s);
							}else
								temp = temp.replaceAll(s,"<a href=/hash/"+URLEncoder.encode(s,"UTF-8")+">"+s+" </a>");
								compare.add(s);
						}
					}
				}
			}
			

			BoardVO vo =new BoardVO();
			vo.setUser_id((String) session.getAttribute("user_id")); // 濡쒓렇�씤 �맂 �븘�씠�뵒濡� 湲� �옉�꽦
			vo.setContent(temp); // 湲� �궡�슜�쓣 ���옣
			vo.setMedia(URLPATH+uploadPath+uid+"."+type); // �뙆�씪�씠 ���옣�맂 �쐞移� ���옣
			vo.setMedia_type(param.get("type").toString());
			vo.setMedia_thumnail(URLPATH+"video_thumnail//"+uid+".png");
			if(param.get("atag")!= EMPTY){
				vo.setTag(param.get("atag").toString()); // �궗�엺 �깭洹몃�� �븯���쓣 寃쎌슦 ���옣
			}
				bs.BoardUP(vo);
				Files.write(path, imagedata); // path�뿉 �벐�뿬吏� 媛믪쓣 �넗���룄 �뙆�씪 �옉�꽦
			
			if(compareToDataType(type)){
				FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(PATH+uploadPath+uid+"."+type); // �룞�쁺�긽�쓣 �봽�젅�엫�떒�쐞濡� �옄由�..
				System.out.println(PATH+uploadPath+uid+"."+type);
				grabber.start();    
				Java2DFrameConverter paintConverter = new Java2DFrameConverter(); // Frame -> bufferedImage濡� 蹂��솚�븯湲� �쐞�븳 而⑤쾭�꽣 �깮�꽦
				ImageIO.write(resizeImage(paintConverter.getBufferedImage(grabber.grabImage(),1),295,295),"png", new File(destination+uid+".png")); // bufferedImage瑜� �씠誘몄�濡� ���옣
				grabber.stop();  // for臾몄씠 �뾾�쑝誘�濡� �빐�떦 �룞�쁺�긽�쓽 泥ロ봽�젅�엫�쓣 �씠誘몄�濡� 媛��졇�샂
			}else{
				BufferedImage buf = ImageIO.read(new File(PATH+uploadPath+uid+"."+type));
				ImageIO.write(resizeImage(buf,200,200), "png",new File(destination+uid+".png"));
			}
			int board_num = bs.getBoardNum(vo);
			if(param.get("content").toString().indexOf("@")!=-1){
				NoticeVO nvo = new NoticeVO();
				nvo.setMedia(URLPATH+uploadPath+uid+"."+type);
				nvo.setBoard_num(board_num);
				nvo.setOther_id((String) session.getAttribute("user_id"));
				nvo.setFlag(4);
				nvo.setRefer_content(temp);
				nvo.setUser_id2(ls);
				ns.insertFromUpload(nvo);
			}
			return "true";
		}catch(Exception e){
			e.printStackTrace();
			return e.toString();
		}
	}
	public static boolean find(List<String> buf,String idx){
		boolean flag = false;
		
		for(String s:buf){
			if(s.equals(idx))
				flag = true;
		}
		
		return flag;
	}
	public static void osSetting(String OS){ // OS 踰꾩졏蹂꾨줈 寃쎈줈 蹂�寃� 諛� �뵒�젆�넗由ш� �뾾�쓣寃쎌슦 �뵒�젆�넗由ъ깮�꽦
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
	public static String createUploadPath(String type){ // upload�뤃�뜑 �궡遺��뿉 ���옣�맆 �쐞移� 寃곗젙
		String path = "image/";
		for (String d : VIDEOTYPE){
			if(type.toLowerCase().equals(d))
				path = "video/";
		}
		return path;
	}
	
	public static boolean compareToDataType(String type){ // �쁽�옱 �뾽濡쒕뱶 �릺怨� �엳�뒗 �뜲�씠�꽣媛� �룞�쁺�긽�씤吏� �븘�땶吏� 援щ퀎 �룞�쁺�긽�씠 �뱾�뼱�삤硫� true �븘�땲硫� false瑜� 由ы꽩
		boolean flag = false;
		
		for (String d : VIDEOTYPE){
			if(type.toLowerCase().equals(d))
				flag = true;
		}
		return flag;
	}
	
	// �룞�쁺�긽�뿉�꽌 �씫�뼱�삩 �씠誘몄�瑜� resizing
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
		if(w>h)
			return Scalr.rotate(resizedImage, Rotation.CW_90);
		else
			return resizedImage;
		
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
