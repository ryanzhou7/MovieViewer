package com.ryanzhou.company.movieviewer.detailMovie;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryanzhou.company.movieviewer.APIs.TheMovieDB2;
import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.helper.ItemOffsetDecoration;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.model.MovieReview;
import com.ryanzhou.company.movieviewer.model.MovieReviews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsFragment extends Fragment implements Callback<MovieReviews>{


    private final String LOG_TAG = this.getClass().getSimpleName();

    public static final String TITLE = "title";
    public static final String POSTER_IMAGE_URL = "posterImageUrl";
    public static final String PLOT_SYNOPSIS = "plotSynopsis";
    public static final String USER_RATING = "userRating";
    public static final String RELEASE_DATE = "releaseDate";

    private MovieDetailsFragment.OnFragmentInteractionListener mListener;
    private MyMovieReviewRecyclerViewAdapter myMovieReviewRecyclerViewAdapter;
    private List<MovieReview> savedInstanceMovieReviews;
    private RecyclerView mRecyclerView;
    private int mColumnCount = 2;

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

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mColumnCount = determineColumnCount( getResources().getConfiguration().orientation );
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.movie_item_offset);
                mRecyclerView.addItemDecoration(itemDecoration);
            }
            myMovieReviewRecyclerViewAdapter = new MyMovieReviewRecyclerViewAdapter(
                    savedInstanceMovieReviews != null ? savedInstanceMovieReviews : new ArrayList<MovieReview>(),
                    mListener, context);
            mRecyclerView.setAdapter(myMovieReviewRecyclerViewAdapter);
            savedInstanceMovieReviews = null;
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

    //Retrofit callback
    @Override
    public void onResponse(Call<MovieReviews> call, Response<MovieReviews> response) {

        //List<Movie> currentList = mMyMovieRecyclerViewAdapter.getmValues();
        //currentList.clear();
        //currentList.addAll(response.body().items);
        //mMyMovieRecyclerViewAdapter.notifyDataSetChanged();

    }

    private int determineColumnCount(int configCode){
        if (configCode == Configuration.ORIENTATION_LANDSCAPE)
            return 3;
        //is Configuration.ORIENTATION_PORTRAIT
        return 2;
    }

    @Override
    public void onFailure(Call<MovieReviews> call, Throwable t) {

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
            String posterImageUrl = getImageUrlWithPathAndSize(imageUrl,
                    TheMovieDB2.IMAGE_SIZE_SMALL);
            Picasso.with(getContext())
                    .load(posterImageUrl)
                    .placeholder(R.drawable.loading)
                    .into(mImageViewThumbnail);
        }

        loadMovieReviewsWithID(mMovie.getmMovieID());

    }

    public String getImageUrlWithPathAndSize(String imagePath, String size){
        StringBuilder fullImageUrl = new StringBuilder(TheMovieDB2.BASE_IMAGE_URL);
        return fullImageUrl.append(size).append(imagePath).toString();
    }

    private String validateData( String s){
        return s == null || s.isEmpty() ?
                getResources().getString(R.string.movie_detail_not_available): s;
    }

    private void loadMovieReviewsWithID(long movieID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( TheMovieDB2.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDB2 mdb = retrofit.create(TheMovieDB2.class);
        Call<MovieReviews> call = mdb.loadMovieReviews(String.valueOf(movieID));
        call.enqueue(this);
    }

}
