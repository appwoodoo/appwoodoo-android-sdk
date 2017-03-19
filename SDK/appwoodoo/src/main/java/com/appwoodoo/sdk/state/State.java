package com.appwoodoo.sdk.state;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.Uri;

import com.appwoodoo.sdk.model.RemoteSetting;
import com.appwoodoo.sdk.storage.SharedPreferencesHelper;

public class State {

	private String appKey;
	private String gcmSender;
	private String notificationIntentClassName;
	private String notificationTitle;
	private Integer notificationResourceId;
	private String notificationSound;
	private String packageName;

	private ArrayList<RemoteSetting> settings;
	private boolean settingsArrived = false;

	private static State _instance;

	private State() {}

	public static State getInstance() {
		synchronized(State.class) {
			if (_instance == null) {
				_instance = new State();
			}
		}		
		return _instance;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(Context context) {
		packageName = context.getPackageName();
	}

	public String getGcmSender(Context context) {
		if (gcmSender == null) {
			SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
			gcmSender = SharedPreferencesHelper.getInstance().getStoredString(sp, "WOODOO_GCM_SENDER");
		}
		return gcmSender;
	}
	
	public void setGcmSender(String gcmSender, Context context) {
		SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
		SharedPreferencesHelper.getInstance().storeValue(sp, "WOODOO_GCM_SENDER", gcmSender);
		this.gcmSender = gcmSender;
	}
	
	public String getNotificationIntentClassName(Context context) {
		if (notificationIntentClassName == null) {
			SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
			notificationIntentClassName = SharedPreferencesHelper.getInstance().getStoredString(sp, "WOODOO_NOTIFICATION_INTENT_CLASS");
		}
		return notificationIntentClassName;
	}

	public void setNotificationIntentClassName(String notificationIntentClass, Context context) {
		SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
		SharedPreferencesHelper.getInstance().storeValue(sp, "WOODOO_NOTIFICATION_INTENT_CLASS", notificationIntentClass);
		this.notificationIntentClassName = notificationIntentClass;
	}

	public String getNotificationTitle(Context context) {
		if (notificationTitle == null) {
			SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
			notificationTitle = SharedPreferencesHelper.getInstance().getStoredString(sp, "WOODOO_NOTIFICATION_TITLE");
		}
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle, Context context) {
		SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
		SharedPreferencesHelper.getInstance().storeValue(sp, "WOODOO_NOTIFICATION_TITLE", notificationTitle);
		this.notificationTitle = notificationTitle;
	}

	public Integer getNotificationResourceId(Context context) {
		if (notificationResourceId == null) {
			SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
			notificationResourceId = SharedPreferencesHelper.getInstance().getStoredInteger(sp, "WOODOO_NOTIFICATION_RESOURCE_ID");
		}
		return notificationResourceId;
	}
	
	public void setNotificationResourceId(int notificationResourceId, Context context) {
		SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
		SharedPreferencesHelper.getInstance().storeValue(sp, "WOODOO_NOTIFICATION_RESOURCE_ID", notificationResourceId);
		this.notificationResourceId = notificationResourceId;
	}

	public String getNotificationSound(Context context) {
		if (notificationSound == null) {
			SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
			notificationSound = SharedPreferencesHelper.getInstance().getStoredString(sp, "WOODOO_NOTIFICATION_SOUND_URI");
		}
		return notificationSound;
	}

	public void setNotificationSound(String notificationSound, Context context) {
		SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
		SharedPreferencesHelper.getInstance().storeValue(sp, "WOODOO_NOTIFICATION_SOUND_URI", notificationSound);
		this.notificationSound = notificationSound;
	}

	public void setSettingsArrived(boolean settingsArrived) {
		this.settingsArrived = settingsArrived;
	}
	
	public boolean isSettingsArrived() {
		return settingsArrived;
	}
	
	public void setSettings(ArrayList<RemoteSetting> settings) {
		this.settings = settings;
	}
	
	public ArrayList<RemoteSetting> getSettings() {
		return settings;
	}
	
}
