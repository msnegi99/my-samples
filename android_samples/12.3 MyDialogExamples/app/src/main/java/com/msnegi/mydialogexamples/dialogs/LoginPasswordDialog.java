package com.msnegi.mydialogexamples.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.msnegi.mydialogexamples.R;

import java.util.Objects;

public class LoginPasswordDialog extends DialogFragment {


    ImageView img_cross;
    MyCallback myCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_register_master_password, container, false);

        EditText loginPwd = view.findViewById(R.id.loginPwd);
        EditText masterPwd = view.findViewById(R.id.masterPwd);

        Button okBtn = view.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginPwd.getText().toString().trim().length() != 0 && masterPwd.getText().toString().trim().length() != 0) {
                    myCallback.onSubmit(loginPwd.getText().toString().trim(), masterPwd.getText().toString().trim());
                }else {
                    Toast.makeText(getContext(),getString(R.string.password_cant_empty),Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_cross = view.findViewById(R.id.img_cross);
        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCallback.onCrossImgClick();
            }
        });

        setCancelable(false);
        return view;
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout( (int)(size.x * 0.9), WindowManager.LayoutParams.WRAP_CONTENT );
        window.setGravity( Gravity.CENTER );
        super.onResume();
    }

    public void openMessagePopup(FragmentManager fragmentManager, String tag, MyCallback myCallback) {
        if (!isVisible() && !isAdded() && fragmentManager!=null) {
            fragmentManager.beginTransaction();
            show(fragmentManager, tag);
            this.myCallback = myCallback;
        }
    }

    public interface MyCallback {
        void onSubmit(String loginPassword, String masterPassword);
        void onCrossImgClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
