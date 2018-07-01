package com.example.jshaik.videogamesearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jshaik.videogamesearch.R;
import com.example.jshaik.videogamesearch.beans.GamesListData;
import com.example.jshaik.videogamesearch.beans.GamesResultsData;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CustomSearchAdapter extends RecyclerView.Adapter<CustomSearchAdapter.MyViewHolder> {

    private GamesListData gamesListData;
    private List<GamesResultsData> dataSet;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.game_title);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.list_image);
        }
    }

    public CustomSearchAdapter(Context context, List<GamesResultsData> data) {
        this.mContext = context;
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        Context context = holder.imageViewIcon.getContext();
        TextView textViewName = holder.textViewName;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getAliases());

        Picasso.with(context)
                .load(dataSet.get(listPosition).getImage().getIconUrl())
                .resize(50,50).into(imageView);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
