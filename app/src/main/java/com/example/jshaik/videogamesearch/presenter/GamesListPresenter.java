package com.example.jshaik.videogamesearch.presenter;

import com.example.jshaik.videogamesearch.di.contract.GamesListContract;
import com.example.jshaik.videogamesearch.service.GetNoticeDataService;
import com.example.jshaik.videogamesearch.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GamesListPresenter implements GamesListContract.Presenter {

    Retrofit retrofit;
    GamesListContract.View mView;

    @Inject
    public GamesListPresenter(Retrofit retrofit, GamesListContract.View mView) {
            this.retrofit = retrofit;
            this.mView = mView;
    }

    @Override
    public void loadGamesList(String searchQuery) {

        Map<String, String> params = new HashMap<>();
        params.put("api_key", Constants.API_KEY);
        params.put("format", Constants.SCHEMA_TYPE);
        params.put("query", searchQuery);
        params.put("limit", Constants.PAGE_LIMIT);
        params.put("resources", Constants.RESOURCE_TYPE);


        retrofit.create(GetNoticeDataService.class).getGamesList(Constants.BASE_URL, params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<com.example.jshaik.videogamesearch.model.beans.GamesListData>() {
                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            mView.showError(e.getMessage());
                        } catch (Throwable et) {
                            et.printStackTrace();

                        }
                    }

                    @Override
                    public void onNext(com.example.jshaik.videogamesearch.model.beans.GamesListData json) {
                        mView.showGameDetailsList(json.getResults());
                    }
                });
    }
}

