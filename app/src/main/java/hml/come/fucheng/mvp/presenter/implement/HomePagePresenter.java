package hml.come.fucheng.mvp.presenter.implement;

import hml.come.fucheng.mvp.modle.imp.IHomePageModle;
import hml.come.fucheng.mvp.presenter.imp.IHomePagePresenter;
import hml.come.fucheng.mvp.view.IHomePageView;

/**
 * Created by tangxing on 8/15/17.
 */

public class HomePagePresenter implements IHomePagePresenter {
    private IHomePageModle mModle;
    private IHomePageView mView;
    public HomePagePresenter(IHomePageModle modle, IHomePageView view){
        mModle = modle;
        mView = view;
    }
}
