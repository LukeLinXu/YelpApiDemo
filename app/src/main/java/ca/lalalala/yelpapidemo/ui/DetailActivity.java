package ca.lalalala.yelpapidemo.ui; // File created by llin on 30/05/2016

import java.util.Date;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ca.lalalala.yelpapidemo.R;
import ca.lalalala.yelpapidemo.Utils;
import ca.lalalala.yelpapidemo.pojos.Business;
import ca.lalalala.yelpapidemo.pojos.Review;
import ca.lalalala.yelpapidemo.restfulclient.YelpAPIFactory;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity{

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

    private void setupView(final Business business) {
        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        View address = findViewById(R.id.activity_detail_address);
        View tel = findViewById(R.id.activity_detail_tel);
        View review = findViewById(R.id.activity_detail_review);

        Glide.with(this).load(business.getLargeImage_url()).centerCrop().into(imageView);
        setupCardView(address, getString(R.string.detail_activity_address), business.getLocation().getDisplay_address());
        setupCardView(tel, getString(R.string.detail_activity_phone), business.getPhone());

        if(business.getReviews().size() != 0){
            review.setVisibility(View.VISIBLE);
            Review reviewObject = business.getReviews().iterator().next();
            TextView titleTextView = (TextView) review.findViewById(R.id.card_layout_title);
            TextView contentTextView = (TextView) review.findViewById(R.id.card_layout_content);
            TextView name = (TextView) review.findViewById(R.id.card_layout_user_name);
            TextView time = (TextView) review.findViewById(R.id.card_layout_user_create_time);
            ImageView user_image = (ImageView) review.findViewById(R.id.card_layout_user_image);
            ImageView user_rating = (ImageView) review.findViewById(R.id.card_layout_user_review_rating);
            titleTextView.setText(getString(R.string.detail_activity_latest_review));
            contentTextView.setText(reviewObject.getExcerpt());
            name.setText(reviewObject.getUser().getName());
            time.setText(new Date(reviewObject.getTime_created() * 1000).toString());
            Glide.with(this).load(reviewObject.getRating_image_url()).fitCenter().into(user_rating);
            Glide.with(this).load(reviewObject.getUser().getImage_url()).fitCenter().into(user_image);
        }

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String display_address = business.getLocation().getDisplay_address();
                Utils.showDialog(DetailActivity.this, getString(R.string.you_will_be_guided_to, display_address), getString(R.string.open_google_map), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + display_address);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        DetailActivity.this.startActivity(mapIntent);
                    }
                });
            }
        });
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialog(DetailActivity.this, getString(R.string.you_will_be_calling, business.getPhone()), getString(R.string.make_phone_call), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + business.getPhone()));
                        DetailActivity.this.startActivity(intent);
                    }
                });
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialog(DetailActivity.this, getString(R.string.we_got_reviews_want_add_more, business.getReview_count()), getString(R.string.go_to_web), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(business.getMobile_url()));
                        startActivity(browserIntent);
                    }
                });
            }
        });
    }

    private void setupCardView(View view, String title, String content){
        TextView titleTextView = (TextView) view.findViewById(R.id.card_layout_title);
        TextView contentTextView = (TextView) view.findViewById(R.id.card_layout_content);
        titleTextView.setText(title);
        contentTextView.setText(content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
