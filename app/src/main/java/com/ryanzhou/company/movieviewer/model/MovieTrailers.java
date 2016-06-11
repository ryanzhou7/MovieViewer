package com.ryanzhou.company.movieviewer.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanzhou on 6/11/16.
 */
public class MovieTrailers{

    public static final String MOVIETRAILERS_LIST_KEY = "moviesTrailersListKey";
    //"results" is key for json data to be put into items
    @SerializedName("results") public List<MovieTrailer> items;
    public MovieTrailers(){
        items = new ArrayList<>();
    }

    public static MovieTrailers parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieTrailers movieTrailers = gson.fromJson(response, MovieTrailers.class);
        return movieTrailers;
    }
}
