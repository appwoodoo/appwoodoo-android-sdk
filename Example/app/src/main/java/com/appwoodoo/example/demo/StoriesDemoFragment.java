package com.appwoodoo.example.demo;

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
import android.widget.TextView;

import com.appwoodoo.example.R;
import com.appwoodoo.sdk.Woodoo;
import com.appwoodoo.sdk.model.Dialog;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Displays the stories
 *
 *   1. Add settings on www.appwoodoo.com
 *   2. Run this test app
 *   3. Put your API key in the text field
 *
 * More info on www.appwoodoo.com or follow the Github repository.
 *
 * @author wimagguc
 * @since 22.02.17
 */
public class StoriesDemoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stories_demo, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.title_stories);
        title.setText(Woodoo.stories().getStoryWall(getContext()).getTitle());

        try {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Fragment fragment = Woodoo.stories().getFragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        } catch(Exception e) {
            // it might fail if the activity is in the state onSaveInstanceState
        }

        return rootView;
    }

}
