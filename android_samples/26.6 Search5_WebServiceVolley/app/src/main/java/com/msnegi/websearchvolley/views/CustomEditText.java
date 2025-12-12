package com.msnegi.websearchvolley.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.msnegi.websearchvolley.tools.Utilities;

public class CustomEditText extends AppCompatEditText {
    private Context context;
    public CustomEditText(Context context) {
        super(context);
        this.context = context;
        setTypeface();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setTypeface();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setTypeface();
    }

    private void setTypeface(){
        Utilities.setRegularFont(context, this);
    }
}
