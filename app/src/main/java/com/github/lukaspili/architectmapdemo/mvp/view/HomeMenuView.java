package com.github.lukaspili.architectmapdemo.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.github.lukaspili.architectmapdemo.R;
import com.github.lukaspili.architectmapdemo.mvp.presenter.HomeMenuPresenter;
import com.github.lukaspili.architectmapdemo.mvp.presenter.stackable.HomeMapStackable;
import com.github.lukaspili.architectmapdemo.mvp.presenter.stackable.HomeMenuStackable;
import com.github.lukaspili.architectmapdemo.mvp.presenter.stackable.HomeMenuStackableComponent;

import architect.Stackable;
import architect.commons.view.StackedFrameLayout;
import architect.robot.DaggerService;
import autodagger.AutoInjector;
import butterknife.ButterKnife;

/**
 * Created by lukasz on 21/03/15.
 */
@AutoInjector(HomeMenuPresenter.class)
public class HomeMenuView extends StackedFrameLayout<HomeMenuPresenter> {

    public HomeMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Stackable getStackable() {
        return new HomeMenuStackable();
    }

    @Override
    public void initWithContext(Context context) {
        DaggerService.<HomeMenuStackableComponent>get(context).inject(this);

        View view = View.inflate(context, R.layout.view_home_menu, this);
        ButterKnife.inject(view);
    }
}
