//
//  AppWoodoo.java
//  SDK
//
//  Created by Richard Dancsi
//  Copyright (c) 2013 appwoodoo. All rights reserved.
//  See the file MIT-LICENSE for copying permission.
//
package com.appwoodoo.sdk;

import java.util.ArrayList;

import com.appwoodoo.sdk.io.SettingsApiHandler;
import com.appwoodoo.sdk.model.RemoteSetting;
import com.appwoodoo.sdk.push.PushNotificationHelper;
import com.appwoodoo.sdk.state.State;

/**
 * The main interface to AppWoodoo
 *
 * Use the methods of this class to get the Woodoo in your app.
 * See the README for details.
 *
 * @author wimagguc
 * @since 11.06.13
 */
public class Woodoo {

	private static WoodooDelegate delegate;

	public static enum WoodooStatus {ERROR, NETWORK_ERROR, SUCCESS};

	private Woodoo() {}

	public static void takeOff(String appKey) {
		State.getInstance().setAppKey(appKey);
		downloadSettings(appKey);
	}

	public static void takeOffWithCallback(String appKey, WoodooDelegate _delegate) {
		State.getInstance().setAppKey(appKey);
		delegate = _delegate;
		downloadSettings(appKey);
	}

	private static void downloadSettings(final String appKey) {
		SettingsApiHandler.downloadSettings(delegate);
	}

	public static ArrayList<String> getKeys() {
		ArrayList<RemoteSetting> settings = State.getInstance().getSettings();

		if (settings == null) {
			return null;
		}

		ArrayList<String> keys = new ArrayList<String>();
		for (RemoteSetting s : settings) {
			keys.add(s.getKey());
		}
		return keys;
	}

	public static boolean getBooleanForKey(String key) {
		String stringVal = getStringForKey(key);
		try {
			return Boolean.parseBoolean(stringVal);
		} catch(Exception e) {
			return false;
		}
	}
	
	public static float getFloatForKey(String key) {
		String stringVal = getStringForKey(key);
		try {
			return Float.parseFloat(stringVal);
		} catch(Exception e) {
			return 0f;
		}
	}

	public static int getIntegerForKey(String key) {
		String stringVal = getStringForKey(key);
		try {
			return Integer.parseInt(stringVal);
		} catch(Exception e) {
			return 0;
		}
	}

	public static String getStringForKey(String key) {
		if (key==null) { return null; }

		ArrayList<RemoteSetting> settings = State.getInstance().getSettings();

		if (settings == null) { return null; }

		for (RemoteSetting s : settings) {
			if (s.getKey().contentEquals(key)) {
				return s.getValue(); 
			}
		}

		return "";
	}

	public static PushNotificationHelper pushNotifications() {
		return PushNotificationHelper.getInstance();
	}
	
}
