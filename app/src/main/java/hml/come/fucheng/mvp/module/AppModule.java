package hml.come.fucheng.mvp.module;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hml.come.fucheng.volleyrequest.SingleToneRequestQueue;

/**
 * Created by tangdi on 8/15/17.
 */
@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context){
        mContext = context;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return mContext;
    }

    @Singleton
    @Provides
    public Gson provideGson(){
        return new Gson();
    }

    @Singleton
    @Provides
    public RequestQueue provideRequestQueue(){
        return SingleToneRequestQueue.getInstance().requestQueue();
    }

}
