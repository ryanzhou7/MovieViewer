package com.ryanzhou.company.movieviewer.detailMovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.model.MovieReview;

import java.util.List;

/**
 * Created by ryanzhou on 6/9/16.
 */
public class MyMovieReviewRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieReviewRecyclerViewAdapter.ViewHolder> {

    private final List<MovieReview> mValues;
    private final MovieDetailsFragment.OnFragmentInteractionListener mListener;
    public final String LOG_TAG = this.getClass().getSimpleName();
    private Context mContext;

    public MyMovieReviewRecyclerViewAdapter(List<MovieReview> items,
                                            MovieDetailsFragment.OnFragmentInteractionListener listener,
                                      Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = getmValues().get(position);
//        Movie currentMovie = holder.mItem;

    }

    @Override
    public int getItemCount() {
        return getmValues().size();
    }

    public List<MovieReview> getmValues() {
        return mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageView;
        public final View mView;
        public Movie mItem;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.imageViewPoster);
            mView = view;
        }
    }
}
