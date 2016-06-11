package com.ryanzhou.company.movieviewer.detailMovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.MovieTrailer;

import java.util.List;

/**
 * Created by ryanzhou on 6/11/16.
 */
public class MovieTrailerRecyclerViewAdapter extends RecyclerView.Adapter<MovieTrailerRecyclerViewAdapter.ViewHolder> {

    private final List<MovieTrailer> mMovieTrailerValues;
    private final MovieDetailsFragment.OnFragmentInteractionListener mListener;
    public final String LOG_TAG = this.getClass().getSimpleName();
    private Context mContext;

    public MovieTrailerRecyclerViewAdapter(List<MovieTrailer> items,
                                           MovieDetailsFragment.OnFragmentInteractionListener listener,
                                           Context context) {
        mMovieTrailerValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_review_item, parent, false);
        //TODO create movie trailer item
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = getmMovieTrailerValues().get(position);
        MovieTrailer movieTrailer = holder.mItem;
    }

    @Override
    public int getItemCount() {
        return getmMovieTrailerValues().size();
    }

    public List<MovieTrailer> getmMovieTrailerValues() {
        return mMovieTrailerValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public MovieTrailer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }
}

