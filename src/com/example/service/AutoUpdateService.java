package com.example.service;



import com.example.receiver.AutoUpdateReceiver;
import com.example.util.HttpCallbackListener;
import com.example.util.HttpUtil;
import com.example.util.UtilHandle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				updateWeather();
			}
		}).start();
		//传入的参数是Context.ALARM_SERVICE 获取一个AlarmManager
		
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 8 * 60 * 60 * 1000; 
		//获取到系统开机至今所经历时间的毫秒数
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		
		Intent i = new Intent(this, AutoUpdateReceiver.class);
		//发送广播
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		//ELAPSED_REALTIME_WAKEUP 表示让定时任务的触 发时间从系统开机开始算起，会唤醒CPU
		
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}

	private void updateWeather() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weather_code", "");
		String address = "http://www.weather.com.cn/data/cityinfo/"
				+ weatherCode + ".html";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {
				UtilHandle.handleWeatherResponse(AutoUpdateService.this,
						response);
			}

			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
		});
	}

}
