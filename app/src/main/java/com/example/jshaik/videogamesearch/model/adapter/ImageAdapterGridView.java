package com.example.jshaik.videogamesearch.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jshaik.videogamesearch.R;

public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;
        private final Integer[] imageIDs;
        private final String[] gridViewString;

        public ImageAdapterGridView(Context c, String[] gridViewString,Integer[] imgIDS) {
            mContext = c;
            this.imageIDs = imgIDS;
            this.gridViewString = gridViewString;
        }

        public int getCount() {
            return gridViewString.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return imageIDs[position];
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View gridViewAndroid;

            if (convertView == null) {
                gridViewAndroid = new View(mContext);
                gridViewAndroid = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_layout, null);
                TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
                ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
                textViewAndroid.setText(gridViewString[position]);
                imageViewAndroid.setImageResource(imageIDs[position]);
            } else {
                gridViewAndroid = (View) convertView;
            }

            return gridViewAndroid;
        }

}