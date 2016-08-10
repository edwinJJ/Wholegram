package net.nigne.wholegram.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.nigne.wholegram.common.DebugStream;
import net.nigne.wholegram.common.HashSearchResult;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Inject 
	private SearchService sService;
	private static final String NULL = "";
	
	// 자동완성('#'문자를 안쓰고 검색하였을시 입력한 유저들의 아이디를 검색
	@RequestMapping(value = "/autocomplete/{idx}", method = RequestMethod.POST) 
	public ResponseEntity<List<MemberVO>> searchText(@PathVariable("idx")String idx,Locale locale, Model model) {
		ResponseEntity<List<MemberVO>> entity = null;
		DebugStream.activate();
		System.out.println(idx);
		List<MemberVO> list = sService.getSearch(idx);
		try{
		entity = new ResponseEntity<>(list,HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		return entity;
	}

	// 자동완성('#'문자를 사용하여 검색하였을시 입력한 태그의 게시물수를 출력
	@RequestMapping(value = "/autocomplete/hashtag/{idx}", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<List<HashSearchResult>> searchHash(@PathVariable("idx")String idx,Locale locale, Model model) {
		ResponseEntity<List<HashSearchResult>> entity = null;
		DebugStream.activate();//Debug용
		List<HashSearchResult> result = new ArrayList<>();
		for( String s: idx.trim().split("#")){ // ex)'#1 #2 #3' 으로 검색하였을시  s=[1,2,3] 으로 구분
			if(!"".equals(s)){
						HashSearchResult hsr = new HashSearchResult();
						hsr.setTag(s.trim());
						hsr.setCount(sService.getHashSearchCount("#"+s.trim()));;
						result.add(hsr);			
			}
		}
		//TODO 제거할것
//		Iterator<HashSearchResult> list = result.iterator();
//		while(list.hasNext()){
//			HashSearchResult ss = list.next();
//			System.out.println(ss.getTag()+" "+ss.getCount());
//		}

		try{
			entity = new ResponseEntity<>(result,HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	 // 검색창에서 검색후 enter를 눌렀을시 검색 텍스트를 분류함 
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void searches(Locale locale, Model model, String searchValue, HttpServletResponse response) {
		//TODO 디버깅 제거할것
		DebugStream.activate();
		System.out.println(searchValue);
		try {
			response.setCharacterEncoding("utf-8");
			String url = URLEncoder.encode(searchValue,"utf-8");
			if(hashTagDetector(searchValue))
				// 해시태그가 있을경우 해시태그 관련 페이지로 이동
				response.sendRedirect("/hash/"+url); 
			else
				// 입력한 유저의 user영역으로 이동
				response.sendRedirect("/"+url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// TODO 아직 사용안하고 있는 함수임... 해시태그 중복 검색시에 사용예정
	public static boolean compare(List<HashSearchResult> result, String s){
		boolean flag = false;
		Iterator<HashSearchResult> ir = result.iterator();
		// 해시태그
		while(ir.hasNext()){
			if(ir.next().getTag().equals(s))
				flag = true;
			System.out.println(flag);
		}
		
		return flag;
	}
	
	// 해시태그 감지기
	public static boolean hashTagDetector(String idx){
		if(idx.indexOf("#")>= 0)
			return true;
		else
			return false;
	}
}
