package com.udacity.projectpopularmovies.Utils;

import android.net.Uri;
import android.util.Log;

import com.udacity.projectpopularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    final static String PARAM_FILTER_2 = "top_rated";
    static String CUR_FILTER = PARAM_FILTER;
    //The API Key For Our Api
    final static String PARAM_API = "api_key";

    //Build the URL with URI without Parameters
    public static URL buildUrl(){
        final String key = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        Uri buildUri = Uri.parse(DATABASE_BASE_URL).buildUpon()
                .appendEncodedPath(PARAM_CATEGORY)
                .appendEncodedPath(CUR_FILTER)
                .appendQueryParameter(PARAM_API,key).build();
        URL url = null;
        try{
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG,"Building URI \n" + url);
        return url;
    }

    //Build the URL and Returns it with Filter
    public static URL buildUrl(String filter){
        final String key = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        CUR_FILTER = filter;
        Uri buildUri = Uri.parse(DATABASE_BASE_URL).buildUpon()
                .appendEncodedPath(PARAM_CATEGORY)
                .appendEncodedPath(CUR_FILTER)
                .appendQueryParameter(PARAM_API,key).build();
        URL url = null;
        try{
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG,"Building URI \n" + url);
        return url;
    }

    //Get Response from Http with URL and get the string of JSON
    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }else{
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }



}
