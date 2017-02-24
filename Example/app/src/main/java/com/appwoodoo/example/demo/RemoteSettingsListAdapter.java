package com.appwoodoo.example.demo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appwoodoo.example.R;
import com.appwoodoo.sdk.Woodoo;
import com.appwoodoo.sdk.model.Dialog;

import java.util.ArrayList;
import java.util.Map;

public class RemoteSettingsListAdapter extends ArrayAdapter<String> {

    private ArrayList items = new ArrayList();

    private Activity activity;

    public RemoteSettingsListAdapter(Activity activity, ArrayList<String> items) {
        super(activity, -1, items);

        this.activity = activity;
        this.items = items;
    }

    @Override
    public String getItem(int position) {
        return (String) items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        AdHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.remotesettings_list_row, parent, false);

            holder = new AdHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.subtitle = (TextView) convertView.findViewById(R.id.subtitle);

            convertView.setTag(holder);

        } else {
            holder = (AdHolder) convertView.getTag();
        }

        String key = getItem(position);
        if (key != null) {
            holder.title.setText(key);
            holder.subtitle.setText( Woodoo.getStringForKey(key) );
        }

        return convertView;
    }

    private static class AdHolder {
        TextView title;
        TextView subtitle;
    }

}
