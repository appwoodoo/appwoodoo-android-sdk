package com.appwoodoo.sdk.io;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.appwoodoo.sdk.BuildConfig;
import com.appwoodoo.sdk.model.ApiResponse;
import com.appwoodoo.sdk.model.StoryWall;
import com.appwoodoo.sdk.state.Config;
import com.appwoodoo.sdk.state.State;
import com.appwoodoo.sdk.storage.SharedPreferencesHelper;

import java.io.IOException;

public class StoriesApiHandler {

	public static String KEY_STORYWALL_DATA = "WOODOO_STORYWALL_DATA";

	public static void getStoryData(final SharedPreferences sp) {

        final String url = Config.API_ENDPOINT + "stories/" + State.getInstance().getAppKey() + "/";

		AsyncTask<Void, Void, String> bgTask = new AsyncTask<Void, Void, String>(){
			@Override
			protected String doInBackground(Void... arg) {
				try {
					return HttpsClient.getInstance().doGetRequest(url);
				} catch (IOException e) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace();
                    }
                    return null;
				}
			}
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null && sp != null) {
                    ApiResponse ar = ApiResponse.parseJSON(s);
                    if (ar != null && ar.getStatus() != null && ar.getStatus() == 200) {
                        StoryWall sw = StoryWall.parseJSON(s);
                        SharedPreferencesHelper.getInstance().storeValue(sp, KEY_STORYWALL_DATA, sw.getJsonString());
                    }
                }
            }
        };
		bgTask.execute((Void) null);
	}

}
