package com.msnegi.parampassing.parcelable_array;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Intent;
import android.view.View;

import com.msnegi.parampassing.R;

public class ParceFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parce_first);

        // Create custom class Object
        Student s1 = new Student();
        s1.id = 1;
        s1.name = "mahender";

        Student s2 = new Student();
        s2.id = 2;
        s2.name = "ram";

        // Create Array List of custom Class and add the Created object
        ArrayList<Student> aList = new ArrayList<Student>();
        aList.add(s1);
        aList.add(s2);

        findViewById(R.id.callBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Bundle and Put Bundle in to it
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", aList);

                // Put Bundle in to Intent and call start Activity
                // Intent Creation and Initialization
                Intent i = new Intent(ParceFirstActivity.this, ParceSecondActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

    }
}