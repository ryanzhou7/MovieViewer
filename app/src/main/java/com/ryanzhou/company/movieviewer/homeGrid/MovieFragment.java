package com.ryanzhou.company.movieviewer.homeGrid;

import android.content.Context;
import android.content.res.Configuration;
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

import com.ryanzhou.company.movieviewer.APIs.TheMovieDbAPI;
import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.helper.ItemOffsetDecoration;
import com.ryanzhou.company.movieviewer.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment implements TheMovieDbAPI.NetworkListener{

    private int mColumnCount = 2;
    public final String LOG_TAG = this.getClass().getSimpleName();

    private RecyclerView mRecyclerView;
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
            mTheMovieDbAPI.getMoviesSortPopular();
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
            mTheMovieDbAPI.getMoviesSortPopular();
        }
        else if( id == R.id.action_filter_ratings ){
            mTheMovieDbAPI.getMoviesSortRatings();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

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
            mMyMovieRecyclerViewAdapter = new MyMovieRecyclerViewAdapter(
                    savedInstanceMovies != null ? savedInstanceMovies : new ArrayList<Movie>(),
                    mListener, context );
            mRecyclerView.setAdapter(mMyMovieRecyclerViewAdapter);
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
