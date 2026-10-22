package com.msnegi.callbackexamples

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MessageReceiver : BroadcastReceiver() {
    private var callback: CallBackInterface? = null

    fun setContext(callback: CallBackInterface) {
        this.callback = callback
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        callback!!.callbackfunction("This is a callback from Broadcast receiver !!!")
    }
}
