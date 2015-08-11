package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义PagerAdapter，实现引导页
 */
public class GuideAdapter extends PagerAdapter {
	/** 界面列表 */
	List<View> views;
	/** 上下文内容 */
	Context context;

	public GuideAdapter(List<View> views, Context context) {
		this.views = views;
		this.context = context;
	}

	/*
	 * 获得当前界面数(non-Javadoc)
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
	 * 销毁arg1位置的界面(non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.View,
	 * int, java.lang.Object)
	 */
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	/*
	 * 初始化arg1位置的界面(non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view
	 * .View, int)
	 */
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position));
		return views.get(position);
	}

}
