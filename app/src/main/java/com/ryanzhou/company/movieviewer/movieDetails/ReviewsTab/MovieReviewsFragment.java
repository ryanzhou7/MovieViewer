package com.ryanzhou.company.movieviewer.movieDetails.reviewsTab;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.api.TheMovieDB;
import com.ryanzhou.company.movieviewer.helper.ItemOffsetDecoration;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.model.MovieReview;
import com.ryanzhou.company.movieviewer.model.MovieReviews;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ryanzhou on 6/13/16.
 */
public class MovieReviewsFragment extends Fragment{

    public final String LOG_TAG = this.getClass().getSimpleName();
    public final static String TAB_NAME = "Reviews";
    private Movie mMovie;
    private int mColumnCount = 1;
    private RecyclerView mRecyclerView;
    private OnListFragmentInteractionListener mListener;
    private MovieReviewRecyclerViewAdapter mMovieReviewRecyclerViewAdapter;
    private List<MovieReview> savedInstanceMovieReviews;

    public MovieReviewsFragment() {}

    public static MovieReviewsFragment newInstance(Bundle bundle) {
        MovieReviewsFragment movieReviewsFragment = new MovieReviewsFragment();
        movieReviewsFragment.setArguments(bundle);
        return movieReviewsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            mMovie = (Movie)getArguments().getParcelable(Movie.MOVIE_ITEM_KEY);
        if( savedInstanceState != null ){
            savedInstanceMovieReviews =
                    savedInstanceState.getParcelableArrayList(MovieReviews.MOVIEREVIEWS_LIST_KEY);
        }
        else{
            loadMovieReviewsWithMovieID(mMovie.getmMovieID());
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState){
        if( mMovieReviewRecyclerViewAdapter != null &&
                mMovieReviewRecyclerViewAdapter.getmMovieReviewValues() != null ){
            outState.putParcelableArrayList(MovieReviews.MOVIEREVIEWS_LIST_KEY,
                    (ArrayList<? extends Parcelable>) mMovieReviewRecyclerViewAdapter.getmMovieReviewValues());
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_reviews_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.movie_item_offset);
                mRecyclerView.addItemDecoration(itemDecoration);
            }

            mMovieReviewRecyclerViewAdapter = new MovieReviewRecyclerViewAdapter(
                    savedInstanceMovieReviews != null ?
                            savedInstanceMovieReviews: new ArrayList<MovieReview>(),
                    mListener, context );
            mRecyclerView.setAdapter(mMovieReviewRecyclerViewAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener)
            mListener = (OnListFragmentInteractionListener) context;
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onMoviewReviewSelected(MovieReview mr);
    }
}