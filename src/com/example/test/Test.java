package com.example.test;

import com.example.DB.SQLiteHelper;
import com.example.DB.WeatherDB;
import com.example.model.City;
import com.example.model.County;
import com.example.model.Province;
import com.example.util.HttpCallbackListener;
import com.example.util.HttpUtil;
import com.example.util.UtilHandle;
import com.example.weathers.SelectArea;
import com.example.weathers.Weather;

import android.test.AndroidTestCase;
/*
 *≤‚ ‘ 
 */
public class Test extends AndroidTestCase {

	Province province;
	City city;
	County county;
	
	
	public void testsavecity(){
		WeatherDB weatherDB=WeatherDB.getInstance(getContext());
		weatherDB.saveCity(city);
		
	}
	public void testwloadcity(){
		WeatherDB weatherDB=WeatherDB.getInstance(getContext());
		int id=city.getProvinceid();
		weatherDB.loadCity(id);
		
	}
	public void testesavecount(){
		WeatherDB weatherDB=WeatherDB.getInstance(getContext());
		weatherDB.saveCounty(county);
	};
	public void test4(){
		WeatherDB weatherDB=WeatherDB.getInstance(getContext());
		
		
		
	}
	public void testxxx(){
		Weather weather=new Weather();
		
		
		
	}

}
