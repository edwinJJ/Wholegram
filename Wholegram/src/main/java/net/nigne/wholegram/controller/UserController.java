package net.nigne.wholegram.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Blob;

import net.nigne.wholegram.common.DebugStream;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.EncryptService;
import net.nigne.wholegram.service.MemberService;
import net.nigne.wholegram.service.ProfileImageService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Inject
	private MemberService service;

	@Inject
	private EncryptService encrypt;
	
	@Inject
	private ProfileImageService profileImageService;
	
	/*로그아웃*/
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("redirect:/login");
		return mav;
	}
	
	/*프로필 편집 페이지*/
	@RequestMapping(value = "/update_form", method = RequestMethod.GET)
	public ModelAndView update_form(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		MemberVO vo = service.MemInfo(user_id);
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("user_profile");
		mav.addObject("vo", vo);
		return mav;
	}
	
	/*User_Id 변경시 중복 체크*/
	@RequestMapping(value = "/id_chk/{id}", method = RequestMethod.POST)
	public ResponseEntity<Integer> id_chk(@PathVariable("id") String id, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		
		ResponseEntity<Integer> entity = null;
		
		if(!(id.equals(user_id))) {
			try{
				entity = new ResponseEntity<>(service.compareId(id), HttpStatus.OK);
			} catch(Exception e) {
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return entity = new ResponseEntity<Integer>(2, HttpStatus.OK);
		}
		return entity;
	}
	
	/*Email 변경시 중복 체크*/ 
	@RequestMapping(value = "/email_chk/{em}", method = RequestMethod.POST)
	public ResponseEntity<Integer> email_chk(@PathVariable("em") String em, HttpServletRequest request) {
		System.out.println("일로 오니?");
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		em = em + ".com";
		MemberVO vo = service.MemInfo(user_id);
		
		ResponseEntity<Integer> entity = null;
		System.out.println("email : " + vo.getEmail());
		System.out.println("em : " + em);
		if(!(vo.getEmail().equals(em))){
			try{
				entity = new ResponseEntity<>(service.compareEmail(em), HttpStatus.OK);
			} catch(Exception e) {
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return entity = new ResponseEntity<Integer>(2, HttpStatus.OK);
		}
		return entity;
	}
	
	/*프로필 전체 업데이트*/
	@RequestMapping(value = "/update_user", method = RequestMethod.POST)
	public ModelAndView update_user(MemberVO vo, Model model, HttpServletRequest request) {
		service.updateUser(vo);
		HttpSession session = request.getSession();
		session.setAttribute("user_id", vo.getUser_id());
		return new ModelAndView("redirect:/user/update_form");
	}

	/*비밀번호 변경 페이지*/
	@RequestMapping(value = "/passwd_form", method = RequestMethod.GET)
	public ModelAndView passwd_form(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		MemberVO vo = service.MemInfo(user_id);
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("user_passwd");
		mav.addObject("vo", vo);
		return mav;
	}
	
	/*이전 비밀번호 확인*/
	@RequestMapping(value = "/check_passwd/{mem_no}/{passwd}", method = RequestMethod.POST)
	public ResponseEntity<Integer> update_passwd(@PathVariable("mem_no") int mem_no, @PathVariable("passwd") String passwd) {

		passwd = encrypt.shaEncrypt(passwd);
		
		ResponseEntity<Integer> entity = null;
		try{
			String passwd_real = service.checkPasswd(mem_no);
			entity = new ResponseEntity<>(service.comparePasswd(passwd_real, passwd), HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/*비밀번호 변경*/
	@RequestMapping(value = "/change_passwd/{mem_no}/{passwd_new}", method = RequestMethod.POST)
	public ResponseEntity<String> change_passwd(@PathVariable("mem_no") int mem_no, @PathVariable("passwd_new") String passwd_new) {
		
		MemberVO vo = new MemberVO();
		vo.setMem_no(mem_no);
		vo.setPasswd(encrypt.shaEncrypt(passwd_new));
		
		ResponseEntity<String> entity = null;
		try{
			service.updatePasswd(vo);
			entity = new ResponseEntity<>("SUCCESS!!", HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/* 프로필 이미지 출력 */
	@RequestMapping(value="/getByteImage")
	public ResponseEntity<byte[]> getByteImage(HttpServletRequest request) {
		
/*		System.out.println("test");
		String str = " 1asf23";
		byte[] bt = str.getBytes();
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(bt, HttpStatus.BAD_REQUEST);
		
		return entity;*/
		
	   HttpSession session = request.getSession();
	   String user_id = (String) session.getAttribute("user_id");
	   byte[] Image = profileImageService.getProfileImage(user_id);
	   
	   final HttpHeaders headers = new HttpHeaders();
	   headers.setContentType(MediaType.IMAGE_PNG);
	   return new ResponseEntity<byte[]>(Image, headers, HttpStatus.OK);
	}
	
	/* 프로필 이미지 등록 */
	@RequestMapping(value = "/change_profile", method = RequestMethod.POST)
	public byte[] uploadFile(MultipartHttpServletRequest request) {
	   
		DebugStream.activate();
		
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
	   
        Iterator<String> itr =  request.getFileNames();
        if(itr.hasNext()) {
        	MultipartFile mpf = request.getFile(itr.next());
            System.out.println(mpf.getOriginalFilename() +" uploaded!");
            try {
                //just temporary save file info into ufile
                System.out.println("file length : " + mpf.getBytes().length);
                System.out.println("file name : " + mpf.getOriginalFilename());
                
                HashMap<String, Object> profileImage = new HashMap<String, Object>();
                profileImage.put("user_id", user_id);
                profileImage.put("ImageFile", mpf.getBytes());
                profileImageService.setProfileImage(profileImage);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {}
  
        byte[] Image = profileImageService.getProfileImage(user_id);
        return Image;
    }
}
