package com.ryanzhou.company.movieviewer.homeGrid;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.api.TheMovieDB;
import com.ryanzhou.company.movieviewer.helper.ItemOffsetDecoration;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.model.Movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieGridFragment extends Fragment implements Callback<Movies>{

    public final String LOG_TAG = this.getClass().getSimpleName();
    private int mColumnCount = 2;
    private RecyclerView mRecyclerView;
    private OnListFragmentInteractionListener mListener;
    private MovieRecyclerViewAdapter mMovieRecyclerViewAdapter;
    private List<Movie> savedInstanceMovies;

    public MovieGridFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState == null){
            loadPopularMoviesToList();
        }
        else{
            savedInstanceMovies = savedInstanceState.getParcelableArrayList(Movies.MOVIES_LIST_KEY);
        }
    }

    @NonNull
    private Retrofit buildRetrofitWithUrl(String baseUrl){
        return new Retrofit.Builder()
                .baseUrl( baseUrl )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void loadTopRatedMoviesToList(){
        Retrofit retrofit = buildRetrofitWithUrl( TheMovieDB.BASE_URL );
        TheMovieDB mdb = retrofit.create(TheMovieDB.class);
        Call<Movies> call = mdb.loadTopRatedMovies();
        call.enqueue(this);
    }

    private void loadPopularMoviesToList(){
        Retrofit retrofit = buildRetrofitWithUrl( TheMovieDB.BASE_URL );
        TheMovieDB mdb = retrofit.create(TheMovieDB.class);
        Call<Movies> call = mdb.loadPopularMovies();
        call.enqueue(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        if( mMovieRecyclerViewAdapter != null && mMovieRecyclerViewAdapter.getmValues() != null ){
            outState.putParcelableArrayList(Movies.MOVIES_LIST_KEY,
                    (ArrayList<? extends Parcelable>) mMovieRecyclerViewAdapter.getmValues());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter_popularity)
            loadPopularMoviesToList();
        else if( id == R.id.action_filter_ratings )
            loadTopRatedMoviesToList();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        //http://www.android4devs.com/2014/12/how-to-make-material-design-app.html
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
            mMovieRecyclerViewAdapter = new MovieRecyclerViewAdapter(
                    savedInstanceMovies != null ? savedInstanceMovies : new ArrayList<Movie>(),
                    mListener, context );
            mRecyclerView.setAdapter(mMovieRecyclerViewAdapter);
            savedInstanceMovies = null;
        }
        return view;
    }

    private int determineColumnCount(int configCode){
        if (configCode == Configuration.ORIENTATION_LANDSCAPE)
            return 3;
        //is Configuration.ORIENTATION_PORTRAIT
        return 2;
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


    //Callback<Movies> implementation
    @Override
    public void onResponse(Call<Movies> call, Response<Movies> response) {
        List<Movie> currentList = mMovieRecyclerViewAdapter.getmValues();
        currentList.clear();
        currentList.addAll(response.body().items);
        mMovieRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<Movies> call, Throwable t) {

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Movie m);
    }
}
