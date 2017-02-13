package com.udacity.projectpopularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.udacity.projectpopularmovies.Data.MoviesContract;
import com.udacity.projectpopularmovies.Model.Movie;

import java.util.ArrayList;

import static com.udacity.projectpopularmovies.Data.MoviesContract.MovieEntry.TABLE_NAME;

/**
 * Created by Petros on 2/6/2017.
 */

public class MovieProvider extends ContentProvider {


    private static final int CODE_MOVIES = 100;
    private static final int CODE_MOVIES_WITH_ID = 101;

    static UriMatcher uriMatcher = buildUriMatcher();
    MovieDbHelper movieDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIES, CODE_MOVIES);
        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIES + "/*", CODE_MOVIES_WITH_ID);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match) {
            case CODE_MOVIES: {
                cursor = movieDbHelper.getReadableDatabase().query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_MOVIES_WITH_ID: {
                String id = uri.getPathSegments().get(1);
                String mSelection = "MOVIE_ID=?";
                String[] selectionArguments = new String[]{
                        id
                };
                cursor = movieDbHelper.getReadableDatabase().query(
                        MoviesContract.MovieEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new RuntimeException(
                "We are not implementing insert in Sunshine. Use bulkInsert instead");
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match) {
            case CODE_MOVIES:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int movieDeleted;
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        if (null == selection) selection = "1";
        switch (match) {
            case CODE_MOVIES:
                movieDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        if (movieDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return movieDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int movieDetailUpdated;
        int match = uriMatcher.match(uri);
        switch (match) {
            case CODE_MOVIES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                movieDetailUpdated =
                        movieDbHelper.getWritableDatabase().update(TABLE_NAME,
                        values,"MOVIE_ID=?", new String[] {String.valueOf(id)});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(movieDetailUpdated != 0 ){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return movieDetailUpdated;
    }

    @Override
    public void shutdown() {
        movieDbHelper.close();
        super.shutdown();
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in Sunshine.");
    }
}

