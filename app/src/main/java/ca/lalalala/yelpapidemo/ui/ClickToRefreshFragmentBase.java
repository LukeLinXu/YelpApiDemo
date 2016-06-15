package ca.lalalala.yelpapidemo.ui;// File created by llin on 30/05/2016

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import ca.lalalala.yelpapidemo.R;
import ca.lalalala.yelpapidemo.databinding.ClickBaseBinding;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

public abstract class ClickToRefreshFragmentBase extends Fragment {

    private Subscription subscription;
    private ClickBaseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.click_to_refresh_fragment_base, container, false);
        View spView = inflater.inflate(getLayoutId(), container, false);
        binding.clickToRefreshFragmentMaincontent.addView(spView);
        initView(spView);
        binding.clickToRefreshFragmentRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        refresh();
        return binding.getRoot();
    }

    protected abstract void initView(View spView);

    void refresh(){
        if(subscription != null) subscription.unsubscribe();
        this.subscription = doRefresh()
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        onProgress();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onFail();
                    }

                    @Override
                    public void onNext(Object object) {
                        onSuccess(object);
                    }
                });
    }

    protected abstract Observable<Object> doRefresh();

    private void onFail() {
        binding.setShowContent(false);
        binding.setShowProgress(false);
    }

    private void onSuccess(Object object) {
        binding.setShowContent(true);
        binding.setShowProgress(false);
        refreshUI(binding.clickToRefreshFragmentMaincontent, object);
    }

    protected abstract void refreshUI(RelativeLayout mainContent, Object object);

    private void onProgress() {
        binding.setShowContent(false);
        binding.setShowProgress(true);
    }


    @Override
    public void onDestroy() {
        if(subscription != null) subscription.unsubscribe();
        super.onDestroy();
    }

    protected abstract int getLayoutId();

}
