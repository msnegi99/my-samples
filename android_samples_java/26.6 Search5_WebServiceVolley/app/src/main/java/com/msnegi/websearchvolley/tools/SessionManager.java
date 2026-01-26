package com.msnegi.websearchvolley.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager
{
    SharedPreferences pref;
    Editor editor;

    public SessionManager(Context context)
    {
        pref = context.getSharedPreferences("ocr_plus", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setCount(){
        int cnt = getCount();
        cnt = cnt + 1;
        editor.putInt("APP_COUNT", cnt);
        editor.commit();
    }

    public int getCount(){
        return pref.getInt("APP_COUNT", 0);
    }

    public void setRated(String str){
        editor.putString("RATING_SET","true");
        editor.commit();
    }

    public String isRated(){
        return pref.getString("RATING_SET", "false");
    }
	
}

