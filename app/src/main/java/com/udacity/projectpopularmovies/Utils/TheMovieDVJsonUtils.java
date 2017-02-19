package com.udacity.projectpopularmovies.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.util.Log;

import com.udacity.projectpopularmovies.Data.MovieDBSyncTask;
import com.udacity.projectpopularmovies.Data.MovieDbHelper;
import com.udacity.projectpopularmovies.Data.MoviesContract;
import com.udacity.projectpopularmovies.Model.Movie;
import com.udacity.projectpopularmovies.Model.Review;
import com.udacity.projectpopularmovies.Model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petros on 1/19/2017.
 */

public class TheMovieDVJsonUtils {
    final static String TAG = TheMovieDVJsonUtils.class.getSimpleName();
    private static final String RESULTS = "results";


    public static ArrayList<Video> getMovieTrailersFromJSON(Context content, String moveJsonString) throws JSONException {
        ArrayList<Video> mVideos = new ArrayList<>();
        String ID = "id";
        String ISO_639_1 = "iso_639_1";
        String ISO_3166_1 = "iso_3166_1";
        String KEY = "key";
        String NAME = "name";
        String SITE = "site";
        String SIZE = "size";
        String TYPE = "type";

        String[] parsedMovieData;
        JSONObject ReviewJSONString = new JSONObject(moveJsonString);
        JSONArray moviesArray = ReviewJSONString.getJSONArray(RESULTS);
        parsedMovieData = new String[moviesArray.length()];
        Video video = null;
        for (int i = 0; i < parsedMovieData.length; i++) {
            JSONObject jsonObject = moviesArray.getJSONObject(i);
            video = new Video(
                    jsonObject.getString(ID),
                    jsonObject.getString(ISO_639_1),
                    jsonObject.getString(ISO_3166_1),
                    jsonObject.getString(KEY),
                    jsonObject.getString(NAME),
                    jsonObject.getString(SITE),
                    jsonObject.getString(SIZE),
                    jsonObject.getString(TYPE));
            mVideos.add(video);
        }
        return mVideos;
    }

    public static ArrayList<Review> getMovieReviewFromJason(Context content, String moveJsonString) throws JSONException {
        ArrayList<Review> mReviews = new ArrayList<>();
        String ID = "id";
        String AUTHOR = "author";
        String CONTENT = "content";
        String URL = "url";
        String[] parsedMovieData;
        JSONObject ReviewJSONString = new JSONObject(moveJsonString);
        JSONArray moviesArray = ReviewJSONString.getJSONArray(RESULTS);
        parsedMovieData = new String[moviesArray.length()];
        Review review = null;
        for (int i = 0; i < parsedMovieData.length; i++) {
            JSONObject jsonObject = moviesArray.getJSONObject(i);
            review = new Review(
                    jsonObject.getString(ID),
                    jsonObject.getString(AUTHOR),
                    jsonObject.getString(CONTENT),
                    jsonObject.getString(URL));


            mReviews.add(review);
        }

        return mReviews;
    }

    public static ContentValues[] getMovieContentValuesFromJson(Context context, String moveJsonStr) throws JSONException {

        final String ID = "id";
        final String TITLE = "title";
        final String DESCRIPTION = "overview";
        final String POPULARITY = "popularity";
        final String VOTE_COUNT = "vote_count";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASEDATE = "release_date";
        final String POSTER = "poster_path";
        final String BACKDROP = "backdrop_path";
        String[] parsedMovieData;
        JSONObject moveiesJson = new JSONObject(moveJsonStr);
        JSONArray moviesArray = moveiesJson.getJSONArray(RESULTS);
        parsedMovieData = new String[moviesArray.length()];
        ContentValues[] movieContentValues = new ContentValues[moviesArray.length()];

        for (int i = 0; i < parsedMovieData.length; i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            ContentValues movieValue = new ContentValues();
            movieValue.put(MoviesContract.MovieEntry.MOVIE_ID, movie.getString(ID));
            movieValue.put(MoviesContract.MovieEntry.MOVIE_TITLE, movie.getString(TITLE));
            movieValue.put(MoviesContract.MovieEntry.MOVIE_DESCRIPTION, movie.getString(DESCRIPTION));
            movieValue.put(MoviesContract.MovieEntry.MOVIE_POPULARITY, movie.getString(POPULARITY));
            movieValue.put(MoviesContract.MovieEntry.MOVIE_VOTE_COUNT, movie.getString(VOTE_COUNT));
            movieValue.put(MoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE, movie.getString(VOTE_AVERAGE));
            movieValue.put(MoviesContract.MovieEntry.MOVIE_RELEASE_DATE, movie.getString(RELEASEDATE));
            movieValue.put(MoviesContract.MovieEntry.MOVIE_POSTER_PATH, movie.getString(POSTER));
            movieValue.put(MoviesContract.MovieEntry.MOVIE_BACKDROP_PATH, movie.getString(BACKDROP));
            movieContentValues[i] = movieValue;
        }
        return movieContentValues;
    }


}
