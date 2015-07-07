package com.github.lukaspili.architectmapdemo.mvp.presenter;

import com.github.lukaspili.architectmapdemo.di.DaggerScope;
import com.github.lukaspili.architectmapdemo.di.FromHomeAutoComponent;
import com.github.lukaspili.architectmapdemo.mvp.view.HomeMapView;

import architect.autostack.AutoStack;
import autodagger.AutoComponent;
import mortar.ViewPresenter;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@AutoStack(
        component = @AutoComponent(includes = FromHomeAutoComponent.class)
)
@DaggerScope(HomeMapPresenter.class)
public class HomeMapPresenter extends ViewPresenter<HomeMapView> {

    private final HomePresenter homePresenter;

    public HomeMapPresenter(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }
}
