package com.ryanzhou.company.movieviewer.movieDetails.trailersTab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ryanzhou on 6/11/16.
 */
public class MovieTrailerRecyclerViewAdapter extends RecyclerView.Adapter<MovieTrailerRecyclerViewAdapter.ViewHolder> {

    private final List<MovieTrailer> mMovieTrailerValues;
    private final MovieTrailersFragment.OnListFragmentInteractionListener mListener;
    public final String LOG_TAG = this.getClass().getSimpleName();
    private Context mContext;

    public MovieTrailerRecyclerViewAdapter(List<MovieTrailer> items,
                                           MovieTrailersFragment.OnListFragmentInteractionListener listener,
                                           Context context) {
        mMovieTrailerValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = getmMovieTrailerValues().get(position);
        MovieTrailer movieTrailer = holder.mItem;
        holder.mTrailerType.setText(movieTrailer.getmType());
        String url = "http://img.youtube.com/vi/" + holder.mItem.getmYoutubeVideoID() + "/0.jpg";
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(holder.mImageButton);
    }


    @Override
    public int getItemCount() {
        return getmMovieTrailerValues().size();
    }

    public List<MovieTrailer> getmMovieTrailerValues() {
        return mMovieTrailerValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTrailerType;
        public final ImageButton mImageButton;
        public final View mView;
        public MovieTrailer mItem;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTrailerType = (TextView) view.findViewById(R.id.textView_video_type);
            mImageButton = (ImageButton) view.findViewById(R.id.imageButtonVideo);
        }
    }
}

