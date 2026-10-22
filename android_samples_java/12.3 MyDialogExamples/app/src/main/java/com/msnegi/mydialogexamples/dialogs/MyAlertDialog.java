package com.msnegi.mydialogexamples.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.msnegi.mydialogexamples.R;

import java.util.Objects;

public class MyAlertDialog extends DialogFragment {

    TextView tv_title_hint;
    TextView tv_title;
    Button btn_single_submit;
    ImageView img_cross;

    String title="";
    String bodyText="";
    String buttonText="";

    MyAlertSubmitCallback myAlertSubmitCallback;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_alert, container, false);

        tv_title_hint = view.findViewById(R.id.tv_title_hint);
        tv_title = view.findViewById(R.id.tv_title);
        btn_single_submit = view.findViewById(R.id.btn_single_submit);
        img_cross = view.findViewById(R.id.img_cross);
        btn_single_submit = view.findViewById(R.id.btn_single_submit);

        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertSubmitCallback.onCrossImgClick();
            }
        });

        if (!title.isEmpty()) {
            tv_title.setText(title);
        } else {
            tv_title.setText(getString(R.string.app_name));
        }

        tv_title_hint.setText(Html.fromHtml(bodyText));

        btn_single_submit.setText(buttonText);
        btn_single_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertSubmitCallback.onSubmit();
            }
        });

        setCancelable(false);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void openMessagePopup(FragmentManager fragmentManager, String tag, MyAlertSubmitCallback myAlertSubmitCallback) {
        if (!isVisible() && !isAdded() && fragmentManager!=null) {

            show(fragmentManager, tag);
            this.myAlertSubmitCallback = myAlertSubmitCallback;
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
