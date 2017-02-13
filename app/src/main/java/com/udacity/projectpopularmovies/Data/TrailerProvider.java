package com.udacity.projectpopularmovies.Data;

import android.content.Context;
import android.os.AsyncTask;

import com.udacity.projectpopularmovies.Model.Review;
import com.udacity.projectpopularmovies.Model.Video;
import com.udacity.projectpopularmovies.MovieActivity;
import com.udacity.projectpopularmovies.Utils.Networking;
import com.udacity.projectpopularmovies.Utils.TheMovieDVJsonUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Petros on 2/12/2017.
 */

public class TrailerProvider {
    public static ArrayList<Video> mTrailers;
    Context mContext;

    public TrailerProvider(Context context, String id) {
        mContext = context;
        mTrailers = syncReview(id);
    }

    public ArrayList<Video> syncReview(String id) {
        new TrailerProvider.RetrieveMovieDataTask().execute(id);
        return mTrailers;
    }

    class RetrieveMovieDataTask extends AsyncTask<String, Void, ArrayList<Video>> {
        @Override
        protected ArrayList<Video> doInBackground(String... params) {
            try {
                String id = params[0];
                URL myJasonURL = Networking.buildUrlForVideos(id);
                String JsonResponse = Networking.getResponseFromHttpUrl(myJasonURL);
                return TheMovieDVJsonUtils.getMovieTrailersFromJSON(mContext, JsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Video> trailerArrayList) {
            if (trailerArrayList != null && trailerArrayList.size() != 0) {
                mTrailers = trailerArrayList;
            }

        }
    }

}
