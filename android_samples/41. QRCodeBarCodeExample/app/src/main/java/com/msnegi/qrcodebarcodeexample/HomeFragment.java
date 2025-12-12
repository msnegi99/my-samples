package com.msnegi.qrcodebarcodeexample;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.msnegi.qrcodebarcodeexample.R;
import com.msnegi.qrcodebarcodeexample.CallBackInterface;
import com.msnegi.qrcodebarcodeexample.HomeActivity;
import com.msnegi.qrcodebarcodeexample.qrbarcodescanner.QRBarScannerFragment;

public class HomeFragment extends Fragment {

    Button readQRCodeBtn, createQRCodeBtn;
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

        readQRCodeBtn = view.findViewById(R.id.readQRCodeBtn);
        readQRCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new QRBarScannerFragment(), new Bundle(),"QRBarScannerFragment");
            }
        });

        createQRCodeBtn = view.findViewById(R.id.createQRCodeBtn);
        createQRCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.loadFragment(new QRCodeGeneratorFragment(), new Bundle(),"QRCodeGeneratorFragment");
            }
        });

        return view;
    }
}
