package com.example.checkboxdropdownexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CallbackInterface {

    Button btnShowValues;
    TextView txtValues;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_spinner);

        final String[] select_qualification = {
                "Select Qualification", "10th / Below", "12th", "Diploma", "UG",
                "PG", "Phd", "Doctor", "M Phill", "Metric Pass", "Nano Degree"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayList<StateVO> listVOs = new ArrayList<>();

        for (int i = 0; i < select_qualification.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, 0, listVOs);

        spinner.setAdapter(myAdapter);

        btnShowValues = findViewById(R.id.btnShowValues);
        btnShowValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = "";
                for(int i=0; i<myAdapter.listState.size(); i++){
                    if(myAdapter.listState.get(i).isSelected())
                        selected += myAdapter.listState.get(i).getTitle();
                }
                Toast.makeText(MainActivity.this,selected, Toast.LENGTH_SHORT).show();
            }
        });

        txtValues = findViewById(R.id.txtValues);
    }

    @Override
    public void callFunction(ArrayList<StateVO> listState) {
        String selected = "";
        for(int i=0; i<listState.size(); i++){
            if(listState.get(i).isSelected())
            selected += listState.get(i).getTitle();
        }
        txtValues.setText(selected);
    }
}