package com.ryanzhou.company.movieviewer.movieDetails.overviewTab;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.api.TheMovieDB;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.model.MovieReviews;
import com.ryanzhou.company.movieviewer.model.MovieTrailer;
import com.ryanzhou.company.movieviewer.model.Movies;
import com.ryanzhou.company.movieviewer.movieDetails.reviewsTab.MovieReviewRecyclerViewAdapter;
import com.ryanzhou.company.movieviewer.movieDetails.trailersTab.MovieTrailerRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieOverviewFragment extends Fragment{

    private final String LOG_TAG = this.getClass().getSimpleName();
    public final static String TAB_NAME = "Overview";

    private MovieOverviewFragment.OnFragmentInteractionListener mListener;
    private MovieReviewRecyclerViewAdapter mMovieReviewRecyclerViewAdapter;
    private MovieTrailerRecyclerViewAdapter mMovieTrailerRecyclerViewAdapter;
    private List<MovieTrailer> savedInstanceMovieTrailers;
    private RecyclerView mRecyclerView;
    private int mColumnCount = 1;
    private Movie mMovie;
    private CoordinatorLayout mCoordinatorLayout;
    FloatingActionButton mFloatingActionButton;
    ImageView mImageViewThumbnail;
    TextView mTextViewTitle;
    TextView mTextViewUserRating;
    TextView mTextViewReleaseDate;
    TextView mTextViewSynopsis;

    public static MovieOverviewFragment newInstance(Bundle bundle ) {
        MovieOverviewFragment f = new MovieOverviewFragment();
        f.setArguments( bundle );
        return f;
    }


    public MovieOverviewFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        assignWidgetsToClassIVar(view);
        if( getArguments() != null )
            bindMovieData( (Movie)getArguments().getParcelable(Movie.MOVIE_ITEM_KEY) );

        if( savedInstanceState != null ){
            //we have saved instance data
            bindMovieData( (Movie) savedInstanceState.getParcelable(Movie.MOVIE_ITEM_KEY) );
        }
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.saveMovieAsFavorite(null);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void saveMovieAsFavorite(Movie m);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelable(Movie.MOVIE_ITEM_KEY, mMovie);
        if( mMovieReviewRecyclerViewAdapter != null && mMovieReviewRecyclerViewAdapter.getmMovieReviewValues() != null ){
            outState.putParcelableArrayList(MovieReviews.MOVIEREVIEWS_LIST_KEY,
                    (ArrayList<? extends Parcelable>) mMovieReviewRecyclerViewAdapter.getmMovieReviewValues());
        }
        super.onSaveInstanceState(outState);
    }

    private void assignWidgetsToClassIVar(View v){
        mImageViewThumbnail = (ImageView) v.findViewById(R.id.imageView_thumbnail);
        mTextViewTitle = (TextView) v.findViewById(R.id.textView_title);
        mTextViewUserRating = (TextView) v.findViewById(R.id.textView_user_ratings);
        mTextViewReleaseDate = (TextView) v.findViewById(R.id.textView_release_date);
        mTextViewSynopsis = (TextView) v.findViewById(R.id.textView_plot_synopsis);
        mFloatingActionButton = (FloatingActionButton) v.findViewById(R.id.floatingActionButton_add_movie);
        mCoordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences =
                        getContext().getSharedPreferences(
                                getContext().getString(R.string.shared_pref_movies_key), Context.MODE_PRIVATE);
                Set<String> savedMoviesSet = new HashSet<String>();
                savedMoviesSet = sharedPreferences.getStringSet(Movies.MOVIES_LIST_KEY, savedMoviesSet);
                Gson gson = new Gson();
                String jsonMovie = gson.toJson(mMovie);
                savedMoviesSet.add(jsonMovie);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet(Movies.MOVIES_LIST_KEY, savedMoviesSet);
                editor.commit();
                Snackbar.make(mCoordinatorLayout, "Saved "+ mMovie.getmOriginalTitle(),
                        Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    private void bindMovieData(Movie m){
        mMovie = m;
        String title = mMovie.getmOriginalTitle();
        String date = mMovie.getmReleaseDate();
        String synopsis = mMovie.getmSynopsis();
        Double rating = mMovie.getmUserRating();
        String imageUrl = mMovie.getmImagePath();

        mTextViewTitle.setText( validateMovieData(title) );
        mTextViewReleaseDate.setText( validateMovieData(date) );
        mTextViewSynopsis.setText( validateMovieData(synopsis) );
        StringBuilder ratingString = new StringBuilder( Double.toString(rating) );
        ratingString.append( getResources().getString(R.string.movie_rating_denominator) );
        mTextViewUserRating.setText( validateMovieData(ratingString.toString() ) );

        if( mMovie.isValidImageUrl() ){
            mImageViewThumbnail.setVisibility(View.VISIBLE);
            String posterImageUrl = getImageUrlWithPathAndSize(imageUrl,
                    TheMovieDB.IMAGE_SIZE_SMALL);
            Picasso.with(getContext())
                    .load(posterImageUrl)
                    .placeholder(R.drawable.loading)
                    .into(mImageViewThumbnail);
        }

    }

    public String getImageUrlWithPathAndSize(String imagePath, String size){
        StringBuilder fullImageUrl = new StringBuilder(TheMovieDB.BASE_IMAGE_URL);
        return fullImageUrl.append(size).append(imagePath).toString();
    }

    private String validateMovieData(String s){
        return s == null || s.isEmpty() ?
                getResources().getString(R.string.movie_detail_not_available): s;
    }

}
