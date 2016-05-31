package ca.lalalala.yelpapidemo.ui;// File created by llin on 31/05/2016

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import ca.lalalala.yelpapidemo.Extras;
import ca.lalalala.yelpapidemo.R;
import ca.lalalala.yelpapidemo.Utils;

public class SearchActivity extends AppCompatActivity {

    private android.support.v7.widget.SearchView mSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchView = (SearchView) findViewById(R.id.activity_search_search_view);
        mSearchView.setOnQueryTextListener(new MySearchViewListener());
        mSearchView.setIconified(false);
        mSearchView.setQueryHint(getString(R.string.search_hint));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class MySearchViewListener implements android.support.v7.widget.SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String searchText) {
            return submitTextChanges(searchText);
        }

        @Override
        public boolean onQueryTextChange(String searchText) {
            return true;
        }
    }

    private boolean submitTextChanges(String searchText) {
        Fragment fragment = new BusinessListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Extras.DATA, searchText);
        bundle.putInt(Extras.COUNT, 10);
        bundle.putInt(Extras.SORT_OPTIONS, getIntent().getIntExtra(Extras.SORT_OPTIONS, MainActivity.SORT_OPTIONS_BEST));
        fragment.setArguments(bundle);
        Utils.switchFragment(getSupportFragmentManager(), fragment, R.id.fragment_container);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

}
