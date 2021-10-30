package com.wiley.api.search.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResp {

    public static final String SERIALIZED_NAME_SUGGESTIONS = "suggestions";
    @SerializedName(SERIALIZED_NAME_SUGGESTIONS)
    private List<Suggestion> suggestions;

    public static final String SERIALIZED_NAME_PAGES = "pages";
    @SerializedName(SERIALIZED_NAME_PAGES)
    private List<Page> pages;

    public static final String SERIALIZED_NAME_SHOW_SEE_ALL_PRODUCTS = "showSeeAllProducts";
    @SerializedName(SERIALIZED_NAME_SHOW_SEE_ALL_PRODUCTS)
    private boolean showSeeAllProducts;

    public static final String SERIALIZED_NAME_SHOW_SEE_ALL_PAGES = "showSeeAllPages";
    @SerializedName(SERIALIZED_NAME_SHOW_SEE_ALL_PAGES)
    private boolean showSeeAllPages;

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public List<Page> getPages() {
        return pages;
    }

    public boolean isShowSeeAllProducts() {
        return showSeeAllProducts;
    }

    public boolean isShowSeeAllPages() {
        return showSeeAllPages;
    }
}
