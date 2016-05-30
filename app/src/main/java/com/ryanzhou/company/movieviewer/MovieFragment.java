package com.ryanzhou.company.movieviewer;

import android.content.Context;
import android.os.Bundle;
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

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.s
 */
public class MovieFragment extends Fragment implements TheMovieDbAPI.NetworkListener{

    // TODO: Customize parameters
    private int mColumnCount = 2;
    public final String LOG_TAG = this.getClass().getSimpleName();

    private OnListFragmentInteractionListener mListener;
    private MyMovieRecyclerViewAdapter mMyMovieRecyclerViewAdapter;

    private TheMovieDbAPI mTheMovieDbAPI;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if( savedInstanceState == null){
            //do a network call since 1st time instantiating
            mTheMovieDbAPI = new TheMovieDbAPI(this);
            mTheMovieDbAPI.getHighestRateMovies();
        }
        else{

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if( mTheMovieDbAPI == null){
            mTheMovieDbAPI = new TheMovieDbAPI(this);
        }
        if (id == R.id.action_filter_popularity) {
            //Do pop req
            mTheMovieDbAPI.getPopularMovies();
        }
        else if( id == R.id.action_filter_ratings ){
            //do action req
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
            //TODO pass in arraylist of objects for
            mMyMovieRecyclerViewAdapter = new MyMovieRecyclerViewAdapter(new ArrayList<Movie>(),
                    mListener, context );
            recyclerView.setAdapter(mMyMovieRecyclerViewAdapter);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Movie m);
    }
}
