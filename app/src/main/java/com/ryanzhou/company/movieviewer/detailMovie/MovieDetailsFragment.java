package com.ryanzhou.company.movieviewer.detailMovie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ryanzhou.company.movieviewer.API.TheMovieDB;
import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.model.MovieReview;
import com.ryanzhou.company.movieviewer.model.MovieReviews;
import com.ryanzhou.company.movieviewer.model.MovieTrailer;
import com.ryanzhou.company.movieviewer.model.MovieTrailers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsFragment extends Fragment{

    private final String LOG_TAG = this.getClass().getSimpleName();

    public static final String TITLE = "title";
    public static final String POSTER_IMAGE_URL = "posterImageUrl";
    public static final String PLOT_SYNOPSIS = "plotSynopsis";
    public static final String USER_RATING = "userRating";
    public static final String RELEASE_DATE = "releaseDate";

    private MovieDetailsFragment.OnFragmentInteractionListener mListener;
    private MovieReviewRecyclerViewAdapter mMovieReviewRecyclerViewAdapter;
    private MovieTrailerRecyclerViewAdapter mMovieTrailerRecyclerViewAdapter;
    private List<MovieReview> savedInstanceMovieReviews;
    private List<MovieTrailer> savedInstanceMovieTrailers;
    private RecyclerView mRecyclerView;
    private int mColumnCount = 1;

    Movie mMovie;
    ImageView mImageViewThumbnail;
    TextView mTextViewTitle;
    TextView mTextViewUserRating;
    TextView mTextViewReleaseDate;
    TextView mTextViewSynopsis;
    RadioGroup mRadioListInfoGroup;
    RadioButton mRadioButtonMovieTrailer;
    RadioButton mRadioButtonMovieReview;

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
            //we have movie data passed in
            mMovie = getArguments().getParcelable(Movie.MOVIE_ITEM_KEY);
            bindData( mMovie );
        }

        setRecyclerLayout(view);
        setRecyclerAdapter();

        if( savedInstanceState != null ){
            //we have saved instance data
            bindData( (Movie) savedInstanceState.getParcelable(Movie.MOVIE_ITEM_KEY) );
            savedInstanceMovieReviews =
                    savedInstanceState.getParcelableArrayList(MovieReviews.MOVIEREVIEWS_LIST_KEY);
            savedInstanceMovieTrailers =
                    savedInstanceState.getParcelableArrayList(MovieTrailers.MOVIETRAILERS_LIST_KEY);
        }
        else{
            //we have no saved instance data, need to do call for moviewReviews (default display option)
            loadMovieReviewsWithMovieID(mMovie.getmMovieID());
        }
        //TODO figure out why I set this to null?
        //savedInstanceMovieReviews = null;
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
        if( mMovieReviewRecyclerViewAdapter != null && mMovieReviewRecyclerViewAdapter.getmMovieReviewValues() != null ){
            outState.putParcelableArrayList(MovieReviews.MOVIEREVIEWS_LIST_KEY,
                    (ArrayList<? extends Parcelable>) mMovieReviewRecyclerViewAdapter.getmMovieReviewValues());
        }
        super.onSaveInstanceState(outState);
    }

    private void linkUI(View v){
        mImageViewThumbnail = (ImageView) v.findViewById(R.id.imageView_thumbnail);
        mTextViewTitle = (TextView) v.findViewById(R.id.textView_title);
        mTextViewUserRating = (TextView) v.findViewById(R.id.textView_user_ratings);
        mTextViewReleaseDate = (TextView) v.findViewById(R.id.textView_release_date);
        mTextViewSynopsis = (TextView) v.findViewById(R.id.textView_plot_synopsis);
        mRadioListInfoGroup = (RadioGroup) v.findViewById(R.id.radioGroupListView);
        mRadioListInfoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonReviews:
                        switchListToMovieReviews();
                        break;
                    case R.id.radioButtonTrailer:
                        switchListToMovieTrailers();
                        break;
                    default:
                        break;
                }
            }
        });
        mRadioButtonMovieReview = (RadioButton) v.findViewById(R.id.radioButtonReviews);
        mRadioButtonMovieTrailer = (RadioButton) v.findViewById(R.id.radioButtonTrailer);
    }

    private void switchListToMovieReviews() {
        if( savedMovieReviewsDataExists() ){

        }else{
            loadMovieReviewsWithMovieID(mMovie.getmMovieID());
        }
    }

    private boolean savedMovieReviewsDataExists() {
        return savedInstanceMovieReviews == null? false : true;
    }


    private void switchListToMovieTrailers(){
        if( savedMovieTrailersDataExists() ){

        }else{
            loadMovieTrailerIDsWithMovieID(mMovie.getmMovieID());
        }

    }

    private boolean savedMovieTrailersDataExists() {
        return savedInstanceMovieTrailers == null? false : true;
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

    private String validateData( String s){
        return s == null || s.isEmpty() ?
                getResources().getString(R.string.movie_detail_not_available): s;
    }

    private void loadMovieReviewsWithMovieID(long movieID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( TheMovieDB.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDB mdb = retrofit.create(TheMovieDB.class);
        Call<MovieReviews> call = mdb.loadMovieReviewsOf(String.valueOf(movieID));
        //TODO, non anonymous dynamic
        //http://stackoverflow.com/questions/7201231/java-erasure-with-generic-overloading-not-overriding
        call.enqueue(new Callback<MovieReviews>() {
            @Override
            public void onResponse(Call<MovieReviews> call, Response<MovieReviews> response) {
                List<MovieReview> currentList = mMovieReviewRecyclerViewAdapter.getmMovieReviewValues();
                currentList.clear();
                currentList.addAll(response.body().items);
                mMovieReviewRecyclerViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<MovieReviews> call, Throwable t) {

            }
        });
    }

    private void loadMovieTrailerIDsWithMovieID(long movieID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( TheMovieDB.BASE_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDB mdb = retrofit.create(TheMovieDB.class);
        Call<MovieTrailers> call = mdb.loadVideoIDsOf(String.valueOf(movieID));
        call.enqueue(new Callback<MovieTrailers>() {
            @Override
            public void onResponse(Call<MovieTrailers> call, Response<MovieTrailers> response) {
                List<MovieTrailer> currentList = mMovieTrailerRecyclerViewAdapter.getmMovieTrailerValues();
                if( currentList == null)
                    currentList = new ArrayList<MovieTrailer>();
                else
                    currentList.clear();
                currentList.addAll(response.body().items);
                mMovieReviewRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieTrailers> call, Throwable t) {

            }
        });
    }


    private void setRecyclerLayout(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.info_list);
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }
    }

    private void setRecyclerAdapter(){
        if(mRadioButtonMovieReview.isChecked()){
            if( mMovieReviewRecyclerViewAdapter == null ){
                mMovieReviewRecyclerViewAdapter = new MovieReviewRecyclerViewAdapter(
                        savedInstanceMovieReviews != null ? savedInstanceMovieReviews : new ArrayList<MovieReview>(),
                        mListener, getContext() );
                mRecyclerView.setAdapter(mMovieReviewRecyclerViewAdapter);
            } else{
                mMovieReviewRecyclerViewAdapter = new MovieReviewRecyclerViewAdapter(
                        savedInstanceMovieReviews != null ? savedInstanceMovieReviews : new ArrayList<MovieReview>(),
                        mListener, getContext() );
                mRecyclerView.setAdapter(mMovieReviewRecyclerViewAdapter);
            }
        }
        else{
            mMovieTrailerRecyclerViewAdapter = new MovieTrailerRecyclerViewAdapter(
                    savedInstanceMovieTrailers != null ? savedInstanceMovieTrailers : new ArrayList<MovieTrailer>(),
                    mListener, getContext() );
            mRecyclerView.swapAdapter(mMovieTrailerRecyclerViewAdapter, false);
            //boolean: If set to true, RecyclerView will recycle all existing Views.
            // If adapters have stable ids and/or you want to animate the disappearing views,
            // you may prefer to set this to false.
            //http://stackoverflow.com/questions/26635841/recyclerview-change-data-set
        }
    }

}
