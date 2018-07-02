package com.example.jshaik.videogamesearch.di.component;


import com.example.jshaik.videogamesearch.di.module.AppModule;
import com.example.jshaik.videogamesearch.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    // downstream components need these exposed with the return type
    // method name does not really matter
    Retrofit retrofit();
}