package ca.lalalala.yelpapidemo.pojos;// File created by llin on 30/05/2016

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.lalalala.yelpapidemo.ui.MainActivity;

public class SearchResponse {
    private Region region;
    private int total;
    private ArrayList<Business> businesses;

    public SearchResponse() {
    }

    public ArrayList<Business> getBusinesses(int sortOption) {
        if(sortOption == MainActivity.SORT_ALPHA){
            Collections.sort(businesses, new Comparator<Business>() {
                @Override
                public int compare(Business lhs, Business rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
        }
        return businesses;
    }
}
