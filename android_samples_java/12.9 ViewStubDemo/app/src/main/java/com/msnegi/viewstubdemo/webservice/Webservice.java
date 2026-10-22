package com.msnegi.viewstubdemo.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.view.WindowManager;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.msnegi.viewstubdemo.R;
import com.msnegi.viewstubdemo.webservice.VolleyMultipartRequest;
import com.msnegi.viewstubdemo.webservice.WebserviceCallback;
import com.msnegi.viewstubdemo.webservice.WebserviceSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Webservice {


    public void fetchDataFromServer(final Context context, final String URL, JSONObject obj, final WebserviceCallback callback) {
        ProgressDialog dialog = null;
        if (context instanceof AppCompatActivity) {
            dialog = new ProgressDialog(context, R.style.AppTheme);
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.setTitle("");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            try {
                dialog.show();
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
        }

        /*
         *  Get cache from Volley
         */
        Cache cache = WebserviceSingleton.getInstance(context).getRequestQueue().getCache();
        final Cache.Entry requestEntry = cache.get(URL);

        /*
         * if cache is not null fetch data from cache
         */
        if (requestEntry != null) {
            try {
                JSONObject object = new JSONObject(new String(requestEntry.data, "UTF-8"));
                System.out.println(URL);
                System.out.println(object.toString());
                callback.success(object, object.toString(), URL);
                if (dialog != null && dialog.isShowing()) {
                    try {
                        dialog.dismiss();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            /*
             * if cache is null fetch data from server and invalidate volley cache
             */

            final ProgressDialog finalDialog = dialog;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, obj, response -> {
                if (response.optString("Status").equalsIgnoreCase("ok")) {
                    callback.success(response, response.optString("Message"), URL);
                    invalidateCache(context, URL);
                } else {
                    callback.error(response.optString("Message"), URL);
                }
                if (finalDialog != null && finalDialog.isShowing()) {
                    try {
                        finalDialog.dismiss();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }

            }, error -> {
                if (error == null || error.getMessage() == null || error.getMessage().contains("Network is unreachable")) {
                    callback.error(context.getResources().getString(R.string.network_issue), URL);
                } else {
                    callback.error(error.getMessage(), URL);
                }
                if (finalDialog != null && finalDialog.isShowing()) {
                    try {
                        finalDialog.dismiss();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }

            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    //params.put("Authorization","key=AIzaSyBc1J-QIu7mR8pb8KoLhk0cyUt2LzHcoyE");
                    params.put("Content-Type","application/json");
                    /*params.put(
                            "Authorization",
                            String.format("Basic %s", Base64.encodeToString(
                                    String.format("%s:%s", "abc", "abc").getBytes(), Base64.DEFAULT)));*/
                    return params;
                }

            };
            request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            WebserviceSingleton.getInstance(context).addToRequestQueue(request, URL);
        }
    }

    public void multipartRequest(final Context context, final String URL, final Map<String, String> imgParam,
                                 final Map<String, VolleyMultipartRequest.DataPart> param, final WebserviceCallback callback) {

        final ProgressDialog dialog = new ProgressDialog(context, R.style.AppTheme);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Cache cache = WebserviceSingleton.getInstance(context).getRequestQueue().getCache();
        final Cache.Entry requestEntry = cache.get(URL);

        /*
         * if cache is not null fetch data from cache
         */
        if (requestEntry != null) {
            try {
                JSONObject object = new JSONObject(new String(requestEntry.data, "UTF-8"));
                callback.success(object, object.toString(), URL);
                if (dialog.isShowing()) {
                    try {
                        dialog.dismiss();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }
        } else {

            final VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, URL,
                    response -> {
                        String resultResponse = new String(response.data);
                        try {
                            JSONObject result = new JSONObject(resultResponse);
                            if (result.optString("Status").equalsIgnoreCase("ok")) {
                                callback.success(result.opt("Data"), result.optString("Message"), URL);
                                invalidateCache(context, URL);
                            } else {
                                callback.error(result.optString("Message"), URL);
                            }
                            if (dialog.isShowing()) {
                                try {
                                    dialog.dismiss();
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                if (error == null || error.getMessage().contains("Network is unreachable")) {
                    callback.error(context.getResources().getString(R.string.network_issue), URL);
                } else {
                    callback.error(error.getMessage(), URL);
                }
                if (dialog.isShowing()) {
                    try {
                        dialog.dismiss();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization",
                                String.format("Basic %s", Base64.encodeToString(String.format("%s:%s", "abc", "abc").getBytes(), Base64.DEFAULT)));
                    return params;
                }

                @Override
                protected Map<String, String> getParams() {
                    return imgParam;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    return param;
                }
            };
            WebserviceSingleton.getInstance(context).addToRequestQueue(request, URL);
        }
    }


    /*public void cancelServiceRequest(Context context, String URL) {
        WebserviceSingleton.getInstance(context).cancelPendingRequests(URL);
    }*/

    private void invalidateCache(Context context, String URL) {
        WebserviceSingleton.getInstance(context).getRequestQueue().getCache().invalidate(URL, true);
    }

    /*private void deleteCache(Context context, String URL) {
        WebserviceSingleton.getInstance(context).getRequestQueue().getCache().remove(URL);
    }

    private void clearCache(Context context) {
        WebserviceSingleton.getInstance(context).getRequestQueue().getCache().clear();
    }*/
}
