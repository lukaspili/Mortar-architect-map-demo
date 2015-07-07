package com.github.lukaspili.architectmapdemo.di;

import com.github.lukaspili.architectmapdemo.mvp.presenter.HomePresenter;

import autodagger.AutoComponent;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoComponent(
        dependencies = HomePresenter.class,
        includes = AutoComponentDependencies.class
)
public @interface FromHomeAutoComponent {
}
