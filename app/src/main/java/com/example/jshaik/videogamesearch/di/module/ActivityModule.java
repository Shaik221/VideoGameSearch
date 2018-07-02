package com.example.jshaik.videogamesearch.di.module;

import com.example.jshaik.videogamesearch.di.contract.GamesListContract;
import com.example.jshaik.videogamesearch.utils.CustomScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {

    private GamesListContract.View mView;

    public ActivityModule(GamesListContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    GamesListContract.View providesMainScreenContractView() {
        return mView;
    }
}