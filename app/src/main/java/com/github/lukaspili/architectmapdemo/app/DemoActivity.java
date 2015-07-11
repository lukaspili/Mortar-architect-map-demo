package com.github.lukaspili.architectmapdemo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.github.lukaspili.architectmapdemo.R;
import com.github.lukaspili.architectmapdemo.architect.Parceler;
import com.github.lukaspili.architectmapdemo.di.AutoComponentDependencies;
import com.github.lukaspili.architectmapdemo.di.DaggerScope;
import com.github.lukaspili.architectmapdemo.mvp.presenter.stackable.HomeStackable;
import com.github.lukaspili.architectmapdemo.mvp.presenter.util.ActivityPresenter;

import javax.inject.Inject;

import architect.Navigator;
import architect.NavigatorView;
import architect.TransitionsMapping;
import architect.commons.ActivityArchitector;
import architect.commons.Architected;
import architect.robot.DaggerService;
import architect.transition.Config;
import architect.transition.LateralViewTransition;
import autodagger.AutoComponent;
import autodagger.AutoInjector;
import butterknife.ButterKnife;
import butterknife.InjectView;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

/**
 * Created by lukasz on 16/02/15.
 */
@AutoComponent(
        dependencies = DemoApp.class, includes = AutoComponentDependencies.class)
@DaggerScope(DemoActivity.class)
@AutoInjector
public class DemoActivity extends Activity {

    @Inject
    ActivityPresenter activityPresenter;

    @InjectView(R.id.navigator_container)
    protected NavigatorView containerView;

    private MortarScope scope;
    private Navigator navigator;

    @Override
    public Object getSystemService(String name) {
        if (scope != null && scope.hasService(name)) {
            return scope.getService(name);
        }

        return super.getSystemService(name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scope = ActivityArchitector.onCreateScope(this, savedInstanceState, new Architected() {
            @Override
            public Navigator createNavigator(MortarScope scope) {
                Navigator navigator = Navigator.create(scope, new Parceler());
                navigator.transitions().register(new TransitionsMapping()
                        .byDefault(new LateralViewTransition(new Config().duration(300))));
                return navigator;
            }

            @Override
            public void configureScope(MortarScope.Builder builder, MortarScope parentScope) {
                DemoActivityComponent component = DaggerDemoActivityComponent.builder()
                        .demoAppComponent(parentScope.<DemoAppComponent>getService(DaggerService.SERVICE_NAME))
                        .build();
                builder.withService(DaggerService.SERVICE_NAME, component);
            }
        });

        DaggerService.<DemoActivityComponent>get(this).inject(this);

        setContentView(R.layout.activity_root);
        ButterKnife.inject(this);

        navigator = ActivityArchitector.onCreateNavigator(this, savedInstanceState, containerView, new HomeStackable());

        activityPresenter.takeView(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        navigator.delegate().onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityPresenter.onResume();
    }

    @Override
    protected void onPause() {
        activityPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(scope).onSaveInstanceState(outState);
        navigator.delegate().onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigator.delegate().onStart();
    }

    @Override
    protected void onStop() {
        navigator.delegate().onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        activityPresenter.dropView(this);

        navigator.delegate().onDestroy();
        navigator = null;

        if (isFinishing() && scope != null) {
            scope.destroy();
            scope = null;
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (navigator.delegate().onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        activityPresenter.onLowMemory();
    }
}
