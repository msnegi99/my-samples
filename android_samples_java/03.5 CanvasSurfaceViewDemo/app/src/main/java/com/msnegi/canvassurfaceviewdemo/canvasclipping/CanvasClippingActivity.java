package com.msnegi.canvassurfaceviewdemo.canvasclipping;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.msnegi.canvassurfaceviewdemo.R;

public class CanvasClippingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_clipping);

        setContentView(new ClippedView(this));
    }
}