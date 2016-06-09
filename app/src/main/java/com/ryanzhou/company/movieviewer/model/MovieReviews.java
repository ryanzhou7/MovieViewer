package com.ryanzhou.company.movieviewer.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanzhou on 6/8/16.
 */
public class MovieReviews {

    public static final String MOVIEREVIEWS_LIST_KEY = "moviesReviewsListKey";
    //"results" is key for json data to be put into items
    @SerializedName("results") public List<MovieReview> items;
    public MovieReviews(){
        items = new ArrayList<>();
    }

    public static MovieReviews parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieReviews movieReviews = gson.fromJson(response, MovieReviews.class);
        return movieReviews;
    }
}
