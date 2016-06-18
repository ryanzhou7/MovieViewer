package com.ryanzhou.company.movieviewer.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanzhou on 6/9/16.
 */
public class Movies {

    //"results" is key for json data to be put into items
    public static final String MOVIES_LIST_KEY = "moviesListKey";
    public static final String SHARED_PREF_SAVED_MOVIES_SET_KEY = "sharedPrefSavedMoviesSetKey";
    @SerializedName("results")
    public List<Movie> items;
    public Movies(){
        items = new ArrayList<>();
    }

    public static Movies parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Movies movies = gson.fromJson(response, Movies.class);
        return movies;
    }
}
