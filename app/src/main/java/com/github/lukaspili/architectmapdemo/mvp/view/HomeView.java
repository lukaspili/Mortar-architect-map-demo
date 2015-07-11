package com.github.lukaspili.architectmapdemo.mvp.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.github.lukaspili.architectmapdemo.R;
import com.github.lukaspili.architectmapdemo.mvp.presenter.HomePresenter;
import com.github.lukaspili.architectmapdemo.mvp.presenter.stackable.HomeStackableComponent;

import architect.commons.view.PresentedFrameLayout;
import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@AutoInjector(HomePresenter.class)
public class HomeView extends PresentedFrameLayout<HomePresenter> {

    @InjectView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    @InjectView(R.id.content_view)
    public HomeMapView mapView;

    public HomeView(Context context) {
        super(context);

        DaggerService.<HomeStackableComponent>get(context).inject(this);
        View view = View.inflate(context, R.layout.view_home, this);
        ButterKnife.inject(view);
    }
}
