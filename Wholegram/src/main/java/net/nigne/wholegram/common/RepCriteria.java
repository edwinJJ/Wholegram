package net.nigne.wholegram.common;

public class RepCriteria { // 댓글에 스크롤 지정해놓음, 설정된 숫자만큼 댓글의 내용을 가져옴
	private int board_num; 
	private int start;
	private int end;
	public RepCriteria(int num,int idx){
		this.board_num = num;
		this.start = idx;
		this.end = idx+20;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
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
