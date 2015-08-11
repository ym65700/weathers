package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/*
 * �ӷ������˻�ȡʡ���ص�����
 * 
 */
public class HttpUtil {

	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener) {

		new Thread() {
			public void run() {
				HttpURLConnection connection = null;

				
				try {
					URL url = new URL(address);

					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					 // ����apikey��HTTP header
			        connection.setRequestProperty("apikey",  "ba3ba35303ed91a1e18d3dd87c9dffc8");
					InputStream in = connection.getInputStream();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in));
					String len;
					StringBuilder sbBuilder = new StringBuilder();
					while ((len = br.readLine()) != null) {
						sbBuilder.append(len);
					}
					if (listener != null) {
						/*
						 * �ص�onfinish
						 */
						listener.onFinish(sbBuilder.toString());
					}

				} catch (IOException e) {
					/*
					 * �ص�onerror
					 */
					if (listener != null) {
						listener.onError(e);
					}

				} finally {

					if (connection != null) {
						connection.disconnect();
					}
				}
			};
		}.start();

	}

}
