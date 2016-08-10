package net.nigne.wholegram.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.nigne.wholegram.common.RepCriteria;
import net.nigne.wholegram.service.ReplyService;


@Controller
@RequestMapping( "/rep" )
public class ReplyController {
	@Inject
	private ReplyService service;
	
	// 댓글의 스크롤이 끝에 도달할시 추가로 댓글을 가져옴
	@RequestMapping(value = "/{no}/{rep_idx}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getNum(@PathVariable("no") int board_num,@PathVariable("rep_idx") int rep_idx) {
		ResponseEntity<Map<String, Object>> entity = null;
		RepCriteria rc = new RepCriteria(board_num,rep_idx); // 게시물번호와 댓글의 개수 입력
		Map<String, Object> map = new HashMap<>();
		map.put("rp",service.getListLimit(rc));
		try{
			entity = new ResponseEntity<>(map,HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

}
