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
 * The main interface of Appwoodoo
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
		Woodoo.takeOffWithCallback(appKey, null);
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
		return getBooleanForKey(key, false);
	}

	public static boolean getBooleanForKey(String key, boolean defaultValue) {
		String stringVal = getStringForKey(key);
		try {
			return Boolean.parseBoolean(stringVal);
		} catch(Exception e) {
			return defaultValue;
		}
	}

	public static long getLongForKey(String key) {
		return getLongForKey(key, 0);
	}

	public static long getLongForKey(String key, long defaultValue) {
		String stringVal = getStringForKey(key);
		try {
			return Long.parseLong(stringVal);
		} catch(Exception e) {
			return defaultValue;
		}
	}

    public static float getFloatForKey(String key) {
        return getFloatForKey(key, 0f);
    }

    public static float getFloatForKey(String key, float defaultValue) {
        String stringVal = getStringForKey(key);
        try {
            return Float.parseFloat(stringVal);
        } catch(Exception e) {
            return defaultValue;
        }
    }

    public static int getIntegerForKey(String key) {
        return getIntegerForKey(key, 0);
    }

    public static int getIntegerForKey(String key, int defaultValue) {
		String stringVal = getStringForKey(key);
		try {
			return Integer.parseInt(stringVal);
		} catch(Exception e) {
			return defaultValue;
		}
	}

    public static String getStringForKey(String key) {
        return getStringForKey(key, null);
    }

    public static String getStringForKey(String key, String defaultValue) {
		if (key==null) { return defaultValue; }

		ArrayList<RemoteSetting> settings = State.getInstance().getSettings();

		if (settings == null) { return defaultValue; }

		for (RemoteSetting s : settings) {
			if (s.getKey().contentEquals(key)) {
				return s.getValue(); 
			}
		}

		return defaultValue;
	}

	public static PushNotificationHelper pushNotifications() {
		return PushNotificationHelper.getInstance();
	}

}
