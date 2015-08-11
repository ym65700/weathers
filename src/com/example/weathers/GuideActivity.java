package com.example.weathers;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.adapter.GuideAdapter;


public class GuideActivity extends Activity implements OnPageChangeListener {
	public final String TAG = "GuidePage";
	/** 保存引导页布局 */
	private List<View> views;
	/** 显示引导页中所有页面的控件 */
	private ViewPager viewPager;
	/** 页面中的点 */
	private ImageView[] dots;
	private Button button;
	
	int[] ids = new int[] { R.id.imageView1, R.id.imageView2, R.id.imageView3 ,R.id.imageView4};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		initViews();
		initdot();
	}

	/**
	 * 初始化操作
	 */
	public void initViews() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		LayoutInflater inflater = getLayoutInflater();

		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.guide_one, null));
		views.add(inflater.inflate(R.layout.guide_two, null));
		views.add(inflater.inflate(R.layout.guide_three, null));
		views.add(inflater.inflate(R.layout.guide_four, null));
		viewPager.setAdapter(new GuideAdapter(views, this));
		viewPager.setOnPageChangeListener(this);
		button = (Button) views.get(3).findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GuideActivity.this,
						SelectArea.class);
				startActivity(intent);
				finish();

			}
		});

	}

	public void initdot() {
		dots = new ImageView[views.size()];
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) findViewById(ids[i]);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < ids.length; i++) {
			if (arg0 == i) {
				dots[i].setImageResource(R.drawable.dot);
			} else {
				dots[i].setImageResource(R.drawable.dot1);
			}
		}
	}

}
