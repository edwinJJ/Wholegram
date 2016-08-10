package net.nigne.wholegram.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PeriodTimeGenerator {
	/**
	 * Ŭ�������� �±� ��
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
			return String.format("%y�� %m�� %d��",nYear, nMonth,nDays);
		}
		if (nDays > 0) {
			return String.format("%d����", nDays);
		}
		if (nHours > 0) {
			return String.format("%d�ð���", nHours);
		}
		if (nMins > 0) {
			return String.format("%d����", nMins);
		}
		if (nSecs > 1) {
			return String.format("%d����", nSecs);
		}
		if (nSecs < 2) {
			return String.format("������", nSecs);
		}
		return ret;
	}
	



	/**
	 * ���� ������ ���� ���� Ŭ����
	 * 
	 * @author yeongeon
	 * 
	 */
	class Params {
		/**
		 * �ð��� ���� ������ �Ǵ� Ÿ���� ���� �⺻��
		 */
		public final static String TIMEZONE_ID_DEFAULT = "Asia/Seoul";

		/**
		 * �Է� Datetime ����
		 */
		public final static String INPUT_DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	}

	/**
	 * long�� �ð���
	 */
	private long mMilliSecs = 0L;

	/**
	 * long�� �ð����� �Է� �޴� ������
	 * 
	 * @param milliSecs
	 */
	public PeriodTimeGenerator(long milliSecs) {
		this.mMilliSecs = milliSecs;
	}

	/**
	 * Date�� �ð����� �Է� �޴� ������
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
	 * �������� ���ڿ� �ð��� �Է� �޴� ������
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
	 * �������� �ð� ����� �������� ���ڿ� �ð��� �Է� �޴� ������
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
	 * ��(sec)�� ��ȯ�մϴ�.
	 * 
	 * @return
	 */
	private int getSeconds() {
		return (int) (this.mMilliSecs / 1000);
	}

	/**
	 * ��(min)�� ��ȯ�մϴ�.
	 * 
	 * @return
	 */
	private int getMinutes() {
		return (getSeconds() / 60);
	}

	/**
	 * �ð�(hour)�� ��ȯ�մϴ�.
	 * 
	 * @return
	 */
	private int getHours() {
		return (getMinutes() / 60);
	}

	/**
	 * �ϼ�(day)�� ��ȯ�մϴ�.
	 * 
	 * @return
	 */
	private int getDays() {
		return (getHours() / 24);
	}
	
	/**
	 * ��(month)�� ��ȯ�մϴ�.
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
