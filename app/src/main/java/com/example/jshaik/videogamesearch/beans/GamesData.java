package com.example.jshaik.videogamesearch.beans;

import java.util.ArrayList;
import java.util.List;

public class GamesData {
    private static List<String> stores ;
    static {
        stores =  new ArrayList<String>();
        stores.add("American War");
        stores.add("Titans");
        stores.add("BabyGames");
        stores.add("KidsGames");
        stores.add("Road Rash");
        stores.add("Car crash");
        stores.add("American Snapper");
        stores.add("Super Mario");
        stores.add("Karate Kid");
        stores.add("GoT");
        stores.add("Travel Games");
        stores.add("Home Games");
        stores.add("Safe Games");
    }

    public static List<String> getStores(){
        return stores;
    }

    public static List<String> filterData(String searchString){
        List<String> searchResults =  new ArrayList<String>();
        if(searchString != null){
            searchString = searchString.toLowerCase();

            for(String rec :  stores){
                if(rec.toLowerCase().contains(searchString)){
                    searchResults.add(rec);
                }
            }
        }
        return searchResults;
    }
}