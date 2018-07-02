package com.example.jshaik.videogamesearch.service;

import com.example.jshaik.videogamesearch.model.beans.GamesListData;

import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface GetNoticeDataService {

   /**
     * URL MANIPULATION
     * A request URL can be updated dynamically using replacement blocks and parameters on the method.
     * A corresponding parameter must be annotated with @QueryMap using the same string value pairs
     * */
    @GET
    Observable<GamesListData> getGamesList(@Url String url, @QueryMap Map<String, String> params);

}