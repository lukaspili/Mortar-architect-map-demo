package com.github.lukaspili.architectmapdemo.di;

import autodagger.AutoComponent;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoComponent(superinterfaces = {AppDependencies.class, ActivityDependencies.class})
public @interface AutoComponentDependencies {
}
