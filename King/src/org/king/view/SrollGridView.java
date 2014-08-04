package org.king.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ������ͼ
 * PS:������������ͼ����ʱ�̳����������߶���ʾ���������⣩
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
