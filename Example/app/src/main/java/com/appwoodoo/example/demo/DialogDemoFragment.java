package com.appwoodoo.example.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.appwoodoo.example.R;
import com.appwoodoo.sdk.Woodoo;
import com.appwoodoo.sdk.model.Dialog;

import java.util.ArrayList;

/**
 * Displays the dialogs
 *
 *   1. Add settings on www.appwoodoo.com
 *   2. Run this test app
 *   3. Put your API key in the text field
 *
 * For help setting up Google Cloud Messaging and push notifications, check our help
 * at www.appwoodoo.com or follow the Github repository.
 *
 * @author wimagguc
 * @since 11.06.13
 */
public class DialogDemoFragment extends Fragment {

    private DialogsListAdapter adapter;
    private ArrayList<Dialog> dialogs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_demo, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new DialogsListAdapter(getActivity(), dialogs);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getContext();
                FragmentActivity activity = getActivity();
                Dialog dialog = dialogs.get(position);
                if (context != null && activity != null && dialog != null) {
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    Woodoo.dialogs().show(context, fragmentManager, dialog, false);
                }
            }
        });

        setupUI();

        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setupUI();
    }

    private void setupUI() {
        dialogs.clear();
        for (Dialog d : Woodoo.dialogs().getDialogs(getContext())) {
            dialogs.add(d);
        }
        adapter.notifyDataSetChanged();
    }

}
