package com.appwoodoo.sdk.io;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

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
	
	public void doPostRequestInBackground(final String url, final List<NameValuePair> postData) {

		AsyncTask<Void, Void, String> bgTask = new AsyncTask<Void, Void, String>(){
			@Override
			protected String doInBackground(Void... arg0) {
				return doPostRequest(url, postData);
			}
		};
		bgTask.execute((Void) null);

	}
	
	public String doPostRequest(String url, List<NameValuePair> postData) {
		HttpClient httpclient = createHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpEntity entity = response.getEntity();

		try {
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			return "";
		}

	}

	public String doGetRequest(String url) throws IOException {
		HttpClient httpclient = createHttpClient();
		HttpGet httpget = new HttpGet(url);

		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}

		HttpEntity entity = response.getEntity();

		try {
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			return "";
		}

	}
	
	// TODO now we accept all certificates
	public static HttpClient createHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory socketFactory = new CustomSSLSocketFactory(trustStore);
			socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
			HttpConnectionParams.setStaleCheckingEnabled(params, true);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", socketFactory, 443));

			ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(params, registry);
			return new DefaultHttpClient(connectionManager, params);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
