package net.nigne.wholegram.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.common.DebugStream;
import net.nigne.wholegram.common.Encrypt;
import net.nigne.wholegram.common.RepCriteria;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.FollowVO;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.domain.NoticeVO;
import net.nigne.wholegram.service.BoardService;
import net.nigne.wholegram.service.FollowService;
import net.nigne.wholegram.service.MemberService;
import net.nigne.wholegram.service.NoticeService;
import net.nigne.wholegram.service.ProfileImageService;
import net.nigne.wholegram.service.ReplyService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Inject
	private MemberService service;

	@Inject
	private BoardService bservice;
	
	@Inject
	private ReplyService rService;
	
	@Inject
	private Encrypt encrypt;
	
	@Inject
	private ProfileImageService profileImageService;
	
	@Inject
	private NoticeService nservice;
	
	@Inject
	private FollowService fservice;
	
	/*로그아웃*/
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("redirect:/login");
		return mav;
	}
	
	// 팔로우 등록
	@RequestMapping( value = "/{uid}", method = RequestMethod.GET )
	public ResponseEntity<Boolean> followInsert( @PathVariable("uid") String uid, HttpServletRequest request, Model model, HttpServletResponse response ) {
		ResponseEntity<Boolean> entity = null;
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		try {

			FollowVO vo = new FollowVO();
			vo.setFollower( uid );
			vo.setFollowing( user_id );
			vo.setFlag(0);							// follow 테이블에 입력
			if(fservice.followCheck(uid, user_id)){
				fservice.followInsert(vo);	
				fservice.statusUpdate(uid , user_id,1);
			}else{
				fservice.followInsert(vo);	
			}
			nservice.insertFollow(vo, 1);								// notice 테이블에 입력
			entity = new ResponseEntity<>( true, HttpStatus.OK);
		} catch( Exception e ) {
			entity = new ResponseEntity<>(false, HttpStatus.BAD_REQUEST );
			System.out.println(e.toString());
			}
		return entity;
	}
	// 팔로우 삭제
		@RequestMapping( value = "/{uid}/{uid2}", method = RequestMethod.GET )
		public ResponseEntity<Boolean> followDelete( @PathVariable("uid") String uid,@PathVariable("uid2") String uid2, HttpServletRequest request, Model model, HttpServletResponse response ) {
			ResponseEntity<Boolean> entity = null;
			HttpSession session = request.getSession();
			String user_id = (String)session.getAttribute("user_id");
			try {
				FollowVO vo = new FollowVO();
				vo.setFollower( uid );
				vo.setFollowing( user_id );
				fservice.userfollowDelete(vo);
				if(fservice.followCheck(uid, user_id)){
					fservice.statusUpdate(uid , user_id,0);
				}
				NoticeVO nvo = new NoticeVO();
				nvo.setUser_id(user_id);
				nvo.setOther_id(uid);
				nvo.setFlag(1);
				nservice.followDelete(nvo);
				entity = new ResponseEntity<>( true, HttpStatus.OK);
			} catch( Exception e ) {
				entity = new ResponseEntity<>( false,HttpStatus.BAD_REQUEST );
				System.out.println(e.toString());
			}
			return entity;
		}
	// 팔로워 목록 보여주기
	@RequestMapping(value = "/getFollower/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getFollower(@PathVariable("id") String id,Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ResponseEntity<Map<String, Object>> entity = null;
		List<FollowVO> ls = fservice.getMyFollwerList(id);
		Map<String,Object> map = new HashMap<>();
		map.put("list", ls);
		try{
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//팔로잉 목록 보여주기
	@RequestMapping(value = "/getFollowing/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getFollowing(@PathVariable("id") String id,Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ResponseEntity<Map<String, Object>> entity = null;
		List<FollowVO> ls = fservice.getMyFollwingList(id);
		Map<String,Object> map = new HashMap<>();
		map.put("list", ls);
		try{
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/*프로필 편집 페이지*/
	@RequestMapping(value = "/update_form", method = RequestMethod.GET)
	public ModelAndView update_form(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");

		model.addAttribute( "sessionId", user_id );
		
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
		
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		em = em + ".com";
		MemberVO vo = service.MemInfo(user_id);
		
		ResponseEntity<Integer> entity = null;
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
	
	/*유저 페이지에서 게시물당 댓글 스크롤링 처리*/
	@RequestMapping(value = "/getNum/{no}/{rep_idx}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getNum(@PathVariable("no") int board_num,@PathVariable("rep_idx") int rep_idx) {
		ResponseEntity<Map<String, Object>> entity = null;
		BoardVO vo = new BoardVO();
		vo.setBoard_num(board_num);
		RepCriteria rc = new RepCriteria(board_num,rep_idx);
		Map<String, Object> map = new HashMap<>();
		map.put("bd", bservice.getOne(vo));
		map.put("rp",rService.getListLimit(rc));
		try{
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/*비밀번호 변경 페이지*/
	@RequestMapping(value = "/passwd_form", method = RequestMethod.GET)
	public ModelAndView passwd_form(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");

		model.addAttribute( "sessionId", user_id );
		
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
	
	/* 알림(상대방) 프로필 이미지 출력 */
	@RequestMapping(value="/getByteImage/{user_id}")
	public ResponseEntity<byte[]> getByteImageNotice(@PathVariable("user_id") String user_id) {
		
	   byte[] Image = profileImageService.getProfileImage(user_id);				 // 프로필 이미지 추출		
	   HttpHeaders headers = new HttpHeaders();
	   headers.setContentType(MediaType.IMAGE_PNG);
	   return new ResponseEntity<byte[]>(Image, headers, HttpStatus.OK);
	}
	
	/* 프로필 이미지 출력 */
	@RequestMapping(value="/getByteImage")
	public ResponseEntity<byte[]> getByteImage(HttpServletRequest request) {
		
	   HttpSession session = request.getSession();
	   String user_id = (String) session.getAttribute("user_id");
	   
	   byte[] Image = profileImageService.getProfileImage(user_id);				 // 프로필 이미지 추출		
	   HttpHeaders headers = new HttpHeaders();
	   headers.setContentType(MediaType.IMAGE_PNG);
	   return new ResponseEntity<byte[]>(Image, headers, HttpStatus.OK);
	}
	
	/* 프로필 이미지 등록 */
	@RequestMapping(value = "/change_profile", method = RequestMethod.POST)
	public ResponseEntity<Object> uploadFile(MultipartHttpServletRequest request) {

		DebugStream.activate(); // 디버그.. 에러난곳 위치 찾아줌

		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		
		ResponseEntity<Object> entity = null;
		byte[] resizeFile = null;
		
		Iterator<String> itr = request.getFileNames();									
		if (itr.hasNext()) {
			
			MultipartFile mpf = request.getFile(itr.next());						// 파일 추출
			if(mpf.getSize() > 5000000) {											// 10M 이상일 경우 업로드 거부
				return null;
			}
			resizeFile = service.reSizeProfileImg(mpf);
			
			HashMap<String, Object> profileImage = new HashMap<String, Object>();	// 파일 정보 Map에 담아둠
			profileImage.put("user_id", user_id);
			profileImage.put("ImageFile", resizeFile);								// 파일 용량 resize 후 저장
			profileImageService.setProfileImage(profileImage);						// 파일을 유저의 프로필로 저장
		} else {}
		
		entity = new ResponseEntity<Object>("SUCCESS", HttpStatus.OK);
		return entity;
	}

	
	/* 프로필 이미지 -> 기본 이미지로 변경 */
	@RequestMapping(value = "/change_default_profile", method = RequestMethod.POST)
	public ResponseEntity<String> change_default_profile(HttpServletRequest request) {
		
		DebugStream.activate(); // 디버그.. 에러난곳 위치 찾아줌

		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		
		ResponseEntity<String> entity = null;
		
		try{
			service.setDefaultProfileImage(user_id);
			entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			System.out.println(e.toString());
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
	/* 실시간으로(60초) 알림 확인 */
	@RequestMapping(value = "/checkNotice", method = RequestMethod.POST)
	public ResponseEntity<List<NoticeVO>> checkNotice(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		ResponseEntity<List<NoticeVO>> entity = null;
		try{
			entity = new ResponseEntity<List<NoticeVO>>(nservice.checkNotice(user_id), HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<List<NoticeVO>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/* 알림 표시 제거 */
	@RequestMapping(value = "/check_Notice_indicate/{notice_num}", method = RequestMethod.POST)
	public ResponseEntity<String> check_Notice_indicate(@PathVariable ("notice_num") int notice_num) {

		ResponseEntity<String> entity = null;
		try{
			nservice.RemoveNotice(notice_num);
			entity = new ResponseEntity<String>("SUCCESS!!", HttpStatus.OK);
		}catch (Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
}
