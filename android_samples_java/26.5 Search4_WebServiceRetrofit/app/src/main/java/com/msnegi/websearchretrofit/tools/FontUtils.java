package com.msnegi.websearchretrofit.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public abstract class FontUtils {

    //--TextView
    public static void setBoldFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Bold.ttf"));
    }

    public static void setExtraBoldFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-ExtraBold.ttf"));
    }

    public static void setLightFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf"));
    }

    public static void setRegularFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf"));
    }

    public static void setSemiBoldFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-SemiBold.ttf"));
    }

    //--EditText
    public static void setBoldFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Bold.ttf"));
    }

    public static void setExtraBoldFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-ExtraBold.ttf"));
    }

    public static void setLightFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf"));
    }

    public static void setRegularFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf"));
    }

    public static void setSemiBoldFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-SemiBold.ttf"));
    }

    //--Button
    public static void setBoldFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Bold.ttf"));
    }

    public static void setExtraBoldFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-ExtraBold.ttf"));
    }

    public static void setLightFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf"));
    }

    public static void setRegularFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf"));
    }

    public static void setSemiBoldFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-SemiBold.ttf"));
    }

}
