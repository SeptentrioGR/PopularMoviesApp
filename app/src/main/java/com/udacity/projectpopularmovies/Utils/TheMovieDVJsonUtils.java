package com.udacity.projectpopularmovies.Utils;

import android.content.Context;
import android.util.Log;

import com.udacity.projectpopularmovies.Model.Movie;

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


    //Returns the entire JSON in a string
    public static String[] getSimpleMovieStringFromJson(Context context, String moveJsonStr) throws JSONException{

        String[] parsedMovieData = null;
        JSONObject moveiesJson = new JSONObject(moveJsonStr);
        parsedMovieData = new String[moveiesJson.length()];

        return parsedMovieData;
    }

    //Fills the Array of Movies from the String Json
    public static ArrayList<Movie> fillListFromJson(Context context, String moveJsonStr) throws JSONException{
        final String RESULTS = "results";
        final String POSTER = "poster_path";
        final String TITLE = "title";
        final String OVERVIEW = "overview";
        final String RATING = "vote_average";
        final String RELEASEDATE = "release_date";
        String[] parsedMovieData = null;
        JSONObject moveiesJson = new JSONObject(moveJsonStr);
        JSONArray moviesArray = moveiesJson.getJSONArray(RESULTS);
        parsedMovieData = new String[moviesArray.length()];
        ArrayList<Movie> mMovieList = new ArrayList<>();
        for(int i = 0 ; i < parsedMovieData.length;i++){
            JSONObject title = moviesArray.getJSONObject(i);
            Movie m1 =  new Movie(
                    title.getString(TITLE)
                    ,title.getString(POSTER)
                    ,title.getString(OVERVIEW)
                    ,title.getString(RATING)
            ,title.getString(RELEASEDATE));
            mMovieList.add(m1);
        }
        return mMovieList;
    }




}
