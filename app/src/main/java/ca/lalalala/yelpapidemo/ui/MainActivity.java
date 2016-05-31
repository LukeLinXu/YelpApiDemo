package ca.lalalala.yelpapidemo.ui; // File created by llin on 30/05/2016

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import ca.lalalala.yelpapidemo.Category;
import ca.lalalala.yelpapidemo.Extras;
import ca.lalalala.yelpapidemo.R;

public class MainActivity extends AppCompatActivity {

    public static final int SORT_OPTIONS_BEST = 0;
    public static final int SORT_ALPHA = 1;
    private int currentSortOption = SORT_OPTIONS_BEST;
    private ViewPager viewPager;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class).putExtra(Extras.SORT_OPTIONS,
                        currentSortOption));
                break;
            case R.id.sort:
                showSortingOptions(MainActivity.this, currentSortOption);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleSortOptionsChange(int which) {
        currentSortOption = which;
        if(adapter != null){
            adapter.change(which);
        }
    }

    private void setupViewPager() {
        adapter = new SimpleAdapter(getSupportFragmentManager());
        for(Category category : Category.values()){
            adapter.addFragment(category, getResources());
        }
        viewPager.setAdapter(adapter);
    }

    private void showSortingOptions(final MainActivity context, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.pick_sorting_option)
                .setSingleChoiceItems(R.array.sorting_options_array, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which != position){
                            context.handleSortOptionsChange(which);
                        }
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    class SimpleAdapter extends FragmentPagerAdapter {
        private final List<BusinessListFragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SimpleAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Category category, Resources res) {
            BusinessListFragment fragment = new BusinessListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Extras.DATA, category.name());
            bundle.putInt(Extras.SORT_OPTIONS, currentSortOption);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
            mFragmentTitles.add(res.getString(category.getTitleResId()));
        }

        public void change(int sortOption){
            for (BusinessListFragment fragment : mFragments){
                fragment.setSortOption(sortOption);
                if(fragment.isResumed()){
                    fragment.refresh();
                }
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
