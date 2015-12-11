package com.appwoodoo.sdk.push;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.appwoodoo.sdk.io.DeviceApiHandler;
import com.appwoodoo.sdk.state.Config;
import com.appwoodoo.sdk.state.State;
import com.appwoodoo.sdk.storage.SharedPreferencesHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class PushNotificationHelper {

	private static PushNotificationHelper _instance;

	private PushNotificationHelper() {}

	public static PushNotificationHelper getInstance() {
		synchronized(PushNotificationHelper.class) {
			if (_instance == null) {
				_instance = new PushNotificationHelper();
			}
		}		
		return _instance;
	}

    // If you want to use only push notifications without the remote settings, this
    // speeds the app's launch up a bit
	public void setupPushNotificationWithoutRemoteTakeoff(String appKey, Activity activity, String gcmSender, String notificationTitle, int notificationIconResource) {
		State.getInstance().setAppKey(appKey);
		setupPushNotification(activity, gcmSender, notificationTitle, notificationIconResource);
	}

	public void setupPushNotification(Activity activity, String gcmSender, String notificationTitle, int notificationIconResource) {
		State.getInstance().setGcmSender(gcmSender, activity.getApplicationContext());
		State.getInstance().setNotificationIntentClassName(activity.getClass().getName(), activity.getApplicationContext());
		State.getInstance().setNotificationResourceId(notificationIconResource, activity.getApplicationContext());
		State.getInstance().setNotificationTitle(notificationTitle, activity.getApplicationContext());

		if (checkPlayServices(activity.getApplicationContext())) {
			// Start IntentService to register this application with GCM.
			Intent intent = new Intent(activity, WoodooRegistrationIntentService.class);
			activity.startService(intent);
		}
	}

	public boolean pushNotificationsEnabled(Context context) {
        SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
        Integer enabledInt = SharedPreferencesHelper.getInstance().getStoredInteger(sp, DeviceApiHandler.KEY_PUSH_NOTIFICATIONS_ENABLED);
        if (enabledInt != null && enabledInt == 0) {
            return false;
        }
		return true;
	}

	public void disablePushNotifications(Context context) {
        SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
        String deviceToken = SharedPreferencesHelper.getInstance().getStoredString(sp, DeviceApiHandler.KEY_DEVICE_TOKEN);
        DeviceApiHandler.unregister(deviceToken, sp);
	}

	public void reEnablePushNotifications(Context context) {
        SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
        String deviceToken = SharedPreferencesHelper.getInstance().getStoredString(sp, DeviceApiHandler.KEY_DEVICE_TOKEN);
        SharedPreferencesHelper.getInstance().storeValue(sp, DeviceApiHandler.KEY_PUSH_NOTIFICATIONS_ENABLED, 1);
        DeviceApiHandler.register(deviceToken, sp);
	}

	public void showNotification(Context context, String message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(State.getInstance().getNotificationResourceId(context))
                        .setContentTitle(State.getInstance().getNotificationTitle(context))
                        .setContentText(message);

		try {
			ClassLoader cl = context.getClassLoader();

			Intent resultIntent = new Intent(context, Class.forName(State.getInstance().getNotificationIntentClassName(context), true, cl) );
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(Class.forName(State.getInstance().getNotificationIntentClassName(context), true, cl));

			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
			builder.setContentIntent(resultPendingIntent);

			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

			// id allows you to update the notification later on.
			notificationManager.notify(Config.NOTIFICATION_REQCODE, builder.build());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void removeAll(Context context) {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices(Context context) {
		GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
		int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
		if (resultCode != ConnectionResult.SUCCESS) {
			return false;
		}
		return true;
	}

}
