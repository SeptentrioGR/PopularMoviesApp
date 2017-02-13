package com.udacity.projectpopularmovies.Utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.udacity.projectpopularmovies.BuildConfig;
import com.udacity.projectpopularmovies.Model.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Petros on 1/17/2017.
 */

public class Networking {
    private static final String TAG = Networking.class.getSimpleName();
    //The Basic URL For Our API
    private static final String STATIC_DB_URL = "https://api.themoviedb.org/3";
    private static final String DATABASE_BASE_URL = STATIC_DB_URL;
    //The Path of the api link
    final static String PARAM_CATEGORY = "movie";
    final static String PARAM_FILTER = "top_rated";
    static String CUR_FILTER = PARAM_FILTER;
    //The API Key For Our Api
    final static String PARAM_API = "api_key";


    //Review Paths
    final static String PARAM_REVIEW_CATEGORY = "review";


    //Build the URL and Returns it with Filter
    public static URL buildUrl(String filter) {
        final String key = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        CUR_FILTER = filter;
        //https://api.themoviedb.org/3 + movie + popular
        Uri buildUri = Uri.parse(DATABASE_BASE_URL).buildUpon()
                .appendEncodedPath(PARAM_CATEGORY)
                .appendEncodedPath(CUR_FILTER)
                .appendQueryParameter(PARAM_API, key).build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Building URI for Movies \n" + url);
        return url;
    }

    public static URL buildUrlForYoutube(String key) {
        Uri buildUri = Uri.parse("http://www.youtube.com/").buildUpon().appendPath("watch").appendQueryParameter("?v=", key).build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Building URI for Reviews \n" + url);
        return url;
    }

    //Get Response from Http with URL and get the string of JSON
    @Nullable
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildUrlForReviews(String id) {
        final String key = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        Uri buildUri = Uri.parse(DATABASE_BASE_URL).buildUpon()
                .appendEncodedPath(PARAM_CATEGORY)
                .appendEncodedPath(id)
                .appendEncodedPath("reviews")
                .appendQueryParameter(PARAM_API, key).build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Building URI for Reviews \n" + url);
        return url;
    }

    public static URL buildUrlForVideos(String id) {
        final String key = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        Uri buildUri = Uri.parse(DATABASE_BASE_URL).buildUpon()
                .appendEncodedPath(PARAM_CATEGORY)
                .appendEncodedPath(id)
                .appendEncodedPath("videos")
                .appendQueryParameter(PARAM_API, key).build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Building URI for Videos\n" + url);
        return url;
    }


}
