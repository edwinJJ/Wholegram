package net.nigne.wholegram.common;

import java.util.ArrayList;
import java.util.List;

public class HashTagScrollCriteria {       //�ؽ��±� �˻��� ���Ѱ�
	List<String> list = new ArrayList<>(); // �ؽ��±� �ܾ� ����Ʈ
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
