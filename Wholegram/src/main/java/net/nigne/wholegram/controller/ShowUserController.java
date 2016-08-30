package net.nigne.wholegram.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import net.nigne.wholegram.common.Criteria;
import net.nigne.wholegram.common.DebugStream;
import net.nigne.wholegram.common.HashTagScrollCriteria;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.BoardService;
import net.nigne.wholegram.service.FollowService;
import net.nigne.wholegram.service.MemberService;

@RestController
@RequestMapping("/")
public class ShowUserController {
	
	@Inject 
	private MemberService service;
	@Inject 
	private BoardService bdservice;
	@Inject
	private FollowService fservice;
	
	private static final String NULL = "";
	
	//검색창에서 아이디를 입력시에 동작
	@RequestMapping(value = "{user_id}", method = RequestMethod.GET)
	   public ModelAndView searchText(@PathVariable("user_id")String idx,Locale locale, Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		MemberVO vo = new MemberVO();
		vo = service.MemInfo(idx);  
		if(!"favicon".equals(idx) && vo != null){
	      System.out.println(vo);
	      List<BoardVO> list = bdservice.getUserLimitList(vo);
	      ModelAndView mav = new ModelAndView();
	      Criteria cr = new Criteria();
	      cr.setItem(idx);
	      int numberOfBoard = bdservice.getUserCount(cr);
	      Map<String, Integer> numberOfFollow= fservice.getFollowNumberof(idx);;  
	      mav.setViewName("user");
	      mav.addObject("followCheck", fservice.followCheck(user_id, vo.getUser_id()));
	      mav.addObject("sessionId",user_id);
	      mav.addObject("vo", vo);
	      mav.addObject("list", list);
	      mav.addObject("numberOfBoard", numberOfBoard);			// 유저가 올린 게시물 개수
		  mav.addObject("numberOfFollow", numberOfFollow);		// 유저가 팔로잉 / 유저를 팔로우 있는 수
	      return mav;
	      }else if(!"favicon".equals(idx) && vo == null){
	    	  return null;
	      }else{
	    	  return null;
	      }
	   }
	
	//해시태그 검색후 스크롤링과 관련
	@RequestMapping( value = "scroll/hash/{text}/{count}", method = RequestMethod.POST )
	public ResponseEntity<Map<String, Object>> scrollList( @PathVariable("count") Integer cnt,@PathVariable("text") String index,HttpServletResponse response, HttpServletRequest request ) {
		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		if ((session.getAttribute("user_id")) != null && !(session.getAttribute("user_id").equals(""))) {
			try {
				String in[] = index.replaceAll("\\+","").split("#");
				List<String> tag = new ArrayList<>();
				for(String s : in){
					System.out.println(s);
					tag.add("#"+s.trim());
				}
				HashTagScrollCriteria htsc = new HashTagScrollCriteria();
				htsc.setTag(tag);
				htsc.setStart(cnt);
				htsc.setEnd(cnt+9);
				List<BoardVO> list = bdservice.SearchScrollIterate(htsc);
				Map<String, Object> map = new HashMap<>();
				map.put("list", list);
				System.out.println(bdservice.searchCount(htsc.getTag()));
				if(bdservice.searchCount(htsc.getTag())<htsc.getEnd()){
					map.put("flag", false);
					
				}else{
					map.put("flag", true);
				}
				entity = new ResponseEntity<>( map, HttpStatus.OK);
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
	
	// 해시태그를 검색시 해시태그검색가 있는 게시물을 찾아 뿌림.
	@RequestMapping(value = "hash/{idx}", method = RequestMethod.GET)
	public ModelAndView searchHash(@PathVariable("idx")String idx,Locale locale, Model model, HttpServletRequest request) {
			DebugStream.activate();
			String urlToIdx = null;
			// '#'문자가 브라우저 URL로 들어갈시 자기자신을 가리키므로 스크립트에서 URL인코딩하여 넘겨놓았음.
			// 밑의 부분은 URL을 디코딩함
			try {
				urlToIdx = URLDecoder.decode(idx,"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// 검색어를 해시태그별로 split하여 배열에 집어넣음
			String tempIdx ="";
			urlToIdx = "1"+urlToIdx; 
			String[] idxs = urlToIdx.trim().split("#");
			ArrayList<String> queryList = new ArrayList<String>();
			for(String s:idxs){
				if(!("1".equals(s))){
					String[] temp = s.split(" ");
					queryList.add("#"+temp[0]);
					tempIdx += "#"+temp[0]+" ";
				}
			}

			HttpSession hs = request.getSession();
	        String user_id = (String)hs.getAttribute("user_id");
	        List<BoardVO> blist= bdservice.searchIterate(queryList);
	        int bcount= bdservice.searchCount(queryList);
	        ModelAndView mav = new ModelAndView();      
	        mav.setViewName("searchResult");
	        mav.addObject("sessionId", user_id);
	        mav.addObject("list",blist);
	        mav.addObject("count",bcount);
	        mav.addObject("searchTag",tempIdx);
			return mav;
	}
}
