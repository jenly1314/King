package org.king.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 网格视图
 * PS:下拉与网格视图共存时继承这个（解决高度显示不正常问题）
 *
 */
public class SrollGridView  extends GridView{


	public SrollGridView(Context context) {
		super(context);
	}
	
	public SrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,    
                MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	
}
