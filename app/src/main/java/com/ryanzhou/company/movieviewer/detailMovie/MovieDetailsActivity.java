package com.ryanzhou.company.movieviewer.detailMovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryanzhou.company.movieviewer.APIs.TheMovieDbAPI;
import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    private final String LOG_TAG = this.getClass().getSimpleName();

    public static final String TITLE = "title";
    public static final String POSTER_IMAGE_URL = "posterImageUrl";
    public static final String PLOT_SYNOPSIS = "plotSynopsis";
    public static final String USER_RATING = "userRating";
    public static final String RELEASE_DATE = "releaseDate";

    private Movie mMovie;

    @BindView(R.id.imageView_thumbnail) ImageView mImageViewThumbnail;
    @BindView(R.id.textView_title) TextView mTextViewTitle;
    @BindView(R.id.textView_user_ratings) TextView mTextViewUserRating;
    @BindView(R.id.textView_release_date) TextView mTextViewReleaseDate;
    @BindView(R.id.textView_plot_synopsis) TextView mTextViewSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        if( getIntent() != null ){
            Movie m = getIntent().getExtras().getParcelable(Movie.MOVIE_ITEM_KEY);
            bindData( m );
        }
        else if( !savedInstanceState.isEmpty() ){
            bindData( (Movie) savedInstanceState.getParcelable(Movie.MOVIE_ITEM_KEY) );
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelable(Movie.MOVIE_ITEM_KEY, mMovie);
        super.onSaveInstanceState(outState);
    }

    private void bindData(Movie m){

        mMovie = m;
        String title = mMovie.getmOriginalTitle();
        String date = mMovie.getmReleaseDate();
        String synopsis = mMovie.getmSynopsis();
        Double rating = mMovie.getmUserRating();
        String imageUrl = mMovie.getmImagePath();

        mTextViewTitle.setText(title);
        mTextViewReleaseDate.setText(date);
        mTextViewSynopsis.setText( synopsis.length() == 0 ? getResources().getString(R.string.movie_no_plot_available) : synopsis);
        StringBuilder ratingString = new StringBuilder( Double.toString(rating) );
        ratingString.append( getResources().getString(R.string.movie_rating_denominator) );
        mTextViewUserRating.setText( ratingString.toString() );

        String posterImageUrl = TheMovieDbAPI.getImageUrlWithPath(imageUrl);
        Picasso.with(getApplicationContext())
                .load(posterImageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.poster_not_available)
                .into(mImageViewThumbnail);

    }
}
