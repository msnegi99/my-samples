package com.javapapers.androidgeocoding;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GeocodingActivity extends Activity {
    Button showLatLongBtn, btngetAddress;
    TextView latLongTxt, addressTxt;
    EditText addressEdt, latlongEdt;
    private Context context=null;
    private ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geocoding);

        context=this;
        latLongTxt = (TextView) findViewById(R.id.latLongTxt);
        addressTxt  = (TextView) findViewById(R.id.addressTxt);
        latlongEdt  = (EditText) findViewById(R.id.latlongEdt);

        showLatLongBtn = (Button) findViewById(R.id.showLatLongBtn);
        showLatLongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText editText = (EditText) findViewById(R.id.addressEdt);
                String address = editText.getText().toString();

                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address, getApplicationContext(), new GeocoderHandler());
            }
        });

        btngetAddress  = (Button) findViewById(R.id.button_getAddress);
        btngetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog = ProgressDialog.show(context, "","Please wait..", true);
                GetCurrentAddress currentadd=new GetCurrentAddress();
                currentadd.execute();
            }
        });
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            latLongTxt.setText(locationAddress);
            latlongEdt.setText(locationAddress);
        }
    }

    public  String getAddress(Context ctx, double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        List<Address> addresses = new ArrayList<Address>();
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);

                String locality=address.getLocality();
                String city=address.getCountryName();
                String region_code=address.getCountryCode();
                String zipcode=address.getPostalCode();
                double lat =address.getLatitude();
                double lon= address.getLongitude();

                result.append(locality+" ");
                result.append(city+" "+ region_code+" ");
                result.append(zipcode);
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        //return result.toString();
        return  addresses.get(0).getSubThoroughfare() + " " + addresses.get(0).getThoroughfare()
                + "\n" + addresses.get(0).getAdminArea() + " " + addresses.get(0).getLocality()
                + "\n" + addresses.get(0).getCountryName() + " " + addresses.get(0).getPostalCode();
    }

    private class GetCurrentAddress extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // this lat and log we can get from current location but here we given hard coded
            double latitude=38.8976633;
            double longitude=-77.0365739;

            String address=	getAddress(context, latitude, longitude);
            return address;
        }

        @Override
        protected void onPostExecute(String resultString) {
            dialog.dismiss();
            addressTxt.setText(resultString);
        }
    }

}