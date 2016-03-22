package com.appwoodoo.example;

import java.util.ArrayList;

import com.appwoodoo.sdk.Woodoo;
import com.appwoodoo.sdk.WoodooDelegate;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.SharedPreferences;

/**
 * An example app that uses the Appwoodoo SDK.
 * Downloads and displays the a list of remote settings available for an API key.
 *
 *   1. Add settings on www.appwoodoo.com
 *   2. Run this test app
 *   3. Put your API key in the text field and go!
 *
 * For help setting up Google Cloud Messaging and push notifications, check our help
 * at www.appwoodoo.com or follow the Github repository.
 * 
 * @author wimagguc
 * @since 11.06.13
 */
public class MainActivity extends Activity implements WoodooDelegate {

	private EditText apiKey;
	private EditText gcmId;

	private Button getSettingsButton;
    private ArrayAdapter<String> settingListAdapter;
	private ArrayList<String> settingListItems = new ArrayList<>();

	SharedPreferences preferences;

	private final WoodooDelegate delegate = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// EXAMPLE APP SETUP: UI >>>
		apiKey = (EditText) findViewById(R.id.apiKey);
		gcmId = (EditText) findViewById(R.id.gcmId);
		getSettingsButton = (Button) findViewById(R.id.getSettings);
        ListView settingList = (ListView) findViewById(R.id.settingList);

		settingListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, settingListItems);
		settingList.setAdapter(settingListAdapter);

		getSettingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				downloadSettings();
			}
		});
		// ^^^

		// EXAMPLE APP SETUP: CHECK WHETHER WE HAVE AN API KEY STORED ALREADY >>>
		preferences = getSharedPreferences("WoodooSharedPreferences", MODE_PRIVATE);
		if (preferences != null) {
			String savedApiKey = preferences.getString("SavedApiKey", "");
			apiKey.setText(savedApiKey);

			String savedGcmId = preferences.getString("SavedGcmId", "");
			gcmId.setText(savedGcmId);

			downloadSettings();
		}
		// ^^^
	}

	@Override
	public void onPause() {
		super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SavedApiKey", String.valueOf(apiKey.getText()) );
        editor.putString("SavedGcmId", String.valueOf(gcmId.getText()) );
        editor.apply();
	}

	// Getting the Woodoo, preparing the UI
	private void downloadSettings() {
		settingListItems.clear();
		settingListAdapter.notifyDataSetChanged();

		String api_key = String.valueOf(apiKey.getText());
		String gcm_id = String.valueOf(gcmId.getText());

		Woodoo.takeOffWithCallback(api_key, delegate);

		// ADD THE PUSH NOTIFICATIONS TO THIS APP >>>
		String notificationTitle = getResources().getString(R.string.app_name);

		Woodoo.pushNotifications().setupPushNotification(this, gcm_id, notificationTitle, R.drawable.ic_push_notification);
		Woodoo.pushNotifications().setupPushNotificationSound(this, R.raw.notification_sound);
		Woodoo.pushNotifications().removeAll(getApplicationContext());
		// ^^^

		getSettingsButton.setEnabled(false);
	}
	
	@Override
	public void woodooArrived(Woodoo.WoodooStatus status) {
		switch (status)
		{
			case SUCCESS:
				Toast.makeText(getApplicationContext(), "Settings arrived!", Toast.LENGTH_SHORT).show();

				settingListItems.clear();
				for (String key : Woodoo.getKeys()) {
					settingListItems.add( key + " - " + Woodoo.getStringForKey(key) );
				}

				settingListAdapter.notifyDataSetChanged();

				break;
			case ERROR:
			case NETWORK_ERROR:
			default:
				// Retry here if you want
				Toast.makeText(getApplicationContext(), status + " occurred", Toast.LENGTH_SHORT).show();
		}

		getSettingsButton.setEnabled(true);
	}

}
