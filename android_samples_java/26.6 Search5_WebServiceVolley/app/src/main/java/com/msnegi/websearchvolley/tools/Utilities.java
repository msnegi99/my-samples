package com.msnegi.websearchvolley.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.internal.LinkedTreeMap;
import com.msnegi.websearchvolley.R;
//import com.msnegi.websearchvolley.web.RequestCallback;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class Utilities {

    public enum sharedPrefVariable{
        userInfo,loginTime,attendanceMarked,dateAttendanceMarked;
    }

    public static DisplayMetrics displayMetrics;
    public static int widthPixels, heightPixels;


    public static void saveUserToSharedPrefs(SharedPreferences sharedPreferences, @NotNull Object result, Context context ){
        SharedPreferences.Editor userDetailEditor = sharedPreferences.edit();
        userDetailEditor.putString(sharedPrefVariable.userInfo.name(), result.toString());
        userDetailEditor.putLong(sharedPrefVariable.loginTime.name(), System.currentTimeMillis());
        userDetailEditor.apply();
    }

    public static void logoutUser(SharedPreferences sharedPreferences, Context context){
        SharedPreferences.Editor userDetailEditor = sharedPreferences.edit();
        userDetailEditor.putString(sharedPrefVariable.userInfo.name(), "");
        userDetailEditor.putLong(sharedPrefVariable.loginTime.name(), System.currentTimeMillis());
        userDetailEditor.apply();
    }

    public static int getOrientation(Context context) {
        switch (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK){
            case Configuration.SCREENLAYOUT_SIZE_LARGE: case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return Configuration.ORIENTATION_LANDSCAPE;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL: case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return Configuration.ORIENTATION_PORTRAIT;
            default:
                return Configuration.ORIENTATION_PORTRAIT;

        }
    }

    public static String getScreenSize(Context context) {
        String size;
        try {
            if ((context.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_LARGE) {
                size = "large";
            } else if ((context.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                size = "xLarge";
            } else if ((context.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_NORMAL) {
                size = "normal";
            } else {
                size = "small";
            }
            return size;
        } catch (Exception e) {
            e.printStackTrace();
            return "normal";
        }
    }

    public static boolean isTabletDevice(Context context){
        String screenSize = getScreenSize(context);
        if(screenSize.equalsIgnoreCase("large") || screenSize.equalsIgnoreCase("xLarge")){
            return true;
        }
        return false;
    }

    public static String getPublicPath(Context context) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), context.getResources().getString(R.string.app_name));
        if (!file.mkdirs()) {
        }
        Constants.publicPath = file.getAbsolutePath();
        return Constants.publicPath;
    }

    public static String getBasePath(Context context) {
        File file = new File(Environment.getExternalStorageDirectory(), context.getResources().getString(R.string.app_name));
        if (!file.mkdirs()) {
        }
        Constants.basePath = context.getExternalFilesDir(null).getAbsolutePath();
        return Constants.basePath;
    }

    public static void setMetrics(Context context){
        displayMetrics = context.getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        heightPixels = displayMetrics.heightPixels;
    }

    public static float getBannerHeight(float width, float height){
        float ratio = widthPixels / width;
        float aspectHeight = height * ratio;
        System.out.println(aspectHeight + "----------------------------------");
        return aspectHeight;
    }

    public static int dpTopx(Context context, float val){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, context.getResources().getDisplayMetrics());
    }

    public static void ShowMessages(String msg){
        if(Constants.DEBUG){
            Log.d(Constants.TAG, msg);
        }
    }

    @SuppressLint("MissingPermission")
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /*public static void showErrorLayout(@NotNull View layout_assert, @Nullable String msg, final RequestCallback requestCallback) {
        layout_assert.setVisibility(View.VISIBLE);
        ((TextView)layout_assert.findViewById(R.id.error_text)).setText(msg);
        layout_assert.findViewById(R.id.btnRetry1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCallback.result(null);
            }
        });
    }*/

    public static class VerticalItemDecoration extends RecyclerView.ItemDecoration{
        private float value;
        public VerticalItemDecoration(float value){
            this.value = value;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = (int) value;
            outRect.bottom = (int) value;
        }
    }

    public static class HorizontalItemDecoration extends RecyclerView.ItemDecoration{
        private float value;
        public HorizontalItemDecoration(float value){
            this.value = value;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = (int) value;
            outRect.right = (int) value;
        }
    }


    public static String getValue(LinkedTreeMap linkedTreeMap, String key){
        if(linkedTreeMap.containsKey(key)){
            if(linkedTreeMap.get(key) != null){
                return linkedTreeMap.get(key).toString();
            }
        }
        return "";
    }

    public static String prevScreenName = "";
    /*public static void trackEvent(Activity activity, String screenName, String eventName, String itemId, String itemName){
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(activity);
         if(!prevScreenName.equalsIgnoreCase(screenName)){
            firebaseAnalytics.logEvent(eventName, null);
        }

        Bundle bundle = new Bundle();
        bundle.putString("customer_email", Constants.user != null ? Constants.user.getEmail() : "");
        bundle.putString("customer_number", Constants.user != null ? Constants.user.getMobile() : "");
        bundle.putString("customer_id", Constants.user != null ? Constants.user.getUid() : "");
        bundle.putString("item_id", itemId.isEmpty() ? "NA": itemId);
        bundle.putString("item_name", itemName.isEmpty() ? "NA" : itemName);
        bundle.putString("App_Screen", screenName);

        //firebaseAnalytics.setCurrentScreen(activity, screenName, screenName);
        firebaseAnalytics.setUserProperty("App_Screen", screenName);
        firebaseAnalytics.logEvent(eventName, bundle);
    }*/

    //region Font Factory
    public static void setBoldFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Bold.ttf"));
    }
    public static void setBoldFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Bold.ttf"));
    }
    public static void setBoldFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Bold.ttf"));
    }

    public static void setSemiBoldFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-SemiBold.ttf"));
    }
    public static void setSemiBoldFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-SemiBold.ttf"));
    }
    public static void setSemiBoldFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-SemiBold.ttf"));
    }

    public static void setLightFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Light.ttf"));
    }
    public static void setLightFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Light.ttf"));
    }
    public static void setLightFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Light.ttf"));
    }

    public static void setRegularFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Regular.ttf"));
    }
    public static void setRegularFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Regular.ttf"));
    }
    public static void setRegularFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Regular.ttf"));
    }
    public static Typeface getRegularTypeface(Context context){
        return  Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Regular.ttf");
    }

    public static void setExtraBoldFont(Context context, TextView textView){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-ExtraBold.ttf"));
    }
    public static void setExtraBoldFont(Context context, EditText editText){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-ExtraBold.ttf"));
    }
    public static void setExtraBoldFont(Context context, Button button){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-ExtraBold.ttf"));
    }
    //endregion

    public static void setError(final AppCompatEditText field, String message ) {
        field.requestFocus();
        field.setError(message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                field.setError(null);
            }
        }, 2000);
    }
}
