package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * �Զ���PagerAdapter��ʵ������ҳ
 */
public class GuideAdapter extends PagerAdapter {
	/** �����б� */
	List<View> views;
	/** ���������� */
	Context context;

	public GuideAdapter(List<View> views, Context context) {
		this.views = views;
		this.context = context;
	}

	/*
	 * ��õ�ǰ������(non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */

	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 == arg1;
	}

	/*
	 * ����arg1λ�õĽ���(non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.View,
	 * int, java.lang.Object)
	 */
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	/*
	 * ��ʼ��arg1λ�õĽ���(non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view
	 * .View, int)
	 */
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position));
		return views.get(position);
	}

}
