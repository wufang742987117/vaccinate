package com.junl.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPost {
	public static String post(String uri, String data) throws IOException {
		URL url = new URL(uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setConnectTimeout(3 * 1000);
		conn.setRequestProperty("Accept-Charset", "utf-8");
		conn.setRequestProperty("contentType", "utf-8");
		conn.setRequestProperty("Cookie", "JSESSIONID=4037EA919860EC1BC9000692076FC4F7");
		if (data != null) {
			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes("UTF-8"));
			os.flush();
		}
		conn.connect();
		InputStream stream = conn.getInputStream();
		String result = convertStreamToString(stream);
		return result;

	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		return sb.toString();
	}

	public static InputStream getSource(String uri, String data) throws IOException {
		URL url = new URL(uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setConnectTimeout(3 * 1000);
		conn.setRequestProperty("Accept-Charset", "utf-8");
		conn.setRequestProperty("contentType", "utf-8");
		conn.setRequestProperty("Cookie", "JSESSIONID=4037EA919860EC1BC9000692076FC4F7");
		if (data != null) {
			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes("UTF-8"));
			os.flush();
		}
		conn.connect();
		InputStream stream = conn.getInputStream();
		
		return stream;

	}
}
