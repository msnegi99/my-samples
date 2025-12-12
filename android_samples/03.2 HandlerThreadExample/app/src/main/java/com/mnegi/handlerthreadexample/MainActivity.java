package com.mnegi.handlerthreadexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ExampleHandlerThread handlerThread;
    private ExampleRunnable1 runnable1 = new ExampleRunnable1();
    private Object token = new Object();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        handlerThread = new ExampleHandlerThread();
        handlerThread.start();
    }

    final Handler responseHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
           String str = (String) msg.obj;
           System.out.println(str);
            textView.setText(str);                  //-- values returned but textView does not update
        }
    };

    public void doWork(View view) {
        Message msg = Message.obtain(handlerThread.getHandler());
        msg.what = 423;
        msg.arg1 = 23;
        msg.obj = "Obj String";
        //msg.setData();
        msg.sendToTarget();
        //handlerThread.getHandler().sendEmptyMessage(1);
 //--       handlerThread.getHandler().postAtTime(runnable1, token, SystemClock.uptimeMillis());
 //--       handlerThread.getHandler().post(runnable1);
        //handlerThread.getHandler().post(new ExampleRunnable1());
        //handlerThread.getHandler().postAtFrontOfQueue(new ExampleRunnable2());
    }

    public void removeMessages(View view) {
        handlerThread.getHandler().removeCallbacks(runnable1, token);
        textView.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }

    static class ExampleRunnable1 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                Log.d(TAG, "Runnable1: " + i);
                SystemClock.sleep(1000);
            }
        }
    }

    class ExampleHandlerThread extends HandlerThread {

        private static final String TAG = "ExampleHandlerThread";
        public static final int EXAMPLE_TASK = 423;
        private Handler handler;

        public ExampleHandlerThread() {
            super("ExampleHandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
        }

        @SuppressLint("HandlerLeak")
        @Override
        protected void onLooperPrepared() {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case EXAMPLE_TASK:
                            Log.d(TAG, "Example Task, arg1: " + msg.arg1 + ", obj: " + msg.obj);
                            for (int i = 0; i < 4; i++) {
                                Log.d(TAG, "Test " + i);
                                SystemClock.sleep(1000);
                            }

                            Message msg1 = new Message();
                            msg1.obj = "Example Task, arg1: " + msg.arg1 + ", obj: " + msg.obj;
                            responseHandler.sendMessage(msg1);
                            break;
                    }
                }
            };
        }

        public Handler getHandler() {
            return handler;
        }

    }
}