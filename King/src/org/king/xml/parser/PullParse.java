package org.king.xml.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.king.http.HttpService;
import org.king.utils.LogUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Pull解析
 * 
 * @author Jenly
 * @date 2013-4-16
 */
public class PullParse {
	
	public static final String CLAZZ = "PullParser";
	
	/**
	 * 解析XML
	 * @param response
	 * @param startNode
	 * @param nodes
	 * @return
	 */
	public static List<HashMap<String,String>> parserXml(final String response,final String startNode,final String... nodes){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map = null;
		
		try {
			XmlPullParser  parser = XmlPullParserFactory.newInstance().newPullParser();
			
			parser.setInput(new StringReader(response));
			
			int evenType = parser.getEventType();
			
			while (evenType!=XmlPullParser.END_DOCUMENT) {
				
				String nodeName = parser.getName();
				
				switch (evenType) {
				case XmlPullParser.START_TAG:
					if(startNode.equals(nodeName)){
						map = new HashMap<String, String>();
					}
					for(int i=0;i<nodes.length;i++){
						if(nodes[i].equalsIgnoreCase(nodeName)){
							String temp = parser.nextText();
							map.put(nodes[i],temp);
							LogUtils.logI("节点："+nodes[i]+"\t----------->\t"+temp);
						}
					}
					break;

				case XmlPullParser.END_TAG:
					if(startNode.equals(nodeName)&&map!=null&&map.size()>0){
						
							list.add(map);
					}
					break;
				}
				
				evenType = parser.next();
				
			}
			
		} catch (XmlPullParserException e) {
			LogUtils.logE(CLAZZ, "解析出错："+e.getMessage());
		} catch (IOException e) {
			LogUtils.logE(CLAZZ, "IO异常："+e.getMessage());
		}
		
		return list;
	}
	

	/**
	 * 解析XML
	 * 通过传入的流解析
	 * @param inputStream
	 * @param encoding
	 * @param startNode
	 * @param nodes
	 * @return
	 */
	public static List<HashMap<String,String>> parserXml(InputStream inputStream,String startNode,String... nodes){
		return parserXml(inputStream, HttpService.DEFAULT_ENCODING,startNode, nodes);
	}
	
	//-------------------------------
	public static List<HashMap<String,String>> parserXml(InputStream inputStream,String encoding,String startNode,String... nodes){
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map = null;
		
		try {
			XmlPullParser  parser = XmlPullParserFactory.newInstance().newPullParser();
			
			parser.setInput(inputStream,encoding);
			
			int evenType = parser.getEventType();
			
			while (evenType!=XmlPullParser.END_DOCUMENT) {
				
				String nodeName = parser.getName();
				
				switch (evenType) {
				case XmlPullParser.START_TAG:
					if(startNode.equals(nodeName)){
						map = new HashMap<String, String>();
					}
					for(int i=0;i<nodes.length;i++){
						if(nodes[i].equalsIgnoreCase(nodeName)){
							String temp = parser.nextText();
							map.put(nodes[i],temp);
							LogUtils.logI("节点："+nodes[i]+"\t----------->\t"+temp);
						}
					}
					break;

				case XmlPullParser.END_TAG:
					if(startNode.equals(nodeName)&&map!=null){
						list.add(map);
					}
					break;
				}
				
				evenType = parser.next();
				
			}
			
		} catch (XmlPullParserException e) {
			LogUtils.logE(CLAZZ, "解析出错："+e.getMessage());
		} catch (IOException e) {
			LogUtils.logE(CLAZZ, "IO异常："+e.getMessage());
		}
		
		return list;
	}

}
