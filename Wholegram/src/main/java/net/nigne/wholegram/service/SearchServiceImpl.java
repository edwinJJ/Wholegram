package net.nigne.wholegram.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.persistance.SearchDAO;
@Service

public class SearchServiceImpl implements SearchService {
	
	
	@Inject
	private SearchDAO sdao;
	
	@Override
	public List<MemberVO> getSearch(String idx) {
		// TODO Auto-generated method stub
		return sdao.getSearch(idx);
	}

	@Override
	public int getHashSearchCount(String idx) {
		// TODO Auto-generated method stub
		return sdao.getHashSearchCount(idx);
	}

	@Override
	public List<BoardVO> getHashSearch(String idx) {
		System.out.println(idx);
		return sdao.getHashSearch(idx);
	}


}
