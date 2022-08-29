package com.sprong.board.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;

@Component
public class StringUtil {
	
	public String isEmpty(String value) {
		return StringUtils.isEmpty(value) ? null : value;
	}
	
	public static String NVL(Object text) {
		if(text == null || text.equals("null")) {
			return"";
		}else {
			return text.toString();
		}
	}
	
	
	public boolean isEmptyToBoolean(String value) {
		return StringUtils.isEmpty(value);
		
	}
	
	public int isEmptyToInt(String value, int emptyValue) {
		return StringUtils.isEmpty(value) ? emptyValue : Integer.parseInt(value);
	}
	
	public String isEmptyToString(String value, String emptyValue) {
		return StringUtils.isEmpty(value) ? emptyValue : value;
	}
	
	public int STringToHashcCode(String value) {
		return value.hashCode();
	}
	
	public boolean StringEquals(String key, String value) {
		return StringUtils.equals(key, value);
	}
	
	public static String encryptPassword(String data)throws Exception{
		
		if(data == null) {
			return "";
		}
		
		byte[] plaintText = null;//평문
		byte[] hashValue = null; //해쉬값
		plaintText = data.getBytes();
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		hashValue = md.digest(plaintText);
		System.out.println(hashValue);
		return new String(Base64.encodeBase64(hashValue));
	}
	
}
