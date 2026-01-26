package com.msnegi.mydialogexamples.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.msnegi.mydialogexamples.R;

import java.util.Objects;

public class ForgotPasswordDialog extends DialogFragment {

    EditText masterPwd,loginPwd,confirmPwd;
    LinearLayout masterPassSec,changePassSec;
    Button savePasswordBtn;

    MyAlertSubmitCallback myAlertSubmitCallback;
    String userKey;

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
        View view = inflater.inflate(R.layout.dialog_verify_password, container, false);

        masterPassSec = view.findViewById(R.id.masterPassSec);
        changePassSec = view.findViewById(R.id.changePassSec);

        masterPwd = view.findViewById(R.id.masterPwd);
        Button verifyMasterPassword = view.findViewById(R.id.verifyMasterPassword);
        verifyMasterPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(masterPwd.getText().toString().trim().length()>0){

                }else{
                    Toast.makeText(getContext(),getString(R.string.master_pass_cant_empty_msg),Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginPwd = view.findViewById(R.id.loginPwd);
        confirmPwd = view.findViewById(R.id.confirmPwd);

        savePasswordBtn = view.findViewById(R.id.savePasswordBtn);
        savePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loginPwd.getText().toString().trim().length()>0 && confirmPwd.getText().toString().trim().length()>0) {
                    if(loginPwd.getText().toString().equals(confirmPwd.getText().toString())){

                        myAlertSubmitCallback.onSubmit();
                    }else{
                        Toast.makeText(getContext(),getString(R.string.both_login_confirm_pass_should_be_same),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(),getString(R.string.both_login_confirm_pass_should_no_be_empty),Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView img_cross = view.findViewById(R.id.img_cross);
        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertSubmitCallback.onCrossImgClick();
            }
        });

        setCancelable(false);
        return view;
    }

    public void openMessagePopup(FragmentManager fragmentManager, String tag, String userKey, MyAlertSubmitCallback myAlertSubmitCallback) {
        if (!isVisible() && !isAdded() && fragmentManager!=null) {
            show(fragmentManager, tag);
            this.myAlertSubmitCallback = myAlertSubmitCallback;
            this.userKey = userKey;
        }
    }

    public interface MyAlertSubmitCallback {
        void onSubmit();
        void onCrossImgClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
