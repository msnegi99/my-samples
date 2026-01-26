package com.example.androidframeanimation;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView myAnimation;
    private AnimationDrawable myAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAnimation = (ImageView) findViewById(R.id.animation);
        myAnimationDrawable = (AnimationDrawable) myAnimation.getBackground();

        /*int duration = 0;
        for(int i = 0; i < myAnimationDrawable.getNumberOfFrames(); i++){
            duration += myAnimationDrawable.getDuration(i);
        }*/
        //start the animation
        myAnimationDrawable.start();
        //stop the animation
        //myAnimationDrawable.stop();

    }
}
