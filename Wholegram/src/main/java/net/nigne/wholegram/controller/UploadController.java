package net.nigne.wholegram.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.service.BoardService;



@RestController
@RequestMapping("/")
public class UploadController {
	static final String PATH = "e:\\";
	@Inject
	private BoardService bs;
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView signUp(Locale locale, Model model) {
	
		return new ModelAndView("upload");
	}
	//Ajax로 파일 및 태그 업로드 
	@ResponseBody
	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	public boolean uploadajax(@RequestBody Map<String,Object> param,HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();	
		try{
			String rsq = param.get("dataurl").toString(); // json의 dataurl를 String타입으로 변환
			String type = rsq.substring(rsq.indexOf("/")+1,rsq.indexOf(";")); // dataurl의 확장자 타입을 분리
			byte[] imagedata = DatatypeConverter.parseBase64Binary(rsq.substring(rsq.indexOf(",") + 1)); // dataurl의 data영역을 byte타입으로 변환
			UUID uid = UUID.randomUUID(); // 임의의 ID를 부여
			Path path = Paths.get(PATH+uid+"."+type); // 파일을 저장 할 위치 및 파일 이름을 지정
			Files.write(path, imagedata); // path에 쓰여진 값을 토대도 파일 작성
			
			BoardVO vo =new BoardVO();
			vo.setUser_id((String) session.getAttribute("user_id")); // 로그인 된 아이디로 글 작성
			vo.setContent(param.get("content").toString()); // 글 내용을 저장
			vo.setMedia(PATH+uid+"."+type); // 파일이 저장된 위치 저장
			if(param.get("atag")!= null)
				vo.setTag(param.get("atag").toString()); // 사람 태그를 하였을 경우 저장
			bs.BoardUP(vo);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/*
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
