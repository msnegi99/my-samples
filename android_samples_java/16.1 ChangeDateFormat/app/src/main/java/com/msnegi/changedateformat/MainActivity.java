package com.msnegi.changedateformat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spnDateFormat;
    TextView txtDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnDateFormat = (Spinner) findViewById(R.id.spnDateFormat);
        txtDateFormat = (TextView) findViewById(R.id.txtDateFormat);

        List<String> list = new ArrayList<String>();
        list.add("dd-MMM-yyyy");
        list.add("yyyy-MMM-dd");
        list.add("yyyy-MM-dd");
        list.add("dd-MM-yyyy");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnDateFormat.setAdapter(dataAdapter);

        spnDateFormat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try{
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    SimpleDateFormat sdf = new SimpleDateFormat(selectedItem);

                    Calendar cal = Calendar.getInstance();
                    String today = sdf.format(cal.getTime());

                    txtDateFormat.setText(today);

                }catch(Exception pe){}
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

}
