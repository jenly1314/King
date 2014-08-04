package org.king.xml.parser;

import org.xml.sax.InputSource;

/**
 * XML解析接口
 * 
 * @author Jenly
 * @date 2013-4-3
 */
public interface XmlParser {
	
	/**
	 * XML解析回调接口
	 */
	public interface XmlParserCallback{
		
		void onFinish();
		
		void onError(String error);
	}
	
	public void parse(InputSource inputSource);
	
	public void parse(String response);

}
