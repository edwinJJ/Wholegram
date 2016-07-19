package net.nigne.wholegram.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.common.Status;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.HeartVO;
import net.nigne.wholegram.domain.ReplyVO;
import net.nigne.wholegram.service.BoardService;
import net.nigne.wholegram.service.HeartService;
import net.nigne.wholegram.service.HeartTableService;
import net.nigne.wholegram.service.ReplyService;

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
	private HeartTableService htService;

	/* 처음 게시물 리스트 보여줄 때*/ 
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView Board_List(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();
		
		if (user_id != null) {
			model.addAttribute( "sessionId", user_id );
			
			// 게시글에 이미 좋아요를 누른 게시물 목록 추출 
			List<HeartVO> hList = hService.getaldyList(user_id);
			
			// home 게시글 리스트 -> 좋아요 누른/누르지않은 게시물을 구분해서 리스트를 가져옴 
			List<BoardVO> bList = bService.getList(hList);
			mav.addObject("bList", bList);

			// home 게시글 댓글 리스트 
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
					replyResult.add(rv);
				}
			}
			mav.addObject("replyResult", replyResult);
			mav.setViewName("home");
		} else {
			mav.setViewName("login");
		}
		return mav;
	}

	/* 댓글 입력할 때*/ 
	@RequestMapping(value = "/{board_num}/{content}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> insert(@PathVariable("board_num") int board_num, @PathVariable("content")String content, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		
		if (user_id != null) {
			try {
				ReplyVO vo = new ReplyVO();
				vo.setBoard_num(board_num);
				vo.setUser_id(user_id);
				vo.setContent(content);
				rService.insert(vo);

				List<ReplyVO> list = rService.getList( board_num );
				Map<String, Object> map = new HashMap<>();
				map.put("result", list);
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

	/* 댓글 삭제할 때 */
	@RequestMapping( value = "/{board_num}/{reply_num}", method = RequestMethod.DELETE )
	public ResponseEntity<Map<String, Object>> Reply_delete( @PathVariable( "board_num") Integer board_num, @PathVariable( "reply_num") Integer reply_num, HttpServletResponse response, HttpServletRequest request ) {
		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		if ((session.getAttribute("user_id")) != null && !(session.getAttribute("user_id").equals(""))) {
			try {
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
	@RequestMapping( value = "/heart/{board_num}", method = RequestMethod.GET )
	public ResponseEntity<Integer> insertHeart( @PathVariable( "board_num" ) int board_num,  HttpServletResponse response, HttpServletRequest request ) {
		
		ResponseEntity<Integer> entity = null;
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		
		if (user_id != null) {
			try {
				
				// heart 테이블에 추가할지 결정 (좋아요 증/감 여부 결정)
				Status HeartTableStatus = htService.HeartTableStatus(hService.checkHeart(user_id, board_num));
				HeartVO hv = new HeartVO();
				hv.setBoard_num(board_num);
				hv.setUser_id(user_id);

				// Heart테이블 추가 or 제거 & 게시물 좋아요 증감
				if(HeartTableStatus.isSuccess()) {
					hService.insertHeart(hv);
					bService.heartCount(board_num, 1);
				} else {
					hService.deleteHeart(hv);
					bService.heartCount(board_num, -1);
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

}
