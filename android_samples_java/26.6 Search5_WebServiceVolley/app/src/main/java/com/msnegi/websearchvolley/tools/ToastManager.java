package com.msnegi.websearchvolley.tools;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.msnegi.websearchvolley.R;

public class ToastManager {
    private static Toast toast;

    private ToastManager() {
        super();
    }

    public static void showToast(Context context, String message, boolean isShort) {

            if(toast!=null) {
                toast.setText(message);
                if (toast.getView()!=null) {
                    if(!toast.getView().isShown())
                    toast.show();
                }else {
                    showMessage(context,message);
                }
            }
            else {
                showMessage(context,message);
            }
    }

    public static void showMessage(Context context, String message)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.layout_custom_toast, null);
        TextView toastTextView = (TextView) layout.findViewById(android.R.id.message);
        toastTextView.setText(Html.fromHtml(message));
        toast = new Toast(context);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
