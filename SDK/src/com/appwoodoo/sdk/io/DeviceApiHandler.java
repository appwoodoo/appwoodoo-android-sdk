package com.appwoodoo.sdk.io;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.appwoodoo.sdk.state.Config;
import com.appwoodoo.sdk.state.State;

public class DeviceApiHandler {

	public static void register(String deviceId, String registrationId) {
		String url = Config.API_ENDPOINT + "push/gcm/register/";

		List<NameValuePair> postData = new ArrayList<NameValuePair>();
		postData.add(new BasicNameValuePair("api_key", State.getInstance().getAppKey()));
		postData.add(new BasicNameValuePair("dev_id", deviceId));
		postData.add(new BasicNameValuePair("reg_id", registrationId));

		HttpsClient.getInstance().doPostRequestInBackground(url, postData);
	}

	public static void unregister(String deviceId) {
		String url = Config.API_ENDPOINT + "push/gcm/unregister/";

		List<NameValuePair> postData = new ArrayList<NameValuePair>();
		postData.add(new BasicNameValuePair("api_key", State.getInstance().getAppKey()));
		postData.add(new BasicNameValuePair("dev_id", deviceId));

		HttpsClient.getInstance().doPostRequestInBackground(url, postData);
	}

}
