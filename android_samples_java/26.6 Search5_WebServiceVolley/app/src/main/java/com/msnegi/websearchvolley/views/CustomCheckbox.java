package com.msnegi.websearchvolley.views;

import android.content.Context;
import android.util.AttributeSet;

import com.msnegi.websearchvolley.tools.Utilities;

public class CustomCheckbox extends androidx.appcompat.widget.AppCompatCheckBox {
    private Context context;
    public CustomCheckbox(Context context) {
        super(context);
        this.context = context;
        setTypeface();
    }

    public CustomCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setTypeface();
    }

    public CustomCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setTypeface();
    }

    private void setTypeface(){
        Utilities.setRegularFont(context, this);
    }
}
