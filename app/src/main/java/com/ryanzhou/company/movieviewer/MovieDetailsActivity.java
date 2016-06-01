package com.ryanzhou.company.movieviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryanzhou.company.movieviewer.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private final String LOG_TAG = this.getClass().getSimpleName();

    public static final String TITLE = "title";
    public static final String POSTER_IMAGE_URL = "posterImageUrl";
    public static final String PLOT_SYNOPSIS = "plotSynopsis";
    public static final String USER_RATING = "userRating";
    public static final String RELEASE_DATE = "releaseDate";

    ImageView mImageViewThumbnail;
    TextView mTextViewTitle;
    TextView mTextViewUserRating;
    TextView mTextViewReleaseDate;
    TextView mTextViewSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if( savedInstanceState == null ){
            linkUI();
        }
        if( getIntent() != null){
            bindData( getIntent().getExtras() );
        }
    }

    private void linkUI(){
        mImageViewThumbnail = (ImageView)findViewById(R.id.imageView_thumbnail);
        mTextViewTitle = (TextView) findViewById(R.id.textView_title);
        mTextViewUserRating = (TextView) findViewById(R.id.textView_user_ratings);
        mTextViewReleaseDate = (TextView) findViewById(R.id.textView_release_date);
        mTextViewSynopsis = (TextView) findViewById(R.id.textView_plot_synopsis);
    }

    private void bindData(Bundle bundle ){
        String title = bundle.getString(TITLE);
        String date = bundle.getString(RELEASE_DATE);
        String synopsis = bundle.getString(PLOT_SYNOPSIS);
        Double rating = bundle.getDouble(USER_RATING);
        String imageUrl = bundle.getString(POSTER_IMAGE_URL);

        mTextViewTitle.setText(title);
        mTextViewReleaseDate.setText(date);
        mTextViewSynopsis.setText( synopsis.length() == 0 ? "Plot not available" : synopsis);
        StringBuilder ratingString = new StringBuilder( Double.toString(rating) );
        ratingString.append( getResources().getString(R.string.movie_rating_denominator) );
        mTextViewUserRating.setText( ratingString.toString() );
        String posterImageUrl = Movie.isInValidImageUrl(imageUrl) ? TheMovieDbAPI.notAvailablePostUrl:
                TheMovieDbAPI.getImageUrlWithPath(imageUrl);
        Picasso.with(getApplicationContext()).load(posterImageUrl).into(mImageViewThumbnail);

    }
}
