package com.msnegi.websearchretrofit.tools;

import android.text.TextUtils;

public class StringUtils {

    public static boolean isEmpty(String data) {
        if(data==null){
            return true;
        }
        if(data!=null) {
            if (TextUtils.isEmpty(data)) {
                return true;
            } else {
                String inputData = data.trim();
                if (inputData.equalsIgnoreCase("null")) {
                    return true;
                } else if (inputData.equalsIgnoreCase("\"\"")) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isEmpty1(String data) {
        if(data==null){
            return true;
        }
            if (TextUtils.isEmpty(data)) {
                return true;
            } else {
                String inputData = data.trim();
                if (inputData.equalsIgnoreCase("null")) {
                    return true;
                } else if (inputData.equalsIgnoreCase("\"\"")) {
                    return true;
                }
            }

        return false;
    }

    public static String FirstLetterInUpperCase(String word) {
        String result = "";
        if (isValidString(word)) {
            if (word.length() > 0) {
                char c = word.charAt(0);
                String splitedString = word.substring(1, word.length());
                result = Character.toUpperCase(c) + splitedString;
            }
        }
        return result;
    }

    public static boolean isValidString(String str) {
        boolean isValid = false;
        if (str != null && str.length() > 0) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;

    }

    public static int getIntValueFromString(String value) {

        try {
            int iValue = Integer.parseInt(value);
            return iValue;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String getIntValueFromFloat(String value) {

        try {
            int iValue = Float.valueOf(value).intValue();
            return String.valueOf(iValue);
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    public static Double getDoubleValueFromString(String value) {

        if (value == null) {
            return 0.0;
        }
        try {
            Double iValue = Double.valueOf(value);
            return iValue;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static String getStringValueFromBoolean(boolean value) {
        String sValue = (value) ? "1" : "0";
        return sValue;
    }

    public static String getStringValueFromFloat(String fValue) {
        try {
            return String.valueOf(fValue);
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    public static String getStringFromBoolean(boolean value) {
        if (value) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


}


