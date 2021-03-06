package hml.come.fucheng.aplication;

import android.app.Application;
import android.content.SharedPreferences;

import hml.come.fucheng.mvp.component.DaggerAppComponent;
import hml.come.fucheng.mvp.module.AppModule;
import hml.come.fucheng.singleton.CustomInfo;

/**
 * Created by TX on 2017/7/26.
 */

public class AppAplication extends Application{
    private SharedPreferences preferences;
    private String user_id, dealer_id, resource_id;
    private String number;


    private static AppAplication instance;

    public static AppAplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        user_id = preferences.getString("user_id", "");
        number = preferences.getString("number", "");
        dealer_id = preferences.getString("dealer_id", "");
        resource_id = preferences.getString("resource_id", "");
        if (user_id == null || user_id.equals("")){
            CustomInfo.getInfo().setIslanding(false);
        }else {
            CustomInfo.getInfo().setIslanding(true);
            CustomInfo.getInfo().setUser_id(user_id);
            CustomInfo.getInfo().setNumber(number);
        }

        if (dealer_id == null || dealer_id.equals("")){
            CustomInfo.getInfo().setDealer_landing(false);
        }else {
            CustomInfo.getInfo().setDealer_id(dealer_id);
            CustomInfo.getInfo().setDealer_landing(true);
        }

        if (resource_id == null || resource_id.equals("")){
            CustomInfo.getInfo().setResource_landing(false);
        }else {
            CustomInfo.getInfo().setResource_id(resource_id);
            CustomInfo.getInfo().setResource_landing(true);
        }
        instance = this;
        initMVP();

    }

    private void initMVP(){
        DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
