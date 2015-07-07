package com.github.lukaspili.architectmapdemo.di;

import com.github.lukaspili.architectmapdemo.app.DemoActivity;

import autodagger.AutoComponent;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoComponent(
        dependencies = DemoActivity.class,
        includes = AutoComponentDependencies.class
)
public @interface FromActivityAutoComponent {
}
