package net.nigne.wholegram.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.common.Criteria;
import net.nigne.wholegram.common.DebugStream;
import net.nigne.wholegram.common.Status;
import net.nigne.wholegram.common.TableService;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.HeartVO;
import net.nigne.wholegram.domain.PageMaker;
import net.nigne.wholegram.domain.ReplyVO;
import net.nigne.wholegram.service.BoardService;
import net.nigne.wholegram.service.HeartService;
import net.nigne.wholegram.service.MemberService;
import net.nigne.wholegram.service.NoticeServiceImpl;
import net.nigne.wholegram.service.ReplyService;
import net.nigne.wholegram.service.ReportService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Inject
	private BoardService bService;
	@Inject
	private ReplyService rService;
	@Inject
	private HeartService hService;
	@Inject
	private TableService tService;
	@Inject
	private NoticeServiceImpl nService;
	@Inject
	private MemberService mService;
	@Inject
	private ReportService rptService;
	
	/* 처음 게시물 리스트 보여줄 때*/ 
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView Board_List(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();
		
		if (user_id != null) {
			model.addAttribute( "sessionId", user_id );
			
			PageMaker pm = new PageMaker();
			int page = pm.getPage();
			if( page == 0 ) {
				page = 1;
			}
			
			int pagePerBlock = 5;
			int startNum = ( page - 1 ) * pagePerBlock;
			
			List<HeartVO> hList = hService.getaldyList(user_id); 								// 좋아요를 누른 게시물 목록 추출 (DB -> Heart table)
			
			List<BoardVO> bList = bService.getList( hList, user_id, startNum, pagePerBlock ); 	// home 게시글 리스트 -> 좋아요 누른/누르지않은 게시물을 구분해서 리스트를 가져옴 (BoardVO에 좋아요 '누른/안누른'을 구분하는 변수가 존재함)

			/* home 게시글 댓글 리스트 (각 게시글에 해당되는 댓글들) */
			Iterator<BoardVO> biterator = bList.iterator();
			List<ReplyVO> rList = new ArrayList<ReplyVO>();
			List<ReplyVO> replyResult = new ArrayList<ReplyVO>();
			while (biterator.hasNext()) {
				BoardVO bv = new BoardVO();
				bv = biterator.next();
				rList = rService.getList(bv.getBoard_num()); 									// 각 번호에 해당되는 게시글의 댓글리스트를 가져옴

				Iterator<ReplyVO> riterator = rList.iterator();
				while (riterator.hasNext()) {
					ReplyVO rv = new ReplyVO();
					rv = riterator.next();
					replyResult.add(rv); 														// 각 번호에 해당되는 게시글의 목록을 차례로 add시킴. 
				}
			}
			
			mav.addObject("bList", bList);
			mav.addObject("replyResult", replyResult);
			mav.setViewName("home");
		} else {
			mav.setViewName("login");
		}
		return mav;
	}

	/* user화면에서 스크롤시 게시물 가져옴 */
	@RequestMapping( value = "scroll/{count}", method = RequestMethod.POST )
	public ResponseEntity<Map<String, Object>> scrollList( @PathVariable( "count") Integer cnt,HttpServletResponse response, HttpServletRequest request ) {
		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		
		if ((session.getAttribute("user_id")) != null && !(session.getAttribute("user_id").equals(""))) {
			try {
				Criteria cr = new Criteria(cnt);
				cr.setItem((String) session.getAttribute("user_id"));
				List<BoardVO> list = bService.getScrollList(cr);
				Map<String, Object> map = new HashMap<>();
				map.put("list", list);
				if(bService.getUserCount(cr)<cr.getEnd()){
					map.put("flag", false);
					
				}else{
					map.put("flag", true);
				}
				entity = new ResponseEntity<>( map, HttpStatus.OK);
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
	
	@RequestMapping( value = "scroll/{user_id}/{count}", method = RequestMethod.POST )
	public ResponseEntity<Map<String, Object>> scrollOtherList( @PathVariable( "user_id") String user,@PathVariable( "count") Integer cnt,HttpServletResponse response, HttpServletRequest request ) {
		ResponseEntity<Map<String, Object>> entity = null;
			try {
				Criteria cr = new Criteria(cnt);
				cr.setItem(user);
				List<BoardVO> list = bService.getScrollList(cr);
				Map<String, Object> map = new HashMap<>();
				map.put("list", list);
				if(bService.getUserCount(cr)<cr.getEnd()){
					map.put("flag", false);
					
				}else{
					map.put("flag", true);
				}
				entity = new ResponseEntity<>( map, HttpStatus.OK);
			} catch (Exception e) {
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		
		return entity;
	}
	
	/* Home에서 게시물 5개 이상이면 스크롤링으로 5개 더 보여주기 */
	@RequestMapping(value = "/scroll/page/{page}", method = RequestMethod.POST)
	public ModelAndView Board_Scroll_List(@PathVariable("page") int page, Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();
		DebugStream.activate();
		if (user_id != null) {
			model.addAttribute("sessionId", user_id);

			int pagePerBlock = 5;
			int endNum = page * 5 - pagePerBlock;
			
			List<HeartVO> hList = hService.getaldyList(user_id);				// 좋아요를 누른 게시물 목록 추출 (DB -> Heart table)

			
			List<BoardVO> bList = bService.getList(hList, user_id, 5, endNum);  // home 게시글 리스트 -> 좋아요 누른/누르지않은 게시물을 구분해서 리스트를 가져옴 (BoardVO에 좋아요 '누른/안누른'을 구분하는 변수가 존재함)

			/* home 게시글 댓글 리스트 (각 게시글에 해당되는 댓글들) */
			Iterator<BoardVO> biterator = bList.iterator();
			List<ReplyVO> rList = new ArrayList<ReplyVO>();
			List<ReplyVO> replyResult = new ArrayList<ReplyVO>();
			while (biterator.hasNext()) {
				BoardVO bv = new BoardVO();
				bv = biterator.next();
				rList = rService.getList(bv.getBoard_num());

				Iterator<ReplyVO> riterator = rList.iterator();
				while (riterator.hasNext()) {
					ReplyVO rv = new ReplyVO();
					rv = riterator.next();
					replyResult.add(rv);										// 각 번호에 해당되는 게시글의 목록을 차례로 add시킴. 
				}
			}
			mav.addObject("bList", bList);
			mav.addObject("replyResult", replyResult);
			mav.setViewName("cloneHome");

		} else {
			mav.setViewName("login");
		}
		return mav;
	}
	
	/* 댓글 입력할 때*/ 
	@ResponseBody
	@RequestMapping(value = "/{board_num}/{content}/{uid}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Map<String, Object> param,
			@PathVariable("uid") String uid, @PathVariable("board_num") int board_num,
			@PathVariable("content") String content, HttpServletRequest request, HttpServletResponse response) {

		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");

		if (user_id != null) {
			List<String> compare = new ArrayList<String>();
			List<String> ls = new ArrayList<String>();

			try {
				// 댓글 입력 (reply table)
				ReplyVO vo = new ReplyVO();
				vo.setBoard_num(board_num);
				vo.setUser_id(user_id);

				String test = param.get("content").toString();

				test = test.replaceAll("@", " @").replaceAll("#", " #").replaceFirst(" ", "");
				String[] test2 = test.split(" ");
				String temp = test;
				for (String s : test2) {
					if (s.indexOf("@") != -1 || s.indexOf("#") != -1) {
						if (!find(compare, s)) {
							System.out.println(s);
							System.out.println(find(compare, s));
							if (s.indexOf("@") != -1) {
								if (mService.compareId(s.substring(1)) == 1) {
									temp = temp.replaceAll(s, "<a href=/" + s.substring(1) + ">" + s + " </a>");
									ls.add(s.substring(1));
									compare.add(s);
								}
							} else {
								if (!s.equals("#")) {
									temp = temp.replaceAll(s,
											"<a href=/hash/" + URLEncoder.encode(s, "UTF-8") + ">" + s + " </a>");
									compare.add(s);
								}
							}
						}
					}
				}

				vo.setContent(temp.replaceAll("&63", "&#63").replaceAll("&37", "&#37").replace("&46", "&#46")
						.replace("&92", "&#92").replace("&47", "&#47"));
				int reply_num = rService.insert(vo);

				String user = null;

				if (!ls.isEmpty()) {
					Iterator<String> it = ls.iterator();
					while (it.hasNext()) {
						user = it.next();
						// 댓글에서 언급 시에 작성자와 언급되는 사용자가 다른 경우에만 Notice에 추가
						if (user != user_id && !user.equals(user_id)) {
							nService.rnInsert(user_id, user, board_num, temp, 5, reply_num);
						}
					}
				}

				// 접속자 ID와 게시물 작성자 ID가 다른 경우,
				if (uid != user_id && !uid.equals(user_id)) {
					// 게시물에 접속자가 댓글을 입력하면 Reply, Notice table에 입력
					nService.rnInsert(user_id, user, board_num, temp, 3, reply_num);
				}

				List<ReplyVO> list = rService.getList(board_num);
				Map<String, Object> map = new HashMap<>();
				map.put("result", list);

				entity = new ResponseEntity<>(map, HttpStatus.OK);
			} catch (Exception e) {
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				e.printStackTrace();
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

	/* 댓글 삭제할 때 */
	@RequestMapping( value = "/{board_num}/{reply_num}", method = RequestMethod.DELETE )
	public ResponseEntity<Map<String, Object>> Reply_delete( @PathVariable( "board_num") Integer board_num, @PathVariable( "reply_num") Integer reply_num, HttpServletResponse response, HttpServletRequest request ) {
		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		if ((session.getAttribute("user_id")) != null && !(session.getAttribute("user_id").equals(""))) {
			try {
				nService.rnDelete( reply_num ); 
				rService.delete( reply_num );

				
				List<ReplyVO> delList = rService.getList( board_num );
				Map<String, Object> map = new HashMap<>();
				map.put("delList", delList);
				entity = new ResponseEntity<>( map, HttpStatus.OK);
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
	
	/* 게시물 좋아요 증감 */
	@RequestMapping( value = "/heart/{board_num}", method = RequestMethod.POST )
	public ResponseEntity<Integer> insertHeart( @PathVariable( "board_num" ) int board_num,  HttpServletResponse response, HttpServletRequest request ) {
		
		ResponseEntity<Integer> entity = null;
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		
		if (user_id != null) {
			try {
				
				// heart 테이블에 추가할지 결정 (좋아요 증/감 여부 결정)
				Status TableStatus = tService.TableStatus(hService.checkHeart(user_id, board_num));
				HeartVO hv = new HeartVO();
				hv.setBoard_num(board_num);
				hv.setUser_id(user_id);

				// Heart테이블 (추가 or 제거) & 게시물 좋아요 (증 감) 
				if(TableStatus.isSuccess()) {								// 게시물 좋아요 가능
					hService.insertHeart(hv);								// HeartTable에 등록
					bService.heartCount(board_num, 1);						// 게시물 좋아요수 + 1
					nService.insertNoticeHeart(user_id, board_num, 2);		// 좋아요 누름 알림 표시 띄우기
					
				} else {													// 게시물 좋아요 불가능 (이미 누 른상태일 경우)
					hService.deleteHeart(hv);								// HeartTable에서 제거
					bService.heartCount(board_num, -1);						// 게시물 좋아요수 - 1
					nService.deleteNoticeHeart(user_id, board_num);			// 좋아요 누름 알림 표시 지우기
				}
				int heart = bService.getHeart(board_num);
				entity = new ResponseEntity<>( heart, HttpStatus.OK );
			} catch (Exception e) {
				entity = new ResponseEntity<>( HttpStatus.BAD_REQUEST );
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
	
	
	/* 알림에 나온 내용 상세보기 */
	@RequestMapping(value = "/{board_num}", method = RequestMethod.GET)
	public ModelAndView detailPage(@PathVariable("board_num") int board_num, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();

		model.addAttribute("sessionId", user_id);

		if (user_id != null) {
			BoardVO bdList = bService.boardList(board_num);				// 번호에 해당되는 게시물에대한 정보
			
			List<HeartVO> hList = hService.getaldyList(user_id); 		// 좋아요를 누른 게시물 목록 추출 (DB -> Heart table)
			bdList = bService.getOneList( hList, bdList);  				// 지금 볼 게시글이 좋아요 누를 누른 게시물인지 확인
			
			List<ReplyVO> rList = new ArrayList<ReplyVO>();
			rList = rService.getList(bdList.getBoard_num());			// 게시물에대한 댓글리스트를 가져옴
			
			mav.addObject("bdList", bdList);
			mav.addObject("replyResult", rList);
			mav.setViewName("detail");
		} else {
			mav.setViewName("login");
		}
		return mav;
	}
	
	/* 게시물 신고 카운트 증가 */
	@RequestMapping(value = "/report/{board_num}", method = RequestMethod.POST)
	public ResponseEntity<String> insertReport(@PathVariable("board_num") int board_num, HttpServletResponse response, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");

		ResponseEntity<String> entity = null;
		
		if (user_id != null) {
			
			try {
				Status TableStatus = tService.TableStatus(rptService.checkReport(user_id, board_num)); // 게시물 신고 등록 or 삭제 여부 결정
				
				/*report테이블 (추가 or 제거) & 게시물 신고수 (증 감) */
				if(TableStatus.isSuccess()) {					// 게시물 신고 할 때
					bService.report(user_id, board_num);		// 게시물 신고 Notice테이블에 등록
					bService.reportCount(board_num);			// 게시물 신고 카운트 증가
					entity = new ResponseEntity<>( "INCREASE", HttpStatus.OK );
					
				} else {										// 이미 신고한 게시물일 때
					bService.reportDelete(user_id, board_num);	// 게시물 신고 Notice테이블에서 제거
					bService.reportDecrease(board_num);			// 게시물 신고 카운트 감소
					entity = new ResponseEntity<>( "DECREASE", HttpStatus.OK );
				}
			} catch (Exception e) {
				e.printStackTrace();
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

	/* 게시물 삭제 */
	@RequestMapping(value = "/{board_num}", method = RequestMethod.DELETE)
	public ResponseEntity<String> Board_delete(@PathVariable("board_num") Integer board_num,
			HttpServletResponse response, HttpServletRequest request) {
		ResponseEntity<String> entity = null;
		HttpSession session = request.getSession();
		if ((session.getAttribute("user_id")) != null && !(session.getAttribute("user_id").equals(""))) {
			try {
				bService.deleteAll(board_num);
				entity = new ResponseEntity<>("1", HttpStatus.OK);
			} catch (Exception e) {
				entity = new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
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
	   
	public static boolean find(List<String> buf,String idx){
        boolean flag = false;
        
        for(String s:buf){
           if(s.equals(idx))
              flag = true;
        }
        return flag;
     }
}
