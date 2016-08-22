package com.appwoodoo.sdk.model;

import com.appwoodoo.sdk.BuildConfig;
import org.json.JSONObject;

public class Story {

    private Integer id;
    private String coverThumb;
    private String body;
    private String url;
    private String date;
    private String lead;
    private String title;
    private String cover;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoverThumb() {
        return coverThumb;
    }

    public void setCoverThumb(String coverThumb) {
        this.coverThumb = coverThumb;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public static Story parseJSON(JSONObject json) {
        if (json == null) {
            return null;
        }

        Story s = new Story();

        try {

            if (json.has("cover_thumb")) {
                s.setCoverThumb( json.getString("cover_thumb") );
            }
            if (json.has("body")) {
                s.setBody( json.getString("body") );
            }
            if (json.has("id")) {
                s.setId( json.getInt("id") );
            }
            if (json.has("url")) {
                s.setUrl( json.getString("url") );
            }
            if (json.has("date")) {
                // TODO parse date
                s.setDate( json.getString("date") );
            }
            if (json.has("lead")) {
                s.setLead( json.getString("lead") );
            }
            if (json.has("title")) {
                s.setTitle( json.getString("title") );
            }
            if (json.has("cover")) {
                s.setCover( json.getString("cover") );
            }

        } catch(Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

        return s;
    }

    public JSONObject parseToJSONObject() {
        try {
            JSONObject s = new JSONObject();
            if (getCoverThumb() != null) {
                s.put("cover_thumb", getCoverThumb());
            }
            if (getId() != null) {
                s.put("id", getId());
            }
            if (getBody() != null) {
                s.put("body", getBody());
            }
            if (getUrl() != null) {
                s.put("url", getUrl());
            }
            if (getDate() != null) {
                s.put("date", getDate());
            }
            if (getLead() != null) {
                s.put("lead", getLead());
            }
            if (getTitle() != null) {
                s.put("title", getTitle());
            }
            if (getCover() != null) {
                s.put("cover", getCover());
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
