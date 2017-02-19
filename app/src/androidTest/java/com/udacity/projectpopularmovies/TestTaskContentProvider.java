/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.udacity.projectpopularmovies;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.projectpopularmovies.Data.MovieDbHelper;
import com.udacity.projectpopularmovies.Data.MovieProvider;
import com.udacity.projectpopularmovies.Data.MoviesContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class TestTaskContentProvider {

    public static final String[] MAIN_MOVIE_PROJECTION = {
            MoviesContract.MovieEntry.MOVIE_ID,
            MoviesContract.MovieEntry.MOVIE_TITLE,
            MoviesContract.MovieEntry.MOVIE_DESCRIPTION,
            MoviesContract.MovieEntry.MOVIE_POPULARITY,
            MoviesContract.MovieEntry.MOVIE_VOTE_COUNT,
            MoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE,
            MoviesContract.MovieEntry.MOVIE_RELEASE_DATE,
            MoviesContract.MovieEntry.MOVIE_POSTER_PATH,
            MoviesContract.MovieEntry.MOVIE_BACKDROP_PATH,
            MoviesContract.MovieEntry.MOVIE_IS_FAVORITE,
    };
    public static final int INDEX_MOVIE_ID = 0;
    public static final int INDEX_MOVIE_TITLE = 1;
    public static final int INDEX_MOVIE_DESCRIPTION = 2;
    public static final int INDEX_MOVIE_POPULARITY = 3;
    public static final int INDEX_MOVIE_VOTE_COUNT = 4;
    public static final int INDEX_MOVIE_VOTE_AVERAGE = 5;
    public static final int INDEX_MOVIE_RELEASE_DATE = 6;
    public static final int INDEX_MOVIE_POSTER_PATH = 7;
    public static final int INDEX_MOVIE_BACKDROP_PATH = 8;
    public static final int INDEX_MOVIE_IS_FAVORITE = 9;

    /* Context used to access various parts of the system */
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    /**
     * Because we annotate this method with the @Before annotation, this method will be called
     * before every single method with an @Test annotation. We want to start each test clean, so we
     * delete all entries in the tasks directory to do so.
     */
    @Before
    public void setUp() {
        /* Use TaskDbHelper to get access to a writable database */
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(MoviesContract.MovieEntry.TABLE_NAME, null, null);
    }


    //================================================================================
    // Test ContentProvider Registration
    //================================================================================


    /**
     * This test checks to make sure that the content provider is registered correctly in the
     * AndroidManifest file. If it fails, you should check the AndroidManifest to see if you've
     * added a <provider/> tag and that you've properly specified the android:authorities attribute.
     */
    @Test
    public void testProviderRegistry() {

        /*
         * A ComponentName is an identifier for a specific application component, such as an
         * Activity, ContentProvider, BroadcastReceiver, or a Service.
         *
         * Two pieces of information are required to identify a component: the package (a String)
         * it exists in, and the class (a String) name inside of that package.
         *
         * We will use the ComponentName for our ContentProvider class to ask the system
         * information about the ContentProvider, specifically, the authority under which it is
         * registered.
         */
        String packageName = mContext.getPackageName();
        String taskProviderClassName = MovieProvider.class.getName();
        ComponentName componentName = new ComponentName(packageName, taskProviderClassName);

        try {

            /*
             * Get a reference to the package manager. The package manager allows us to access
             * information about packages installed on a particular device. In this case, we're
             * going to use it to get some information about our ContentProvider under test.
             */
            PackageManager pm = mContext.getPackageManager();

            /* The ProviderInfo will contain the authority, which is what we want to test */
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority = providerInfo.authority;
            String expectedAuthority = packageName;

            /* Make sure that the registered authority matches the authority from the Contract */
            String incorrectAuthority =
                    "Error: TaskContentProvider registered with authority: " + actualAuthority +
                            " instead of expected authority: " + expectedAuthority;
            assertEquals(incorrectAuthority,
                    actualAuthority,
                    expectedAuthority);

        } catch (PackageManager.NameNotFoundException e) {
            String providerNotRegisteredAtAll =
                    "Error: TaskContentProvider not registered at " + mContext.getPackageName();
            /*
             * This exception is thrown if the ContentProvider hasn't been registered with the
             * manifest at all. If this is the case, you need to double check your
             * AndroidManifest file
             */
            fail(providerNotRegisteredAtAll);
        }
    }


    //================================================================================
    // Test UriMatcher
    //================================================================================


    private static final Uri TEST_TASKS = MoviesContract.MovieEntry.CONTENT_URI;
    // Content URI for a single task with id = 1
    private static final Uri TEST_TASK_WITH_ID = TEST_TASKS.buildUpon().appendPath("1").build();


    /**
     * This function tests that the UriMatcher returns the correct integer value for
     * each of the Uri types that the ContentProvider can handle. Uncomment this when you are
     * ready to test your UriMatcher.
     */
    @Test
    public void testUriMatcher() {

        /* Create a URI matcher that the TaskContentProvider uses */
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        /* Test that the code returned from our matcher matches the expected TASKS int */
        String tasksUriDoesNotMatch = "Error: The TASKS URI was matched incorrectly.";
        int actualTasksMatchCode = testMatcher.match(TEST_TASKS);
        int expectedTasksMatchCode = MovieProvider.CODE_MOVIES;
        assertEquals(tasksUriDoesNotMatch,
                actualTasksMatchCode,
                expectedTasksMatchCode);

        /* Test that the code returned from our matcher matches the expected TASK_WITH_ID */
        String taskWithIdDoesNotMatch =
                "Error: The TASK_WITH_ID URI was matched incorrectly.";
        int actualTaskWithIdCode = testMatcher.match(TEST_TASK_WITH_ID);
        int expectedTaskWithIdCode = MovieProvider.CODE_MOVIES_WITH_ID;
        assertEquals(taskWithIdDoesNotMatch,
                actualTaskWithIdCode,
                expectedTaskWithIdCode);
    }


    //================================================================================
    // Test Insert
    //================================================================================


    /**
     * Tests inserting a single row of data via a ContentResolver
     */
    @Test
    public void testInsert() {

        /* Create values to insert */
        ContentValues[] movieContentValues = new ContentValues[10];
        ContentValues testTaskValue = new ContentValues();
        for (int i = 0; i < movieContentValues.length; i++) {
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_ID, "Test ID");
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_TITLE, "TEST TITLE");
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_DESCRIPTION, "TEST DESCRIPTION");
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_POPULARITY, "TEST POPULARITY");
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_VOTE_COUNT, "TEST VOTE COUNT");
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE, "TEST VOTE AVERAGE");
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_RELEASE_DATE, "TEST RELEASE DATE");
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_POSTER_PATH, "TEST POSTER PATH");
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_BACKDROP_PATH, "TEST BACKDROP PATH");
            testTaskValue.put(MoviesContract.MovieEntry.MOVIE_IS_FAVORITE, 1);
            movieContentValues[i] = testTaskValue;
        }

        /* TestContentObserver allows us to test if notifyChange was called appropriately */
        TestUtilities.TestContentObserver taskObserver = TestUtilities.getTestContentObserver();

        ContentResolver contentResolver = mContext.getContentResolver();

        /* Register a content observer to be notified of changes to data at a given URI (tasks) */
        contentResolver.registerContentObserver(
                /* URI that we would like to observe changes to */
                MoviesContract.MovieEntry.CONTENT_URI,
                /* Whether or not to notify us if descendants of this URI change */
                true,
                /* The observer to register (that will receive notifyChange callbacks) */
                taskObserver);

        int updaterows = contentResolver.bulkInsert(MoviesContract.MovieEntry.CONTENT_URI,movieContentValues);


        Uri expectedUri = ContentUris.withAppendedId(   MoviesContract.MovieEntry.CONTENT_URI, 1);

        String insertProviderFailed = "Unable to insert item through Provider";
        assertEquals(10,updaterows);


        /*
         * If this fails, it's likely you didn't call notifyChange in your insert method from
         * your ContentProvider.
         */
        taskObserver.waitForNotificationOrFail();

        /*
         * waitForNotificationOrFail is synchronous, so after that call, we are done observing
         * changes to content and should therefore unregister this observer.
         */
        contentResolver.unregisterContentObserver(taskObserver);
    }


    //================================================================================
    // Test Query (for tasks directory)
    //================================================================================


    /**
     * Inserts data, then tests if a query for the tasks directory returns that data as a Cursor
     */
    @Test
    public void testQuery() {

        /* Get access to a writable database */
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        /* Create values to insert */
        ContentValues testTaskValues = new ContentValues();
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_ID, "102");
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_TITLE, "TEST TITLE");
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_DESCRIPTION, "TEST DESCRIPTION");
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_POPULARITY, "TEST POPULARITY");
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_VOTE_COUNT, "TEST VOTE COUNT");
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE,"TEST VOTE AVERAGE");
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_RELEASE_DATE, "TEST RELEASE DATE");
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_POSTER_PATH, "TEST POSTER PATH");
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_BACKDROP_PATH,"TEST BACKDROP PATH");
        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_IS_FAVORITE,1);

        /* Insert ContentValues into database and get a row ID back */
        long taskRowId = database.insert(
                /* Table to insert values into */
                MoviesContract.MovieEntry.TABLE_NAME,
                null,
                /* Values to insert into table */
                testTaskValues);

        String insertFailed = "Unable to insert directly into the database";
        assertTrue(insertFailed, taskRowId != -1);

        /* We are done with the database, close it now. */
        database.close();

        String test[] = {
                "102"
        };
        /* Perform the ContentProvider query */
        Cursor taskCursor = mContext.getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                /* Columns; leaving this null returns every column in the table */
                MAIN_MOVIE_PROJECTION,
                /* Optional specification for columns in the "where" clause above */
                MoviesContract.MovieEntry.MOVIE_ID + "=?",
                /* Values for "where" clause */
                test,
                /* Sort order to return in Cursor */
                null);


        String queryFailed = "Query failed to return a valid Cursor";
        taskCursor.moveToFirst();

        assertEquals(test.length,taskCursor.getCount());
        /* We are done with the cursor, close it now. */
        taskCursor.close();
    }

    //================================================================================
    // Test Delete (for a single item)
    //================================================================================


//    /**
//     * Tests deleting a single row of data via a ContentResolver
//     */
//    @Test
//    public void testDelete() {
//        /* Access writable database */
//        MovieDbHelper helper = new MovieDbHelper(InstrumentationRegistry.getTargetContext());
//        SQLiteDatabase database = helper.getWritableDatabase();
//
//        /* Create a new row of task data */
//        ContentValues testTaskValues = new ContentValues();
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_ID, "Test ID");
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_TITLE, "TEST TITLE");
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_DESCRIPTION, "TEST DESCRIPTION");
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_POPULARITY, "TEST POPULARITY");
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_VOTE_COUNT, "TEST VOTE COUNT");
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE,"TEST VOTE AVERAGE");
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_RELEASE_DATE, "TEST RELEASE DATE");
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_POSTER_PATH, "TEST POSTER PATH");
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_BACKDROP_PATH,"TEST BACKDROP PATH");
//        testTaskValues.put(MoviesContract.MovieEntry.MOVIE_IS_FAVORITE,1);
//
//        /* Insert ContentValues into database and get a row ID back */
//        long taskRowId = database.insert(
//                /* Table to insert values into */
//                MoviesContract.MovieEntry.TABLE_NAME,
//                null,
//                /* Values to insert into table */
//                testTaskValues);
//
//        /* Always close the database when you're through with it */
//        database.close();
//
//        String insertFailed = "Unable to insert into the database";
//        assertTrue(insertFailed, taskRowId != -1);
//
//
//        /* TestContentObserver allows us to test if notifyChange was called appropriately */
//        TestUtilities.TestContentObserver taskObserver = TestUtilities.getTestContentObserver();
//
//        ContentResolver contentResolver = mContext.getContentResolver();
//
//        /* Register a content observer to be notified of changes to data at a given URI (tasks) */
//        contentResolver.registerContentObserver(
//                /* URI that we would like to observe changes to */
//                MoviesContract.MovieEntry.CONTENT_URI,
//                /* Whether or not to notify us if descendants of this URI change */
//                true,
//                /* The observer to register (that will receive notifyChange callbacks) */
//                taskObserver);
//
//
//
//        /* The delete method deletes the previously inserted row with id = 1 */
//        Uri uriToDelete =  MoviesContract.MovieEntry.CONTENT_URI.buildUpon().appendPath("1").build();
//        int tasksDeleted = contentResolver.delete(uriToDelete, null, null);
//
//        String deleteFailed = "Unable to delete item in the database";
//        assertTrue(deleteFailed, tasksDeleted != 0);
//
//        /*
//         * If this fails, it's likely you didn't call notifyChange in your delete method from
//         * your ContentProvider.
//         */
//        taskObserver.waitForNotificationOrFail();
//
//        /*
//         * waitForNotificationOrFail is synchronous, so after that call, we are done observing
//         * changes to content and should therefore unregister this observer.
//         */
//        contentResolver.unregisterContentObserver(taskObserver);
//    }

}
