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
import com.appwoodoo.sdk.model.Dialog;
import java.util.ArrayList;

public class DialogsListAdapter extends ArrayAdapter<Dialog> {

    private ArrayList dialogs = new ArrayList();

    private Activity activity;

    public DialogsListAdapter(Activity activity, ArrayList<Dialog> dialogs) {
        super(activity, -1, dialogs);

        this.activity = activity;
        this.dialogs = dialogs;
    }

    @Override
    public Dialog getItem(int position) {
        return (Dialog) dialogs.get(position);
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
            convertView = inflater.inflate(R.layout.dialog_list_row, parent, false);

            holder = new AdHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);

        } else {
            holder = (AdHolder) convertView.getTag();
        }

        Dialog dialog = getItem(position);
        if (dialog != null) {
            holder.title.setText(dialog.getTitle());
        }

        return convertView;
    }

    private static class AdHolder {
        TextView title;
    }

}
