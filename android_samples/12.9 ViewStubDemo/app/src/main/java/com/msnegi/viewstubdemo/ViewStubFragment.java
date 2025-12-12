package com.msnegi.viewstubdemo;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewStub;
import android.widget.Toast;

import com.msnegi.viewstubdemo.R;
import com.msnegi.viewstubdemo.webservice.Webservice;
import com.msnegi.viewstubdemo.webservice.WebserviceCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewStubFragment extends Fragment implements WebserviceCallback {

    private Context context;
    private LinearLayout networkView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_view_stub, container, false);

        getData();

        return view;
    }

    private void showNetworkScreen(View view, String message, boolean calledFromOnCreate) {
        if (networkView == null || calledFromOnCreate) {
            networkView = (LinearLayout) ((ViewStub) view.findViewById(R.id.network_container)).inflate();
            networkView.findViewById(R.id.network_reload_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Retrying...", Toast.LENGTH_SHORT).show();
                    run();
                }
            });
        } else {
            networkView.setVisibility(View.VISIBLE);
        }

        ((ImageView) networkView.findViewById(R.id.network_issue_icon)).setImageResource(R.drawable.internet_disconnect_icon);
        ((TextView) networkView.findViewById(R.id.network_issue_message)).setText(message);
        ((ImageView) networkView.findViewById(R.id.network_reload_icon)).setImageResource(R.drawable.retry_icon);
    }

    private void run(){
        //-- call web service again to retry the same
    }

    @Override
    public void success(Object obj, String message, String URL) {

    }

    @Override
    public void error(String message, String URL) {
        showNetworkScreen(getView(), message, false);
    }

    private void getData() {
        try {
            JSONObject params = new JSONObject();
            params.put("CompanyId", 0);

            new Webservice().fetchDataFromServer(getActivity(), "serviceURL", params, new WebserviceCallback() {
                @Override
                public void success(Object obj, String message, String URL) {

                    if (networkView != null && networkView.getVisibility() == View.VISIBLE) {
                        networkView.setVisibility(View.GONE);
                        //...further processing

                    } else {
                        showNetworkScreen(getView(), "No Products available", false);
                    }
                }

                @Override
                public void error(String message, String URL) {
                    showNetworkScreen(getView(), "No Network Available", true);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}