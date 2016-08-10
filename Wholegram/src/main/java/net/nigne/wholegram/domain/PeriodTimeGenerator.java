package net.nigne.wholegram.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PeriodTimeGenerator {
	/**
	 * 클래스내의 태그 값
	 */
	private final String TAG = "PeriodTimeGenerator";

	@Override
	public String toString() {
		int nSecs = this.getSeconds();
		int nMins = this.getMinutes();
		int nHours = this.getHours();
		int nDays = this.getDays();
		int nMonth = this.getMonths();
		int nYear = this.getYear();
		
		String ret = "";
		if (nDays > 7) {
			return String.format("%y년 %m월 %d일",nYear, nMonth,nDays);
		}
		if (nDays > 0) {
			return String.format("%d일전", nDays);
		}
		if (nHours > 0) {
			return String.format("%d시간전", nHours);
		}
		if (nMins > 0) {
			return String.format("%d분전", nMins);
		}
		if (nSecs > 1) {
			return String.format("%d초전", nSecs);
		}
		if (nSecs < 2) {
			return String.format("조금전", nSecs);
		}
		return ret;
	}
	



	/**
	 * 각종 데이터 정의 내부 클래스
	 * 
	 * @author yeongeon
	 * 
	 */
	class Params {
		/**
		 * 시간차 계산시 기준이 되는 타임존 설정 기본값
		 */
		public final static String TIMEZONE_ID_DEFAULT = "Asia/Seoul";

		/**
		 * 입력 Datetime 형식
		 */
		public final static String INPUT_DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	}

	/**
	 * long형 시간차
	 */
	private long mMilliSecs = 0L;

	/**
	 * long형 시간차를 입력 받는 생성자
	 * 
	 * @param milliSecs
	 */
	public PeriodTimeGenerator(long milliSecs) {
		this.mMilliSecs = milliSecs;
	}

	/**
	 * Date형 시간차를 입력 받는 생성자
	 * 
	 * @param date
	 */
	public PeriodTimeGenerator(Date date) {
		long write_datetime = date.getTime();
		Date nowDate = new Date();
		long now_datetime = nowDate.getTime();
		this.mMilliSecs = (now_datetime - write_datetime);
	}

	/**
	 * 데이터의 문자열 시간을 입력 받는 생성자
	 * 
	 * @param strDate
	 * @throws ParseException
	 */
	public PeriodTimeGenerator(String strDate) throws ParseException {
		DateFormat df = new SimpleDateFormat(Params.INPUT_DATE_FORMAT_DEFAULT);
		df.setTimeZone(TimeZone.getTimeZone(Params.TIMEZONE_ID_DEFAULT));
		Date date = df.parse(strDate);
		long write_datetime = date.getTime();

		Date nowDate = new Date();
		long now_datetime = nowDate.getTime();

		this.mMilliSecs = (now_datetime - write_datetime);
	}

	/**
	 * 데이터의 시간 포멧과 데이터의 문자열 시간을 입력 받는 생성자
	 * 
	 * @param format
	 * @param strDate
	 * @throws ParseException
	 */
	public PeriodTimeGenerator(String format, String strDate)
			throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(TimeZone.getTimeZone(Params.TIMEZONE_ID_DEFAULT));
		Date date = df.parse(strDate);
		long write_datetime = date.getTime();

		Date nowDate = new Date();
		long now_datetime = nowDate.getTime();

		this.mMilliSecs = (now_datetime - write_datetime);
	}

	/**
	 * 초(sec)를 반환합니다.
	 * 
	 * @return
	 */
	private int getSeconds() {
		return (int) (this.mMilliSecs / 1000);
	}

	/**
	 * 분(min)을 반환합니다.
	 * 
	 * @return
	 */
	private int getMinutes() {
		return (getSeconds() / 60);
	}

	/**
	 * 시간(hour)을 반환합니다.
	 * 
	 * @return
	 */
	private int getHours() {
		return (getMinutes() / 60);
	}

	/**
	 * 일수(day)를 반환합니다.
	 * 
	 * @return
	 */
	private int getDays() {
		return (getHours() / 24);
	}
	
	/**
	 * 월(month)를 반환합니다.
	 * 
	 * @return
	 */
	private int getMonths() {
		return (getDays() / 30);
	}
	
	private int getYear() {
		// TODO Auto-generated method stub
		return 0;
	}

}
