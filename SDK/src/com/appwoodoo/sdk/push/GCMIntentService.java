package com.appwoodoo.sdk.push;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.provider.Settings.Secure;

import com.appwoodoo.sdk.io.DeviceApiHandler;
import com.appwoodoo.sdk.state.State;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	static final String LOG_TAG = "GCMIntentService";

	@Override
	protected String[] getSenderIds(Context context) {
		String[] ids = new String[1];
		ids[0] = State.getInstance().getGcmSender(context);
		return ids;
	}
	
	@Override
	public void onError(Context context, String errorId) {
		Log.d(LOG_TAG, "Messaging registration error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.d(LOG_TAG, "Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String msg = intent.getExtras().getString("msg");
		PushNotificationHelper.getInstance().showNotification(context, msg);
	}

	@Override
	public void onRegistered(Context context, String registrationId) {
		String deviceID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		DeviceApiHandler.register(deviceID, registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String s) {
		String deviceID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		DeviceApiHandler.unregister(deviceID);
	}

}
