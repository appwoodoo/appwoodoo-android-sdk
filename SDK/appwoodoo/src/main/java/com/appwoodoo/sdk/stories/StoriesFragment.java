package com.appwoodoo.sdk.stories;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.appwoodoo.sdk.model.Story;
import com.appwoodoo.sdk.model.StoryWall;

import java.util.ArrayList;

public class StoriesFragment extends Fragment {

    StoryListAdapter storyListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listView = new ListView(getActivity());

        listView.setBackgroundColor(StoriesHelper.getInstance().getViewOptions().getStoryWallBackgroundColour());

        storyListAdapter = new StoryListAdapter(getActivity().getApplicationContext(), new ArrayList<Story>());

        listView.setAdapter(storyListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent i = new Intent(getActivity(), StoryActivity.class);
                i.putExtra(StoryActivity.STORY_ID_KEY, ((Story) parent.getItemAtPosition(position)).getId() );
                startActivity(i);
            }
        });

        int dividerColour = StoriesHelper.getInstance().getViewOptions().getStoryWallCellDividerColour();
        listView.setDivider(new ColorDrawable(dividerColour));
        listView.setDividerHeight(1);

        refreshListView();

        return listView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refreshListView();
        }
    }

    private void refreshListView() {
        StoryWall storyWall = StoriesHelper.getInstance().getStoryWall(getContext());
        if (storyWall != null) {
            ArrayList<Story> stories = storyWall.getStories();
            storyListAdapter.clear();
            for (Story s: stories) {
                storyListAdapter.add(s);
            }
            storyListAdapter.notifyDataSetChanged();
        }
    }

}
