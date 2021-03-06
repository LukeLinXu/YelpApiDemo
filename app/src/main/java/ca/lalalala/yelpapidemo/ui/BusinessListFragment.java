package ca.lalalala.yelpapidemo.ui;// File created by llin on 30/05/2016

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ca.lalalala.yelpapidemo.Extras;
import ca.lalalala.yelpapidemo.R;
import ca.lalalala.yelpapidemo.pojos.Business;
import ca.lalalala.yelpapidemo.pojos.SearchResponse;
import ca.lalalala.yelpapidemo.restfulclient.YelpAPIFactory;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * This fragment is used to show business list.
 */
public class BusinessListFragment extends ClickToRefreshFragmentBase{
    private RecyclerView recyclerView;
    private String term;
    private int count = 0;
    private int sortOption;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        term = getArguments().getString(Extras.DATA);
        count = getArguments().getInt(Extras.COUNT, 0);
        sortOption = getArguments().getInt(Extras.SORT_OPTIONS, MainActivity.SORT_OPTIONS_BEST);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View spView) {
        recyclerView = (RecyclerView) spView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    protected Observable<Object> doRefresh() {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                Map<String, String> params = new HashMap<>();
                params.put("term", term);
                if(count != 0){
                    params.put("limit", String.valueOf(count));
                }
                //hard code location in Toronto
                Call<SearchResponse> call = YelpAPIFactory.getYelpAPI().search("Toronto", params);
                call.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        subscriber.onNext(response.body());
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        subscriber.onError(t);
                    }
                });
            }
        });
    }

    @Override
    protected void refreshUI(RelativeLayout mainContent, Object object) {
        if(!(object instanceof SearchResponse)) return;
        SearchResponse searchResponse = (SearchResponse) object;
        recyclerView.setAdapter(new SimpleRecyclerViewAdapter(getActivity(), searchResponse.getBusinesses
                (sortOption)));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_business_list;
    }

    public void setSortOption(int sortOption) {
        this.sortOption = sortOption;
    }

    public static class SimpleRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Business> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final ImageView mImageView;
            public final TextView mName;
            public final TextView mAdd;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.list_item_avatar);
                mName = (TextView) view.findViewById(R.id.list_item_name);
                mAdd = (TextView) view.findViewById(R.id.list_item_add);
            }

        }

        public SimpleRecyclerViewAdapter(Context context, List<Business> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mName.setText(mValues.get(position).getName());
            holder.mAdd.setText(mValues.get(position).getLocation().getDisplay_address());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.INFO, mValues.get(position).getId());
                    context.startActivity(intent);
                }
            });
            Glide.with(holder.mImageView.getContext())
                    .load(mValues.get(position).getImage_url())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
