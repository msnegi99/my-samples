package com.msnegi.websearchvolley.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.msnegi.websearchvolley.R;
import com.msnegi.websearchvolley.tools.Utilities;

import ir.alirezabdn.wp7progress.WP10ProgressBar;

public class ProgressDialog extends Dialog {

    private Context context;
    private WP10ProgressBar progressBar;
    private CustomTextView customTextView;
    private String msg;
    private boolean isCanceled = false;

    public ProgressDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public ProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public ProgressDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }

    private void init() {
        View v = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        progressBar = v.findViewById(R.id.progress_bar);
        customTextView = v.findViewById(R.id.progress_text);
        isCanceled = false;
        setContentView(v, new FrameLayout.LayoutParams(Utilities.dpTopx(context, 200), Utilities.dpTopx(context, 200)));
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public ProgressDialog setCanceled(boolean canceled) {
        isCanceled = canceled;
        return this;
    }

    public void setMessage(String msg){
        this.msg = msg;
    }

    @Override
    public void show() {
        super.show();
        if(progressBar != null)
            progressBar.showProgressBar();
        customTextView.setText(msg);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(progressBar != null)
            progressBar.hideProgressBar();
    }
}