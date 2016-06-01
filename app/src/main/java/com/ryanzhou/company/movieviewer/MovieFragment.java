package com.ryanzhou.company.movieviewer;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.ryanzhou.company.movieviewer.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment implements TheMovieDbAPI.NetworkListener{

    private int mColumnCount = 2;
    public final String LOG_TAG = this.getClass().getSimpleName();

    private OnListFragmentInteractionListener mListener;
    private MyMovieRecyclerViewAdapter mMyMovieRecyclerViewAdapter;
    private List<Movie> savedInstanceMovies;

    private TheMovieDbAPI mTheMovieDbAPI;

    public MovieFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState == null){
            mTheMovieDbAPI = new TheMovieDbAPI(this);
            mTheMovieDbAPI.getHighestRateMovies();
        }
        else{
            savedInstanceMovies = savedInstanceState.getParcelableArrayList(Movie.MOVIES_LIST_KEY);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        if( mMyMovieRecyclerViewAdapter != null && mMyMovieRecyclerViewAdapter.getmValues() != null ){
            outState.putParcelableArrayList(Movie.MOVIES_LIST_KEY,
                    (ArrayList<? extends Parcelable>) mMyMovieRecyclerViewAdapter.getmValues());
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
        if( mTheMovieDbAPI == null){
            mTheMovieDbAPI = new TheMovieDbAPI(this);
        }
        if (id == R.id.action_filter_popularity) {
            mTheMovieDbAPI.getPopularMovies();
        }
        else if( id == R.id.action_filter_ratings ){
            mTheMovieDbAPI.getHighestRateMovies();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mMyMovieRecyclerViewAdapter = new MyMovieRecyclerViewAdapter(
                    savedInstanceMovies != null ? savedInstanceMovies : new ArrayList<Movie>(),
                    mListener, context );
            recyclerView.setAdapter(mMyMovieRecyclerViewAdapter);
            savedInstanceMovies = null;
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPostExecuteDone(List<Movie> list) {
        List<Movie> currentList = mMyMovieRecyclerViewAdapter.getmValues();
        currentList.clear();
        currentList.addAll(list);
        mMyMovieRecyclerViewAdapter.notifyDataSetChanged();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Movie m);
    }
}
