package com.github.lukaspili.architectmapdemo.app;

import android.content.Context;

import com.github.lukaspili.architectmapdemo.BuildConfig;
import com.github.lukaspili.architectmapdemo.di.AppDependencies;
import com.github.lukaspili.architectmapdemo.di.DaggerScope;

import architect.autostack.DaggerService;
import architect.commons.ArchitectApp;
import autodagger.AutoComponent;
import autodagger.AutoInjector;
import dagger.Provides;
import mortar.MortarScope;
import timber.log.Timber;

/**
 * Created by lukasz on 16/02/15.
 */
@AutoComponent(superinterfaces = AppDependencies.class, modules = DemoApp.Module.class)
@DaggerScope(DemoApp.class)
@AutoInjector
public class DemoApp extends ArchitectApp {

    @Override
    protected void configureScope(MortarScope.Builder builder) {
        DemoAppComponent component = DaggerDemoAppComponent.builder()
                .module(new Module())
                .build();
        component.inject(this);

        builder.withService(DaggerService.SERVICE_NAME, component);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        DaggerService.<DemoAppComponent>get(this).inject(this);
    }

    @dagger.Module
    public class Module {

        @Provides
        @DaggerScope(DemoApp.class)
        public Context providesContext() {
            return getApplicationContext();
        }
    }
}
