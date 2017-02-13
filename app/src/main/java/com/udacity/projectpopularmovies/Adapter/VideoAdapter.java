package com.udacity.projectpopularmovies.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.projectpopularmovies.Data.ReviewProvider;
import com.udacity.projectpopularmovies.Model.Review;
import com.udacity.projectpopularmovies.Model.Video;
import com.udacity.projectpopularmovies.R;
import com.udacity.projectpopularmovies.Utils.Networking;
import com.udacity.projectpopularmovies.Utils.TheMovieDVJsonUtils;

import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Petros on 2/12/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private ArrayList<Video> mVideos;
    private Context mContext;

    public VideoAdapter(Context context){
        mContext = context;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        public final TextView mNumberDisplay;
        public final ImageView mImageButton;
        public final TextView mTrailerName;


        public VideoViewHolder(View view){
            super(view);
            mNumberDisplay = (TextView)view.findViewById(R.id.pm_trailer_number);
            mImageButton = (ImageView)view.findViewById(R.id.pm_image_poster);
            mTrailerName = (TextView)view.findViewById(R.id.pm_trailer_name);
        }
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_movie_trailers;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttatchTopParntImmediatly = false;
        View view = inflater.inflate(layoutIdForListItem,viewGroup,shouldAttatchTopParntImmediatly);
        return new VideoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final VideoAdapter.VideoViewHolder holder, final int position)
    {
        int itemPos = position +1;
        final Video video = mVideos.get(position);
        holder.mNumberDisplay.setText("Review " + itemPos);
        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String key = video.getKey();
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("www.youtube.com")
                            .appendPath("watch")
                            .appendQueryParameter("v",key);
                    String myUrl = builder.build().toString();
                    Log.i(TAG,myUrl);
                    playYoutube(myUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        holder.mTrailerName.setText(video.getName());

    }

    public void playYoutube(String url){
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount()
    {
        if (null == mVideos) return 0;
        return mVideos.size();
    }



    public void setMovieData(ArrayList<Video> videoData) {
        mVideos = videoData;
        Log.i(TAG,"LOADING COMPLETED " + mVideos.size());
        notifyDataSetChanged();
    }


}
