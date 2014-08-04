package org.king.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;

/**
 * ϵͳ������
 * 
 * @author Jenly
 * @date 2013-3-25
 */
public class SystemUtils {
	
	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * ʱ���ʽ��
	 * @param time
	 * @return
	 */
	public static String DateFormat(long time){
		return  sdf.format(time);
	}
	
	public static String DateFormat(Calendar cal){
		return  sdf.format(cal.getTime());
	}
	
	public static String DateFormat(Date date){
		return  sdf.format(date.getTime());
	}
	
	/**
	 * ����
	 * @param activity
	 * @param requestCode
	 * @param fileFullPath
	 * 
	 * <uses-permission android:name="android.permission.CAMERA" />
	 */
	public static void imageCapture(Activity activity,int requestCode,String fileFullPath){
		if(StringUtils.getString(fileFullPath)==null){
			return;
		}
		String path = fileFullPath.substring(0, fileFullPath.lastIndexOf('/')+1);
		String fileName = fileFullPath.substring(fileFullPath.lastIndexOf('/')+1);
		imageCapture(activity, requestCode, path, fileName);
		
	}
	
	public static void imageCapture(Activity activity,int requestCode,String path,String fileName){
		
		LogUtils.logI("·����"+path+"�ļ�����"+fileName);
		
		if(path.indexOf('/')==-1){
			path+='/';
		}
		File dirFile = new File(path);
		if(!dirFile.exists()){
			FileUtils.createNewDir(path);
			LogUtils.logI("����·����"+path);
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(path+fileName);
		Uri uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity.startActivityForResult(intent,requestCode);
	}
	
	/**
	 * ¼��Ƶ
	 * @param activity
	 * @param requestCode
	 * @param fileFullPath
	 * 
	 * <uses-permission android:name="android.permission.CAMERA" />
	 */
	public static void videoCapture(Activity activity,int requestCode,String fileFullPath){
		if(StringUtils.getString(fileFullPath)==null){
			return;
		}
		String path = fileFullPath.substring(0, fileFullPath.lastIndexOf('/')+1);
		String fileName = fileFullPath.substring(fileFullPath.lastIndexOf('/')+1);
		videoCapture(activity, requestCode, path, fileName);
		
	}
	
	public static void videoCapture(Activity activity,int requestCode,String path,String fileName){
		
		if(path.indexOf('/')==-1){
			path+='/';
		}
		File dirFile = new File(path);
		if(!dirFile.exists()){
			FileUtils.createNewDir(path);
		}
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		File file = new File(path+fileName);
		Uri uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity.startActivityForResult(intent,requestCode);
	}
	
	/**
	 * ��绰
	 * @param context
	 * @param phoneNumber
	 * 
	 * <uses-permission android:name="android.permission.CALL_PHONE"/>
	 */
	public static void call(Context context,String phoneNumber){

		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));
		
		context.startActivity(intent);
		
	}
	
	/**
	 * ���÷����Ž���
	 * @param context
	 * @param phoneNumber
	 */
	public static void sendSMS(Context context,String phoneNumber){
		
		Intent intent = new Intent();
		
		intent.setAction(Intent.ACTION_SENDTO);
		
		intent.setData(Uri.parse("smsto:"+phoneNumber));
		
		context.startActivity(intent);
	}
	
	/**
	 * ������
	 * @param context
	 * @param phoneNumber
	 * @param msg
	 * 
	 * <uses-permission android:name="android.permission.SEND_SMS"/>
	 */
	public static void sendSMS(String phoneNumber,String msg){
		
		SmsManager sm = SmsManager.getDefault();
		
		List<String> msgs = sm.divideMessage(msg);
		
		for(String text:msgs){
			sm.sendTextMessage(phoneNumber, null, text,null, null);
		}
		
	}
	
	/**
	 * �ж��Ƿ��д洢��
	 * @return
	 */
	public static boolean hasSDCard(){
		
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}
	
	/**
	 * �ж��Ƿ���wifi����
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context){
		
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		
		if(networkInfo!=null&&ConnectivityManager.TYPE_WIFI==networkInfo.getType()){
			return true;
		}
		return false;
	}
	
	/**
	 * �ж��Ƿ�����wifi����
	 * @param context
	 * @return
	 * 
	 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	 * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
	 */
	public static boolean isWifiEnabled(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}
	
	/**
	 * ����wifi��״̬
	 * @param context
	 * @param isEnabled
	 */
	public static void setWifiEnabled(Context context,boolean isEnabled){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(isEnabled);
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @param context
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context){
		
		PackageManager manager = context.getPackageManager();
		
		try {
			return manager.getPackageInfo(context.getPackageName(),0);
		} catch (NameNotFoundException e) {
			LogUtils.logE("��ȡ����Ϣ����"+e.getMessage());
		}
		
		return null;
	}
	
	
	
	/**
	 * ��װapk
	 * @param context
	 * @param file
	 */
	public static void installApk(Context context,File file){
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		
		Uri uriData = Uri.fromFile(file);
		String type = "application/vnd.android.package-archive";
		
		intent.setDataAndType(uriData, type);
		context.startActivity(intent);
	}
	
	/**
	 * ж��apk
	 * @param context
	 * @param packageName
	 */
	public static void uninstallApk(Context context,String packageName){
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		
		Uri uriData = Uri.parse("package:" + packageName);
		intent.setData(uriData);
		context.startActivity(intent);
	}
	
	
	public static void uninstallApk(Context context){
		uninstallApk(context,context.getPackageName());
	}
	
	
	/**
	 * ִ�������ÿ���һ���̣߳�
	 * @param cmd
	 * @return
	 */
	public static String execCommand(String cmd){
		
		StringBuilder sb = null;
		BufferedReader br = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			sb = new StringBuilder();
			String temp=null;
			while((temp = br.readLine())!=null){
				sb.append(temp+"\n");
			}
			process.waitFor();
		} catch (IOException e) {
			LogUtils.logE("�쳣��"+e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(br!=null){
					br.close();
				}
				if(process!=null)
				process.destroy();
			} catch (IOException e) {
				LogUtils.logE("IO�쳣!");
			}
		}
		
		return sb.toString(); 
	}
	/**
	 * ����ROOTȨ�޺�ִ�������ÿ���һ���̣߳�
	 * @param cmd	(pm install -r *.apk)
	 * @return
	 */
	public static boolean runRootCommand(String cmd) { 
        Process process = null; 
        DataOutputStream os = null; 
		BufferedReader br = null;
		StringBuilder sb = null;
            try { 
            process = Runtime.getRuntime().exec("su"); 
            os = new DataOutputStream(process.getOutputStream()); 
            os.writeBytes(cmd+"\n"); 
            os.writeBytes("exit\n"); 
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			sb = new StringBuilder();
			String temp=null;
			while((temp = br.readLine())!=null){
				sb.append(temp+"\n");
				if("Success".equalsIgnoreCase(temp)){
					LogUtils.logE("----------"+sb.toString());
					return true; 
				}
			}
            process.waitFor(); 
            } catch (Exception e) { 
                    LogUtils.logE("�쳣: "+e.getMessage()); 
            }finally { 
                try { 
                    if (os != null) { 
                    	os.flush(); 
                        os.close(); 
                    } 
                    if(br!=null){
                    	br.close();
                    }
                    process.destroy(); 
                } catch (Exception e) { 
                       
                	return false; 
                } 
            } 
            return false; 
    } 
	
	
} 
	

