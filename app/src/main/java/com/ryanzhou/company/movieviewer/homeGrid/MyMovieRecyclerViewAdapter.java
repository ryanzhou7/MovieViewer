package com.ryanzhou.company.movieviewer.homeGrid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ryanzhou.company.movieviewer.APIs.TheMovieDbAPI;
import com.ryanzhou.company.movieviewer.R;
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
        String posterImageUrl = TheMovieDbAPI.getImageUrlWithPathAndSize( currentMovie.getmImagePath(),
                TheMovieDbAPI.IMAGE_SIZE_LARGE);
        Picasso.with(mContext)
                .load(posterImageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.poster_not_available)
                .into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
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
