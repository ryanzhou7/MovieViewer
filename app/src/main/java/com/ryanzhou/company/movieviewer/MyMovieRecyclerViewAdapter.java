package com.ryanzhou.company.movieviewer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ryanzhou.company.movieviewer.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

    private final List<Movie> mValues;
    private final MovieFragment.OnListFragmentInteractionListener mListener;
    public final String LOG_TAG = this.getClass().getSimpleName();
    private Context mContext;

    public MyMovieRecyclerViewAdapter(List<Movie> items,
                                      MovieFragment.OnListFragmentInteractionListener listener,
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
        holder.mItem = getmValues().get(position);
        Movie currentMovie = holder.mItem;
        final String imageSize = "w342";

        if( currentMovie.getmImagePath() == "null" ){
            final String posterImageUrl = "https://upload.wikimedia.org/wikipedia/commons/6/64/Poster_not_available.jpg";
            Picasso.with(mContext).load(posterImageUrl).into(holder.mImageView);
        }
        else{
            final String posterImageUrl = "https://image.tmdb.org/t/p/" + imageSize + currentMovie.getmImagePath();
            Picasso.with(mContext).load(posterImageUrl).into(holder.mImageView);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getmValues().size();
    }

    public List<Movie> getmValues() {
        return mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public Movie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.imageViewPoster);
        }
    }
}
