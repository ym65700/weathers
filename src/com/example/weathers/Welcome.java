package com.example.weathers;


import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

/**
 * 开始启动界面
 */
public class Welcome extends Activity {
	/** 设置延迟时间 */
	private static final int TIME = 2000;
	/** 跳转到主界面int型标记 */
	private static final int GO_HOME = 1;
	/** 跳转到引导页int型标记 */
	private static final int GO_GUIDE = 2;
	/** 判断是否是第一次运行 */
	private boolean isFirst = false;
	
	/**
	 * 跳转到不同的界面`
	 */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GO_HOME:
				gohome();
				break;
			case GO_GUIDE:
				goguide();
				break;

			}
		};

	};

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		init();

	}
	/*
	 * 初始化操作
	 */
	private void init() {
		SharedPreferences sharedPreferences = getSharedPreferences("first",
				MODE_PRIVATE);
		isFirst = sharedPreferences.getBoolean("isFirst", true);
		if (!isFirst) {
			handler.sendEmptyMessageDelayed(GO_HOME, TIME);
		} else {
			handler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
			Editor editor = sharedPreferences.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
		}

	}
	/**
	 * 去首页
	 */
	private void gohome() {
		Intent intent = new Intent(Welcome.this, SelectArea.class);
		startActivity(intent);
		finish();
	}
	/**
	 * 去引导页
	 */
	private void goguide() {
		Intent intent = new Intent(Welcome.this, GuideActivity.class);
		startActivity(intent);
		finish();
	}

}
