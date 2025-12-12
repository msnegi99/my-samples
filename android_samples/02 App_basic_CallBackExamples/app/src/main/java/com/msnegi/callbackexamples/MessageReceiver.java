package com.msnegi.callbackexamples;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MessageReceiver extends BroadcastReceiver {

    private CallBackInterface callback;

    public void setContext(CallBackInterface callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        callback.callbackfunction("This is a callback from Broadcast receiver !!!");
    }
}
