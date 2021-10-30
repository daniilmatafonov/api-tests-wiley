package com.wiley.api.search.model;

import com.google.gson.annotations.SerializedName;

public class Page {

    public static final String SERIALIZED_NAME_ID = "id";
    @SerializedName(SERIALIZED_NAME_ID)
    private String id;

    public static final String SERIALIZED_NAME_BOOST = "boost";
    @SerializedName(SERIALIZED_NAME_BOOST)
    private double boost;

    public static final String SERIALIZED_NAME_TITLE = "title";
    @SerializedName(SERIALIZED_NAME_TITLE)
    private String title;

    public static final String SERIALIZED_NAME_URL = "url";
    @SerializedName(SERIALIZED_NAME_URL)
    private String url;

    public static final String SERIALIZED_NAME_CONTENT = "content";
    @SerializedName(SERIALIZED_NAME_CONTENT)
    private String content;

    public String getId() {
        return id;
    }

    public double getBoost() {
        return boost;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }
}
