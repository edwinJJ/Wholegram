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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.common.Criteria;
import net.nigne.wholegram.common.DebugStream;
import net.nigne.wholegram.common.Status;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.HeartVO;
import net.nigne.wholegram.domain.PageMaker;
import net.nigne.wholegram.domain.ReplyVO;
import net.nigne.wholegram.service.BoardService;
import net.nigne.wholegram.service.HeartService;
import net.nigne.wholegram.service.HeartTableService;
import net.nigne.wholegram.service.NoticeServiceImpl;
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
	@Inject
	private NoticeServiceImpl nService;

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
			//int endNum = page * 5; 
			int startNum = ( page - 1 ) * pagePerBlock;
			
			// 게시글에 이미 좋아요를 누른 게시물 목록 추출 (DB -> Heart table)
			List<HeartVO> hList = hService.getaldyList(user_id);
			
			// home 게시글 리스트 -> 좋아요 누른/누르지않은 게시물을 구분해서 리스트를 가져옴 (BoardVO에 좋아요 '누른/안누른'을 구분하는 변수가 존재함)
			List<BoardVO> bList = bService.getList( hList, user_id, startNum, pagePerBlock );
			mav.addObject("bList", bList);

			// home 게시글 댓글 리스트 (각 게시글에 해당되는 댓글들)
			Iterator<BoardVO> biterator = bList.iterator();
			List<ReplyVO> rList = new ArrayList<ReplyVO>();
			List<ReplyVO> replyResult = new ArrayList<ReplyVO>();
			while (biterator.hasNext()) {
				BoardVO bv = new BoardVO();
				bv = biterator.next();
				rList = rService.getList(bv.getBoard_num()); // 각 번호에 해당되는 게시글의 댓글리스트를 가져옴

				Iterator<ReplyVO> riterator = rList.iterator();
				while (riterator.hasNext()) {
					ReplyVO rv = new ReplyVO();
					rv = riterator.next();
					replyResult.add(rv); // 각 번호에 해당되는 게시글의 목록을 차례로 add시킴. 
				}
			}
			mav.addObject("replyResult", replyResult);
			mav.setViewName("home");
		} else {
			mav.setViewName("login");
		}
		return mav;
	}

	// TODO - 게시물을 다 가져왔을경우 더이상 가져오지 못하게 할것 ex) return false하도록
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
	
	/* 쩍쨘횇짤쨌횗 횈채횑횂징 */
	@RequestMapping(value = "/scroll/page/{page}", method = RequestMethod.GET)
	public ModelAndView Board_Scroll_List(@PathVariable("page") int page, Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();
		DebugStream.activate();
		if (user_id != null) {
			model.addAttribute("sessionId", user_id);

			int pagePerBlock = 5;
			int endNum = page * 5 - pagePerBlock;
			System.out.println(endNum);
			// 째횚쩍횄짹횤쩔징 횑쨔횑 횁횁쩐횈쩔채쨍짝 쨈짤쨍짜 째횚쩍횄쨔째 쨍챰쨌횕 횄횩횄창
			List<HeartVO> hList = hService.getaldyList(user_id);

			// home 째횚쩍횄짹횤 쨍짰쩍쨘횈짰 -> 횁횁쩐횈쩔채 쨈짤쨍짜/쨈짤쨍짙횁철쩐횎쨘 째횚쩍횄쨔째쨩 짹쨍쨘횖횉횠쩌짯 5쨔첩횂째 째횚쩍횄쨔째쨘횓횇횒 쨍쨋횁철쨍쨌 째횚쩍횄쨔째 쨍짰쩍쨘횈짰쨍짝 째징횁짰쩔횊
			List<BoardVO> bList = bService.getList(hList, user_id, 5, endNum);

			// home 째횚쩍횄짹횤 쨈챰짹횤 쨍짰쩍쨘횈짰
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
			mav.addObject("bList", bList);
			mav.addObject("replyResult", replyResult);
			mav.setViewName("cloneHome");

		} else {
			mav.setViewName("login");
		}
		return mav;
	}
	
	/* 댓글 입력할 때*/ 
	@RequestMapping(value = "/{board_num}/{content}/{uid}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> insert(@PathVariable("uid")String uid, @PathVariable("board_num") int board_num, @PathVariable("content")String content, HttpServletRequest request, HttpServletResponse response) {
		
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
	            
	            String test = content;

	               test = test.replaceAll("@", " @").replaceAll("#"," #").replaceFirst(" ","");
	               String[] test2 = test.split(" ");
	               String temp=test;
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
	            
	            vo.setContent(temp);
	            int reply_num = rService.insert(vo);

	            if( !ls.isEmpty() ) {
	               Iterator<String> it = ls.iterator();
	               while( it.hasNext() ) {
	            	   System.out.println("www");
	                  nService.rnInsert(it.next(), board_num, temp, 5, reply_num);
	               }
	            } 
	            
	            // 접속자 ID와 게시물 작성자 ID가 다른 경우,
	            if( uid != user_id && !uid.equals( user_id ) ) {
	               // 게시물에 접속자가 댓글을 입력하면 reply, notice table에 입력
	               nService.rnInsert(user_id, board_num, temp, 3, reply_num);
	            }    
	            
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

				// Heart테이블 (추가 or 제거) & 게시물 좋아요 (증 감) 
				if(HeartTableStatus.isSuccess()) {
					hService.insertHeart(hv);
					bService.heartCount(board_num, 1);
					nService.insertNoticeHeart(user_id, board_num, 2);		// 좋아요 누름 알림 표시 띄우기
				} else {
					hService.deleteHeart(hv);
					bService.heartCount(board_num, -1);
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

	public static boolean find(List<String> buf,String idx){
        boolean flag = false;
        
        for(String s:buf){
           if(s.equals(idx))
              flag = true;
        }
        
        return flag;
     }
}
