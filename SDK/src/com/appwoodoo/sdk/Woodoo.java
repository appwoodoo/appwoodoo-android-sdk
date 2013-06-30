//
//  AppWoodoo.java
//  SDK
//
//  Created by Richard Dancsi
//  Copyright (c) 2013 appwoodoo. All rights reserved.
//  See the file MIT-LICENSE for copying permission.
//
package com.appwoodoo.sdk;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.os.AsyncTask;

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
	private static ArrayList<RemoteSetting> settings;
	private static boolean settingsArrived = false;
	
	public static enum WoodooStatus {ERROR, NETWORK_ERROR, SUCCESS};

	private Woodoo() {}

	public static void takeOff(String appKey) {
		downloadSettings(appKey);
	}

	public static void takeOffWithCallback(String appKey, WoodooDelegate _delegate) {
		delegate = _delegate;
		downloadSettings(appKey);
	}
	
	private static void downloadSettings(final String appKey) {
		settingsArrived = false;

		AsyncTask<Void, Void, WoodooStatus> task = new AsyncTask<Void, Void, WoodooStatus>() {
			@Override
			protected WoodooStatus doInBackground(Void... params) {
				WoodooStatus status = WoodooStatus.ERROR;
				
				try {
					URL address = new URL("http://www.appwoodoo.com/api/v1/settings/" + appKey + "/");

					URLConnection con = address.openConnection();
					con.setConnectTimeout(10000);
					con.setReadTimeout(10000);
					InputStream is = con.getInputStream();

			        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			        StringBuilder sb = new StringBuilder();

			        String line = null;
			        try {
			            while ((line = reader.readLine()) != null) {
			                sb.append(line);
			            }
			        } catch (IOException e) {
			        } finally {
		                is.close();
			        }

			        settings = RemoteSetting.parseJSON( sb.toString() );

					if (settings!=null) {
						status = WoodooStatus.SUCCESS;
					}

				} catch (MalformedURLException e) {
				} catch (IOException e) {
					status = WoodooStatus.NETWORK_ERROR;
				}

				return status;
			};
			@Override
			protected void onPostExecute(WoodooStatus result) {
				settingsArrived = true;
				if (delegate!=null) {
					delegate.woodooArrived(result);
				}
			}
		};
		task.execute((Void) null);
	}

	public static boolean settingsArrived() {
		return settingsArrived;
	}
	
	public static ArrayList<String> getKeys() {
		if (settings == null) { return null; }

		ArrayList<String> keys = new ArrayList<String>();
		for (RemoteSetting s : settings) {
			keys.add(s.getKey());
		}
		return keys;
	}

	public static boolean getBooleanForKey(String key) {
		if (key==null) { return false; }
		if (settings == null) { return false; }

		for (RemoteSetting s : settings) {
			if (s.getKey().contentEquals(key)) {
				try {
					Boolean.parseBoolean(s.getValue());
				} catch(Exception e) {
				}
			}
		}

		return false;
	}
	
	public static float getFloatForKey(String key) {
		if (key==null) { return 0f; }
		if (settings == null) { return 0f; }

		for (RemoteSetting s : settings) {
			if (s.getKey().contentEquals(key)) {
				try {
					Float.parseFloat(s.getValue());
				} catch(Exception e) {
				}
			}
		}

		return 0f;
	}

	public static int getIntegerForKey(String key) {
		if (key==null) { return 0; }
		if (settings == null) { return 0; }

		for (RemoteSetting s : settings) {
			if (s.getKey().contentEquals(key)) {
				try {
					Integer.parseInt(s.getValue());
				} catch(Exception e) {
				}
			}
		}
		
		return 0;
	}

	public static String getStringForKey(String key) {
		if (key==null) { return null; }
		if (settings == null) { return null; }

		for (RemoteSetting s : settings) {
			if (s.getKey().contentEquals(key)) {
				return s.getValue();
			}
		}
		return null;
	}

}
