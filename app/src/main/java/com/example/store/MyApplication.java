package com.example.store;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{
    private static Context context;
    private static MyApplication instance;
    private static String token;
    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
        instance=this;
    }
    public static MyApplication getInstance(){
        return instance;
    }
    public static void setInstance(MyApplication instance){
        MyApplication.instance=instance;
    }
    public static Context getContext(){
        return context;
    }
    public static void setContext(Context context){
        MyApplication.context=context;
    }
    public static String getToken(){
        return token;
    }
    public static void setToken(String token){
        MyApplication.token=token;
    }
}
