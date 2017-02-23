package com.appwoodoo.example.app;

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
import com.appwoodoo.example.R;

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
public class SettingsFragment extends Fragment {

    private EditText apiKey;
    private EditText gcmId;

    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // EXAMPLE APP SETUP: UI >>>
        apiKey = (EditText) rootView.findViewById(R.id.apiKey);
        gcmId = (EditText) rootView.findViewById(R.id.gcmId);
        Button getSettingsButton = (Button) rootView.findViewById(R.id.getSettings);

        getSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditedSettings();
            }
        });

        // EXAMPLE APP SETUP: CHECK WHETHER WE HAVE AN API KEY STORED ALREADY >>>
        preferences = getActivity().getSharedPreferences("WoodooSharedPreferences", Activity.MODE_PRIVATE);
        if (preferences != null) {
            String savedApiKey = preferences.getString("SavedApiKey", "");
            apiKey.setText(savedApiKey);

            String savedGcmId = preferences.getString("SavedGcmId", "");
            gcmId.setText(savedGcmId);
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
            } else {
                apiKey.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveEditedSettings();
    }

    private void saveEditedSettings() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SavedApiKey", String.valueOf(apiKey.getText()) );
        editor.putString("SavedGcmId", String.valueOf(gcmId.getText()) );
        editor.apply();
    }

}
