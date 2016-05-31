package ca.lalalala.yelpapidemo.pojos;// File created by llin on 30/05/2016

import java.util.Collection;

import javax.sql.RowSetEvent;

public class Business {

    private boolean is_claimed;
    private double rating;
    private String mobile_url;
    private String rating_img_url;
    private int review_count;
    private String name;
    private String rating_img_url_small;
    private String url;
    private String phone;
    private String snippet_text;
    private String image_url;
    private String snippet_image_url;
    private String display_phone;
    private String rating_img_url_large;
    private String id;
    private boolean is_closed;
    private Location location;
    private Collection<Review> reviews;

    public Business() {
    }

    public boolean is_claimed() {
        return is_claimed;
    }

    public double getRating() {
        return rating;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public String getRating_img_url() {
        return rating_img_url;
    }

    public int getReview_count() {
        return review_count;
    }

    public String getName() {
        return name;
    }

    public String getRating_img_url_small() {
        return rating_img_url_small;
    }

    public String getUrl() {
        return url;
    }

    public String getPhone() {
        return phone;
    }

    public String getSnippet_text() {
        return snippet_text;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getLargeImage_url() {
        return image_url.replace("ms.jpg", "l.jpg");
    }

    public String getSnippet_image_url() {
        return snippet_image_url;
    }

    public String getDisplay_phone() {
        return display_phone;
    }

    public String getRating_img_url_large() {
        return rating_img_url_large;
    }

    public String getId() {
        return id;
    }

    public boolean is_closed() {
        return is_closed;
    }

    public Location getLocation() {
        return location;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }
}
