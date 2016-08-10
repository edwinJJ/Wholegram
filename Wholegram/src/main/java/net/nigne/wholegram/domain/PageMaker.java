package net.nigne.wholegram.domain;

public class PageMaker {
	int page;
	int pagePerBlock;
	int endNum; 
	//( int )Math.ceil( Integer.parseInt(page) / (double)pagePerBlock ) * pagePerBlock; // 
	int startNum;
	int next;
	
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getPagePerBlock() {
		return pagePerBlock;
	}
	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = 5;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = page * 5;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = ( page  - 1 ) * pagePerBlock;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
