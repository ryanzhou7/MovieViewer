package com.ryanzhou.company.movieviewer.detailMovie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryanzhou.company.movieviewer.APIs.TheMovieDb;
import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private final String LOG_TAG = this.getClass().getSimpleName();

    public static final String TITLE = "title";
    public static final String POSTER_IMAGE_URL = "posterImageUrl";
    public static final String PLOT_SYNOPSIS = "plotSynopsis";
    public static final String USER_RATING = "userRating";
    public static final String RELEASE_DATE = "releaseDate";

    Movie mMovie;
    ImageView mImageViewThumbnail;
    TextView mTextViewTitle;
    TextView mTextViewUserRating;
    TextView mTextViewReleaseDate;
    TextView mTextViewSynopsis;

    public static MovieDetailsFragment newInstance( Bundle bundle ) {
        MovieDetailsFragment f = new MovieDetailsFragment();
        f.setArguments( bundle );
        return f;
    }


    public MovieDetailsFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        linkUI(view);
        if( getArguments() != null ){
            //we have data passed in
            Movie m = getArguments().getParcelable(Movie.MOVIE_ITEM_KEY);
            bindData( m );
        }
        else if( !savedInstanceState.isEmpty() ){
            //we have saved instance data
            bindData( (Movie) savedInstanceState.getParcelable(Movie.MOVIE_ITEM_KEY) );
        }
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelable(Movie.MOVIE_ITEM_KEY, mMovie);
        super.onSaveInstanceState(outState);
    }

    private void linkUI(View v){
        mImageViewThumbnail = (ImageView) v.findViewById(R.id.imageView_thumbnail);
        mTextViewTitle = (TextView) v.findViewById(R.id.textView_title);
        mTextViewUserRating = (TextView) v.findViewById(R.id.textView_user_ratings);
        mTextViewReleaseDate = (TextView) v.findViewById(R.id.textView_release_date);
        mTextViewSynopsis = (TextView) v.findViewById(R.id.textView_plot_synopsis);
    }

    private void bindData(Movie m){

        mMovie = m;
        String title = mMovie.getmOriginalTitle();
        String date = mMovie.getmReleaseDate();
        String synopsis = mMovie.getmSynopsis();
        Double rating = mMovie.getmUserRating();
        String imageUrl = mMovie.getmImagePath();

        mTextViewTitle.setText( validateData(title) );
        mTextViewReleaseDate.setText( validateData(date) );
        mTextViewSynopsis.setText( validateData(synopsis) );
        StringBuilder ratingString = new StringBuilder( Double.toString(rating) );
        ratingString.append( getResources().getString(R.string.movie_rating_denominator) );
        mTextViewUserRating.setText( validateData(ratingString.toString() ) );

        if( mMovie.isValidImageUrl() ){
            mImageViewThumbnail.setVisibility(View.VISIBLE);
            String posterImageUrl = TheMovieDb.getImageUrlWithPathAndSize(imageUrl,
                    TheMovieDb.IMAGE_SIZE_SMALL);
            Picasso.with(getContext())
                    .load(posterImageUrl)
                    .placeholder(R.drawable.loading)
                    .into(mImageViewThumbnail);
        }

        new TheMovieDb(null).getReviewsWithId(mMovie.getmMovieID());

    }

    private String validateData( String s){
        return s == null || s.isEmpty() ?
                getResources().getString(R.string.movie_detail_not_available): s;
    }
}
