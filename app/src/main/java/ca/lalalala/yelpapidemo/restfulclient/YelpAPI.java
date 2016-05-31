package ca.lalalala.yelpapidemo.restfulclient;// File created by llin on 30/05/2016

import java.util.Map;

import ca.lalalala.yelpapidemo.pojos.Business;
import ca.lalalala.yelpapidemo.pojos.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Yelp API interface, all the call will go with this
 */
public interface YelpAPI {

    @GET("/v2/business/{businessId}")
    Call<Business> getBusiness(@Path("businessId") String businessId);

    @GET("/v2/search")
    Call<SearchResponse> search(@Query("location") String location, @QueryMap Map<String, String> params);
}
