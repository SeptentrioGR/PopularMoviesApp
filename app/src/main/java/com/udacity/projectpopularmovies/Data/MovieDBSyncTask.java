package com.udacity.projectpopularmovies.Data;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import com.udacity.projectpopularmovies.Utils.Networking;
import com.udacity.projectpopularmovies.Utils.TheMovieDVJsonUtils;
import java.net.URL;

public class MovieDBSyncTask {
    public static final String TAG = MovieDBSyncTask.class.getSimpleName();
    //An Array of All Our Movies and their details
    ContentValues[] movieValues = null;
    private String filterChosen;
    Context mContext;

    public MovieDBSyncTask(Context conext) {
        mContext = conext;
    }

    public void syncMovies() {
        new RetrieveMovieDataTask().execute();
        try {
            if (movieValues != null && movieValues.length != 0) {
                ContentResolver popularmoviesContentRevolver = mContext.getContentResolver();
                //popularmoviesContentRevolver.delete(MoviesContract.MovieEntry.CONTENT_URI, null, null);
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
