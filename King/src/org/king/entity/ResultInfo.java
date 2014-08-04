package org.king.entity;

public class ResultInfo {
	
	private String resultCode;
	private String resultMessage="";
	
	public static final String RESULT_CODE_OK = "1";
//	public static final String RESULT_CODE_ERROR = "-1";
	public static final String RESULT_CODE_ERROR = "0";
	
	//-------------------------------
	
	public ResultInfo(){
		
	}
	
	public ResultInfo(String resultCode,String resultMessage){
		
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}
	
	//-------------------------------
	
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	//-------------------------------

	
	@Override
	public String toString() {
		return "resultInfo:["+resultCode+" "+resultMessage +"]";
	}
	
	
}
