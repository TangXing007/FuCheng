package hml.come.fucheng.mvp.component;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import hml.come.fucheng.mvp.module.AppModule;

/**
 * Created by tangdi on 8/15/17.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context context();
    Gson gson();
    RequestQueue requestqueue();
}
