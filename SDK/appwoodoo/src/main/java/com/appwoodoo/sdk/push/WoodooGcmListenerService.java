package com.appwoodoo.sdk.push;

import android.os.Bundle;
import com.google.android.gms.gcm.GcmListenerService;

public class WoodooGcmListenerService extends GcmListenerService {

    private static final String TAG = "WoodooGcmListenerService";

	/**
	 * Called when message is received.
	 *
	 * @param from SenderID of the sender.
	 * @param data Data bundle containing message data as key/value pairs.
	 *             For Set of keys use data.keySet().
	 */
	@Override
	public void onMessageReceived(String from, Bundle data) {
		String message = data.getString("msg");
		PushNotificationHelper.getInstance().showNotification(getApplicationContext(), message);
	}

}
