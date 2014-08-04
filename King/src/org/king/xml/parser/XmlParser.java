package org.king.xml.parser;

import org.xml.sax.InputSource;

/**
 * XML�����ӿ�
 * 
 * @author Jenly
 * @date 2013-4-3
 */
public interface XmlParser {
	
	/**
	 * XML�����ص��ӿ�
	 */
	public interface XmlParserCallback{
		
		void onFinish();
		
		void onError(String error);
	}
	
	public void parse(InputSource inputSource);
	
	public void parse(String response);

}
