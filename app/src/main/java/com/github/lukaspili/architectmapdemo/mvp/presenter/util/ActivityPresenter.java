package com.github.lukaspili.architectmapdemo.mvp.presenter.util;

import android.app.Activity;

import com.github.lukaspili.architectmapdemo.app.DemoActivity;
import com.github.lukaspili.architectmapdemo.di.DaggerScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.Presenter;
import mortar.bundler.BundleService;

@DaggerScope(DemoActivity.class)
public class ActivityPresenter extends Presenter<Activity> {

    private List<Listener> listeners = new ArrayList<>();

    @Inject
    public ActivityPresenter() {
    }

    @Override
    protected BundleService extractBundleService(Activity view) {
        return BundleService.getBundleService(view);
    }

    @Override
    protected void onEnterScope(MortarScope scope) {
    }

    @Override
    protected void onExitScope() {
        listeners.clear();
    }

    public void subscribe(Listener listener) {
        listeners.add(listener);
    }

    public void unscribe(Listener listener) {
        listeners.remove(listener);
    }

    public void onResume() {
        for (Listener listener : listeners) {
            listener.onActivityResume();
        }
    }

    public void onPause() {
        for (Listener listener : listeners) {
            listener.onActivityPause();
        }
    }

    public void onLowMemory() {
        for (Listener listener : listeners) {
            listener.onActivityLowMemory();
        }
    }

    public interface Listener {
        void onActivityResume();

        void onActivityPause();

        void onActivityLowMemory();
    }
}