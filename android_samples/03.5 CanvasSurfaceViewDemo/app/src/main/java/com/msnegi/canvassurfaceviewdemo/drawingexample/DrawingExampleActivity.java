package com.msnegi.canvassurfaceviewdemo.drawingexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.msnegi.canvassurfaceviewdemo.R;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

public class DrawingExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_example);

        MyCanvasView myCanvasView;
        // No XML file; just one custom view created programmatically.
        myCanvasView = new MyCanvasView(this);
        // Request the full available screen for layout.
        myCanvasView.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(myCanvasView);
    }
}