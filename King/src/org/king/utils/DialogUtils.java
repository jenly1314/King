package org.king.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 各种提示框工具类
 * 
 * @author jenly
 * @date 2013-3-25
 */

public class DialogUtils {
	
	public static final int one = 0X01;
	public static final int two = 0X02;
	public static final int three = 0X03;
	
	public static final int DEFAULT_ICON = -1;
	public static final String BTN_NULL = "";
	public static final String DEFAULT_BTN_OK = "确定";
	public static final String DEFAULT_BTN_CANCEL = "取消";
	public static final String BTN_YES = "是";
	public static final String BTN_NO = "否";
	public static final String DEFAULT_BTN_CLOSE = "关闭";
	public static final String DEFAULT_DIALOG_TITLE = "提示";
	public static final String DEFAULT_CHOICE_DIALOG_TITLE = "选择";
	public static final String DEFAULT_PROGRESS_MESSAGE = "正在处理...";
	
	
	
	//=========================	Toast提示框
	/**
	 * 弹出框
	 * @param context
	 * @param message
	 */
	public static void showToast(final Context context,final String message){
		showToast(context, message,Toast.LENGTH_SHORT,null);
	}
	
	//-------------------------------
	public static void showToast(final Context context,final String message,int duration){
		showToast(context, message,duration, null);
	}
	
	//-------------------------------
	public static void showToast(final Context context,final String message,Handler handler){
		showToast(context, message,Toast.LENGTH_SHORT,handler);
	}
	//-------------------------------
	public static void showToast(final Context context,final String message,final int duration,Handler handler){
		if(!TextUtils.isEmpty(message)){
			if(handler!=null){
				handler.post(new Runnable() {
					public void run() {
						Toast.makeText(context, message, duration).show();
					}
				});
				
			}else {
				Toast.makeText(context, message, duration).show();
			}
		}
		
	}
	
	//========================= 警告对话框
	
	//------------------------------------------------	默认图标、标题、按钮名
	
	//-------------------------------
	/**
	 * 警告对话框
	 * @param context
	 * @param message
	 * @param btnNum
	 * @param listener
	 */
	public static void showAlertDialog(final Context context,final String message,final int btnNum,final OnClickListener listener){
		showAlertDialog(context, DEFAULT_ICON, DEFAULT_DIALOG_TITLE, message, DEFAULT_BTN_OK, BTN_NULL,DEFAULT_BTN_CANCEL,btnNum,null, listener);
	}
	
	//-------------------------------	
	public static void showAlertDialog(final Context context,final String message,final int btnNum,Handler handler,final OnClickListener listener){
		showAlertDialog(context, DEFAULT_ICON, DEFAULT_DIALOG_TITLE, message, DEFAULT_BTN_OK, BTN_NULL,DEFAULT_BTN_CANCEL,btnNum,handler, listener);
	}
	//-------------------------------	
	public static void showAlertDialog(final Context context,final String message,final String btnNeutral, int btnNum,Handler handler,final OnClickListener listener){
		showAlertDialog(context, DEFAULT_ICON, DEFAULT_DIALOG_TITLE, message, DEFAULT_BTN_OK, btnNeutral,DEFAULT_BTN_CANCEL,btnNum,handler, listener);
	}
	
	//------------------------------------------------	默认图标和标题
	
	//-------------------------------	一个按钮
	public static void showAlertDialog(final Context context,final String message,final String btnOk,Handler handler,final OnClickListener listener){
		showAlertDialog(context, DEFAULT_ICON, DEFAULT_DIALOG_TITLE, message, btnOk,BTN_NULL,DEFAULT_BTN_CANCEL,one,handler, listener);
	}
	//-------------------------------	两个按钮
	public static void showAlertDialog(final Context context,final String message,final String btnOk,final String btnCancel,Handler handler,final OnClickListener listener){
		showAlertDialog(context, DEFAULT_ICON, DEFAULT_DIALOG_TITLE, message, btnOk, BTN_NULL,btnCancel,two,handler, listener);
	}
	
	//-------------------------------	三个按钮
	public static void showAlertDialog(final Context context,final String message,final String btnOk,final String btnNeutral,final String btnCancel,Handler handler,final OnClickListener listener){
		showAlertDialog(context, DEFAULT_ICON, DEFAULT_DIALOG_TITLE, message, btnOk, btnNeutral, btnCancel,three, handler, listener);
	}
	
	//------------------------------------------------	
	
	//-------------------------------	一个按钮
	public static void showAlertDialog(final Context context,final int icon,final String title,final String message,final String btnOk,Handler handler,final OnClickListener listener){
		showAlertDialog(context, icon, title, message, btnOk,BTN_NULL,DEFAULT_BTN_CANCEL,one,handler, listener);
	}
	//-------------------------------	两个按钮
	public static void showAlertDialog(final Context context,final int icon,final String title,final String message,final String btnOk,final String btnCancel,Handler handler,final OnClickListener listener){
		showAlertDialog(context, icon, title, message, btnOk, BTN_NULL,btnCancel,two,handler, listener);
	}
	
	//-------------------------------	三个按钮
	public static void showAlertDialog(final Context context,final int icon,final String title,final String message,final String btnOk,final String btnNeutral,final String btnCancel,Handler handler,final OnClickListener listener){
		showAlertDialog(context, icon, title, message, btnOk, btnNeutral, btnCancel,three, handler, listener);
	}
	
	//-------------------------------
	public static void showAlertDialog(final Context context,final int icon,final String title,final String message,final String btnOk,final String btnNeutral,final String btnCancel,final int btnNum,Handler handler,final OnClickListener listener){
		if(handler!=null){
			handler.post(new Runnable() {
				@Override
				public void run() {
					
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					if(DEFAULT_ICON!=icon){
						builder.setIcon(icon);
					}
					
					builder.setTitle(title!=null?title:DEFAULT_DIALOG_TITLE);
					builder.setMessage(message);
					switch (btnNum) {
					case three:
						builder.setNeutralButton(btnNeutral, listener);
					case two:
						builder.setNegativeButton(btnCancel, listener);
					default:
						builder.setPositiveButton(btnOk, listener);
						break;
					}
					
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			});
			
		}else {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			if(DEFAULT_ICON!=icon){
				builder.setIcon(icon);
			}
			
			builder.setTitle(title!=null?title:DEFAULT_DIALOG_TITLE);
			builder.setMessage(message);
			switch (btnNum) {
			case three:
				builder.setNeutralButton(btnNeutral, listener);
			case two:
				builder.setNegativeButton(btnCancel, listener);
			default:
				builder.setPositiveButton(btnOk, listener);
				break;
			}
			
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}
	
	
	//========================= 进度消息框
	
	//-------------------------------
	/**
	 * 进度消息框
	 * @param context
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context){
		return showProgressDialog(context,DEFAULT_PROGRESS_MESSAGE,false);
	}
	//-------------------------------
	public static ProgressDialog showProgressDialog(Context context,boolean isCancelable){
		return showProgressDialog(context,DEFAULT_PROGRESS_MESSAGE,isCancelable);
	}
	
	//-------------------------------
	public static ProgressDialog showProgressDialog(Context context,String message,boolean isCancelable){
		ProgressDialog progressDialog = ProgressDialog.show(context, null, message);
		progressDialog.setCancelable(isCancelable);
		return progressDialog;
	}
	
	//-------------------------------
	public static ProgressDialog showProgressDialog(Context context,String title,String message){
		return ProgressDialog.show(context, title, message);
	}
	
	//-------------------------------
	public static void updateProgressDialog(final ProgressDialog progressDialog,final String message){
		updateProgressDialog(progressDialog,null,message, null);
	}
	
	//-------------------------------
	public static void updateProgressDialog(final ProgressDialog progressDialog,final String message,final Handler handler){
		updateProgressDialog(progressDialog,null,message, handler);
	}
	
	//-------------------------------
	public static void updateProgressDialog(final ProgressDialog progressDialog,final String title,final String message,final Handler handler){
		if(handler!=null){
			handler.post(new Runnable() {
				@Override
				public void run() {
					
					if(!TextUtils.isEmpty(title)){
						progressDialog.setTitle(title);
					}
					if(!TextUtils.isEmpty(title)){
						progressDialog.setMessage(message);
					}
				}
			});
			
		}else {
			
			if(!TextUtils.isEmpty(title)){
				progressDialog.setTitle(title);
			}
			if(!TextUtils.isEmpty(title)){
				progressDialog.setMessage(message);
			}
		}
	}
	
	//-------------------------------
	public static void dismissProgressDialog(final ProgressDialog progressDialog){
		dismissProgressDialog(progressDialog,null);
	}
	
	//-------------------------------
	public static void dismissProgressDialog(final ProgressDialog progressDialog,final Handler handler){
		try{
			
			if(progressDialog!=null){
				if(handler!=null){
					handler.post(new Runnable() {
						@Override
						public void run() {
							
							progressDialog.dismiss();
						}
					});
					
				}else{
					progressDialog.dismiss();
				}
			}
		}catch(NullPointerException e){
			LogUtils.logE("空指针异常！"+e.getMessage());
		}
	}
	
	
	
	//========================= 带进度条进度消息框
	
	//-------------------------------
	/**
	 * 带进度条消息框
	 * @param context
	 * @param progressDialog
	 * @param message
	 * @return
	 */
	public static ProgressDialog showProgressBarDialog(Context context,ProgressDialog progressDialog,String message){
		
		return showProgressBarDialog(context, progressDialog,DEFAULT_ICON,DEFAULT_DIALOG_TITLE,message);
	}
	
	//-------------------------------
	public static ProgressDialog showProgressBarDialog(Context context,ProgressDialog progressDialog,String title,String message){
		
		return showProgressBarDialog(context, progressDialog,DEFAULT_ICON,title,message);
	}
	
	//-------------------------------
	public static ProgressDialog showProgressBarDialog(Context context,ProgressDialog progressDialog,int icon,String title,String message){

		// 创建ProgressDialog对象  
        progressDialog = new ProgressDialog(context);  
        // 设置进度条风格，风格为长形  
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
        // 设置ProgressDialog 标题  
        progressDialog.setTitle(title);  
        // 设置ProgressDialog 提示信息  
        progressDialog.setMessage(message);
        if(icon!=DEFAULT_ICON){
        	// 设置ProgressDialog 标题图标  
        	progressDialog.setIcon(icon);  
        }
        // 设置ProgressDialog 进度条最大进度  
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        // 设置ProgressDialog 的进度条是否不明确  
        progressDialog.setIndeterminate(false);  
        // 设置ProgressDialog 是否可以按退回按键取消  
        progressDialog.setCancelable(true);  
        // 设置ProgressDialog 的一个Button    
//      dialog.setButton("取消", new DialogInterface.OnClickListener() {    
//            public void onClick(DialogInterface dialog, int i)    
//            {    
//                //点击“确定按钮”取消对话框    
//                dialog.cancel();    
//            }    
//        });   
        // 让ProgressDialog显示  
         progressDialog.show();  
        
         return progressDialog;
	}
	
	public static void updateProgressBarDialog(ProgressDialog progressDialog,int progress){
		updateProgressBarDialog(progressDialog, progress, null);
	}
	
	public static void updateProgressBarDialog(ProgressDialog progressDialog,int progress,String message){
		if(progressDialog.getMax()>=progress){
			progressDialog.setProgress(progress);
		}
		if(null!=message){
			progressDialog.setMessage(message);
		}
	}
	
	
	
	
	
	
	
}
