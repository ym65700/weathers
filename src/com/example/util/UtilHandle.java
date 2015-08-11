package com.example.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.DB.WeatherDB;
import com.example.model.City;
import com.example.model.County;
import com.example.model.Province;

/*
 * 解析和处理服务器返回的省市县数据
 */
public class UtilHandle {
	public synchronized static boolean handleProvincesResponse(WeatherDB db,
			String response) {

		if (!TextUtils.isEmpty(response)) {
			String[] provinces = response.split(",");
			if (provinces != null && provinces.length > 0) {
				for (String p : provinces) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);

					db.saveProvince(province);
				}
				return true;
			}

		}

		return false;
	}

	public static boolean handleCityResponse(WeatherDB db, String response,
			int provinceid) {
		if (!TextUtils.isEmpty(response)) {
			String[] citys = response.split(",");
			if (citys != null && citys.length > 0) {
				for (String s : citys) {
					String[] array = s.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceid(provinceid);

					db.saveCity(city);

				}
			}
			return true;
		}

		return false;
	}

	public static boolean handleCountyResponse(WeatherDB db, String response,
			int cityid) {
		if (!TextUtils.isEmpty(response)) {
			String[] countys = response.split(",");
			if (countys != null && countys.length > 0) {
				for (String s : countys) {
					String[] array = s.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityid(cityid);

					// 将解析出来的数据存储到County表
					db.saveCounty(county);
				}
				return true;
			}

		}
		return false;
	}

	public static void handleWeatherResponse(Context context, String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);

			JSONObject retData = jsonObject.getJSONObject("retData");
			String cityName = retData.getString("city");
			String weatherCode = retData.getString("cityid");

			JSONObject today = retData.getJSONObject("today");
			String today_week = today.getString("week");
			String today_curTemp = today.getString("curTemp");
			String today_temp1 = today.getString("lowtemp");
			String today_temp2 = today.getString("hightemp");
			String today_type = today.getString("type");
			String today_date = today.getString("date");

			JSONArray forecast = retData.getJSONArray("forecast");

			JSONObject forecast1 = forecast.getJSONObject(0);
			String forecast1_week = forecast1.getString("week");
			String forecast1_temp1 = forecast1.getString("lowtemp");
			String forecast1_temp2 = forecast1.getString("hightemp");
			String forecast1_type = forecast1.getString("type");

			String forecast1_fengxiang = forecast1.getString("fengxiang");

			JSONObject forecast2 = forecast.getJSONObject(1);
			String forecast2_week = forecast2.getString("week");
			String forecast2_temp1 = forecast2.getString("lowtemp");
			String forecast2_temp2 = forecast2.getString("hightemp");
			String forecast2_type = forecast2.getString("type");

			String forecast2_fengxiang = forecast2.getString("fengxiang");

			JSONObject forecast3 = forecast.getJSONObject(2);
			String forecast3_week = forecast3.getString("week");
			String forecast3_temp1 = forecast3.getString("lowtemp");
			String forecast3_temp2 = forecast3.getString("hightemp");
			String forecast3_type = forecast3.getString("type");

			String forecast3_fengxiang = forecast3.getString("fengxiang");

			saveWeatherInfo(context, cityName, weatherCode, today_week,
					today_curTemp, today_temp1, today_temp2, today_type,
					today_date, forecast2_week, forecast2_temp1,
					forecast2_temp2, forecast2_type, forecast2_fengxiang,
					forecast3_week, forecast3_temp1, forecast3_temp2,
					forecast3_type, forecast3_fengxiang, forecast1_week,
					forecast1_temp1, forecast1_temp2, forecast1_type,
					forecast1_fengxiang);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 将服务器返回的所有天气信息存储到SharedPreferences文件中。
	 */
	public static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String today_week, String today_curTemp,
			String today_temp1, String today_temp2, String today_type,
			String today_date, String forecast2_week, String forecast2_temp1,
			String forecast2_temp2, String forecast2_type,
			String forecast2_fengxiang, String forecast3_week,
			String forecast3_temp1, String forecast3_temp2,
			String forecast3_type, String forecast3_fengxiang,
			String forecast1_week, String forecast1_temp1,
			String forecast1_temp2, String forecast1_type,
			String forecast1_fengxiang) {

		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("today_week", today_week);
		editor.putString("today_temp1", today_temp1);
		editor.putString("today_temp2", today_temp2);
		editor.putString("today_type", today_type);
		editor.putString("today_date", today_date);
		editor.putString("today_curTemp", today_curTemp);

		editor.putString("forecast1_week", forecast1_week);
		editor.putString("forecast1_temp1", forecast1_temp1);
		editor.putString("forecast1_temp2", forecast1_temp2);
		editor.putString("forecast1_type", forecast1_type);
		editor.putString("forecast1_fengxiang", forecast1_fengxiang);

		editor.putString("forecast2_week", forecast2_week);
		editor.putString("forecast2_temp1", forecast2_temp1);
		editor.putString("forecast2_temp2", forecast2_temp2);
		editor.putString("forecast2_type", forecast2_type);
		editor.putString("forecast2_fengxiang", forecast2_fengxiang);

		editor.putString("forecast3_week", forecast3_week);
		editor.putString("forecast3_temp1", forecast3_temp1);
		editor.putString("forecast3_temp2", forecast3_temp2);
		editor.putString("forecast3_type", forecast3_type);
		editor.putString("forecast3_fengxiang", forecast3_fengxiang);

		editor.commit();
	}

}
