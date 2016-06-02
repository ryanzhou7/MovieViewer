package com.ryanzhou.company.movieviewer.APIs;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ryanzhou.company.movieviewer.BuildConfig;
import com.ryanzhou.company.movieviewer.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanzhou on 5/9/16.
 * FetchSortedDataTask recycle from Udacity's HTTP GET for weather data example github
 */
public class TheMovieDbAPI implements MovieDataNetworker {
    public final String LOG_TAG = this.getClass().getSimpleName();

    public final static String BASE_IMAGE_URL =  "https://image.tmdb.org/t/p/";
    public final static String IMAGE_NOT_AVAILABLE_URL =
            "https://upload.wikimedia.org/wikipedia/commons/6/64/Poster_not_available.jpg";

    final static String IMAGE_SIZE = "w342";

    final static String BASE_URL = "https://api.themoviedb.org";
    final static String VERSION = "/3";
    final static String DISCOVER_SERVICE = "/discover";
    final static String MOVIE_RETRIEVE = "/movie";
    final static String QUERY_PARAM = "?";
    final static String API_KEY_PARAM = "api_key";
    final static String PAGE_LIMIT_PARAM = "page";
    final static String PAGE_LIMIT = "10";

    private static final String API_KEY = BuildConfig.API_KEY;
    final static String SORT_PARAM = "sort_by";
    final static String ASCENDING = ".asc";
    final static String DESCENDING = ".desc";
    final static String POPULARITY = "popularity";
    final static String VOTE_AVERAGE = "vote_average";

    private NetworkListener mListener;
    public interface NetworkListener {
        void onPostExecuteDone( List<Movie> list);
    }

    public TheMovieDbAPI(Fragment fragment){
        if (fragment instanceof NetworkListener) {
            mListener = (NetworkListener) fragment;
        } else {
            throw new RuntimeException(fragment.toString() + " must implement NetworkListener");
        }
    }

    public static String getImageUrlWithPath(String imagePath){
        StringBuilder fullImageUrl = new StringBuilder(BASE_IMAGE_URL);
        return fullImageUrl.append(IMAGE_SIZE).append(imagePath).toString();
    }

    @Override
    public void getPopularMovies() {
        FetchSortedDataTask f = new FetchSortedDataTask();
        f.execute(POPULARITY);
    }

    @Override
    public void getHighestRateMovies() {
        FetchSortedDataTask f = new FetchSortedDataTask();
        f.execute(VOTE_AVERAGE);
    }

    public class FetchSortedDataTask extends AsyncTask<String, Void, List<Movie> >{
        @Override
        protected List<Movie> doInBackground(String... params) {
            //called execute without param
            if (params.length == 0) {
                return null;
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String responseJsonStr = null;
            try {
                StringBuilder FULL_URL = new StringBuilder();
                FULL_URL.append(BASE_URL).append(VERSION).append(DISCOVER_SERVICE).
                        append(MOVIE_RETRIEVE).append(QUERY_PARAM);

                Uri builtUri = Uri.parse( String.valueOf(FULL_URL) ).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, API_KEY)
                        .appendQueryParameter(SORT_PARAM, params[0] + DESCENDING)
                        .appendQueryParameter(PAGE_LIMIT_PARAM, PAGE_LIMIT)
                        .build();

                URL url = new URL(builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                responseJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(responseJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            // This will only happen if there was an error getting or parsing
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            if(result != null){
                mListener.onPostExecuteDone(result);
                //mListener = null;
            }else{
                Log.e(LOG_TAG, "NO MOVIE RESULTS FOUND");
            }
        }

    }

    private List<Movie> getMovieDataFromJson(String responseJsonStr) throws JSONException{
        final String RESULTS = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_IMAGE = "poster_path";
        final String SYNOPSIS = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE = "release_date";

        List<Movie> movies = new ArrayList<>();
        JSONObject responseJson = new JSONObject(responseJsonStr);
        JSONArray resultsArray = responseJson.getJSONArray(RESULTS);
        for(int i = 0; i< resultsArray.length(); i++){
            JSONObject currentObject = resultsArray.getJSONObject(i);
            double voteAverage = currentObject.getDouble(USER_RATING);
            String title = currentObject.getString(ORIGINAL_TITLE);
            String synopsis = currentObject.getString(SYNOPSIS);
            String releaseDate = currentObject.getString(RELEASE_DATE);
            String postImage = currentObject.getString(POSTER_IMAGE);
            Movie newMovie = new Movie(title, postImage, synopsis, releaseDate, voteAverage);
            movies.add( newMovie );
        }
        return movies;
    }

}
