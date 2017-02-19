package com.udacity.projectpopularmovies.Data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Petros on 2/6/2017.
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.udacity.projectpopularmovies";
    public static final Uri BASE_CONTENT_URL = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URL.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();
        //Table Name
        public static final String TABLE_NAME = "movies";
        //ID
        public static final String MOVIE_ID = "movie_id";
        //TITLE
        public static final String MOVIE_TITLE = "movie_title";
        //OVERVIEW
        public static final String MOVIE_DESCRIPTION = "movie_description";
        //POPULARITY
        public static final String MOVIE_POPULARITY = "movie_popularity";
        //GENRE_IDS
        //VOTE_COUNT
        public static final String MOVIE_VOTE_COUNT = "movie_vote_count";
        //VOTE_AVERAGE
        public static final String MOVIE_VOTE_AVERAGE = "movie_vote_average";
        //RELEASE_DATE
        public static final String MOVIE_RELEASE_DATE = "movie_date";
        //POSTER_PATH
        public static final String MOVIE_POSTER_PATH = "movie_poster_path";
        //BACKDROP_PATH
        public static final String MOVIE_BACKDROP_PATH = "movie_backdrop_path";
        //Is Movie Favorite
        public static final String MOVIE_IS_FAVORITE = "movie_is_favorite";
        //Get Uri for a single Movie
        public static Uri buildMovieUriWithId(long id) {
            Uri contentUriWithId = CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();

            return contentUriWithId;
        }

    }


}
