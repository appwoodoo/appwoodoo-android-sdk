package com.appwoodoo.sdk.io;

import java.io.IOException;
import java.util.ArrayList;

import android.os.AsyncTask;

import com.appwoodoo.sdk.BuildConfig;
import com.appwoodoo.sdk.WoodooDelegate;
import com.appwoodoo.sdk.Woodoo.WoodooStatus;
import com.appwoodoo.sdk.model.RemoteSetting;
import com.appwoodoo.sdk.state.Config;
import com.appwoodoo.sdk.state.State;

public class SettingsApiHandler {

	public static void downloadSettings(final WoodooDelegate delegate) {
		State.getInstance().setSettingsArrived(false);

		AsyncTask<Void, Void, WoodooStatus> task = new AsyncTask<Void, Void, WoodooStatus>() {
			@Override
			protected WoodooStatus doInBackground(Void... params) {
				WoodooStatus status = WoodooStatus.ERROR;

				String url = Config.API_ENDPOINT + "settings/" + State.getInstance().getAppKey() + "/";

				String response;
				try {
					response = HttpsClient.getInstance().doGetRequest(url);
				} catch (IOException e) {
					if (BuildConfig.DEBUG) {
						e.printStackTrace();
					}
					return WoodooStatus.NETWORK_ERROR;
				}

		        ArrayList<RemoteSetting> settings = RemoteSetting.parseJSON(response);

				if (settings!=null) {
					status = WoodooStatus.SUCCESS;
					State.getInstance().setSettings( settings );
				}

				return status;
			};
			@Override
			protected void onPostExecute(WoodooStatus result) {
				if (result == WoodooStatus.SUCCESS) {
					State.getInstance().setSettingsArrived(true);
				}
				if (delegate!=null) {
					delegate.woodooArrived(result);
				}
			}
		};
		task.execute((Void) null);
	}

}
