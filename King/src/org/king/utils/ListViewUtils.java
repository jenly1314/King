package org.king.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * �б���ͼ������
 * 
 * @author Jenly
 * @date 2013-3-25
 */
public class ListViewUtils {
	
	
	 /**
	  * ���ݵ�����ͼ�����б���ͼ�߶�
	  * @param listView
	  */
	 public static void setListViewHeightBasedOnChildren(ListView listView) {
         ListAdapter listAdapter = listView.getAdapter(); 
         if (listAdapter == null) {
             return;
         }

         int totalHeight = 0;
         for (int i = 0; i < listAdapter.getCount(); i++) {
             View listItem = listAdapter.getView(i, null, listView);
             listItem.measure(0, 0);
             totalHeight += listItem.getMeasuredHeight();
         }

         ViewGroup.LayoutParams params = listView.getLayoutParams();
         params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))+6;
         listView.setLayoutParams(params);
     }

}
