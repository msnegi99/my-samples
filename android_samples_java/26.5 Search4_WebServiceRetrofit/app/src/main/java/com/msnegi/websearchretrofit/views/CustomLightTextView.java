package com.msnegi.websearchretrofit.views;

import android.content.Context;
import android.util.AttributeSet;

import com.msnegi.websearchretrofit.tools.Utilities;

public class CustomLightTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Context context;
    public CustomLightTextView(Context context) {
        super(context);
        this.context = context;
        setTypeface();
    }

    public CustomLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setTypeface();
    }

    public CustomLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setTypeface();
    }

    private void setTypeface(){
        Utilities.setLightFont(context, this);
    }
}
