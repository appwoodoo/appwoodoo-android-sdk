package com.appwoodoo.sdk.model;

import com.appwoodoo.sdk.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoryWall {

    private String title;
    private ArrayList<Story> stories;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Story> getStories() {
        if (stories == null) {
            return new ArrayList<>();
        }
        return stories;
    }

    public void setStories(ArrayList<Story> stories) {
        this.stories = stories;
    }

    public static StoryWall parseJSON(String jsonString) {
        if (jsonString == null) {
            return null;
        }

        StoryWall sw = new StoryWall();

        try {

            JSONObject json = new JSONObject(jsonString);
            JSONObject jsonSW = json.getJSONObject("story_wall");

            if (jsonSW.has("settings")) {
                JSONObject settings = jsonSW.getJSONObject("settings");
                sw.setTitle(settings.getString("title"));
            }

            if (jsonSW.has("stories")) {

                JSONArray stories = jsonSW.getJSONArray("stories");
                ArrayList<Story> sts = new ArrayList<>();

                for (int i=0; i<stories.length(); i++) {
                    Story st = Story.parseJSON(stories.getJSONObject(i));
                    sts.add(st);
                }

                sw.setStories(sts);
            }

        } catch(Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

        return sw;
    }

    public String getJsonString() {

        try {
            JSONObject storyWall = new JSONObject();

            JSONObject settings = new JSONObject();
            settings.put("title", getTitle());

            JSONArray stories = new JSONArray();
            for (Story s : getStories()) {
                JSONObject story = s.parseToJSONObject();
                if (story != null) {
                    stories.put(story);
                }
            }

            storyWall.put("settings", settings);
            storyWall.put("stories", stories);

            JSONObject wrapper = new JSONObject();
            wrapper.put("story_wall", storyWall);

            return wrapper.toString();

        } catch(Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

        return "";
    }

}
