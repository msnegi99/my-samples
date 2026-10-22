package com.msnegi.intentserivceexample

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver

/**
 * ResultReceiver : Generic interface for receiving a callback result from someone. Use this by creating
 * a subclass and implement onReceiveResult(int, Bundle), which you can then pass to others and send through IPC,
 * and receive results they supply with send(int, Bundle). .
 *
 * Handler : A Handler allows you to send and process Message and Runnable objects associated with a thread's MessageQueue.
 * Each Handler instance is associated with a single thread and that thread's message queue. When you create a new Handler,
 * it is bound to the thread / message queue of the thread that is creating it -- from that point on, it will deliver messages
 * and runnables to that message queue and execute them as they come out of the message queue.
 *
 */

class DownloadResultReceiver(handler: Handler?) : ResultReceiver(handler) {
    private var mReceiver: Receiver? = null

    fun setReceiver(receiver: Receiver?) {
        mReceiver = receiver
    }

    interface Receiver {
        fun onReceiveResult(resultCode: Int, resultData: Bundle?)
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        if (mReceiver != null) {
            mReceiver!!.onReceiveResult(resultCode, resultData)
        }
    }
}

