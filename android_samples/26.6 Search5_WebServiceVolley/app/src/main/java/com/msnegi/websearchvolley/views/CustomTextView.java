package com.msnegi.websearchvolley.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import com.msnegi.websearchvolley.tools.Utilities;

public class CustomTextView extends AppCompatTextView {
    private Context context;
    public CustomTextView(Context context) {
        super(context);
        this.context = context;
        setTypeface();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setTypeface();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setTypeface();
    }

    private void setTypeface(){
        Utilities.setRegularFont(context, this);
    }

    public CustomTextView init(String title, int padding, int textsize, int color, int textColor){
        setText(title);
        setPadding(padding, padding, padding, padding);
        setTextSize(textsize);
        setBackgroundColor(color);
        setTextColor(textColor);
        setGravity(Gravity.CENTER);
        return this;
    }

}
