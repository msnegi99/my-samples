package com.msnegi.canvassurfaceviewdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.msnegi.canvassurfaceviewdemo.R;
import com.msnegi.canvassurfaceviewdemo.canvasclipping.CanvasClippingActivity;
import com.msnegi.canvassurfaceviewdemo.drawingexample.DrawingExampleActivity;
import com.msnegi.canvassurfaceviewdemo.simplecanvas.SimpleCanvasActivity;
import com.msnegi.canvassurfaceviewdemo.surfaceview.SurfaceViewActivity;

public class HomeFragment extends Fragment {

    Button canvasDrawingBtn, simpleCanvasExampleBtn, canvasClippingExampleBtn, surfaceViewExampleBtn;
    CallBackInterface callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeActivity) context;
        Bundle bundle = getArguments();
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.setTitle("Home Fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        canvasDrawingBtn = view.findViewById(R.id.canvasDrawingBtn);
        canvasDrawingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawingExampleActivity.class);
                startActivity(intent);
            }
        });

        simpleCanvasExampleBtn = view.findViewById(R.id.simpleCanvasExampleBtn);
        simpleCanvasExampleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SimpleCanvasActivity.class);
                startActivity(intent);
            }
        });

        canvasClippingExampleBtn = view.findViewById(R.id.canvasClippingExampleBtn);
        canvasClippingExampleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CanvasClippingActivity.class);
                startActivity(intent);
            }
        });

        surfaceViewExampleBtn = view.findViewById(R.id.surfaceViewExampleBtn);
        surfaceViewExampleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SurfaceViewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
