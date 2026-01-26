package com.msnegi.websearchvolley.views;

import android.content.Context;
import android.util.AttributeSet;

import com.msnegi.websearchvolley.tools.Utilities;

public class CustomButton extends androidx.appcompat.widget.AppCompatButton {
    private Context context;
    public CustomButton(Context context) {
        super(context);
        this.context = context;
        setTypeface();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setTypeface();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setTypeface();
    }

    private void setTypeface(){
        Utilities.setRegularFont(context, this);
    }
}
