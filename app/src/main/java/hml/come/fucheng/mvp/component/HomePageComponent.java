package hml.come.fucheng.mvp.component;

import dagger.Component;
import dagger.Module;
import hml.come.fucheng.activity.HomePageActivity;
import hml.come.fucheng.dagger.ActivityScope;
import hml.come.fucheng.mvp.module.HomePageModule;

/**
 * Created by tangxing on 8/15/17.
 */
@ActivityScope
@Component(modules = HomePageModule.class)
public interface HomePageComponent {
    void inject(HomePageActivity activity);
}
