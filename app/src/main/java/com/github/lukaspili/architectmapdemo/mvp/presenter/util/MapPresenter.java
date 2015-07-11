package com.github.lukaspili.architectmapdemo.mvp.presenter.util;

import android.os.Bundle;

import com.github.lukaspili.architectmapdemo.app.DemoActivityComponent;
import com.github.lukaspili.architectmapdemo.di.DaggerScope;
import com.github.lukaspili.architectmapdemo.di.FromActivityAutoComponent;
import com.github.lukaspili.architectmapdemo.mvp.view.util.PresentedMapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import javax.inject.Inject;

import architect.NavigatorServices;
import architect.Stackable;
import architect.robot.DaggerService;
import autodagger.AutoComponent;
import mortar.MortarScope;
import mortar.ViewPresenter;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@DaggerScope(MapPresenter.class)
public class MapPresenter extends ViewPresenter<PresentedMapView> implements ActivityPresenter.Listener {

    private final ActivityPresenter activityPresenter;

    private OnMapReadyCallback onMapReadyCallback;
    private GoogleMap map;

    /**
     * -1 = stopped
     * 0 = paused
     * 1 = resumed
     */
    private int resumePauseState = -1;

    @Inject
    public MapPresenter(ActivityPresenter activityPresenter) {
        this.activityPresenter = activityPresenter;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        getView().onCreate(savedInstanceState);
        Timber.d("MAP ON CREATE");

        if (resumePauseState == -1) {
            getView().onResume();
            Timber.d("MAP ON RESUME");
        }
    }

    @Override
    protected void onSave(Bundle outState) {
        getView().onSaveInstanceState(outState);
    }

    @Override
    public void dropView(PresentedMapView view) {
        resumePauseState = -1;
        view.onDestroy();
        Timber.d("MAP ON DESTROY");

        super.dropView(view);
    }

    @Override
    protected void onEnterScope(MortarScope scope) {
        Timber.d("MAP SUBSCRIBE");
        activityPresenter.subscribe(this);
    }

    @Override
    protected void onExitScope() {
        Timber.d("MAP UNSCRIBE");
        activityPresenter.unscribe(this);
    }

    @Override
    public void onActivityResume() {
        if (!hasView()) return;

        if (resumePauseState != 1) {
            resumePauseState = 1;
            getView().onResume();
            Timber.d("MAP ON ACTIVITY RESUME");
        }
    }

    @Override
    public void onActivityPause() {
        if (!hasView()) return;

        if (resumePauseState != 0) {
            resumePauseState = 0;
            getView().onPause();
            Timber.d("MAP ON ACTIVITY PAUSE");
        }
    }

    @Override
    public void onActivityLowMemory() {
        if (!hasView()) return;
        getView().onLowMemory();
    }

    public void getMap(OnMapReadyCallback callback) {
        onMapReadyCallback = callback;
        provideMap();
    }

    private void provideMap() {
        if (onMapReadyCallback != null) {
            onMapReadyCallback.onMapReady(map);
        }
    }

    @AutoComponent(
            includes = FromActivityAutoComponent.class,
            target = MapPresenter.class
    )
    @DaggerScope(MapPresenter.class)
    public static class MapStackable implements Stackable {

        @Override
        public void configureScope(MortarScope.Builder builder, MortarScope mortarScope) {
            DemoActivityComponent component = NavigatorServices.getService(mortarScope, DaggerService.SERVICE_NAME);
            builder.withService(DaggerService.SERVICE_NAME, DaggerMapStackableComponent.builder()
                    .demoActivityComponent(component)
                    .build());
        }
    }
}
