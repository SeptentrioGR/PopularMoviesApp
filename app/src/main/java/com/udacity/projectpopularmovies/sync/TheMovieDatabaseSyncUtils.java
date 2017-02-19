package com.udacity.projectpopularmovies.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.udacity.projectpopularmovies.Data.MoviesContract;
import com.udacity.projectpopularmovies.Data.Preferences;
import com.udacity.projectpopularmovies.Utils.Networking;
import com.udacity.projectpopularmovies.Utils.TheMovieDVJsonUtils;

import java.net.URL;


/**
 * Created by Petros on 2/19/2017.
 */

public class TheMovieDatabaseSyncUtils {

    public static final String TAG = TheMovieDatabaseSyncUtils.class.getSimpleName();
    //An Array of All Our Movies and their details
    ContentValues[] movieValues = null;
    private String filterChosen;
    Context mContext;

    public TheMovieDatabaseSyncUtils(Context conext) {
        mContext = conext;
    }

    public void syncMovies() {
        new TheMovieDatabaseSyncUtils.RetrieveMovieDataTask().execute();
        try {
            if (movieValues != null && movieValues.length != 0) {
                ContentResolver popularmoviesContentRevolver = mContext.getContentResolver();
                popularmoviesContentRevolver.delete(MoviesContract.MovieEntry.CONTENT_URI, null, null);
                popularmoviesContentRevolver.bulkInsert(MoviesContract.MovieEntry.CONTENT_URI
                        , movieValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class RetrieveMovieDataTask extends AsyncTask<Void, Void, ContentValues[]> {

        @Override
        protected ContentValues[] doInBackground(Void... params) {
            filterChosen = Preferences.getPreferenceFilter(mContext);
            try {
                URL myJasonURL = Networking.buildUrl(filterChosen);
                String JsonResponse = Networking.getResponseFromHttpUrl(myJasonURL);
                ContentValues[] movieValues = TheMovieDVJsonUtils.getMovieContentValuesFromJson(mContext, JsonResponse);
                return movieValues;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ContentValues[] contentValues) {

            movieValues = contentValues;
            mContext.getContentResolver().bulkInsert(MoviesContract.MovieEntry.CONTENT_URI,
                    contentValues);
            super.onPostExecute(movieValues);
        }
    }


}
