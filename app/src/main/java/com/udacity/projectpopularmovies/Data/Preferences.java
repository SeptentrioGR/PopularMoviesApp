package com.udacity.projectpopularmovies.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.udacity.projectpopularmovies.R;

/**
 * Created by Petros on 1/26/2017.
 */

public class Preferences {

    public static String getPreferenceFilter(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String keyForFilter = context.getString(R.string.pref_filters_key);
        String defultFilter = context.getString(R.string.pref_filters_popular);
        return preferences.getString(keyForFilter, defultFilter);
    }

    public static String getPreferenceImageSize(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String keyForFilter = context.getString(R.string.pref_image_size_key);
        String defultFilter = context.getString(R.string.pref_image_label);
        return preferences.getString(keyForFilter, defultFilter);
    }


    public static String getPreferenceGridSize(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String keyForGridSize = context.getString(R.string.pref_grid_colums_key);
        String defailtGridSize = context.getString(R.string.pref_grid_size_label_two);
        String size = preferences.getString(keyForGridSize, defailtGridSize);
        Log.v("Preferences", "" + size);
        return size;
    }

}
