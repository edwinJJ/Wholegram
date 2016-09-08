package net.nigne.wholegram.common;

import java.util.ArrayList;
import java.util.List;

public class HashTagScrollCriteria {       // 해시태그 스크롤링 처리
	List<String> list = new ArrayList<>(); // 다중 검색어 저장용도 
	private int start;						
	private int end;

	public List<String> getTag() {
		return list;
	}

	public void setTag(List<String> tag) {
		this.list = tag;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
