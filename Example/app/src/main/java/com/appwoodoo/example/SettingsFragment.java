package com.appwoodoo.example;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.appwoodoo.sdk.Woodoo;
import com.appwoodoo.sdk.WoodooDelegate;

import java.util.ArrayList;

/**
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
public class SettingsFragment extends Fragment implements WoodooDelegate {

    private EditText apiKey;
    private EditText gcmId;

    private Button getSettingsButton;
    private ArrayAdapter<String> settingListAdapter;
    private ArrayList<String> settingListItems = new ArrayList<>();

    SharedPreferences preferences;

    private final WoodooDelegate delegate = this;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // EXAMPLE APP SETUP: UI >>>
        apiKey = (EditText) rootView.findViewById(R.id.apiKey);
        gcmId = (EditText) rootView.findViewById(R.id.gcmId);
        getSettingsButton = (Button) rootView.findViewById(R.id.getSettings);
        ListView settingList = (ListView) rootView.findViewById(R.id.settingList);

        settingListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, settingListItems);
        settingList.setAdapter(settingListAdapter);

        getSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadSettings();
            }
        });
        // ^^^

        // EXAMPLE APP SETUP: CHECK WHETHER WE HAVE AN API KEY STORED ALREADY >>>
        preferences = getActivity().getSharedPreferences("WoodooSharedPreferences", Activity.MODE_PRIVATE);
        if (preferences != null) {
            String savedApiKey = preferences.getString("SavedApiKey", "");
            apiKey.setText(savedApiKey);

            String savedGcmId = preferences.getString("SavedGcmId", "");
            gcmId.setText(savedGcmId);

            downloadSettings();
        }
        // ^^^

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (apiKey != null) {
            if (!isVisibleToUser) {
                apiKey.setInputType(InputType.TYPE_NULL);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(apiKey.getWindowToken(), 0);
            }
        }
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

        Woodoo.pushNotifications().setupPushNotification(getActivity(), gcm_id, notificationTitle, R.drawable.ic_push_notification);
        Woodoo.pushNotifications().setupPushNotificationSound(getActivity(), R.raw.notification_sound);
        Woodoo.pushNotifications().removeAll(getActivity());
        // ^^^

        // ADD THE STORIES TO THIS APP >>>
        Woodoo.stories().init(getActivity());
        Woodoo.stories().getViewOptions().setStoryWallBackgroundColour("#F1F1F1");
        Woodoo.stories().getViewOptions().setStoryWallCellDateColour("#999999");
        Woodoo.stories().getViewOptions().setStoryWallCellDividerColour("#CCCCCC");
        Woodoo.stories().getViewOptions().setStoryWallCellTextColour("#999999");
        Woodoo.stories().getViewOptions().setStoryWallCellTitleColour("#000000");
        Woodoo.stories().getViewOptions().setStoryWallForegroundColour("#CCCCCC");
        Woodoo.stories().getViewOptions().setStoryWallCellHeight(240);
        // ^^^

        getSettingsButton.setEnabled(false);
    }

    @Override
    public void woodooArrived(Woodoo.WoodooStatus status) {
        switch (status)
        {
            case SUCCESS:
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Settings arrived!", Toast.LENGTH_SHORT).show();
                }

                settingListItems.clear();
                for (String key : Woodoo.getKeys()) {
                    settingListItems.add( key + " - " + Woodoo.getStringForKey(key) );
                }

                settingListAdapter.notifyDataSetChanged();

                break;
            case ERROR:
            case NETWORK_ERROR:
            default:
                if (getContext() != null) {
                    Toast.makeText(getContext(), status + " occurred", Toast.LENGTH_SHORT).show();
                }
        }

        getSettingsButton.setEnabled(true);
    }

}
