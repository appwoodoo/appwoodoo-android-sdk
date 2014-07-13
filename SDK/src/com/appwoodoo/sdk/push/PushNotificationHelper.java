package com.appwoodoo.sdk.push;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.appwoodoo.sdk.io.DeviceApiHandler;
import com.appwoodoo.sdk.state.Config;
import com.appwoodoo.sdk.state.State;
import com.google.android.gcm.GCMRegistrar;

public class PushNotificationHelper {

	protected String registrationId;
	
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

	public void setupPushNotification(Activity activity, String gcmSender, String notificationTitle, int notificationIconResource) {
		
		State.getInstance().setGcmSender(gcmSender, activity.getApplicationContext());
		State.getInstance().setNotificationIntentClassName(activity.getClass().getName(), activity.getApplicationContext());
		State.getInstance().setNotificationResourceId(notificationIconResource, activity.getApplicationContext());
		State.getInstance().setNotificationTitle(notificationTitle, activity.getApplicationContext());
		
		try {
			GCMRegistrar.checkDevice(activity);
			GCMRegistrar.checkManifest(activity);

			registrationId = GCMRegistrar.getRegistrationId(activity.getApplicationContext());

			if (registrationId.equals("")) {
				GCMRegistrar.register(activity.getApplicationContext(), State.getInstance().getGcmSender(activity.getApplicationContext()));
			} else {
				String deviceId = Secure.getString(activity.getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
				DeviceApiHandler.register(deviceId, registrationId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
	
}
