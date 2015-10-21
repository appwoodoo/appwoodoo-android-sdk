package com.appwoodoo.sdk.push;

import android.app.IntentService;
import android.content.Intent;

import com.appwoodoo.sdk.io.DeviceApiHandler;
import com.appwoodoo.sdk.state.State;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class WoodooRegistrationIntentService extends IntentService {

    private static final String TAG = "WoodooRegistrationIntentService";

    public WoodooRegistrationIntentService() {
        super(TAG);
    }

	@Override
	protected void onHandleIntent(Intent intent) {
        // We haven't yet received the key; let's exit here
        String senderKey = State.getInstance().getGcmSender(getApplicationContext());
        if (senderKey == null || senderKey == "") {
            return;
        }

		InstanceID instanceID = InstanceID.getInstance(this);
		try {
			String token = instanceID.getToken(senderKey, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
			DeviceApiHandler.register(token);
		} catch (IOException e) {}
	}

}
