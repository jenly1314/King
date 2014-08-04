package org.king.utils;


import java.io.UnsupportedEncodingException;

import android.text.TextUtils;

/**
 * �ַ���������
 * 
 * @author Jenly
 * @date 2013-3-25
 * 
 */
public class StringUtils {
	
	/**
	 * ��ȡ�ַ�����Ĭ��null=""��
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
	 * �ж��ַ����Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		
		return TextUtils.isEmpty(getString(str));
	}
	
	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		
		return !TextUtils.isEmpty(getString(str));
	}
	
	
	/**
	 * ����ַ�������
	 * @param str 
	 * @return �����ַ���������
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
	 * ת��GBK����
	 * @param str
	 * @return
	 */
	public String transcodeToGBK(String str){
		return transcode(str,"GBK");
	}
	
	/**
	 * ת��UTF-8����
	 * @param str
	 * @return
	 */
	public String transcodeToUTF_8(String str){
		return transcode(str,"UTF-8");
	}
	
	/**
	 * �Զ�ת��
	 * @param str
	 * @param charEncode Ĭ��UTF-8
	 * @return
	 */
	public String transcode(String str,String charEncode){
		
		if(null==charEncode||"".equals(charEncode)){
			charEncode = "UTF-8";
		}
		
		String temp = "";
		try {
			String code = getCharEncode(str);
			LogUtils.logI("���룺"+code);
			temp = new String(str.getBytes(code),charEncode);
		} catch (UnsupportedEncodingException e) {
			LogUtils.logE("ת���쳣��"+e.getMessage());
		}
		return temp;
	}

}
