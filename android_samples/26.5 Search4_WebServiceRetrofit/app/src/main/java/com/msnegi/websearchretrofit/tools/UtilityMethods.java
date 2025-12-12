package com.msnegi.websearchretrofit.tools;

//import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityMethods {

    /**
     * This will return the screen width .
     */
    public static int getScreenWidth(Activity context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.widthPixels;
    }

    /**
     * This will return the screen height .
     */
    public static int getScreenHeight(Activity context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.heightPixels;
    }

    /**
     * This will return true if gps is enabled in your device other wise return false
     *
     * @param context
     * @return
     */

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    /*public static String getDeviceIMEINumber(Context context) throws SecurityException {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.getDeviceId();
        return telephonyManager.getDeviceId();
    }*/

    public static boolean isEmptyLocation(String lat, String lng) {
        if (lat == null || lng == null) {
            return true;
        } else if (lat.isEmpty() || lng.isEmpty()) {
            return true;
        } else if (lat.startsWith("0") || lng.startsWith("0")) {
            return true;
        }
        return false;
    }

    public static String getValueInLakhs(double value) {
        try {
            BigDecimal dvalue = new BigDecimal(value);
            value = Double.parseDouble(dvalue.toPlainString());
            value = Double.parseDouble(String.valueOf(truncateDecimal((value / 100000), 0)));
            return String.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(0.0);
    }

    public static double getValueInLakhsString(double value) {
        if (value == 0.0f) {
            return 0.0;
        }
        try {
            BigDecimal dvalue = new BigDecimal(value);
            value = Double.parseDouble(dvalue.toPlainString());
            value = Double.parseDouble(String.valueOf(truncateDecimal((value / 100000), 0)));
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static double round(double value) {
        // if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, 3);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String getDistance(Location currentLocation, String lat, String lng) {
        if (!isEmptyLocation(lat, lng)) {
            double toLat = Double.parseDouble(lat);
            double toLong = Double.parseDouble(lng);
            return distance(currentLocation, toLat, toLong);
        }
        return null;
    }

    public static DecimalFormat DISTANCEFORMAT = new DecimalFormat("#");
    public static DecimalFormat DISTANCEFORMAT1 = new DecimalFormat("0.00");

    public static String distance(Location currentLocation, double toLat, double toLon) {
        double distance = 0;
        if (currentLocation != null) {
            Location locationA = new Location("point A");
            locationA.setLatitude(currentLocation.getLatitude());
            locationA.setLongitude(currentLocation.getLongitude());
            Location locationB = new Location("point B");
            locationB.setLatitude(toLat);
            locationB.setLongitude(toLon);
            distance = locationA.distanceTo(locationB) / 1000;   //in km
        }
        return DISTANCEFORMAT1.format(distance) + "Km";
    }

    public static String distanceNA(Location currentLocation, double toLat, double toLon) {
        double distance = 0;
        if (currentLocation != null) {
            Location locationA = new Location("point A");
            locationA.setLatitude(currentLocation.getLatitude());
            locationA.setLongitude(currentLocation.getLongitude());
            Location locationB = new Location("point B");
            locationB.setLatitude(toLat);
            locationB.setLongitude(toLon);
            distance = locationA.distanceTo(locationB) / 1000;   //in km
            return DISTANCEFORMAT1.format(distance) + "Km";
        }
        return "NA";
    }


    public static double distanceInMeter(Location currentLocation, double toLat, double toLon) {
        double distance = 0;
        try {
            if (currentLocation != null) {
                Location locationA = new Location("point A");
                locationA.setLatitude(currentLocation.getLatitude());
                locationA.setLongitude(currentLocation.getLongitude());
                Location locationB = new Location("point B");
                locationB.setLatitude(toLat);
                locationB.setLongitude(toLon);
                distance = locationA.distanceTo(locationB);   //in km
                return distance;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String distanceCheck(Location currentLocation, double toLat, double toLon) {
        double distance;
        Location locationA = new Location("point A");
        locationA.setLatitude(currentLocation.getLatitude());
        locationA.setLongitude(currentLocation.getLongitude());
        //26.8759486,75.690265
        // locationA.setLatitude(26.8759486);
        // locationA.setLongitude(75.690265);
        Location locationB = new Location("point B");
        locationB.setLatitude(toLat);
        locationB.setLongitude(toLon);
        distance = locationA.distanceTo(locationB);   //in meters
        //distance = locationA.distanceTo(locationB) / 1000;   //in km
        LogManager.d("distance meter", "" + locationA.distanceTo(locationB));
        LogManager.d("distance km", "" + distance);
        return DISTANCEFORMAT.format(distance);
    }

    public static boolean isNotNull(String text) {
        boolean isNotNull = false;
        if ((text != null) && (!"".equals(text)) && (!"null".equalsIgnoreCase(text))) {

            isNotNull = true;
        } else {
            isNotNull = false;
        }
        return isNotNull;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidPAN(CharSequence target) {
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }


    public static final void focusOnView(final ScrollView scrollView) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }


    public static final void focusOnCurrentView(final ScrollView scrollView, final View view) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                //scrollView.scrollTo(0, (int) (view.getY()));
            }
        });
    }

    public static void updateWeightInLayoutParams(View view, float weightValue) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.weight = weightValue;
        view.setLayoutParams(params);
    }

    /**
     * Method to hide the system keyboard.
     *
     * @param view View is required to get the window token
     */
    public static void hideSystemKeyboard(View view) {
        // hiding the system keyboard:
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);                    
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Default returned value is 0.
     *
     * @param dayString
     * @return
     */
    public static int parseInt(String dayString) {
        try {
            if (dayString != null && TextUtils.isDigitsOnly(dayString)) {
                return Integer.parseInt(dayString);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static int parseInt1(String dayString) {
        try {
            if (dayString != null) {
                return Integer.valueOf(dayString);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void printStackTrace(Exception e) {
        try {
            e.printStackTrace();
        }
        //To handle the null pointer case
        catch (NullPointerException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Method to parse the given string to float.
     *
     * @param string String
     * @return Float value. If the value is invalid will return 0
     */
    public static float parseFloat(String string) {
        try {
            if (!TextUtils.isEmpty(string)) {
                return Float.parseFloat(string);
            }
        } catch (NumberFormatException e) {
            printStackTrace(e);
        } catch (Exception e) {
            printStackTrace(e);
        }
        return 0;
    }

    /**
     * Method to parse the given string to float.
     *
     * @param string String
     * @return Float value. If the value is invalid will return 0
     */
    public static double parseDouble(String string) {
        try {
            if (!TextUtils.isEmpty(string)) {
                return Double.parseDouble(string);
            }
        } catch (NumberFormatException e) {
            printStackTrace(e);
        } catch (Exception e) {
            printStackTrace(e);
        }
        return 0;
    }

    /**
     * Method contains the logic to generate the order number.
     * The logic is  according to new requirement given by client on 26 August 2016
     *
     * @param userCode user code
     * @return
     */
    public static String createOrderNumber(String userCode, String partnerCode) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMMyy/HH:mm");
        String formattedDate = df.format(c.getTime());
//        int randomNumber = generateRandomNumber();
        return (userCode + "/" + partnerCode + "/" + formattedDate.toUpperCase());
    }

    public static int generateRandomNumber() {
        int min = 1;
        int max = 99;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        return i1;
    }

    public static boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void sendEmailTo(Context context, String emailID) {
        try {
            emailID = emailID.trim();
            if (TextUtils.isEmpty(emailID)) {
                return;
            }
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", emailID, null));
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (Exception e) {
            e.printStackTrace();
            //ToastManager.showToast(context, "Email Client Not Found", true);
        }
    }

    public static void makePhoneCall(Context context, String phone) {
        try {
            phone = phone.trim();
            if (TextUtils.isEmpty(phone)) {
                return;
            }
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            context.startActivity(intent);
        } catch (Exception e) {
            //ToastManager.showToast(context, "Call Client Not Found", true);
            e.printStackTrace();
        }

    }

    /**
     * disable edit text
     *
     * @param editText
     */
    public static void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    public static void enableView(View view, boolean enable) {
        if (!enable) {
            view.setFocusable(false);
            view.setFocusableInTouchMode(false);
            view.setClickable(false);
        } else {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setClickable(true);
        }

    }

    // get unique id
    public static String getUniqueId() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }

    private static BigDecimal truncateDecimal(double x, int numberofDecimals) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }

    /**
     * get file path from uri
     *
     * @param context
     * @param uri
     * @return
     * @throws URISyntaxException
     */
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        String[] proj = {MediaStore.Images.Media.DATA};

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(
                context,
                uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);                
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static void setMaxLenghtEdit(EditText inputs, int maxLength) {
        InputFilter[] FilterArray = new InputFilter[2];
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        FilterArray[1] = new InputFilter.AllCaps();
        inputs.setFilters(FilterArray);
    }

    public static InputFilter ignoreFirstWhiteSpace() {
        return new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
    }

    public static void calledInternet(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity");
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            //ToastManager.showToast(context, context.getString(R.string.exception_message_no_connection), true);
        }
    }

    public static boolean isValidList(Object object) {
        if (object == null) return false;
        if (object instanceof List) {
            List data = (List) object;
            return !(data.isEmpty() || data.get(0) == null);
        }
        return false;
    }

    public static String camelCase(String stringToConvert) {
        if (stringToConvert == null || TextUtils.isEmpty(stringToConvert))
            return "";
        String afterBrackets = "";
        if (stringToConvert.contains("(")) {
            afterBrackets = stringToConvert.substring(stringToConvert.indexOf("("));
            stringToConvert = stringToConvert.substring(0, stringToConvert.indexOf("("));
        }
        return Character.toUpperCase(stringToConvert.charAt(0)) +
                stringToConvert.substring(1).toLowerCase() + afterBrackets;
    }

    public static String addZeroAtBeginningIfLessThanNine(int value) {
        return value < 10 ? "0" + value : String.valueOf(value);
    }

    /*public static boolean isMyServiceRunning() {
        Context ctx = getApplicationContext();
        if (ctx != null) {
            ActivityManager manager = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ("com.msnegi.websearchretrofit.services.MyService".equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }*/

    public static String getOrderSummaryColor(String materialCode, String materialName, String uomName) {
        return "<font color='#165BB6'>"
                + String.valueOf(materialCode) + "</font> -"
                + materialName
                + "  <font color='#D32F2F'> ("
                + uomName + ")</font>";
    }

    public static void clearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    public static double calculateGrowth(double newValue, double oldValue) {
        if (oldValue == 0) {
            return 0;
        }
        return (((newValue / oldValue) * 100) - 100);
    }

    public static String getStringUptoTwoDigits(Double num) {
        BigDecimal a = new BigDecimal(num);
        BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        String mReturn = String.format("%.2f", roundOff.doubleValue());
        return mReturn;
    }

    public static String calculateAch(double ach, double target) {
        if (target == 0) {
            return getStringUptoTwoDigits(0.0);
        }
        return getStringUptoTwoDigits(((ach / target) * 100));
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


}
