package com.udacity.projectpopularmovies.Utils;

import android.net.Uri;
import android.util.Log;

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

    //?sort_by=popularity.desc&api_key=6e31e5b00383e8475d3da5710392f51d
    private static final String STATIC_DB_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String DATABASE_BASE_URL = STATIC_DB_URL;
    final static String PARAM_SORT = "sort_by";
    final static String sort_by = "popularity.desc";
    final static String PARAM_API = "API";
    final static String API = "6e31e5b00383e8475d3da5710392f51d";


    public static URL buildUrl(){
        Uri buildUri = Uri.parse(DATABASE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SORT,sort_by)
                .appendQueryParameter(PARAM_API,API).build();
        URL url = null;
        try{
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG,"Build URI" + url);
        return url;
    }

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
