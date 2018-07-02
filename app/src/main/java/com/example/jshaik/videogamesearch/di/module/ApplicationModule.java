package com.example.jshaik.videogamesearch.di.module;

import com.example.jshaik.videogamesearch.di.contract.GamesListContract;
import com.example.jshaik.videogamesearch.utils.CustomScope;


import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private GamesListContract.View mView;

    public ApplicationModule(GamesListContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    GamesListContract.View providesMainScreenContractView() {
        return mView;
    }

}