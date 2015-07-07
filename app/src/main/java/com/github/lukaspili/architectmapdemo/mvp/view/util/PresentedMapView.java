package com.github.lukaspili.architectmapdemo.mvp.view.util;

import android.content.Context;
import android.util.AttributeSet;

import com.github.lukaspili.architectmapdemo.mvp.presenter.util.MapPresenter;
import com.github.lukaspili.architectmapdemo.mvp.presenter.util.MapScopeComponent;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import javax.inject.Inject;

import architect.StackFactory;
import architect.autostack.DaggerService;
import autodagger.AutoInjector;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoInjector(MapPresenter.class)
public class PresentedMapView extends MapView {

    @Inject
    protected MapPresenter presenter;

    public PresentedMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Context newContext = StackFactory.createContext(context, new MapPresenter.MapScope());
        DaggerService.<MapScopeComponent>get(newContext).inject(this);
    }

    public void getMap(OnMapReadyCallback callback) {
        presenter.getMap(callback);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }
}
