package com.example.jshaik.videogamesearch.di.contract;

import com.example.jshaik.videogamesearch.model.beans.GamesResultsData;
import java.util.List;


public interface GamesListContract {
    //get list of all games
    interface View {
        void showGameDetailsList(List<GamesResultsData> gamesDetailsList);

        void showError(String message);

        void showComplete();
    }

    interface Presenter {
        void loadGamesList(String searchQuery);
    }
}
