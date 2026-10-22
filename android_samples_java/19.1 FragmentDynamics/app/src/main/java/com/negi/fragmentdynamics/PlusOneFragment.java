package com.negi.fragmentdynamics;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PlusOneFragment extends Fragment {

    public EditText id;
    public Spinner type;
    public Button remove;
    public Button hide;

    Bundle bundle;
    int n = 0;

    String [] arr = {"Type A", "Type B", "Type C"};

    MyBroadcastRec rec;

    printComma pc;
    public interface printComma
    {
        public void printcomma();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        pc = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plus_one, container, false);

        bundle = getArguments();
        n = bundle.getInt("n");

        id = (EditText) view.findViewById(R.id.id);
        type = (Spinner) view.findViewById(R.id.type);
        remove = (Button) view.findViewById(R.id.remove);
        hide = (Button) view.findViewById(R.id.hide);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = (Fragment) getFragmentManager().findFragmentByTag("a" + n);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);  //does not work here
                transaction.remove(frg).commit();
            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = (Fragment) getFragmentManager().findFragmentByTag("a" + n);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);  //does not work with last fragment ???
                transaction.hide(frg).commit();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        type.setAdapter(adapter);

        rec = new MyBroadcastRec();
        getActivity().registerReceiver(rec,new IntentFilter("printlog"));

        return view;
    }

    class MyBroadcastRec extends BroadcastReceiver
    {
        public void onReceive(Context context, Intent intent)
        {
            System.out.println("{");
            System.out.println("\"id\":" + "\"" + id.getText().toString() + "\",");
            System.out.println("\"type\":" + "\"" + type.getSelectedItem().toString() + "\"");
            System.out.println("}");

            pc.printcomma();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().unregisterReceiver(rec);
    }
}
