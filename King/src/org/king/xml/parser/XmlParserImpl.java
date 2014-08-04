package org.king.xml.parser;


import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

import org.king.entity.ResultInfo;
import org.king.xml.handler.AbstractHandler;
import org.king.xml.handler.ResultHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * XML解析接口的实现
 * 
 * @author Jenly
 * @date 2013-4-3
 */
public class XmlParserImpl implements XmlParser{
	
	//-----------------------------------------
	
	private AbstractHandler handler;
	private XmlParserCallback callback;
	
	//-----------------------------------------
	
	public XmlParserImpl(AbstractHandler handler,XmlParserCallback callback){
		if(null!=handler){
			this.handler = handler;
		}else{
			this.handler = new ResultHandler();
		}
		this.callback = callback;
	}

	//-----------------------------------------
	
	@Override
	public void parse(String response){
		parse(new InputSource(new StringReader(response)));
	}
	
	@Override
	public void parse(InputSource inputSource) {
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			xmlReader.setContentHandler(handler);
			xmlReader.parse(inputSource);
			
			
			ResultInfo resultInfo = handler.getResultInfo();
			
			if(ResultInfo.RESULT_CODE_OK.equals(resultInfo.getResultCode())){
				callback.onFinish();
			}else{
				callback.onError("解析结果码："+resultInfo.getResultCode()+"\n结果信息："+resultInfo.getResultMessage());
			}
		}catch(Exception e){
			callback.onError("XML解析出错！");
			e.printStackTrace();
		}
	}

}
