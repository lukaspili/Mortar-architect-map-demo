package com.github.lukaspili.architectmapdemo.app;

import android.os.Bundle;

import com.github.lukaspili.architectmapdemo.R;
import com.github.lukaspili.architectmapdemo.di.AutoComponentDependencies;
import com.github.lukaspili.architectmapdemo.di.DaggerScope;
import com.github.lukaspili.architectmapdemo.mvp.presenter.scope.path.HomePath;
import com.github.lukaspili.architectmapdemo.mvp.presenter.util.ActivityPresenter;

import javax.inject.Inject;

import architect.Navigator;
import architect.NavigatorView;
import architect.StackPath;
import architect.TransitionsMapping;
import architect.autostack.DaggerService;
import architect.commons.ArchitectActivity;
import architect.transition.LateralViewTransition;
import autodagger.AutoComponent;
import autodagger.AutoInjector;
import butterknife.ButterKnife;
import butterknife.InjectView;
import mortar.MortarScope;

/**
 * Created by lukasz on 16/02/15.
 */
@AutoComponent(
        dependencies = DemoApp.class, includes = AutoComponentDependencies.class)
@DaggerScope(DemoActivity.class)
@AutoInjector
public class DemoActivity extends ArchitectActivity {

    @Inject
    ActivityPresenter activityPresenter;

    @InjectView(R.id.navigator_container)
    protected NavigatorView containerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerService.<DemoActivityComponent>get(this).inject(this);

        activityPresenter.takeView(this);
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
    protected void onDestroy() {
        activityPresenter.dropView(this);
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        activityPresenter.onLowMemory();
    }

    @Override
    protected void createContentView() {
        setContentView(R.layout.activity_root);
        ButterKnife.inject(this);
    }

    @Override
    protected void configureScope(MortarScope.Builder builder, MortarScope parentScope) {
        DemoActivityComponent component = DaggerDemoActivityComponent.builder()
                .demoAppComponent(parentScope.<DemoAppComponent>getService(DaggerService.SERVICE_NAME))
                .build();
        component.inject(this);

        builder.withService(DaggerService.SERVICE_NAME, component);
    }

    @Override
    protected TransitionsMapping getTransitionsMapping() {
        return new TransitionsMapping()
                .byDefault(new LateralViewTransition());
    }

    @Override
    protected Navigator.Config getNavigatorConfig() {
        return null;
    }

    @Override
    protected NavigatorView getNavigatorView() {
        return containerView;
    }

    @Override
    protected StackPath getInitialPath() {
        return new HomePath();
    }
}
