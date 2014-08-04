package org.king.utils;


import java.io.UnsupportedEncodingException;

import android.text.TextUtils;

/**
 * 字符串工具类
 * 
 * @author Jenly
 * @date 2013-3-25
 * 
 */
public class StringUtils {
	
	/**
	 * 获取字符串（默认null=""）
	 * @param str
	 * @return
	 */
	public static String getString(String str){
		try{
			return str!=null ? str:"";
			
		}catch (NullPointerException e) {
			return "";
		}
		
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		
		return TextUtils.isEmpty(getString(str));
	}
	
	/**
	 * 判断字符串是否不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		
		return !TextUtils.isEmpty(getString(str));
	}
	
	
	/**
	 * 获得字符集编码
	 * @param str 
	 * @return 返回字符编码类型
	 */
	public static String getCharEncode(String str){
		
		String charEncode = "GB2312";
		try {
			if(str.equals(new String(str.getBytes(charEncode),charEncode))){
				
				return charEncode;
			}
		} catch (UnsupportedEncodingException e) {
			
		}
		
		charEncode = "GBK";
		try {
			if(str.equals(new String(str.getBytes(charEncode),charEncode))){
				
				return charEncode;
			}
		} catch (UnsupportedEncodingException e) {
			
		}
		
		charEncode = "ISO-8859-1";
		try {
			if(str.equals(new String(str.getBytes(charEncode),charEncode))){
				
				return charEncode;
			}
		} catch (UnsupportedEncodingException e) {
			
		}
		
		charEncode = "UTF-8";
		try {
			if(str.equals(new String(str.getBytes(charEncode),charEncode))){
			
				return charEncode;
			}
		} catch (UnsupportedEncodingException e) {
		
		}
		
		return "";
		
	}
	
	/**
	 * 转成GBK编码
	 * @param str
	 * @return
	 */
	public String transcodeToGBK(String str){
		return transcode(str,"GBK");
	}
	
	/**
	 * 转成UTF-8编码
	 * @param str
	 * @return
	 */
	public String transcodeToUTF_8(String str){
		return transcode(str,"UTF-8");
	}
	
	/**
	 * 自动转码
	 * @param str
	 * @param charEncode 默认UTF-8
	 * @return
	 */
	public String transcode(String str,String charEncode){
		
		if(null==charEncode||"".equals(charEncode)){
			charEncode = "UTF-8";
		}
		
		String temp = "";
		try {
			String code = getCharEncode(str);
			LogUtils.logI("编码："+code);
			temp = new String(str.getBytes(code),charEncode);
		} catch (UnsupportedEncodingException e) {
			LogUtils.logE("转码异常："+e.getMessage());
		}
		return temp;
	}

}
