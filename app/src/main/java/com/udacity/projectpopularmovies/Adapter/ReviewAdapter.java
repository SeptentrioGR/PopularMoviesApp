package com.udacity.projectpopularmovies.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.projectpopularmovies.Data.ReviewProvider;
import com.udacity.projectpopularmovies.Model.Review;
import com.udacity.projectpopularmovies.MovieActivity;
import com.udacity.projectpopularmovies.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Petros on 2/12/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private ArrayList<Review> mReviews;

    public ReviewAdapter() {    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        public final TextView mNumberDisplay;
        public final TextView mAuthorDisplay;
        public final TextView mContentDisplay;


       public ReviewViewHolder(View view){
            super(view);
            mNumberDisplay = (TextView)view.findViewById(R.id.pm_review_author);
            mAuthorDisplay = (TextView)view.findViewById(R.id.pm_review_overview);
            mContentDisplay = (TextView)view.findViewById(R.id.pm_review_url);
        }
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttatchTopParntImmediatly = false;
        View view = inflater.inflate(layoutIdForListItem,viewGroup,shouldAttatchTopParntImmediatly);
        return new ReviewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position)
    {
            int itemPos = position +1;
            Review review = mReviews.get(position);
            holder.mNumberDisplay.setText("Review " + itemPos);
            holder.mAuthorDisplay.setText(review.getAuthor());
            holder.mContentDisplay.setText(review.getContent());
    }

    @Override
    public int getItemCount()
    {
        if (null == mReviews) return 0;
        return mReviews.size();
    }



    public void setReviewData(ArrayList<Review> reviewData) {
        mReviews = reviewData;
        Log.i(TAG,"LOADING COMPLETED " + mReviews.size());
        notifyDataSetChanged();
    }

}
