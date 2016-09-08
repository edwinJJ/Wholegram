package net.nigne.wholegram.common;

public class HashSearchResult {
	private String tag; // 태그 키워드
	private int count;  // 검색된 태그의 숫자
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
