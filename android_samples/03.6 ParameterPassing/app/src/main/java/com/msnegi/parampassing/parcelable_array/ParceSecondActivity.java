package com.msnegi.parampassing.parcelable_array;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.msnegi.parampassing.R;
import java.util.ArrayList;

import android.widget.TextView;

public class ParceSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parce_second);

        String output = "";
        try
        {
            // Get the Bundle Object
            Bundle bundle = getIntent().getExtras();

            // Get ArrayList Bundle
            ArrayList<Student> aList = (ArrayList<Student>) bundle.getSerializable("key");
            for(int i = 0; i < aList.size(); i++)
            {
                Student obj = aList.get(i);
                output += "id : " + obj.id + " Name : " + obj.name + "\n";
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        TextView details = (TextView) findViewById(R.id.detials);
        details.setText(output);
    }
}