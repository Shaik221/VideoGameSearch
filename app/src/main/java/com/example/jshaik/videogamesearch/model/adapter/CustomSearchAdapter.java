package com.example.jshaik.videogamesearch.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jshaik.videogamesearch.R;
import com.example.jshaik.videogamesearch.model.beans.GamesResultsData;
import com.example.jshaik.videogamesearch.utils.Constants;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CustomSearchAdapter extends RecyclerView.Adapter<CustomSearchAdapter.MyViewHolder> {

    private List<GamesResultsData> dataSet;
    private String TAG = this.getClass().getName();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.game_title);
            this.imageViewIcon = itemView.findViewById(R.id.list_image);
        }
    }

    public CustomSearchAdapter(Context context, List<GamesResultsData> data) {
        Context mContext = context;
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        Context context = holder.imageViewIcon.getContext();
        GamesResultsData item = dataSet.get(listPosition);

        TextView textViewName = holder.textViewName;
        ImageView imageView = holder.imageViewIcon;

        //setting game name
        textViewName.setText(dataSet.get(listPosition).getName());

        String imString = dataSet.get(listPosition).getImage().getIconUrl();

        //if there is no icon url available then we have to cancel the request for memory utilization
        if ( imString != null && !imString.isEmpty() )
        {
            try {
                //library to load the images from url
                loadImageWithCustomPicasso(imageView, listPosition);
                //ImageHandler.getSharedInstance(mContext).load(imString).skipMemoryCache().resize(Constants.IMG_WIDTH, Constants.IMG_HEIGHT).into(imageView, null);
            } catch (Exception e) {
                Log.e(TAG,"Exception Occurred::"+e.getMessage());
                e.printStackTrace();
            }
        }
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    //for loading images in the imageView
    public void loadImageWithCustomPicasso(ImageView imageView, int position){
        Picasso.with(imageView.getContext().getApplicationContext())
                .load(dataSet.get(position).getImage().getIconUrl())
                .resize(Constants.IMG_WIDTH, Constants.IMG_HEIGHT)
                .into(imageView);
    }

}
