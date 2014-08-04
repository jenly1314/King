package org.king.xml.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.king.utils.LogUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



/**
 * DOM����
 * 
 * @author Jenly
 * @date 2013-4-16
 */
public class DomParse {
	
	/**
	 * ����XML
	 * @param response
	 * @return
	 */
	public static List<HashMap<String,String>> parserXml(String response){
		
		return parserXml(response,null);
	}
	
	/**
	 * ����XML
	 * @param response
	 * @param startNode
	 * @return
	 */
	public static List<HashMap<String,String>> parserXml(String response,String startNode){
			
			List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
			HashMap<String, String> map = null;
			
			try {
				//������ʵ����
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				//����ĵ�
				Document document = builder.parse(new InputSource(new StringReader(response)));
				
				NodeList nodeList = null;
				
				if(null!=startNode){
					nodeList = document.getElementsByTagName(startNode);
				}else{
					nodeList = document.getChildNodes();
				}
				
				for(int i = 0;i<nodeList.getLength();i++){
					map = new HashMap<String,String>();
					Node node = nodeList.item(i);
					LogUtils.logI("-------------------------------->"+i+"\t�ڵ㣺"+node.getNodeName());
					if(node.hasChildNodes()){
						NodeList nodes = node.getChildNodes();
						
						for(int j= 0;j<nodes.getLength();j++){
							Node n = nodes.item(j);
							map.put(n.getNodeName(),n.getTextContent());
							LogUtils.logI(j+"\t�ڵ㣺"+n.getNodeName()+"\t----------->\t"+n.getTextContent());
						}
					}
					
					if(null!=map&&map.size()>0){
						list.add(map);
					}
				}
				
				
			} catch (ParserConfigurationException e) {
				LogUtils.logE("�����������쳣:"+e.getMessage());
			} catch (SAXException e) {
				LogUtils.logE("�����쳣:"+e.getMessage());
			} catch (IOException e) {
				LogUtils.logE("IO�쳣:"+e.getMessage());
			} catch (Exception e) {
				LogUtils.logE("�쳣:"+e.getMessage());
			}
			
			return list;
			
	}
	
	/**
	 * ����XML
	 * @param inputStream
	 * @return
	 */
	public static List<HashMap<String,String>> parserXml(InputStream inputStream){
		
		return parserXml(inputStream,null);
	}
	
	/**
	 * ����XML
	 * @param inputStream
	 * @param startNode
	 * @return
	 */
	public static List<HashMap<String,String>> parserXml(InputStream inputStream,String startNode){
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map = null;
		
		try {
			//������ʵ����
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			Document document = builder.parse(inputStream);
			
			NodeList nodeList = null;
			
			if(null!=startNode){
				nodeList = document.getElementsByTagName(startNode);
			}else{
				nodeList = document.getChildNodes();
			}
			
			for(int i = 0;i<nodeList.getLength();i++){
				map = new HashMap<String,String>();
				Node node = nodeList.item(i);
				LogUtils.logI("-------------------------------->"+i+"\t�ڵ㣺"+node.getNodeName());
				if(node.hasChildNodes()){
					NodeList nodes = node.getChildNodes();
					
					for(int j= 0;j<nodes.getLength();j++){
						Node n = nodes.item(j);
						map.put(n.getNodeName(),n.getTextContent());
						LogUtils.logI(j+"\t�ڵ㣺"+n.getNodeName()+"\t----------->\t"+n.getTextContent());
					}
				}
				
				if(null!=map&&map.size()>0){
					list.add(map);
				}
			}
			
			
		} catch (ParserConfigurationException e) {
			LogUtils.logE("�����������쳣:"+e.getMessage());
		} catch (SAXException e) {
			LogUtils.logE("�����쳣:"+e.getMessage());
		} catch (IOException e) {
			LogUtils.logE("IO�쳣:"+e.getMessage());
		} catch (Exception e) {
			LogUtils.logE("�쳣:"+e.getMessage());
		}
		
		return list;
		
	}

}
