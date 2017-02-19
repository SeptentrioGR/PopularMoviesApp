package com.udacity.projectpopularmovies.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import com.udacity.projectpopularmovies.MainActivity;

import java.util.ArrayList;

/**
 * Created by Petros on 2/6/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";
    public static final int DATABASE_VERSION = 6;
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME;
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase myDb) {
        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_NAME + "(" +
                        MoviesContract.MovieEntry._ID + " INTEGER NOT NULL PRIMARY KEY," +
                        MoviesContract.MovieEntry.MOVIE_ID + " TEXT  NOT NULL," +
                        MoviesContract.MovieEntry.MOVIE_TITLE + " TEXT NOT NULL," +
                        MoviesContract.MovieEntry.MOVIE_DESCRIPTION + " TEXT NOT NULL," +
                        MoviesContract.MovieEntry.MOVIE_POPULARITY + " TEXT  NOT NULL," +
                        MoviesContract.MovieEntry.MOVIE_VOTE_COUNT + " TEXT NOT NULL," +
                        MoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE + " TEXT NOT NULL," +
                        MoviesContract.MovieEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                        MoviesContract.MovieEntry.MOVIE_POSTER_PATH + " TEXT NOT NULL," +
                        MoviesContract.MovieEntry.MOVIE_BACKDROP_PATH + " TEXT NOT NULL," +
                        MoviesContract.MovieEntry.MOVIE_IS_FAVORITE + " INTEGER NOT NULL DEFAULT 0," +
                        " UNIQUE (" + MoviesContract.MovieEntry.MOVIE_ID + ") ON CONFLICT IGNORE);";
        myDb.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

    }
}
