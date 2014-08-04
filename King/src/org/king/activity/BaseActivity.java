package org.king.activity;


import org.king.R;
import org.king.utils.DialogUtils;
import org.king.utils.LogUtils;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * �������
 * @author Jenly
 *
 */
public class BaseActivity extends Activity{
	
	/**
	 * ������
	 */
	protected Context context = this;
	
	/**
	 * ������
	 */
	protected Handler handler = new Handler();
	
	/**
	 * ��������ʾ��
	 */
	protected ProgressDialog progressDialog = null;
	
	/**
	 * �Ƿ����
	 */
	protected boolean isFinish = false;
	
	/**
	 * �Ƿ�ע���˳��㲥
	 */
	protected boolean isRegister = false;
	
	/**
	 * �Ƿ���ʾ����
	 */
	protected static boolean isShowTitle = false;
	
	/**
	 * �Ƿ���ʾ���ȶ��� 
	 */
	protected static boolean isTransition = false;
	/**
	 * �˳���ʽ��ͼ��Ϊ
	 */
	private static final String ACTION_EXIT = "jenly_action_exit_";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!isShowTitle){
			//ȥ��������
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		
	}
	
	/**
	 * ���ذ�ť�����ӿ�ʵ��
	 */
	protected OnClickListener onBackListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	//-------------------------------------------------
	/**
	 * ������
	 * @param message
	 */
	protected void showToast(String message){
		DialogUtils.showToast(context, message,handler);
	}
	
	protected void showToast(String message,int duration){
		DialogUtils.showToast(context, message,duration,handler);
	}
	//-------------------------------------------------
	/**
	 * ������ʾ��
	 */
	//-------------------
	protected void showProgressDialog(){
		progressDialog = DialogUtils.showProgressDialog(context);
	}
	//-------------------
	protected void showProgressDialog(boolean isCancelable){
		progressDialog = DialogUtils.showProgressDialog(context,isCancelable);
	}
	//-------------------
	protected void showProgressDialog(String message){
		progressDialog = DialogUtils.showProgressDialog(context, message,false);
	}
	//-------------------
	protected void showProgressDialog(String message,boolean isCancelable){
		progressDialog = DialogUtils.showProgressDialog(context, message,isCancelable);
	}
	//-------------------
	protected void dismissProgressDialog(){
		DialogUtils.dismissProgressDialog(progressDialog, handler);
	}
	
	//-------------------------------------------------
	/**
	 * ��־
	 * @param message
	 */
	//-------------------
	protected void logI(String message){
		LogUtils.logI(message);
	}
	//-------------------
	protected void logV(String message){
		LogUtils.logV(message);
	}
	//-------------------
	protected void logD(String message){
		LogUtils.logD(message);
	}
	//-------------------
	protected void logW(String message){
		LogUtils.logW(message);
	}
	//-------------------
	protected void logE(String message){
		LogUtils.logE(message);
	}
	//-------------------
	protected void print(String message){
		LogUtils.print(message);
	}
	//-------------------
	protected void println(String message){
		LogUtils.println(message);
	}
	
	//-------------------------------------------------
	/**
	 * �������
	 * @param clazz
	 */
	//-------------------
	protected void startActivity(Class<?> clazz){
		startActivity(new Intent(context,clazz));
	}
	//-------------------
	protected void startActivity(Class<?> clazz,int flags){
		Intent intent = new Intent(context,clazz);
		intent.setFlags(flags);
		startActivity(intent);
	}
	
	//-------------------
	protected void startActivityFinish(Class<?> clazz){
		startActivity(new Intent(context,clazz));
		super.finish();
		
	}
	//-------------------
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		if(isTransition)
		overridePendingTransition(R.anim.from_right_in,R.anim.to_left_out);
	}
	@Override
	public void finish() {
		super.finish();
		if(isTransition)
		overridePendingTransition(R.anim.from_left_in,R.anim.to_right_out);
	}
	
	//-------------------------------------------------
	/**
	 * �����˳��㲥
	 */
	protected void exit(){
		Intent intent = new Intent(ACTION_EXIT);
		sendBroadcast(intent);
	}
	
	/**
	 * �˳��Ի���
	 */
	protected void exitDialog(){
		DialogUtils.showAlertDialog(context, "ȷ���˳���", "ȷ��","ȡ��", handler,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					exit();
					break;

				default:
					break;
				}
				
			}
		});
	}
	
	/**
	 * �˳��㲥
	 */
	private BroadcastReceiver exitReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			isFinish = true;
			//�ӵ��㲥�˳�
			finish();
		}
	};
	
	
	protected void onResume() {
		super.onResume();
		try{
			if(!isRegister){
				IntentFilter filter = new IntentFilter();
				filter.addAction(ACTION_EXIT);
				//ע��㲥
				registerReceiver(exitReceiver, filter);
				isRegister = true;
			}
		}catch(Exception e){
			logE("�쳣��"+e.getMessage());
		}
		
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(isFinish && isRegister && exitReceiver!=null){
			//ע���㲥
			unregisterReceiver(exitReceiver);
			isRegister = false;
		}
			
	}
	
	
	

}
