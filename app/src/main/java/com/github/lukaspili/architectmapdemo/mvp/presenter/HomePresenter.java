package com.github.lukaspili.architectmapdemo.mvp.presenter;

import android.view.Gravity;

import com.github.lukaspili.architectmapdemo.di.DaggerScope;
import com.github.lukaspili.architectmapdemo.di.FromActivityAutoComponent;
import com.github.lukaspili.architectmapdemo.mvp.view.HomeView;

import architect.autopath.AutoPath;
import architect.autostack.AutoStack;
import autodagger.AutoComponent;
import autodagger.AutoExpose;
import mortar.ViewPresenter;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@AutoStack(
        component = @AutoComponent(includes = FromActivityAutoComponent.class),
        path = @AutoPath(withView = HomeView.class)
)
@AutoExpose
@DaggerScope(HomePresenter.class)
public class HomePresenter extends ViewPresenter<HomeView> {

    public HomePresenter() {

    }

    public void openMenu() {
        getView().drawerLayout.openDrawer(Gravity.LEFT);
    }
}
