package com.msnegi.intentserivceexample;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class DownloadResultReceiver extends ResultReceiver
{
    private Receiver mReceiver;

    public DownloadResultReceiver(Handler handler)
    {
        super(handler);
    }

    public void setReceiver(Receiver receiver)
    {
        mReceiver = receiver;
    }

    public interface Receiver
    {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData)
    {
        if (mReceiver != null)
        {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}

