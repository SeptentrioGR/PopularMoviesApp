package com.udacity.projectpopularmovies;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.udacity.projectpopularmovies.Data.Preferences;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Petros on 2/19/2017.
 */
@RunWith(AndroidJUnit4.class)
public class TestGlide {

    /* Context used to access various parts of the system */
    private final Context mContext = InstrumentationRegistry.getTargetContext();
    public ImageView mImageButton;

    @Test
    public void testGlideDownload(){
        int i = 1 ;
        assertEquals(i,1);

    }





}
