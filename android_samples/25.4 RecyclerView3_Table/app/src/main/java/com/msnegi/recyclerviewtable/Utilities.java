package com.msnegi.recyclerviewtable;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//import android.support.design.widget.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;

public class Utilities  {

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

    /*public static String getPublicPath(Context context) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), context.getResources().getString(R.string.app_name));
        if (!file.mkdirs()) {
        }
        Constants.publicPath = file.getAbsolutePath();
        return Constants.publicPath;
    }*/

    /*public static String getBasePath(Context context) {
        File file = new File(Environment.getExternalStorageDirectory(), context.getResources().getString(R.string.app_name));
        if (!file.mkdirs()) { }
        Constants.basePath = context.getExternalFilesDir(null).getAbsolutePath();
        //Constants.basePath = file.getAbsolutePath();
        return Constants.basePath;
    }*/

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

    /*public static void ShowMessages(String msg){
        if(Constants.DEBUG){
            Log.d(Constants.TAG, msg);
        }
    }*/

    /*public static boolean isInternetAvailable(Context context) {
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
    }*/

    /*public static void showErrorLayout(@NotNull View layout_assert, @Nullable String msg, final RequestCallback requestCallback) {
        layout_assert.setVisibility(View.VISIBLE);
        ((CustomSemiBoldTextView)layout_assert.findViewById(R.id.error_text)).setText(msg);
        layout_assert.findViewById(R.id.btnRetry1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCallback.result(null);
            }
        });
    }*/

    /*public static void showErrorLayout(@NotNull View layout_assert, @Nullable String msg, boolean showRetry, final RequestCallback requestCallback) {
        layout_assert.setVisibility(View.VISIBLE);
        ((CustomSemiBoldTextView)layout_assert.findViewById(R.id.error_text)).setText(msg);
        layout_assert.findViewById(R.id.btnRetry1).setVisibility(showRetry ? View.VISIBLE : View.GONE);
        layout_assert.findViewById(R.id.btnRetry1).setOnClickListener(v -> requestCallback.result(null));
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

    public static class DividerItemDecoration extends androidx.recyclerview.widget.DividerItemDecoration {
        private float value;
        public DividerItemDecoration(Context  context, int orientation, float value, Drawable drawable){
            super(context, orientation);
            this.value = value;
            this.setDrawable(drawable);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = (int) value;
            outRect.bottom = (int) value;
            outRect.left = (int) value;
            outRect.right = (int) value;
        }
    }

    /*public static String getValue(LinkedTreeMap linkedTreeMap, String key){
        if(linkedTreeMap.containsKey(key)){
            if(linkedTreeMap.get(key) != null){
                return linkedTreeMap.get(key).toString();
            }
        }
        return "";
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

    //region User Management
    /*public static void loadUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CelloPrefs.CELLO_PREFS.name(), Context.MODE_PRIVATE);
        String userStr = sharedPreferences.getString(CelloPrefs.LOGIN_USER_KEY.name(), "");
        Constants.user = User.buildUser(userStr);
    }

    public static void saveUser(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CelloPrefs.CELLO_PREFS.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CelloPrefs.LOGIN_USER_KEY.name(), User.buildUser(user).toString());
        editor.commit();

        //Utilities.trackEvent((Activity) context, ScreenName.RegisterScreen, EventName.LOG_IN, user.getUid(), user.getEmail());
    }

    public static void logoutUser(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CelloPrefs.CELLO_PREFS.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CelloPrefs.LOGIN_USER_KEY.name());
        editor.commit();

        //Utilities.trackEvent((Activity) context, ScreenName.RegisterScreen, EventName.LOG_OUT, user.getUid(), user.getEmail());
    }
    //endregion

    //region Home Page Management
    public static HomePage getHomePage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CelloPrefs.CELLO_PREFS.name(), Context.MODE_PRIVATE);
        String homepageStr = sharedPreferences.getString(CelloPrefs.HOMEPAGE_KEY.name(), "");
        HomePage homePage = HomePage.build(homepageStr, context);
        return homePage;
    }

    public static void saveHomePage(Context context, JSONObject jsonObject) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CelloPrefs.CELLO_PREFS.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CelloPrefs.HOMEPAGE_KEY.name(), jsonObject.toString());
        editor.commit();
    }

    public static String getNavigationBar(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CelloPrefs.CELLO_PREFS.name(), Context.MODE_PRIVATE);
        String homepageStr = sharedPreferences.getString(CelloPrefs.NAV_KEY.name(), "");
        return homepageStr;
    }

    public static void saveNavigationBar(Context context, JSONObject jsonObject) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CelloPrefs.CELLO_PREFS.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CelloPrefs.NAV_KEY.name(), jsonObject.toString());
        editor.commit();
    }

    //endregion

    public static void saveUserLocation(Context context, Location.State state) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CelloPrefs.CELLO_PREFS.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CelloPrefs.USER_LOCATION_KEY.name(), state.getState_id());
        editor.commit();
    }

    public static String getUserLocation(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CelloPrefs.CELLO_PREFS.name(), Context.MODE_PRIVATE);
        String homepageStr = sharedPreferences.getString(CelloPrefs.USER_LOCATION_KEY.name(), "");
        *//*if(homepageStr.isEmpty()){
            if(Constants.location != null)
                for(Location.State state : Constants.location.listofStates("IN"))
                    if(state.getState_name().equalsIgnoreCase("delhi"))
                        homepageStr = state.getState_id();
        }*//*
        return homepageStr;
    }


    public static String prevScreenName = "";
    public static void trackEvent(Activity activity, String screenName, String eventName, String itemId, String itemName){
        *//*FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(activity);
        *//**//*if(!prevScreenName.equalsIgnoreCase(screenName)){
            firebaseAnalytics.logEvent(eventName, null);
        }*//**//*

        Bundle bundle = new Bundle();
        bundle.putString("customer_email", Constants.user != null ? Constants.user.getEmail() : "");
        bundle.putString("customer_number", Constants.user != null ? Constants.user.getMobile() : "");
        bundle.putString("customer_id", Constants.user != null ? Constants.user.getUid() : "");
        bundle.putString("item_id", itemId.isEmpty() ? "NA": itemId);
        bundle.putString("item_name", itemName.isEmpty() ? "NA" : itemName);
        bundle.putString("App_Screen", screenName);

        //firebaseAnalytics.setCurrentScreen(activity, screenName, screenName);
        firebaseAnalytics.setUserProperty("App_Screen", screenName);
        firebaseAnalytics.logEvent(eventName, bundle);*//*
    }


    //region Download
    public static void DownloadCatalogue(Context context, Catalogue catalogue){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {
            Download(context, catalogue);
        }
    }

    private static void Download(final Context context, final Catalogue catalogue) {
        if (Constants.downloadQueue.downloadList.isEmpty()) {
            List<String> urls = new ArrayList<>();
            urls.add(catalogue.getLink());
            if (urls.size() > 0) {
                DownloadLib downloadLib = new DownloadLib(context, "2" + catalogue.getId(), urls, Constants.publicPath, new DownloadCallback()
                {
                    @Override
                    public void getReport(HashMap<Integer, Status> reportMap, DownloadLib downloadLib) {
                        //catalogue.setLink(Constants.publicPath + catalogue.getLink().substring(catalogue.getLink().lastIndexOf("/")));
                        ShowOpenDialog(context, context.getString(R.string.open_catalogue), Constants.publicPath + catalogue.getLink().substring(catalogue.getLink().lastIndexOf("/")));
                        //recyclerAdapter.notifyDataSetChanged()

                        Constants.downloadQueue.getElement().setProgress(0);
                        Constants.downloadQueue.removeElement();
                        if (Constants.downloadQueue.getElement() != null) {
                            Constants.downloadQueue.getElement().startDownload();
                        }
                    }

                    @Override
                    public void onProgressUpdate(Status status, int position, int progress, String libId) {

                    }

                    @Override
                    public void onProgress(int progress, String libId) {

                    }

                    @Override
                    public void onCancel() {
                        Constants.downloadQueue.removeElement();
                    }
                }, Utilities.dpTopx(context, 300f), Utilities.dpTopx(context, 130f), false);
                downloadLib.startDownload();
                Constants.downloadQueue.addElement(downloadLib);
            } else {
                Toast.makeText(context, context.getString(R.string.empty_download), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, context.getString(R.string.download_progress), Toast.LENGTH_SHORT).show();
        }
    }

    private static void  ShowOpenDialog(final Context context, String s, final String link) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(link)));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent, "Open Catalogue"));

        *//*AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(s)
                .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(link)));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(Intent.createChooser(intent, "Open Catalogue"));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();*//*
    }
    //endregion

    public static boolean checkValidity(String url){
        if(url != null && !url.isEmpty()){
            return true;
        }
        return false;
    }

    public static String getDownloadPath(Context context, String url, String id){
        StringBuilder builder = new StringBuilder();
        if(url == null || url.isEmpty()){
            return "";
        }
        try {
            builder.append(Utilities.getBasePath(context));
            builder.append("/.Products");
            *//*if(!id.isEmpty()){
                builder.append(File.separator);
                builder.append(id);
            }*//*
            new File(builder.toString()).mkdirs();
            builder.append(url.substring(url.lastIndexOf("/")));
        }catch (Exception e){
            Utilities.ShowMessages(e.getMessage());
        }
        return builder.toString();
    }*/

}
