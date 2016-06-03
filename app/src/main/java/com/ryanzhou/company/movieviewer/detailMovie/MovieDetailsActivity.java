package com.ryanzhou.company.movieviewer.detailMovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryanzhou.company.movieviewer.APIs.TheMovieDbAPI;
import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private final String LOG_TAG = this.getClass().getSimpleName();

    public static final String TITLE = "title";
    public static final String POSTER_IMAGE_URL = "posterImageUrl";
    public static final String PLOT_SYNOPSIS = "plotSynopsis";
    public static final String USER_RATING = "userRating";
    public static final String RELEASE_DATE = "releaseDate";

    Movie mMovie;
    ImageView mImageViewThumbnail;
    TextView mTextViewTitle;
    TextView mTextViewUserRating;
    TextView mTextViewReleaseDate;
    TextView mTextViewSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        linkUI();
        if( getIntent() != null ){
            //we have data passed in
            Movie m = getIntent().getExtras().getParcelable(Movie.MOVIE_ITEM_KEY);
            bindData( m );
        }
        else if( !savedInstanceState.isEmpty() ){
            //we have saved instance data
            bindData( (Movie) savedInstanceState.getParcelable(Movie.MOVIE_ITEM_KEY) );
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelable(Movie.MOVIE_ITEM_KEY, mMovie);
        super.onSaveInstanceState(outState);
    }

    private void linkUI(){
        mImageViewThumbnail = (ImageView)findViewById(R.id.imageView_thumbnail);
        mTextViewTitle = (TextView) findViewById(R.id.textView_title);
        mTextViewUserRating = (TextView) findViewById(R.id.textView_user_ratings);
        mTextViewReleaseDate = (TextView) findViewById(R.id.textView_release_date);
        mTextViewSynopsis = (TextView) findViewById(R.id.textView_plot_synopsis);
    }

    private void bindData(Movie m){

        mMovie = m;
        String title = mMovie.getmOriginalTitle();
        String date = mMovie.getmReleaseDate();
        String synopsis = mMovie.getmSynopsis();
        Double rating = mMovie.getmUserRating();
        String imageUrl = mMovie.getmImagePath();

        mTextViewTitle.setText( validateData(title) );
        mTextViewReleaseDate.setText( validateData(date) );
        mTextViewSynopsis.setText( validateData(synopsis) );
        StringBuilder ratingString = new StringBuilder( Double.toString(rating) );
        ratingString.append( getResources().getString(R.string.movie_rating_denominator) );
        mTextViewUserRating.setText( validateData(ratingString.toString() ) );

        String posterImageUrl = TheMovieDbAPI.getImageUrlWithPath(imageUrl);
        Picasso.with(getApplicationContext())
                .load(posterImageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.poster_not_available)
                .into(mImageViewThumbnail);

    }

    private String validateData( String s){
        return s == null || s.isEmpty() ?
                getResources().getString(R.string.movie_detail_not_available): s;
    }

}
