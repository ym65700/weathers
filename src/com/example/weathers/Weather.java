package com.example.weathers;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.Application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.service.AutoUpdateService;
import com.example.util.HttpCallbackListener;
import com.example.util.HttpUtil;
import com.example.util.UtilHandle;



public class Weather extends Activity {
	private LinearLayout weatherInfoLayout;
	/**
	 * 用于显示城市名 第一行代码——Android 514
	 */
	private TextView cityNameText;
	/**
	 * 用于显示发布时间
	 */
	private TextView publishText;
	/**
	 * 用于显示天气描述信息
	 */
	private TextView weatherDespText;
	/**
	 * 用于显示气温1
	 */
	private TextView temp1Text;
	/**
	 * 用于显示气温2
	 */
	private TextView temp2Text;
	/**
	 * 用于显示当前日期
	 */
	private TextView currentDateText;
	/**
	 * 切换城市按钮
	 */
	// private Button switchCity;
	/**
	 * 更新天气按钮
	 */
	// private Button refreshWeather;
	/*
	 * 头布局
	 */
	private RelativeLayout title;
	private RelativeLayout content;
	private ImageView switchCity;
	private ImageView refreshWeather;
	private TextView temp, week;

	private TextView weekTv1, weekTv2, weekTv3;

	private TextView temperatureTv1, temperatureTv2, temperatureTv3;
	private TextView climateTv1, climateTv2, climateTv3;
	private TextView windTv1, windTv2, windTv3;
	String countyCode, countyName;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather);
		title = (RelativeLayout) findViewById(R.id.title);
		switchCity = (ImageView) title.findViewById(R.id.title_city_manager);
		refreshWeather = (ImageView) title.findViewById(R.id.title_update_btn);

		cityNameText = (TextView) title.findViewById(R.id.title_city_name);

		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);

		// cityNameText = (TextView) findViewById(R.id.city_name);
		content = (RelativeLayout) findViewById(R.id.content);
		publishText = (TextView) content.findViewById(R.id.publish_text);
		temp = (TextView) content.findViewById(R.id.temp);
		weatherDespText = (TextView) content.findViewById(R.id.weather_desp);
		temp1Text = (TextView) content.findViewById(R.id.temp1);
		temp2Text = (TextView) content.findViewById(R.id.temp2);
		week = (TextView) content.findViewById(R.id.week);

		// currentDateText = (TextView) findViewById(R.id.current_date);

		// switchCity = (Button) findViewById(R.id.switch_city);
		// refreshWeather = (Button) findViewById(R.id.refresh_weather);
		String countyCode = getIntent().getStringExtra("county_code");
		String countyName = getIntent().getStringExtra("county_name");

		switchCity.setOnClickListener(new clickListener());
		refreshWeather.setOnClickListener(new clickListener());
		if (!TextUtils.isEmpty(countyCode)) {
			// 有县级代号时就去查询天气
			publishText.setText("同步中...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);

			queryWeatherCode(countyCode);
		} else {
			// 没有县级代号时就直接显示本地天气
			showWeather();
		}

	}

	public class clickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_city_manager:
				Intent intent = new Intent(Weather.this, SelectArea.class);
				intent.putExtra("from_weather_activity", true);
				startActivity(intent);
				finish();
				break;

			case R.id.title_update_btn:
				publishText.setText("同步中...");
				SharedPreferences sp = PreferenceManager
						.getDefaultSharedPreferences(Weather.this);
				String weathercode = sp.getString("weather_code", "");
				if (!TextUtils.isEmpty(weathercode)) {
					queryWeatherInfo(weathercode);
				}

				break;

			}

		}

	}

	/**
	 * 查询县级代号所对应的天气代号。
	 */
	private void queryWeatherCode(String countyCode) {

		String address = "http://www.weather.com.cn/data/list3/city"
				+ countyCode + ".xml";
		queryFromServer(address, "countyCode");
	}

	/**
	 * 查询天气代号所对应的天气。
	 */
	// "cityname=%E5%8C%97%E4%BA%AC&cityid=101010100"
	private void queryWeatherInfo(String weatherCode) {
		String httpArg = "cityname=" + countyName + "&" + "cityid="
				+ weatherCode;
		String address = "http://apis.baidu.com/apistore/weatherservice/recentweathers"
				+ "?" + httpArg;
		System.out.println(address.toString());
		queryFromServer(address, "weatherCode");

	}

	/**
	 * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
	 */
	private void queryFromServer(String address, final String type) {
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {

				// 从服务器返回的数据中解析出天气代号
				if ("countyCode".equals(type)) {
					if (!TextUtils.isEmpty(response)) {
						// 从服务器返回的数据中解析出天气代号
						String[] array = response.split("\\|");
						if (array != null && array.length == 2) {
							String weatherCode = array[1];

							queryWeatherInfo(weatherCode);
						}

					}
				} else if ("weatherCode".equals(type)) {
					// 处理服务器返回的天气信息
					UtilHandle.handleWeatherResponse(Weather.this, response);

					System.out.println(response.toString());
					runOnUiThread(new Runnable() {
						public void run() {
							showWeather();

						}
					});
				}

			}

			@Override
			public void onError(Exception e) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						publishText.setText("同步失败");
					}
				});

			}
		});
	}

	/**
	 * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上。
	 */
	private void showWeather() {
		// 第一部分
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		cityNameText.setText(sp.getString("city_name", ""));

		temp1Text.setText(sp.getString("today_temp1", ""));
		temp2Text.setText(sp.getString("today_temp2", "") );
		weatherDespText.setText(sp.getString("today_type", ""));
		publishText.setText(sp.getString("today_date", ""));
		temp.setText(sp.getString("today_curTemp", "") );
		week.setText(sp.getString("today_week", ""));

		// 第二部分
	
			

		RelativeLayout view1 = (RelativeLayout) findViewById(R.id.subitem1);
		RelativeLayout view2 = (RelativeLayout) findViewById(R.id.subitem2);
		RelativeLayout view3 = (RelativeLayout) findViewById(R.id.subitem3);
		//RelativeLayout view1=(RelativeLayout)findViewById(R.id.subitem1);
		weekTv1 = (TextView) view1.findViewById(R.id.week);
		weekTv2 = (TextView) view2.findViewById(R.id.week);
		weekTv3 = (TextView) view3.findViewById(R.id.week);

		weekTv1.setText(sp.getString("forecast1_week", ""));
	
		weekTv2.setText(sp.getString("forecast2_week", ""));
		weekTv3.setText(sp.getString("forecast3_week", ""));

		temperatureTv1 = (TextView) view1.findViewById(R.id.temperature);
		temperatureTv2 = (TextView) view2.findViewById(R.id.temperature);
		temperatureTv3 = (TextView) view3.findViewById(R.id.temperature);

		climateTv1 = (TextView) view1.findViewById(R.id.climate);
		climateTv2 = (TextView) view2.findViewById(R.id.climate);
		climateTv3 = (TextView) view3.findViewById(R.id.climate);

		windTv1 = (TextView) view1.findViewById(R.id.wind);
		windTv2 = (TextView) view2.findViewById(R.id.wind);
		windTv3 = (TextView) view3.findViewById(R.id.wind);

		 climateTv1.setText(sp.getString("forecast1_temp1", "")+"~"+sp.getString("forecast1_temp2", ""));
		 climateTv2.setText(sp.getString("forecast2_temp1", "")+"~"+sp.getString("forecast2_temp2", ""));
		 climateTv3.setText(sp.getString("forecast3_temp1", "")+"~"+sp.getString("forecast3_temp2", ""));
		
		 temperatureTv1.setText(sp.getString("forecast1_type", ""));
		 temperatureTv2.setText(sp.getString("forecast2_type", ""));
		 temperatureTv3.setText(sp.getString("forecast3_type", ""));
		
		 windTv1.setText(sp.getString("forecast1_fengxiang", ""));
		 windTv2.setText(sp.getString("forecast2_fengxiang", ""));
		 windTv3.setText(sp.getString("forecast3_fengxiang", ""));

		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);

		Intent intent = new Intent(this, AutoUpdateService.class);
		startService(intent);
	}

}
