package ca.lalalala.yelpapidemo.restfulclient;// File created by llin on 30/05/2016

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * this class used to create Yelp API object
 */
public class YelpAPIFactory {

    private static final String YELP_API_BASE_URL = "https://api.yelp.com";
    private static final String CONSUMER_KEY = "OqtKOXReMWo2JQdN0Lh_YQ";
    private static final String CONSUMER_SECRET = "tUz-WmL49NBp7I5cIyhcIYJPthc";
    private static final String TOKEN = "zS-RBlDDToGYB0LuYrSYnCBmp53meEdW";
    private static final String TOKEN_SECRET = "0xXg1Yn7lW-70BxiySDenHvhjvY";
    private OkHttpClient httpClient;
    private static YelpAPI yelpAPI;

    /**
     * sign the client
     */
    public YelpAPIFactory() {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);
        this.httpClient = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();
    }

    public YelpAPI createAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YELP_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return retrofit.create(YelpAPI.class);
    }

    public static YelpAPI getYelpAPI(){
        if(yelpAPI != null) return yelpAPI;
        YelpAPIFactory apiFactory = new YelpAPIFactory();
        yelpAPI = apiFactory.createAPI();
        return yelpAPI;
    }

}
