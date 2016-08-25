package net.nigne.wholegram.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.ReplyVO;
import net.nigne.wholegram.domain.ReportVO;
import net.nigne.wholegram.service.BoardService;
import net.nigne.wholegram.service.HeartService;
import net.nigne.wholegram.service.NoticeServiceImpl;
import net.nigne.wholegram.service.ReplyService;
import net.nigne.wholegram.service.ReportService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Inject
	private BoardService bService;
	
	@Inject
	private ReportService rService;
	
	@Inject
	private ReplyService rpService;
	  
	// 신고된 계시물 출력
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView adminMain(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();
		model.addAttribute( "sessionId", user_id );

		if(user_id != null) {
			List<BoardVO> bList = bService.getReportList();			
			mav.addObject("bList", bList);
			
			// 해당 게시물 신고자 리스트
			Iterator<BoardVO> iterator = bList.iterator();
			List<ReportVO> rpList = new ArrayList<ReportVO>();
			List<ReportVO> rResult = new ArrayList<ReportVO>();
			while (iterator.hasNext()) {
				BoardVO bv = new BoardVO();
				bv = iterator.next();
				rpList = rService.getUserList(bv.getBoard_num()); 
			
				Iterator<ReportVO> riterator = rpList.iterator();
				while (riterator.hasNext()) {
					ReportVO rv = new ReportVO();
					rv = riterator.next();
					rResult.add(rv);
				}
			}
			mav.addObject("rpList", rResult);
			mav.setViewName("admin");	
		} else {
			mav.setViewName("login");
		}
		return mav;
	}
	
	// 신고된 게시물 삭제
	@RequestMapping( value = "/delete/{board_num}", method = RequestMethod.DELETE )
	public ResponseEntity<Map<String, Object>> deleteAll( @PathVariable( "board_num") Integer board_num, HttpServletResponse response, HttpServletRequest request ) {
		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		if ((session.getAttribute("user_id")) != null && !(session.getAttribute("user_id").equals(""))) {
			try {
				System.out.println("1");
				bService.deleteAll(board_num);
				System.out.println("2");
				List<BoardVO> boardList = bService.getReportList();
				System.out.println("3");
				Map<String, Object> map = new HashMap<>();
				map.put("boardList", boardList);
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
	
	// 미리보기 썸네일 클릭 시 해당 게시물 상세페이지로 이동
	@RequestMapping(value = "/{board_num}", method = RequestMethod.GET)
	public ModelAndView detailPage(@PathVariable( "board_num" ) int board_num, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();
		model.addAttribute( "sessionId", user_id );

		if(user_id != null) {
			BoardVO bdList = bService.boardList(board_num);		// 번호에 해당되는 게시물에대한 정보
			
			List<ReplyVO> rList = new ArrayList<ReplyVO>();
			rList = rpService.getList(bdList.getBoard_num());	// 게시물에대한 댓글리스트를 가져옴
			
			mav.addObject("bdList", bdList);
			mav.addObject("replyResult", rList);
			mav.setViewName("detail");	
		} else {
			mav.setViewName("login");
		}
		return mav;
	}
}
