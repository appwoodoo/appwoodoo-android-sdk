package com.appwoodoo.sdk.model;

import com.appwoodoo.sdk.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Dialog {

    private String objectid;
    private String name;
    private String title;
    private String bodyText;
    private String bodyImage;
    private String actionButtonTitle;
    private String actionButtonUrl;
    private String closeButtonTitle;

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getBodyImage() {
        return bodyImage;
    }

    public void setBodyImage(String bodyImage) {
        this.bodyImage = bodyImage;
    }

    public String getActionButtonTitle() {
        return actionButtonTitle;
    }

    public void setActionButtonTitle(String actionButtonTitle) {
        this.actionButtonTitle = actionButtonTitle;
    }

    public String getActionButtonUrl() {
        return actionButtonUrl;
    }

    public void setActionButtonUrl(String actionButtonUrl) {
        this.actionButtonUrl = actionButtonUrl;
    }

    public String getCloseButtonTitle() {
        return closeButtonTitle;
    }

    public void setCloseButtonTitle(String closeButtonTitle) {
        this.closeButtonTitle = closeButtonTitle;
    }

    public static ArrayList<Dialog> parseJSON(String jsonString) {

        ArrayList<Dialog> dialogs = new ArrayList<>();

        if (jsonString == null) {
            return dialogs;
        }

        try {
            JSONObject json = new JSONObject(jsonString);

            JSONArray a = json.getJSONArray("objects");

            for (int i=0; i<a.length(); i++) {
                Dialog s = new Dialog();

                JSONObject dialogJson = a.getJSONObject(i);

                s.setObjectid( dialogJson.optString("objectid") );
                s.setTitle( dialogJson.optString("title") );
                s.setName( dialogJson.optString("name") );
                s.setBodyText( dialogJson.optString("body_text") );
                s.setBodyImage( dialogJson.optString("body_image") );
                s.setActionButtonTitle( dialogJson.optString("action_button_title") );
                s.setActionButtonUrl( dialogJson.optString("action_button_url") );
                s.setCloseButtonTitle( dialogJson.optString("close_button_title") );

                dialogs.add(s);
            }


        } catch(Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

        return dialogs;
    }

    public static String getJsonString(ArrayList<Dialog> dialogs) {

        try {
            JSONArray jsonArray = new JSONArray();

            for (Dialog d : dialogs) {
                JSONObject dialog = d.parseToJSONObject();
                jsonArray.put(dialog);
            }

            JSONObject wrapper = new JSONObject();
            wrapper.put("objects", jsonArray);

            return wrapper.toString();

        } catch(Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

        return "";
    }

    private JSONObject parseToJSONObject() {
        try {
            JSONObject s = new JSONObject();

            if (getObjectid() != null) {
                s.put("objectid", getObjectid());
            }
            if (getTitle() != null) {
                s.put("title", getTitle());
            }
            if (getName() != null) {
                s.put("name", getName());
            }
            if (getBodyText() != null) {
                s.put("body_text", getBodyText());
            }
            if (getBodyImage() != null) {
                s.put("body_image", getBodyImage());
            }
            if (getActionButtonTitle() != null) {
                s.put("action_button_title", getActionButtonTitle());
            }
            if (getActionButtonUrl() != null) {
                s.put("action_button_url", getActionButtonUrl());
            }
            if (getCloseButtonTitle() != null) {
                s.put("close_button_title", getCloseButtonTitle());
            }

            return s;

        } catch(Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
