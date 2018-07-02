package com.example.jshaik.videogamesearch.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.jshaik.videogamesearch.BuildConfig;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;


import static android.content.Context.ACTIVITY_SERVICE;

public class ImageHandler {

    //for loading singleton picaso builder for whole application and caching images
    public static Picasso setCustomPicasoBuilder(Context context)
    {
        Picasso.Builder builder = new Picasso.Builder(context);
        Picasso.with(context);
        builder.memoryCache(new LruCache(getBytesForMemCache(context,12)));
        //set request transformer
        Picasso.RequestTransformer requestTransformer =  new Picasso.RequestTransformer() {
            @Override
            public Request transformRequest(Request request) {
                Log.d("image request", request.toString());
                return request;
            }
        };
        builder.requestTransformer(requestTransformer);

        return builder.build();
    }

    //returns the given percentage of available memory in bytes
    public static int getBytesForMemCache(Context context, int percent){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        double availableMemory= mi.availMem;

        return (int)(percent*availableMemory/100);
    }

}