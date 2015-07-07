package com.github.lukaspili.architectmapdemo.di;

import com.github.lukaspili.architectmapdemo.mvp.presenter.util.ActivityPresenter;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public interface ActivityDependencies {

    ActivityPresenter activityPresenter();
}
