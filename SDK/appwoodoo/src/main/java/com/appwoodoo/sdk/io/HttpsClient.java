package com.appwoodoo.sdk.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.os.AsyncTask;

import com.appwoodoo.sdk.BuildConfig;

public class HttpsClient {

	// until a connection is established (in millis)
	private static final int CONNECTION_TIMEOUT = 5000;

	// for waiting for data (in millis)
	private static final int SOCKET_TIMEOUT = 12000;

	private static HttpsClient _instance;

	public static HttpsClient getInstance() {
		synchronized(HttpsClient.class) {
			if (_instance == null) {
				_instance = new HttpsClient();
			}
		}
		return _instance;
	}

	private HttpsClient() {}
	
	public String doPostRequest(String urlString, Map<String,Object> params) throws IOException {
        byte[] postDataBytes;

        try {
            postDataBytes = bytesDataFromParameters(params);
        } catch (UnsupportedEncodingException e) {
            return "";
        }

        HttpURLConnection urlConnection = getHttpURLConnection(urlString);

        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        urlConnection.getOutputStream().write(postDataBytes);

        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        responseStreamReader.close();

        return stringBuilder.toString();
	}

	public String doGetRequest(String urlString) throws IOException {
        HttpURLConnection urlConnection = getHttpURLConnection(urlString);

        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        responseStreamReader.close();

        return stringBuilder.toString();
	}

    private HttpURLConnection getHttpURLConnection(String urlString) throws IOException {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
            // URL should be hard-coded in the SDK, so we we shouldn't ever be here.
            // Therefore there is no way to recover from this.
            throw new IOException();
        }

        HttpURLConnection httpurlConnection = (HttpURLConnection) url.openConnection();

        httpurlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        httpurlConnection.setReadTimeout(SOCKET_TIMEOUT);

        return httpurlConnection;
    }

    public byte[] bytesDataFromParameters(Map<String,Object> params) throws UnsupportedEncodingException {
        byte[] postDataBytes;

        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        postDataBytes = postData.toString().getBytes("UTF-8");

        return postDataBytes;
    }
}
