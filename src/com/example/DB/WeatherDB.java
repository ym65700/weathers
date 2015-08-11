package com.example.DB;

import java.util.ArrayList;
import java.util.List;

import com.example.model.City;
import com.example.model.County;
import com.example.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeatherDB {
	/*
	 * ���ݿ�����
	 */
	private static final String DB_NAME = "weather";
	/*
	 * ���ݿ�汾
	 */
	private static final int VERSION = 1;
	private SQLiteDatabase db;
	private static WeatherDB weatherDB;

	/*
	 * �����췽��˽�л�
	 */
	private WeatherDB(Context context) {
		SQLiteHelper dbHelper = new SQLiteHelper(context, DB_NAME, null,
				VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/*
	 * ��ȡweatherDBʵ��
	 */
	public synchronized static WeatherDB getInstance(Context context) {
		if (weatherDB == null) {
			weatherDB = new WeatherDB(context);
		}
		return weatherDB;

	}

	/*
	 * ��provinceʵ���������ݿ�
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/*
	 * �����ݿ��ж�ȡȫ������ʡ����Ϣ
	 */
	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));

				list.add(province);

			} while (cursor.moveToNext());
		}

		return list;
	}

	/*
	 * ��cityʵ�����浽���ݿ�
	 */
	public void saveCity(City city) {
		ContentValues values = new ContentValues();
		values.put("city_name", city.getCityName());
		values.put("city_code", city.getCityCode());
		values.put("province_id", city.getProvinceid());
		db.insert("City", null, values);
	}

	/*
	 * �����ݿ��ȡ ĳ ʡ�����г���
	 */
	public List<City> loadCity(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id=?",
				new String[] { String.valueOf(provinceId) }, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setProvinceid(provinceId);
				list.add(city);
			} while (cursor.moveToNext());

		}
		return list;
	}

	/*
	 * ��countyʵ���������ݿ�
	 */
	public void saveCounty(County county) {
		ContentValues values = new ContentValues();
		values.put("county_name", county.getCountyName());
		values.put("county_code", county.getCountyCode());
		values.put("city_id", county.getCityid());
		db.insert("County", null, values);
	}

	/*
	 * �����ݿ��ȡ ĳ ����������
	 */
	public List<County> loadCounty(int cityId) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {

				County county = new County();
				county.setCityid(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor
						.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor
						.getColumnIndex("county_code")));
				county.setCityid(cityId);
				
				list.add(county);
				
			} while (cursor.moveToNext());
		}

		return list;
	}

}
