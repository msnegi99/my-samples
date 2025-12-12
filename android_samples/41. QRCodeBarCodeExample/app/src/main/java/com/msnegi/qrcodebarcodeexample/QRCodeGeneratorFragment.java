package com.msnegi.qrcodebarcodeexample;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.msnegi.qrcodebarcodeexample.R;

import android.widget.ImageView;
import android.widget.TextView;

import com.msnegi.qrcodebarcodeexample.R;

public class QRCodeGeneratorFragment extends Fragment {

    public static final int WIDTH = 500;
    public static final int width = 500;
    public static final String STR = "This is test";

    TextView txtConvert;
    ImageView imageView;
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
        callback.setTitle("QR Code Generator");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_q_r_code_generator, container, false);

        imageView = (ImageView) view.findViewById(R.id.qrCode);
        txtConvert  = (TextView) view.findViewById(R.id.txtConvert);

        view.findViewById(R.id.btnConvert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bitmap = encodeAsBitmap(txtConvert.getText().toString());
                    imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;

        try {
            result = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);

        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }

}