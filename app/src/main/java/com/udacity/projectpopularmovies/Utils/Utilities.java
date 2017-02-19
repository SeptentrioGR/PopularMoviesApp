package com.udacity.projectpopularmovies.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Petros on 1/18/2017.
 */

public class Utilities {
    public static Toast myToast;
    //This is used to make Toast
    public static void MakeToast(Context context, String message, int length){
        if(myToast!=null){
            myToast.cancel();
        }else{
            myToast.makeText(context,message,length).show();
        }
    }

    public static void DebugLog(String TAG,String message){
        Log.i(TAG,message);
    }


}
