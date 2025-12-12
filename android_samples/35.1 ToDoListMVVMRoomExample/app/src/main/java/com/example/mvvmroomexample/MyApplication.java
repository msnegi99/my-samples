package com.example.mvvmroomexample;

import android.app.Application;
import com.example.mvvmroomexample.model.ModelImplementor;

public class MyApplication extends Application
{
    static ModelImplementor ToDosRepositoryImpl;

    @Override
    public void onCreate() {
        super.onCreate();
        ToDosRepositoryImpl = new ModelImplementor(this);
    }

    public static ModelImplementor getToDosRepositoryImpl(){
        return ToDosRepositoryImpl;
    }
}
