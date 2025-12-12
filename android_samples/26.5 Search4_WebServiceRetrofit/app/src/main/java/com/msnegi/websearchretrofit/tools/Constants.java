package com.msnegi.websearchretrofit.tools;

//import com.msnegi.websearchretrofit.pojo.User;

public class Constants {

    public static boolean DEBUG = true;

    public static String BASE_URL = "http://msnegi.com/api/";
    public static String WEBADDRESS = BASE_URL + "/api/";
    public static String SOCIALLOGIN = WEBADDRESS + "UserApi/SocialLogin";

    public static String publicPath = "";
    public static String basePath = "";
    public static String TAG = "AllMySamples";

    public static String dateSelected = "";
//    public static User user = null;

    public static final int REQUEST_WRITE_PERMISSION = 9988;
    public static final int REQUEST_IMAGE_READ = 7788;

    public static String in_time = "";

    public static final String firebaseToken = "";

    public static final String GSTIN = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[0-9A-Za-z]{1}[a-zA-Z]{1}[0-9a-zA-Z]{1}";
    public static final String VOTER_ID = "[a-zA-Z0-9]{10}";
    public static final String DRIVING_LICENSE = "[a-zA-Z0-9]{15}";
    public static final String AADHAR = "[0-9]{12}";
    public static final String PAN = "[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}";

    public static String NAMEALLOWED = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.,&";
    public static String ADDRESSALLOWED = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.,&-/";
    public static String GENERIC_REGEX_FILTER = "[A-Za-z0-9!#$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+";
    public static String GENERIC_REGEX_FILTER1 = "[A-Za-z0-9!#$%&\\\\(){|}\\[\\]~:;<=>?@*+,./^_`\'\" \t\r\n\f-]+";

}
