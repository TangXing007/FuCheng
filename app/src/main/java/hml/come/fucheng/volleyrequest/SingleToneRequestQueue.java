package hml.come.fucheng.volleyrequest;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import hml.come.fucheng.aplication.AppAplication;

/**
 * Created by tangdi on 8/15/17.
 */

public class SingleToneRequestQueue {
    private RequestQueue requestQueue;
    private static SingleToneRequestQueue instance = null;
    private SingleToneRequestQueue(){
        requestQueue = Volley.newRequestQueue(AppAplication.getInstance());
    }

    public static SingleToneRequestQueue getInstance(){
        if(instance == null){
            instance = new SingleToneRequestQueue();
        }
        return instance;
    }

    public RequestQueue requestQueue(){
        return requestQueue;
    }
}
