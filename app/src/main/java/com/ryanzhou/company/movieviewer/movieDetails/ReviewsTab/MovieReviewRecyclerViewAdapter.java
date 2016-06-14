package com.ryanzhou.company.movieviewer.movieDetails.reviewsTab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.MovieReview;

import java.util.List;

/**
 * Created by ryanzhou on 6/9/16.
 */
public class MovieReviewRecyclerViewAdapter extends RecyclerView.Adapter<MovieReviewRecyclerViewAdapter.ViewHolder> {

    private final List<MovieReview> mMovieReviewValues;
    private final MovieReviewsFragment.OnListFragmentInteractionListener mListener;
    public final String LOG_TAG = this.getClass().getSimpleName();
    private Context mContext;

    public MovieReviewRecyclerViewAdapter(List<MovieReview> items,
                                          MovieReviewsFragment.OnListFragmentInteractionListener listener,
                                          Context context) {
        mMovieReviewValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = getmMovieReviewValues().get(position);
        MovieReview currentMovieReview = holder.mItem;
        holder.mTextViewAuthor.setText(  currentMovieReview.getmAuthor() );
        holder.mTextViewContent.setText( addDoubleQuotes(currentMovieReview.getmContent() ) );
    }

    private String addDoubleQuotes(String s) {
        StringBuilder sb = new StringBuilder("\"");
        sb.append(s).append("\"");
        return sb.toString();
    }

    @Override
    public int getItemCount() {
        return getmMovieReviewValues().size();
    }

    public List<MovieReview> getmMovieReviewValues() {
        return mMovieReviewValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextViewContent;
        public final TextView mTextViewAuthor;
        public final View mView;
        public MovieReview mItem;

        public ViewHolder(View view) {
            super(view);
            mTextViewAuthor = (TextView) view.findViewById(R.id.textView_author);
            mTextViewContent = (TextView) view.findViewById(R.id.textView_content);
            mView = view;
        }
    }
}
