package org.king.xml.handler;

import org.king.entity.PageInfo;
import org.king.entity.ResultInfo;
import org.king.utils.LogUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.text.TextUtils;

/**
 * XML解析抽象处理者
 * 
 * @author Jenly
 * @date 2013-4-3
 */
public abstract class AbstractHandler extends DefaultHandler{
	
	protected final String CLAZZ = "AbstractHandler";
	
	//-----------------------------------------
	public static final String TAG_NAME_RESULT_INFO = "ResultInfo";
	public static final String TAG_NAME_RESULT_CODE = "ResultCode";
	public static final String TAG_NAME_RESULT_MESSAGE = "ResultMessage";
//	
//	//-----------------------------------------
	public static final String TAG_NAME_PAGE_INFO = "PageInfo";
	public static final String TAG_NAME_CURRENT_PAGE = "CurrentPage";
	public static final String TAG_NAME_PAGE_SIZE = "PageSize";
	public static final String TAG_NAME_TOTAL_ROWS = "TotalRows";
	public static final String TAG_NAME_TOTAL_PAGES = "TotalPages";
	
	//-----------------------------------------
//	public static final String TAG_NAME_RESULT_INFO = "result";
//	public static final String TAG_NAME_RESULT_CODE = "resultcode";
//	public static final String TAG_NAME_RESULT_MESSAGE = "resultmsg";
//	
//	//-----------------------------------------
//	public static final String TAG_NAME_PAGE_INFO = "PageInfo";
//	public static final String TAG_NAME_CURRENT_PAGE = "CurrentPage";
//	public static final String TAG_NAME_PAGE_SIZE = "PageSize";
//	public static final String TAG_NAME_TOTAL_ROWS = "TotalRows";
//	public static final String TAG_NAME_TOTAL_PAGES = "TotalPages";
	
	//-----------------------------------------
	
	private String tagName;
	private String content;
	
	private ResultInfo resultInfo;
	private PageInfo pageInfo;
	
	//-----------------------------------------
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ResultInfo getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	//-----------------------------------------
	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}
	//-----------------------------------------
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
	
	//-----------------------------------------
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		this.tagName = localName;
		
		if(TAG_NAME_RESULT_INFO.equalsIgnoreCase(localName)){
			resultInfo = new ResultInfo();
		}else if(TAG_NAME_PAGE_INFO.equalsIgnoreCase(localName)){
			pageInfo = new PageInfo();
		}else{
			startElements(uri, localName, qName, attributes);
		}
	}
	
	//-----------------------------------------
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		
		content = new String(ch,start,length);
			try{
				if(!TextUtils.isEmpty(content.trim())){
					if(TAG_NAME_RESULT_CODE.equalsIgnoreCase(tagName)){
						resultInfo.setResultCode(content);
					}else if(TAG_NAME_RESULT_MESSAGE.equalsIgnoreCase(tagName)){
						resultInfo.setResultMessage(content);
					}else if(TAG_NAME_CURRENT_PAGE.equalsIgnoreCase(tagName)){
						pageInfo.setCurrentPage(Integer.parseInt(content));
					}else if(TAG_NAME_PAGE_SIZE.equalsIgnoreCase(tagName)){
						pageInfo.setPageSize(Integer.parseInt(content));
					}else if(TAG_NAME_TOTAL_ROWS.equalsIgnoreCase(tagName)){
						pageInfo.setTotalRows(Integer.parseInt(content));
					}else if(TAG_NAME_TOTAL_PAGES.equalsIgnoreCase(tagName)){
						pageInfo.setTotalPages(Integer.parseInt(content));
					}else{
						characterss(ch, start, length);
					}
				}
				LogUtils.logI(tagName+"\t-------\t"+content);
			}catch (NumberFormatException e) {
				LogUtils.logE(CLAZZ, "数字格式化错误！");
			}catch (Exception e) {
				LogUtils.logE(CLAZZ, e.getMessage());
				e.printStackTrace();
			}
	}
	
	//-----------------------------------------
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		
		endElements(uri, localName, qName);
	
	}
	
	
	//-----------------------------------------
	
	public abstract void startElements(String uri, String localName, String qName, Attributes attributes);
	
	public abstract void characterss(char[] ch, int start, int length);
	
	public abstract void endElements(String uri,String localName,String qName);
}
