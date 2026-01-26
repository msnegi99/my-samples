package com.msnegi.notificationbit;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
//import com.android.volley.RequestQueue;
import com.msnegi.notificationbit.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

//import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.messaging.FirebaseMessaging;
import com.msnegi.notificationbit.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Firebase example";
    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private String[] permission = new String[]{Manifest.permission.POST_NOTIFICATIONS};
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permission, PERMISSIONS_REQUEST_CODE);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {

                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        System.out.println("token " + token);
                    }
                });

        Button btn = findViewById(R.id.sendHello);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();

                            Toast.makeText(MainActivity.this, deepLink.toString(), Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(this, ProductView.class)
                            //.putExtra("url", deepLink.toString())
                            //.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                        Toast.makeText(MainActivity.this, "Dynamic Link Failer", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendNotification()
    {
        try {
            JSONObject request = new JSONObject();
            JSONObject notificationData = new JSONObject();
            notificationData.put("imageName", "Flower");
            notificationData.put("id", "1324");
            notificationData.put("title", "dummy title");
            notificationData.put("textMessage", "Hi!! This is the message from device");
            notificationData.put("sound", "no sound");
            request.put("notification", notificationData);
            request.put("to", token);

            JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send", request,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "key=AAAAl1veyzg:APA91bEoHi96fNzfO5vXvia_TWLLcylC5hg_m27Bavc_ntdRPsoEeSIqflr_Cua0ZbDRCg9LVX2EyaKvSW81TRde9C0W6ltCRy_S3xScRt2kdR7Q42UHSChzcq5iDyhq0Z1wLiXD4NeM");
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            RequestQueue.getInstance(this).addToRequestQueue(jsArrayRequest);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String data = getIntent().getExtras().getString("data");
        String param = data.substring(data.indexOf("?") + 1);

        if (param.contains("productId")) {
            Bundle bundle = new Bundle();
            bundle.putString("productId", param.substring(param.indexOf("=") + 1));
            //replaceFragment(ProductDetailFragment(), bundle, "ProductDetailFragment")
        } else if (param.contains("categoryId")) {
            Bundle bundle = new Bundle();
            bundle.putString("categoryId", param.substring(param.indexOf("=") + 1));
            //replaceFragment(ProductListFragment(), bundle, "ProductListFragment")
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (alertDialog != null) alertDialog.dismiss();

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                            showAlert();
                        } else {
                            Toast.makeText(this, "Move on to Application", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    AlertDialog alertDialog = null;

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs all permissions to run all functionalities !!!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Do not Allow",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                        Toast.makeText(MainActivity.this, "App required the permissions to work properly", Toast.LENGTH_SHORT).show();
                        //-- move on to application
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Allow",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(MainActivity.this, permission, PERMISSIONS_REQUEST_CODE);
                    }
                });
        alertDialog.show();
    }
}