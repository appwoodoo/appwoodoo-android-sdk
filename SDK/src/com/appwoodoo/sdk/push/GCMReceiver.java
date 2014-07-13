package com.appwoodoo.sdk.push;

import android.content.Context;

import com.google.android.gcm.GCMBroadcastReceiver;

public class GCMReceiver extends GCMBroadcastReceiver {

	@Override
	protected String getGCMIntentServiceClassName(Context context) {
		return "com.appwoodoo.sdk.push.GCMIntentService";
	}

}
