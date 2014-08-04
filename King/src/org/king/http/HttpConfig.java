package org.king.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


/**
 * 配置文件辅助类
 * ps:(路径：/assets/http.config)
 *
 *@author jenly
 *@date	2013-3-25
 */
public class HttpConfig {
	
	private static Properties properties = new Properties();
	
	static{
		
		try {
			properties.load(HttpConfig.class.getResourceAsStream("/assets/http.config"));
		} catch (IOException e) {
			throw new RuntimeException("加载配置文件出错！",e);
		}
		
	}
	
	//---------------------------------
	
	
	private static String getProperty(String key){
			try {
				return new String(properties.getProperty(key).getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
	}
	
	//---------------------------------
	
	public static String getProperty(String key,String... parmas){
		
		String value = getProperty(key);
		if(parmas!=null){
			for(int i=0;i<parmas.length;i++){
				value = value.replace( "<" + i + ">", parmas[i] );
			}
		}
		return value;
	}
	
	
	
	

}
