package com.appwoodoo.sdk.stories;

import android.content.Context;
import android.content.SharedPreferences;

import com.appwoodoo.sdk.io.StoriesApiHandler;
import com.appwoodoo.sdk.model.Story;
import com.appwoodoo.sdk.model.StoryWall;
import com.appwoodoo.sdk.storage.SharedPreferencesHelper;

public class StoriesHelper {

    private StoryViewOptions storyViewOptions;
    private static StoriesHelper _instance;

    private StoriesHelper() {}

    public static StoriesHelper getInstance() {
        synchronized(StoriesHelper.class) {
            if (_instance == null) {
                _instance = new StoriesHelper();
            }
        }
        return _instance;
    }

    public void init(Context context) {
        SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
        StoriesApiHandler.getStoryData(sp);
    }

    public StoryWall getStoryWall(Context context) {
        SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
        String storedString = SharedPreferencesHelper.getInstance().getStoredString(sp, StoriesApiHandler.KEY_STORYWALL_DATA);
        return StoryWall.parseJSON(storedString);
    }

    public Story getStoryById(int id, Context context) {
        StoryWall sw = getStoryWall(context);
        if (sw != null && sw.getStories() != null) {
            for (Story s : sw.getStories()) {
                if (s.getId() == id) {
                    return s;
                }
            }
        }
        return null;
    }

    public StoriesFragment getFragment() {
        return new StoriesFragment();
    }

    public StoryViewOptions getViewOptions() {
        if (storyViewOptions != null) {
            return storyViewOptions;
        }
        storyViewOptions = new StoryViewOptions();
        return storyViewOptions;
    }

}
