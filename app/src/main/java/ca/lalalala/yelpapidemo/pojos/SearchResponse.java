package ca.lalalala.yelpapidemo.pojos;// File created by llin on 30/05/2016

import java.util.Collection;

public class SearchResponse {
    private Region region;
    private int total;
    private Collection<Business> businesses;

    public SearchResponse() {
    }

    public Collection<Business> getBusinesses() {
        return businesses;
    }
}
