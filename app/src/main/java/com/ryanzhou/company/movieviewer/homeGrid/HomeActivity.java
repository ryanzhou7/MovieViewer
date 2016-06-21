package com.ryanzhou.company.movieviewer.homeGrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ryanzhou.company.movieviewer.R;
import com.ryanzhou.company.movieviewer.model.Movie;
import com.ryanzhou.company.movieviewer.movieDetails.MovieDetailsActivity;
import com.ryanzhou.company.movieviewer.movieDetails.MovieDetailsFragmentsContainer;

public class HomeActivity extends AppCompatActivity implements MovieGridFragment.OnListFragmentInteractionListener{

    public final String LOG_TAG = this.getClass().getSimpleName();
    private boolean isTwoPane;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        isTwoPane = getResources().getBoolean(R.bool.has_two_panes);
        if (savedInstanceState == null) {
            //Nothing saved, initial creation
            MovieGridFragment mgf = new MovieGridFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_grid_container, mgf)
                    .commit();
        }
        if( isTwoPane ){
//            MovieDetailsFragmentsContainer mdfc = MovieDetailsFragmentsContainer.newInstance();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.movie_details_container, mdfc)
//                    .commit();
        }
    }
    @Override
    public void onListFragmentInteraction(Movie m){
        if( isTwoPane ){
            Bundle bundle = new Bundle();
            bundle.putParcelable(Movie.MOVIE_ITEM_KEY, m);
            MovieDetailsFragmentsContainer mda = MovieDetailsFragmentsContainer.newInstance(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, mda);
        }
        else{
            Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
            intent.putExtra(Movie.MOVIE_ITEM_KEY, m);
            startActivity(intent);
        }

    }

}
