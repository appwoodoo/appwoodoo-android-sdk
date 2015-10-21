package com.appwoodoo.sdk.io;

import java.util.LinkedHashMap;
import java.util.Map;

import com.appwoodoo.sdk.state.Config;
import com.appwoodoo.sdk.state.State;

public class DeviceApiHandler {

	public static void register(String token) {
		String url = Config.API_ENDPOINT + "push/gcm/register/";

		Map<String,Object> params = new LinkedHashMap<>();
		params.put("api_key", State.getInstance().getAppKey());
		params.put("reg_id", token);

		HttpsClient.getInstance().doPostRequestInBackground(url, params);
	}

	public static void unregister(String token) {
		String url = Config.API_ENDPOINT + "push/gcm/unregister/";

		Map<String,Object> params = new LinkedHashMap<>();
		params.put("api_key", State.getInstance().getAppKey());
		params.put("reg_id", token);

		HttpsClient.getInstance().doPostRequestInBackground(url, params);
	}

}
