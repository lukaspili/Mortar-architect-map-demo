package com.github.lukaspili.architectmapdemo.mvp.presenter;

import android.os.Bundle;

import com.github.lukaspili.architectmapdemo.di.DaggerScope;
import com.github.lukaspili.architectmapdemo.di.FromHomeAutoComponent;
import com.github.lukaspili.architectmapdemo.mvp.view.HomeMenuView;

import javax.inject.Inject;

import architect.autostack.AutoStack;
import autodagger.AutoComponent;
import mortar.ViewPresenter;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@AutoStack(
        component = @AutoComponent(includes = FromHomeAutoComponent.class)
)
@DaggerScope(HomeMenuPresenter.class)
public class HomeMenuPresenter extends ViewPresenter<HomeMenuView> {

    private final HomePresenter homePresenter;

    @Inject
    public HomeMenuPresenter(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {

    }
}
