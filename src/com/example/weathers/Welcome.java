package com.example.weathers;


import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

/**
 * ��ʼ��������
 */
public class Welcome extends Activity {
	/** �����ӳ�ʱ�� */
	private static final int TIME = 2000;
	/** ��ת��������int�ͱ�� */
	private static final int GO_HOME = 1;
	/** ��ת������ҳint�ͱ�� */
	private static final int GO_GUIDE = 2;
	/** �ж��Ƿ��ǵ�һ������ */
	private boolean isFirst = false;
	
	/**
	 * ��ת����ͬ�Ľ���`
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
	 * ��ʼ������
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
	 * ȥ��ҳ
	 */
	private void gohome() {
		Intent intent = new Intent(Welcome.this, SelectArea.class);
		startActivity(intent);
		finish();
	}
	/**
	 * ȥ����ҳ
	 */
	private void goguide() {
		Intent intent = new Intent(Welcome.this, GuideActivity.class);
		startActivity(intent);
		finish();
	}

}
