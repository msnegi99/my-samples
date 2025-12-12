package com.msnegi.qrcodebarcodeexample.qrbarcodescanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.msnegi.qrcodebarcodeexample.CallBackInterface;
import com.msnegi.qrcodebarcodeexample.HomeActivity;
import com.msnegi.qrcodebarcodeexample.R;

public class QRBarScannerFragment extends Fragment
{
    public static final int QRBAR_CODE_SCAN = 2234;
    private String codeFormat,codeContent;
    public static final String noResultErrorMsg = "No scan data received!";
    private Button scanbtn;
    TextView scanResultTxt;
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
        callback.setTitle("QR Code Reader");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_qrbar_scanner, container, false);

        scanResultTxt = view.findViewById(R.id.scanResultTxt);

        scanbtn = (Button) view.findViewById(R.id.scanbtn);
        scanbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                scanResultTxt.setText("Cancelled");
            } else {
                scanResultTxt.setText("Scanned: " + result.getContents());
            }
        } else {
            scanResultTxt.setText("No scan data received!");
        }
    }

}
