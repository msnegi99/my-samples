package com.msnegi.websearchvolley.tools;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.io.File;
import java.util.UUID;
import java.util.regex.Pattern;

public abstract class Tools {

    public static DisplayMetrics displayMetrics;
    public static int widthPixels, heightPixels;

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

    public static int getDeviceWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthpixels = displayMetrics.widthPixels;
        int heightpixels = displayMetrics.heightPixels;
        return widthpixels;
    }

    public static int getDeviceHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthpixels = displayMetrics.widthPixels;
        int heightpixels = displayMetrics.heightPixels;
        return heightpixels;
    }

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static int dpToPx(Context context, float dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static String getResizedImage(String imageURL, String imageSize) {
        String imageName = imageURL.split("/")[imageURL.split("/").length - 1].split(Pattern.quote("."))[0];
        switch (imageSize) {
            case "small": {
                return imageURL.replace(imageName, imageName + "_s");
            }
            case "medium": {
                return imageURL.replace(imageName, imageName + "_m");
            }
            case "larger": {
                return imageURL.replace(imageName, imageName + "_l");
            }
            default: {
                return imageURL;
            }
        }
    }

    public static String URLToLocalPath(Context context, String URL) {
        return context.getFilesDir() + File.separator + URL.split("/")[URL.split("/").length - 1];
    }

    // get unique id
    public static String getUniqueld() {
        UUID uniquekey = UUID.randomUUID();
        return uniquekey.toString();
    }

    /*public static String prevScreenName = "";
    public static void trackEvent(Activity activity, String screenName, String eventName, String itemId, String itemName){

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



}
