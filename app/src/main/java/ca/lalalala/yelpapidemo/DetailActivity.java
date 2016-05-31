package ca.lalalala.yelpapidemo; // File created by llin on 30/05/2016

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ca.lalalala.yelpapidemo.pojos.Business;
import ca.lalalala.yelpapidemo.restfulclient.YelpAPIFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String INFO = "info";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        final String id = intent.getStringExtra(INFO);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Call<Business> businessCall = YelpAPIFactory.getYelpAPI().getBusiness(id);
        businessCall.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                setupView(response.body());
                collapsingToolbar.setTitle(response.body().getName());
            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {

            }
        });
    }

    private void setupView(Business business) {
        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(business.getLargeImage_url()).centerCrop().into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
