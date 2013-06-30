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

public class MainActivity extends Activity implements WoodooDelegate {

	private EditText apiKey;
	private Button getSettings;
	private ListView settingList;
	private ArrayAdapter<String> settingListAdapter;
	private ArrayList<String> settingListItems = new ArrayList<String>();

	private final WoodooDelegate delegate = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		apiKey = (EditText) findViewById(R.id.apiKey);
		getSettings = (Button) findViewById(R.id.getSettings);
		settingList = (ListView) findViewById(R.id.settingList);

		settingListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settingListItems);
		settingList.setAdapter(settingListAdapter);

		getSettings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				settingListItems.clear();
				settingListAdapter.notifyDataSetChanged();

				Woodoo.takeOffWithCallback(apiKey.getText().toString(), delegate);
			}
		});
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

	}

}
