package com.udacity.projectpopularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Petros on 2/6/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";
    public static final int DATABASE_VERSION = 4;


        public MovieDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase myDb) {
            final String SQL_CREATE_MOVIES_TABLE =
                    "CREATE TABLE "+ MoviesContract.MovieEntry.TABLE_NAME + "(" +
                            MoviesContract.MovieEntry._ID                    + " INTEGER NOT NULL PRIMARY KEY,"+
                            MoviesContract.MovieEntry.MOVIE_ID           + " TEXT  NOT NULL,"+
                            MoviesContract.MovieEntry.MOVIE_TITLE        + " TEXT NOT NULL," +
                            MoviesContract.MovieEntry.MOVIE_DESCRIPTION        + " TEXT NOT NULL," +
                            MoviesContract.MovieEntry.MOVIE_POPULARITY  + " TEXT  NOT NULL,"+
                            MoviesContract.MovieEntry.MOVIE_VOTE_COUNT       + " TEXT NOT NULL," +
                            MoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE         + " TEXT NOT NULL," +
                            MoviesContract.MovieEntry.MOVIE_RELEASE_DATE         + " TEXT NOT NULL," +
                            MoviesContract.MovieEntry.MOVIE_POSTER_PATH         + " TEXT NOT NULL," +
                            MoviesContract.MovieEntry.MOVIE_BACKDROP_PATH         + " TEXT NOT NULL," +
                            MoviesContract.MovieEntry.MOVIE_RATING         + " TEXT NOT NULL," +
                            " UNIQUE (" + MoviesContract.MovieEntry.MOVIE_ID +") ON CONFLICT REPLACE);";
            myDb.execSQL(SQL_CREATE_MOVIES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);

        }

}
