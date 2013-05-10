package cn.edu.shiep.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private DateUtil(){}
	
	public static String convertDateFormat(Date date){
		SimpleDateFormat format = new SimpleDateFormat("");
		return format.format(date);
	}
	

	public static int priority(String time){
		int priority = 0;
		try {
			String[] data = time.split("-");
			Date date = new Date();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return priority;
	}
	public static void main(String[] args){
		Date date = new Date();
		long result = Date.parse("17:00");
		System.out.println(result);
	}
}
