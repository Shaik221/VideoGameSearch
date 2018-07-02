package com.example.jshaik.videogamesearch.di.component;

import com.example.jshaik.videogamesearch.di.module.ApplicationModule;
import com.example.jshaik.videogamesearch.ui.VideoGameSearchActivity;
import com.example.jshaik.videogamesearch.utils.CustomScope;
import dagger.Component;


@CustomScope
@Component(dependencies = NetComponent.class,modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(VideoGameSearchActivity demoApplication);
}