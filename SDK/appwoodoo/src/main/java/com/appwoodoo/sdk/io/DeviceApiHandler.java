package com.appwoodoo.sdk.io;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.appwoodoo.sdk.model.ApiResponse;
import com.appwoodoo.sdk.state.Config;
import com.appwoodoo.sdk.state.State;
import com.appwoodoo.sdk.storage.SharedPreferencesHelper;

public class DeviceApiHandler {

	public static String KEY_PUSH_NOTIFICATIONS_ENABLED = "WOODOO_PUSH_NOTIFICATIONS_ENABLED";
	public static String KEY_DEVICE_TOKEN = "WOODOO_PUSH_NOTIFICATIONS_TOKEN";
	public static String KEY_DEVICE_TOKEN_SUBMITTED = "WOODOO_PUSH_NOTIFICATIONS_SUBMITTED";

	public static void register(final String token, final SharedPreferences sp) {

        // Push Notifications are disabled, let's not submit anything here.
        // (Mind you, notifications are default on.)
        if (sp.getInt(KEY_PUSH_NOTIFICATIONS_ENABLED, 1) == 0) {
            return;
        }

        // We already submitted the same token, no need to continue now
        if (sp.getString(KEY_DEVICE_TOKEN, "").contentEquals(token)
                && sp.getInt(KEY_DEVICE_TOKEN_SUBMITTED, 0) == 1) {
            return;
        }

        final String url = Config.API_ENDPOINT + "push/gcm/register/";

		final Map<String,Object> params = new LinkedHashMap<>();
		params.put("api_key", State.getInstance().getAppKey());
		params.put("reg_id", token);

		AsyncTask<Void, Void, String> bgTask = new AsyncTask<Void, Void, String>(){
			@Override
			protected String doInBackground(Void... arg) {
				try {
					return HttpsClient.getInstance().doPostRequest(url, params);
				} catch (IOException e) {
                    return null;
				}
			}
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null && sp != null) {
                    ApiResponse ar = ApiResponse.parseJSON(s);
                    if (ar.getStatus() != null && ar.getStatus() == 200) {
                        SharedPreferencesHelper.getInstance().storeValue(sp, KEY_DEVICE_TOKEN, token);
                        SharedPreferencesHelper.getInstance().storeValue(sp, KEY_DEVICE_TOKEN_SUBMITTED, 1);
                    }
                }
            }
        };
		bgTask.execute((Void) null);
	}

	public static void unregister(final String token, final SharedPreferences sp) {
		final String url = Config.API_ENDPOINT + "push/gcm/unregister/";

		final Map<String,Object> params = new LinkedHashMap<>();
		params.put("api_key", State.getInstance().getAppKey());
		params.put("reg_id", token);

		AsyncTask<Void, Void, String> bgTask = new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... arg) {
                try {
                    return HttpsClient.getInstance().doPostRequest(url, params);
                } catch (IOException e) {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null && sp != null) {
                    ApiResponse ar = ApiResponse.parseJSON(s);
                    if (ar.getStatus() != null && ar.getStatus() == 200) {
                        SharedPreferencesHelper.getInstance().storeValue(sp, KEY_PUSH_NOTIFICATIONS_ENABLED, 0);
                        SharedPreferencesHelper.getInstance().storeValue(sp, DeviceApiHandler.KEY_DEVICE_TOKEN_SUBMITTED, 0);
                    }
                }
            }
		};
		bgTask.execute((Void) null);
	}

}
