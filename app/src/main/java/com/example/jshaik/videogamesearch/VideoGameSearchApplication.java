package com.example.jshaik.videogamesearch;

import android.app.Application;
import android.content.Context;

import com.example.jshaik.videogamesearch.di.component.DaggerNetComponent;
import com.example.jshaik.videogamesearch.di.component.NetComponent;
import com.example.jshaik.videogamesearch.di.module.AppModule;
import com.example.jshaik.videogamesearch.di.module.NetModule;
import com.example.jshaik.videogamesearch.utils.Constants;


public class VideoGameSearchApplication extends Application {

    private NetComponent mNetComponent;
    private static VideoGameSearchApplication mInstance;

    public static VideoGameSearchApplication get(Context context) {
        return (VideoGameSearchApplication) context.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Constants.BASE_URL,this))
                .build();

    }
    public static synchronized VideoGameSearchApplication getInstance() {
        return mInstance;
    }
    public NetComponent getNetComponent() {
        return mNetComponent;
    }

}