package com.hari.xxx.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.hari.xxx.constant.Constants;

public class CommonUtils {
	
	private static boolean flag = false;
	
	public static boolean isPatternMatch(String source, String pattern) {
		Pattern patternCompiled = null;
		if(StringUtils.isNotEmpty(pattern) && StringUtils.isNotEmpty(source)) {
			patternCompiled = Pattern.compile(pattern);
			Matcher matcher = patternCompiled.matcher(source);
			flag = matcher.matches();
		}else {
			flag = false; 
		}
		
		return flag;
	}
	
	/*
	public static void main(String ... strings ) {
		System.out.println(isPatternMatch("h ari@hari.co.in", Constants.EMAIL_PATTERN) );
		System.out.println(isPatternMatch("svsghm@gmail.com", Constants.EMAIL_PATTERN) );
		System.out.println(isPatternMatch("hari@hari.co.in", Constants.EMAIL_PATTERN) );
		System.out.println(isPatternMatch("hari@hari.co.", Constants.EMAIL_PATTERN) );
	}
	*/
	
}
