package ca.lalalala.yelpapidemo.ui;// File created by llin on 30/05/2016

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ca.lalalala.yelpapidemo.R;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

public abstract class ClickToRefreshFragmentBase extends Fragment {

    private RelativeLayout mainContent;
    private TextView refreshButton;
    private ProgressBar progressBar;
    private Subscription subscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.click_to_refresh_fragment_base, container, false);
        View spView = inflater.inflate(getLayoutId(), container, false);
        mainContent = (RelativeLayout) view.findViewById(R.id.click_to_refresh_fragment_maincontent);
        mainContent.addView(spView);
        initView(spView);
        refreshButton = (TextView) view.findViewById(R.id.click_to_refresh_fragment_refresh_button);
        progressBar = (ProgressBar) view.findViewById(R.id.click_to_refresh_fragment_progress);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        refresh();
        return view;
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
        mainContent.setVisibility(View.INVISIBLE);
        refreshButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void onSuccess(Object object) {
        mainContent.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        refreshUI(mainContent, object);
    }

    protected abstract void refreshUI(RelativeLayout mainContent, Object object);

    private void onProgress() {
        mainContent.setVisibility(View.INVISIBLE);
        refreshButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroy() {
        if(subscription != null) subscription.unsubscribe();
        super.onDestroy();
    }

    protected abstract int getLayoutId();

}
