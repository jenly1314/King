package org.king.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import org.king.utils.FileUtils;
import org.king.utils.LogUtils;
import org.king.xml.handler.ResultHandler;
import org.king.xml.parser.XmlParser;
import org.king.xml.parser.XmlParserImpl;
import org.king.xml.parser.XmlParser.XmlParserCallback;
import org.xml.sax.InputSource;



public class HttpUploadImpl implements HttpUpload{
	
	public final String CLAZZ = "HttpUploadImpl";
	
	private boolean isCancel = false;

	private int connectTimeout = 5000;
	private int readTimeout = 5000;
	
	//-----------------------------------------
	
	public HttpUploadImpl(){
		
	}
	
	public HttpUploadImpl(int connectTimeout,int readTimeout){
		if(connectTimeout>0){
			this.connectTimeout = connectTimeout;
		}
		if(readTimeout>0){
			this.readTimeout = readTimeout;
		}
	}
	
	//-----------------------------------------
	
	@Override
	public void upload(String url, Map<String, String> params, Map<String, File> files,final HttpUploadListener listener) {
		LogUtils.logV("�ϴ���ַ��"+url);
		String boundary = UUID.randomUUID().toString();
		//ǰ׺
		String prefix = "--";
		String crlf = "\r\n";
		
		DataOutputStream dos = null;
		BufferedReader br = null;
		
		HttpURLConnection connect = null;
		try {
			
//			HttpGet httpRequest = new HttpGet(url);
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
//			HttpEntity entity = response.getEntity();
//			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
//			is = bufferedHttpEntity.getContent();
			
			
			//-----------------------------
			connect = (HttpURLConnection)new URL(url).openConnection();
			connect.setConnectTimeout(connectTimeout);
			connect.setReadTimeout(readTimeout);
			connect.setDoInput(true);
			connect.setDoOutput(true);
			connect.setUseCaches(false);
			connect.setRequestMethod("POST");
//			connect.setRequestMethod("GET");
			connect.setRequestProperty("Connection","keep-alive");
			connect.setRequestProperty("Carset",HttpService.DEFAULT_ENCODING);
			connect.setRequestProperty("Content-type","multipart/form-data"+"; boundary="+boundary);
			
			StringBuffer buffer = new StringBuffer();
			//------------------
			if(params!=null){
				listener.onStatus("�ϴ���������...");
				
				for(Map.Entry<String, String> data :params.entrySet()){
					buffer.append(prefix).append(boundary).append(crlf);
					buffer.append("Content-Disposition:form-data; name=\""+data.getKey()+"\"").append(crlf);
					buffer.append("Content-Type: text/plain; charset=" + HttpService.DEFAULT_ENCODING).append(crlf);
					buffer.append("Content-Transfer-Encoding: 8bit").append(crlf);
					buffer.append(crlf);
					buffer.append(data.getValue()).append(crlf);
				}
			}
			//------------------
			dos = new DataOutputStream(connect.getOutputStream());
			
			dos.write(buffer.toString().getBytes());
			//-----------------------------
			if(!isCancel&&files!=null){
				for(Map.Entry<String, File> file : files.entrySet()){
					listener.onStatus("�ϴ��ļ�..."+file.getKey());
					StringBuffer buffer2 = new StringBuffer();
					buffer2.append(prefix).append(boundary).append(crlf);
					buffer2.append("Content-Disposition: form-data; name=\"upload\"; filename=\""+file.getKey()+"\"").append(crlf);
					buffer2.append("Content-Type: application/octet-stream; carset=" + HttpService.DEFAULT_ENCODING).append(crlf);
					buffer2.append(crlf);
					dos.write(buffer2.toString().getBytes());
					
					InputStream is = new FileInputStream(file.getValue());
					byte[] temp = new byte[FileUtils.DEFAULT_BUFFER_SIZE];
					int len = 0;
					while((len = is.read(temp))!=-1){
						dos.write(temp,0,len);
					}
					is.close();
					dos.write(crlf.getBytes());
				}
			}
			
			byte[] endTag = (prefix+boundary+prefix+crlf).getBytes();
			
			dos.write(endTag); 
			dos.flush();
			
			//------------------------
			//��Ӧ���Ƿ���ok
			
			if(!isCancel&&connect.getResponseCode()==HttpURLConnection.HTTP_OK){
				
				final ResultHandler resultHandler = new ResultHandler();
				
				XmlParser parser = new XmlParserImpl(resultHandler, new XmlParserCallback() {
					@Override
					public void onFinish() {
						listener.onFinish(resultHandler.getResultInfo());
					}
					@Override
					public void onError(String error) {
						listener.onError(error);
					}
				});
				
				
				parser.parse(new InputSource(connect.getInputStream()));
			}else{
				listener.onError("ִ�д��룺"+connect.getResponseCode());
			}
			
//			BufferedReader	reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
//			
//			StringBuilder builder = new StringBuilder();
//			String len = null;
//			while ((len = reader.readLine())!=null) {
//				builder.append(buffer);
//			}
//			LogUtils.logI("���س��ȣ�"+connect.getContentLength()+"���أ�"+builder.toString());
			
		
			
			
		} catch (MalformedURLException e) {
			listener.onError("�쳣��"+e.getMessage());
			LogUtils.logE(CLAZZ,"�쳣��"+e.getMessage());
		} catch (IOException e) {
			listener.onError("IO�쳣��"+e.getMessage());
			LogUtils.logE(CLAZZ,"IO�쳣��"+e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				if(dos!=null){
					dos.close();
				}
				if(br!=null){
					br.close();
				}
				if(connect!=null){
					connect.disconnect();
				}
			}catch (IOException e) {
				listener.onError("�ر����쳣��"+e.getMessage());
				LogUtils.logE(CLAZZ,"�ر�������");
				dos = null;
				br = null;
			}
		}
		
	}

	//-----------------------------------------
	
	@Override
	public void setCancel(boolean isCancel) {
		
		this.isCancel = isCancel;
	}

	
	
}
