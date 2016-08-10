package net.nigne.wholegram.common;

public class Criteria { // DB에서 limit 범위  지정
	private String item; // keyword
	private int start;
	private int end;
	public Criteria(){
	}
	public Criteria(int no){
		this.start = no;
		this.end = no+9;
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
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	
}
