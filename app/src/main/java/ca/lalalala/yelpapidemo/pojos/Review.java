package ca.lalalala.yelpapidemo.pojos;

/**
 * Created by llin on 2016-05-30.
 */
public class Review {
    private int rating;
    private String excerpt;
    private long time_created;
    private String rating_image_url;
    private String rating_image_small_url;
    private User user;
    private String rating_image_large_url;
    private String id;


    public int getRating() {
        return rating;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public long getTime_created() {
        return time_created;
    }

    public String getRating_image_url() {
        return rating_image_url;
    }

    public String getRating_image_small_url() {
        return rating_image_small_url;
    }

    public User getUser() {
        return user;
    }

    public String getRating_image_large_url() {
        return rating_image_large_url;
    }

    public String getId() {
        return id;
    }
}
