package com.mnegi.todolistmvp;

import android.app.Application;
import com.mnegi.todolistmvp.model.Model;
import com.mnegi.todolistmvp.model.ModelImplementor;

public class MyApplication extends Application
{
    static Model model;

    @Override
    public void onCreate() {
        super.onCreate();
        model = new ModelImplementor(this);
    }

    public static Model getModel(){
        return model;
    }
}
