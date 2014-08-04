package org.king.view;

import android.app.Dialog;
import android.content.Context;

public class BaseDialog extends Dialog{
	
	public BaseDialog(Context context) {
		super(context);
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
	}

	protected BaseDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

}
