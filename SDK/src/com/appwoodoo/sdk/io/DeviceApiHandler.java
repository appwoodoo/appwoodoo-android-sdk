package com.appwoodoo.sdk.io;

import java.util.LinkedHashMap;
import java.util.Map;

import com.appwoodoo.sdk.state.Config;
import com.appwoodoo.sdk.state.State;

public class DeviceApiHandler {

	public static void register(String deviceId, String registrationId) {
		String url = Config.API_ENDPOINT + "push/gcm/register/";

		Map<String,Object> params = new LinkedHashMap<>();
		params.put("api_key", State.getInstance().getAppKey());
		params.put("dev_id", deviceId);
		params.put("reg_id", registrationId);

		HttpsClient.getInstance().doPostRequestInBackground(url, params);
	}

	public static void unregister(String deviceId) {
		String url = Config.API_ENDPOINT + "push/gcm/unregister/";

		Map<String,Object> params = new LinkedHashMap<>();
		params.put("api_key", State.getInstance().getAppKey());
		params.put("dev_id", deviceId);

		HttpsClient.getInstance().doPostRequestInBackground(url, params);
	}

}
