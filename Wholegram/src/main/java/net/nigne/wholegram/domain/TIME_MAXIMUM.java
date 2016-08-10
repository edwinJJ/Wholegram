package net.nigne.wholegram.domain;

import java.util.Date;

class TIME_MAXIMUM{
    static final int SEC = 60;
    static final int MIN = 60;
    static final int HOUR = 24;
    static final int DAY = 30;
    static final int MONTH = 12;

    public static String formatTimeString( Date reg_date ) {
		long curTime = System.currentTimeMillis();
		long regTime = reg_date.getTime();
		long diffTime = (curTime - regTime) / 1000;
		
		String msg = null;
		if (diffTime < TIME_MAXIMUM.SEC) {
				// sec
			msg = "��� ��";
		} else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
			// min
			msg = diffTime + "�� ��";
		} else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
			// hour
			msg = (diffTime) + "�ð� ��";
		} else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
			// day
				msg = (diffTime) + "�� ��";
		} else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
			// day
		        msg = (diffTime) + "�� ��";
		} else {
			msg = (diffTime) + "�� ��";
		}
		
		return msg;
    }
}