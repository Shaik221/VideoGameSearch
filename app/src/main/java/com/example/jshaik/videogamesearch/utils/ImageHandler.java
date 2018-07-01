package com.example.jshaik.videogamesearch.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.jshaik.videogamesearch.BuildConfig;
import com.squareup.picasso.Cache;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.util.concurrent.Executors;

import static android.content.Context.ACTIVITY_SERVICE;

public class ImageHandler {

    private static Picasso instance;

    public static Picasso getSharedInstance(Context context)
    {
        if(instance == null)
        {
            instance = new Picasso.Builder(context).executor(Executors.newSingleThreadExecutor()).memoryCache(Cache.NONE).indicatorsEnabled(true).build();
        }
        return instance;
    }

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

    private void setCustomPicaso(Context context)
    {
        Picasso.Builder picassoBuilder = new Picasso.Builder(context);
        if(BuildConfig.DEBUG) { picassoBuilder.loggingEnabled(true); }
        if(isLowMemoryDevice(context)) { //picassoBuilder.defaultBitmapConfig(Bitmap.Config.RGB_565);
        }

        Picasso.setSingletonInstance(picassoBuilder.build());
    }

    private boolean isLowMemoryDevice(Context context) {
        if(Build.VERSION.SDK_INT >= 19) {
            return ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).isLowRamDevice();
        } else {
            return false;
        }
    }

}