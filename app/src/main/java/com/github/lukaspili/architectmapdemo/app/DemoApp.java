package com.github.lukaspili.architectmapdemo.app;

import android.app.Application;
import android.content.Context;

import com.github.lukaspili.architectmapdemo.BuildConfig;
import com.github.lukaspili.architectmapdemo.di.AppDependencies;
import com.github.lukaspili.architectmapdemo.di.DaggerScope;

import architect.robot.DaggerService;
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
public class DemoApp extends Application {

    private MortarScope scope;

    @Override
    public Object getSystemService(String name) {
        return (scope != null && scope.hasService(name)) ? scope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        DemoAppComponent component = DaggerDemoAppComponent.builder()
                .module(new Module())
                .build();
        component.inject(this);

        scope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, component)
                .build("Root");
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
