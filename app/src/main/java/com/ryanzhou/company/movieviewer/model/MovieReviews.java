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

    //For instance, if our property name matches that of the JSON key,
    // then we do not need to annotate the attributes.
    //However, if we have a different name we wish to use,
    // we can simply annotate the declaration with
    @SerializedName("results")
    public List<MovieReview> items;
    public MovieReviews(){
        items = new ArrayList<>();
    }

    public static MovieReviews parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieReviews movieReviews = gson.fromJson(response, MovieReviews.class);
        return movieReviews;
    }

    //
}
