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
	   public ModelAndView searchText(@PathVariable("user_id")String idx, Locale locale, Model model,HttpServletRequest request) {
		// idx == 검색하려는 user_id
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		MemberVO vo = new MemberVO();
		vo = service.MemInfo(idx);  								// 검색하려는 user의 개인 정보를 가져온다
		if(!"favicon".equals(idx) && vo != null){
	      List<BoardVO> list = bdservice.getUserLimitList(vo);		// 검색하려는 user의 게시물 정보 가져온다
	      ModelAndView mav = new ModelAndView();
	      Criteria cr = new Criteria();
	      cr.setItem(idx);
	      int numberOfBoard = bdservice.getUserCount(cr);			// 검색하려는 user의 게시물 개수 가져옴

	      Map<String, Integer> numberOfFollow= fservice.getFollowNumberof(idx);			// 검색하려는 user의 팔로잉/팔로워 숫자 가져옴  
	      mav.setViewName("user");
	      mav.addObject("followCheck", fservice.followCheck(user_id, vo.getUser_id()));	// 검색하려는 user를 내가 팔로우 하고있는지 아닌지 true/false로 리턴
	      mav.addObject("sessionId",user_id);
	      mav.addObject("vo", vo);
	      mav.addObject("list", list);
	      mav.addObject("numberOfBoard", numberOfBoard);								// 유저가 올린 게시물 개수
		  mav.addObject("numberOfFollow", numberOfFollow);								// 유저가 팔로잉 / 유저를 팔로우 있는 수
	      return mav;
	      
	      }else if(!"favicon".equals(idx) && vo == null){
	    	  return null;
	      }else{
	    	  return null;
	      }
	   }
	
	//해시태그 검색 후 결과 게시물 스크롤링으로 더 가져올때
	@RequestMapping( value = "scroll/hash/{text}/{count}", method = RequestMethod.POST )
	public ResponseEntity<Map<String, Object>> scrollList( @PathVariable("count") Integer cnt,@PathVariable("text") String index,HttpServletResponse response, HttpServletRequest request ) {
		
		// text -> 검색했던 태그 이름 
		// cnt -> 현재 검색해서 나온 게시물 개수
		
		ResponseEntity<Map<String, Object>> entity = null;
		HttpSession session = request.getSession();
		
		if ((session.getAttribute("user_id")) != null && !(session.getAttribute("user_id").equals(""))) {
			try {
				System.out.println(index);
				String in[] = index.replaceAll("\\+","").split("#");			// 다중검색시 띄어쓰기 부분이 '+' 로 나옴 그부분을 제거 후 '#' 태그 기준으로 문자열 자름
				List<String> tag = new ArrayList<>();
				for(String s : in){
					System.out.println(s);
					tag.add("#"+s.trim());										// 다중 검색 #키워드 내용을 담는다
				}
				HashTagScrollCriteria htsc = new HashTagScrollCriteria();
				htsc.setTag(tag);												// 다중 검색 #키워드
				htsc.setStart(cnt);												// 더 보여주려는 게시물들의 처음 번호
				htsc.setEnd(cnt+9);												// 더 보여주려는 게시물들의 마지막 번호 --> 즉, 9개씩 늘려가면서 보여준다.
				List<BoardVO> list = bdservice.SearchScrollIterate(htsc);		// 스크롤링으로 인해 더 가져올 게시물들 추출
				Map<String, Object> map = new HashMap<>();
				map.put("list", list);
				System.out.println(bdservice.searchCount(htsc.getTag()));

				if(bdservice.searchCount(htsc.getTag()) < htsc.getEnd()){		// #태그 검색 게시물 총 개수 < 더 보여주려는 게시물 개수  -> 더 보여주려는 게시물수 보다 실제 #태그 검색 게시물 총 개수가 더 적기 때문에 더 보여줄 수가 없다. (false)
					map.put("flag", false);
				}else{
					map.put("flag", true);										// #태그 검색 게시물 총 개수 > 더 보여주려는 게시물 개수  -> #태그 검색 게시물 수가 더 보여주려는 게시물 수보다 많기 때문에 게시물을 더 보여준다. (true)
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
			
			try {
				urlToIdx = URLDecoder.decode(idx,"utf-8");		// 인코딩한 URL을 디코딩함
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			// 검색어를 해시태그별로 split하여 배열에 집어넣음
			String tempIdx ="";
			urlToIdx = "1"+urlToIdx; 
			String[] idxs = urlToIdx.trim().split("#");				// '#' 단위로 문자열 자름
			ArrayList<String> queryList = new ArrayList<String>();

			for(String s:idxs){
				if(!("1".equals(s))){
					String[] temp = s.split(" ");
					queryList.add("#"+temp[0]);						// '#'태그와 내용을 담는다 -> DB에서 찾는용도				
					tempIdx += "#"+temp[0]+" ";						// '#'태그와 내용을 담는다 -> view에서 결과내용 뿌려주는 용도
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
