package com.kbk.fep.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FepStrUtil {
	
	public static String nextTimestamp(String inputTime, int nextTime) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
		String retValue = "";
		
		long c;
		try {
			c = sdf.parse(inputTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return inputTime;
		}
		retValue = sdf.format(new Date(c + (nextTime * 60 * 1000)));
		if ( "000000000".equals(retValue) ) {
			retValue = "240000000";
		}
		return retValue;
	}
	
	public static long elapseTime(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		long retValue = 0l;
		try {
			Date start = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			retValue = end.getTime() - start.getTime();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return retValue;
	}
	
	public static String toKor ( String input ) {
		try {
			if ( input != null ) 
				return new String ( input.getBytes("8859_1"), "ksc5601" );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;
		
	}
	
	public static boolean isNotNull(String input) {
		if ( input !=null && input.trim().length() > 0 ) 
			return true;
		else
			return false;
	}
	
	public static String encoding(String input) {
		String retValue = input;
		try {
			retValue = URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return retValue;
	}

}
