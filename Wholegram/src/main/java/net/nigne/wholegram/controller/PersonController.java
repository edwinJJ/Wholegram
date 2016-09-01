package net.nigne.wholegram.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.FollowVO;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.BoardService;
import net.nigne.wholegram.service.FollowService;
import net.nigne.wholegram.service.MemberService;
import net.nigne.wholegram.service.NoticeService;


@RestController
@RequestMapping("/person")
public class PersonController {
	@Inject
	private BoardService bService;
	@Inject
	private MemberService mService;
	@Inject
	private FollowService fService;
	@Inject
	private NoticeService nService;
	
	/*사람찾기 (둘러보기 페이지)로 이동*/
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView Person(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();

		if (user_id != null) {
			model.addAttribute("sessionId", user_id);
			if (user_id == "admin" || "admin".equals(user_id)) {
				List<MemberVO> memberList = mService.getRandomUser(user_id);
				List<BoardVO> boardList = bService.get(memberList);
				mav.addObject("boardList", boardList);
				mav.setViewName("person");
			} else {

				/*둘러보기 페이지 내용 - 사용자가 following하지 않은 다른 user들의 게시물 랜덤으로 출력*/
				List<MemberVO> mList = mService.getNewPerson(user_id);
				List<BoardVO> bList = bService.get(mList);
				mav.addObject("bList", bList);

				/*알수도있는 사람 페이지 내용 - 사용자가 following한 user의 following user들의 게시물 랜덤으로 출력*/
				List<MemberVO> mbList = new ArrayList<MemberVO>();
				mbList = mService.getKnowablePerson(user_id);
				mav.addObject("mbList", mbList);

				List<List<BoardVO>> bdList = new ArrayList<List<BoardVO>>();
				bdList = bService.getbdList(mbList);
				mav.addObject("bdList", bdList);

				mav.setViewName("new_person");
			}
		} else {
			mav.setViewName("login");
		}
		return mav;
	}

	// 다른 user following 기능
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> followInsert(@PathVariable("uid") String uid, HttpServletRequest request,
			Model model, HttpServletResponse response) {
		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		try {

			FollowVO vo = new FollowVO();
			vo.setFollower(uid);
			vo.setFollowing(user_id);
			vo.setFlag(0);
			
			if(fService.followCheck(uid, user_id)){		// 팔로우 (한쪽이라도 팔로우를 하고있는지) 체크
				fService.followInsert(vo);				// follow 테이블에 입력
				fService.statusUpdate(uid , user_id,1); // 한쪽이 이미 팔로우를 하고있었다면 flag 1으로 update
			}else{
				fService.followInsert(vo);	
			}

			List<FollowVO> list = fService.getfwList(); // follow 테이블 데이터 가져옴

			nService.insertFollow(vo, 1); 				// notice 테이블에 입력
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			entity = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	// 다른 user following 취소 기능
	@RequestMapping(value = "/dele/{fno}/{user}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> followDelete(@PathVariable("user") String user,
			@PathVariable("fno") Integer fno, Model model, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		if (user_id != null) {
			try {
				fService.followDelete(fno, user);				// 팔로우 삭제
				
				if(fService.followCheck(user, user_id)){		// 팔로우 (한쪽이라도 팔로우를 하고있는지) 체크
					fService.statusUpdate(user , user_id,0);	// 한쪽이라도 팔로우를 하고있다면 flag 0으로 update
				}
				
				List<FollowVO> delList = fService.getfwList();	// follow 테이블 데이터 가져옴
				Map<String, Object> map = new HashMap<>();
				map.put("delList", delList);
				entity = new ResponseEntity<>(map, HttpStatus.OK);
			} catch (Exception e) {
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			try {
				response.sendRedirect("login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
}
