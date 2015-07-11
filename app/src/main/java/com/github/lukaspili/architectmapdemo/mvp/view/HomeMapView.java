package com.github.lukaspili.architectmapdemo.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.github.lukaspili.architectmapdemo.R;
import com.github.lukaspili.architectmapdemo.mvp.presenter.HomeMapPresenter;
import com.github.lukaspili.architectmapdemo.mvp.presenter.stackable.HomeMapStackable;
import com.github.lukaspili.architectmapdemo.mvp.presenter.stackable.HomeMapStackableComponent;
import com.github.lukaspili.architectmapdemo.mvp.view.util.PresentedMapView;

import architect.Stackable;
import architect.commons.view.StackedFrameLayout;
import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@AutoInjector(HomeMapPresenter.class)
public class HomeMapView extends StackedFrameLayout<HomeMapPresenter> {

    @InjectView(R.id.home_map_view)
    public PresentedMapView mapView;

    public HomeMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Stackable getStackable() {
        return new HomeMapStackable();
    }

    @Override
    public void initWithContext(Context context) {
        DaggerService.<HomeMapStackableComponent>get(context).inject(this);
        View view = View.inflate(context, R.layout.view_home_map, this);
        ButterKnife.inject(view);
    }
}
