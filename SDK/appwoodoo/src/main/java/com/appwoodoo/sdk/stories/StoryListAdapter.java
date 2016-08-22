package com.appwoodoo.sdk.stories;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appwoodoo.sdk.io.ImageHandler;
import com.appwoodoo.sdk.model.Story;

import java.util.ArrayList;

public class StoryListAdapter extends ArrayAdapter<Story> {

    private ArrayList<Story> stories = new ArrayList<>();

    private Context context;

    public StoryListAdapter(Context context, ArrayList<Story> stories) {
        super(context, -1, stories);

        this.context = context;
        this.stories = stories;
    }

    @Override
    public Story getItem(int position) {
        return stories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StoryHolder holder;

        if (convertView == null) {
            int height = StoriesHelper.getInstance().getViewOptions().getStoryWallCellHeight();
            Typeface typeface = StoriesHelper.getInstance().getViewOptions().getStoryWallCellTypeface();

            RelativeLayout relativeLayout = new RelativeLayout(context);
            relativeLayout.setBackgroundColor(StoriesHelper.getInstance().getViewOptions().getStoryWallBackgroundColour());

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            relativeLayout.setPadding(10, 10, 10, 10);
            relativeLayout.setLayoutParams(params);

            holder = new StoryHolder();

            holder.title = new TextView(context);
            RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 75);
            titleParams.setMargins(height, 10, 0, 0);
            holder.title.setLayoutParams(titleParams);
            holder.title.setPadding(5, 5, 5, 5);
            holder.title.setGravity(Gravity.CENTER_VERTICAL);
            holder.title.setTextSize(20);
            holder.title.setSingleLine();
            holder.title.setEllipsize(TextUtils.TruncateAt.END);
            holder.title.setTextColor( StoriesHelper.getInstance().getViewOptions().getStoryWallCellTitleColour() );
            if (typeface != null) {
                holder.title.setTypeface(typeface);
            }
            relativeLayout.addView(holder.title);

            holder.lead = new TextView(context);
            RelativeLayout.LayoutParams leadParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height-100);
            leadParams.setMargins(height, 70, 0, 40);
            holder.lead.setLayoutParams(leadParams);
            holder.lead.setPadding(5, 5, 5, 5);
            holder.lead.setGravity(Gravity.TOP);
            holder.lead.setTextSize(14);
            holder.lead.setLines( (int) Math.floor((height-100) / 42) );
            holder.lead.setEllipsize(TextUtils.TruncateAt.END);
            holder.lead.setTextColor( StoriesHelper.getInstance().getViewOptions().getStoryWallCellTextColour() );
            if (typeface != null) {
                holder.lead.setTypeface(typeface);
            }
            relativeLayout.addView(holder.lead);

            holder.date = new TextView(context);
            RelativeLayout.LayoutParams dateParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
            dateParams.setMargins(height, height-60, 0, 0);
            holder.date.setLayoutParams(dateParams);
            holder.date.setPadding(5, 5, 5, 5);
            holder.date.setGravity(Gravity.BOTTOM);
            holder.date.setTextSize(10);
            holder.date.setTextColor( StoriesHelper.getInstance().getViewOptions().getStoryWallCellDateColour() );
            if (typeface != null) {
                holder.date.setTypeface(typeface);
            }
            relativeLayout.addView(holder.date);

            holder.cover = new ImageView(context);
            RelativeLayout.LayoutParams coverParams = new RelativeLayout.LayoutParams(height-30, height-30);
            coverParams.setMargins(10, 15, 20, 15);
            holder.cover.setLayoutParams(coverParams);
            holder.cover.setBackgroundColor( StoriesHelper.getInstance().getViewOptions().getStoryWallForegroundColour() );
            holder.cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
            relativeLayout.addView(holder.cover);

            convertView = relativeLayout;
            convertView.setTag(holder);

        } else {
            holder = (StoryHolder) convertView.getTag();
        }

        holder.title.setText(getItem(position).getTitle());
        holder.lead.setText(getItem(position).getLead());
        holder.date.setText(getItem(position).getDate());
        if (getItem(position).getCoverThumb() != null) {
            ImageHandler.downloadIntoImageView(getItem(position).getCoverThumb(), holder.cover);
        }

        return convertView;
    }

    private static class StoryHolder {
        TextView title;
        TextView lead;
        TextView date;
        ImageView cover;
    }

}
