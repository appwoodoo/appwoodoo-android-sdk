package com.appwoodoo.example.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.appwoodoo.example.R;
import com.appwoodoo.sdk.Woodoo;
import com.appwoodoo.sdk.model.Dialog;

import java.util.ArrayList;

/**
 * Displays the a list of remote settings available for an Appwoodoo app.
 *
 * @author wimagguc
 * @since 11.06.13
 */
public class RemoteSettingsDemoFragment extends Fragment {

    private ArrayList<String> settingListItems = new ArrayList<>();
    private ArrayAdapter<String> settingListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_remotesettings, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        settingListAdapter = new RemoteSettingsListAdapter(getActivity(), settingListItems);
        listView.setAdapter(settingListAdapter);

        setupUI();

        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setupUI();
    }

    private void setupUI() {
        ArrayList<String> keys = Woodoo.getKeys();
        if (keys != null) {
            settingListItems.clear();
            for (String key : keys) {
                settingListItems.add(key);
            }
            settingListAdapter.notifyDataSetChanged();
        }
    }

}
