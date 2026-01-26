package com.msnegi.websearchvolley.tools;

import android.text.InputFilter;
import android.text.Spanned;

public class StringInput implements InputFilter {
    private String filterValue;

    public StringInput(String filterValue) {
        this.filterValue = filterValue;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned spanned, int dstart, int dend) {
        try {
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }else if(!filterValue.contains(""+source.charAt(index))) {
                    return "";
                }
            }
        } catch (Exception exp) {
        }
        return null;
    }
}