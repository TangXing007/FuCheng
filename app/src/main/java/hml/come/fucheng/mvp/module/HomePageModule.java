package hml.come.fucheng.mvp.module;

import dagger.Module;
import dagger.Provides;
import hml.come.fucheng.dagger.ActivityScope;
import hml.come.fucheng.mvp.modle.imp.IHomePageModle;
import hml.come.fucheng.mvp.modle.implement.HomePageModle;
import hml.come.fucheng.mvp.presenter.imp.IHomePagePresenter;
import hml.come.fucheng.mvp.presenter.implement.HomePagePresenter;
import hml.come.fucheng.mvp.view.IHomePageView;

/**
 * Created by tangxing on 8/15/17.
 */
@ActivityScope
@Module
public class HomePageModule {
    private IHomePageView mView;

    public HomePageModule(IHomePageView view){
        mView = view;
    }

    @ActivityScope
    @Provides
    public IHomePageView provideView(){
        return mView;
    }

    @ActivityScope
    @Provides
    public IHomePageModle provideModle(){
        return new HomePageModle();
    }

    @ActivityScope
    @Provides
    public IHomePagePresenter providePresenter(IHomePageModle modle, IHomePageView view){
        return new HomePagePresenter(modle, view);
    }
}
