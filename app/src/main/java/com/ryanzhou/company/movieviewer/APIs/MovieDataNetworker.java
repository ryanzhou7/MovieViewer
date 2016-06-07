package com.ryanzhou.company.movieviewer.APIs;

/**
 * Created by ryanzhou on 5/9/16.
 */
public interface MovieDataNetworker {

    public void getMoviesSortPopular();
    public void getMoviesSortRatings();
    public void getPopularMovies();
    public void getTopRatedMovies();
    public void getReviewsMovie(String movieID);

}
