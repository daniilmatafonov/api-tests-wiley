package com.wiley.api.search.model;

import com.google.gson.annotations.SerializedName;

public class Suggestion {

    public static final String SERIALIZED_NAME_TERM = "term";
    @SerializedName(SERIALIZED_NAME_TERM)
    private String term;

    public String getTerm() {
        return term;
    }
}
