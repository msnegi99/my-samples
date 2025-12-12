package com.msnegi.mycounters;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.msnegi.mycounters.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class CountersFragment extends Fragment {

    TextView counterTxt,timerTxt;
    Button startBtn,stopBtn,startTimerBtn,stopTimerBtn;

    CountDownTimer countDownTimer;

    Timer timer;
    TimerTask timerTask;
    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_counters, container, false);

        counterTxt = view.findViewById(R.id.counterTxt);

        startBtn = view.findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countDownTimer = countDownTimer(counterTxt);
            }
        });

        stopBtn = view.findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
            }
        });

        //final Handler handler = new Handler();

        timerTxt = view.findViewById(R.id.timerTxt);

        startTimerBtn = view.findViewById(R.id.startTimerBtn);
        startTimerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startTimerTask();
            }
        });

        stopTimerBtn = view.findViewById(R.id.stopTimerBtn);
        stopTimerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                stopTimerTask();
            }
        });

        return view;
    }


    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int counter = bundle.getInt("counter");
            timerTxt.setText(String.valueOf(counter)); //this is the textview
        }
    };

    public CountDownTimer countDownTimer(final TextView textView){
        textView.setText("00:00");
        return new CountDownTimer(2*60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(minutes);
                String min = "0" + minutes;
                String sec = "";
                if(seconds<10) {
                    sec = "0" + seconds;
                }else {
                    sec = "" + seconds;
                }
                textView.setText(min + ":" + sec);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getActivity(),"Count Down Finished", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    public void startTimerTask() {

        timer = new Timer();
        counter = 0;
        timerTask = new TimerTask() {
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        counter++;
                        //timerTxt.setText(counter);
                        Bundle bundle = new Bundle();
                        bundle.putInt("counter",counter);
                        Message msg = new Message();
                        msg.setData(bundle);
                        mHandler.dispatchMessage(msg);
                    }
                });
            }
        };

        //schedule the timer, after the first 1000ms the TimerTask will run every 50000ms
        timer.schedule(timerTask, 500, 1000); // infinite timer 500 - initial delay, 1000 regular delay
    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
