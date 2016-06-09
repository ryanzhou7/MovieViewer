package com.ryanzhou.company.movieviewer.APIs;

import com.ryanzhou.company.movieviewer.model.MovieReviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ryanzhou on 6/9/16.
 */
public interface TheMovieDB2 {
    @GET("/3/movie/{movieID}/reviews")
    Call<MovieReviews> loadMovieReviews(@Path("movieID") String id,
                                        @Query(TheMovieDb.API_KEY_PARAM) String key);
}
