package com.msnegi.websearchvolley.tools;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

public class GenericInput implements InputFilter {

    Pattern mPattern;
    String GENERIC_REGEX_FILTER = "[A-Za-z0-9!#$%&\\\\(){|}\\[\\]~:;<=>?@*+,./^_`\'\" \t\r\n\f-]+";

    public GenericInput() {
        mPattern = Pattern.compile(GENERIC_REGEX_FILTER);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned spanned, int dstart, int dend) {
        try {
            if (source.toString().equalsIgnoreCase(""))
                return "";
            if (end > 0) {
                if (!mPattern.matcher("" + source.charAt(end - 1)).matches()) {
                    return "";
                }
            }
        } catch (Exception exp) {
        }
        return null;
    }
}