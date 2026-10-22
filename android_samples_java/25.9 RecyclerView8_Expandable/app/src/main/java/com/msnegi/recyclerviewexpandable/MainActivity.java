package com.msnegi.recyclerviewexpandable;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView = null;
    ArrayList<ServiceStatus> statusList = new ArrayList<ServiceStatus>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceStatus s1 = new ServiceStatus();
        s1.setRequestTitle("Request Title 1");
        s1.setRequestTitle("Fixable Issue 1");
        s1.setRequestTitle("Action Taken 1");
        statusList.add(s1);

        ServiceStatus s2 = new ServiceStatus();
        s2.setRequestTitle("Request Title 2");
        s2.setRequestTitle("Fixable Issue 2");
        s2.setRequestTitle("Action Taken 2");
        statusList.add(s2);

        ServiceStatus s3 = new ServiceStatus();
        s3.setRequestTitle("Request Title 3");
        s3.setRequestTitle("Fixable Issue 3");
        s3.setRequestTitle("Action Taken 3");
        statusList.add(s3);


        ServiceStatus s4 = new ServiceStatus();
        s4.setRequestTitle("Request Title 4");
        s4.setRequestTitle("Fixable Issue 4");
        s4.setRequestTitle("Action Taken 5");
        statusList.add(s4);

        ServiceStatus s5 = new ServiceStatus();
        s5.setRequestTitle("Request Title 5");
        s5.setRequestTitle("Fixable Issue 5");
        s5.setRequestTitle("Action Taken 5");
        statusList.add(s5);

        ServiceStatus s6 = new ServiceStatus();
        s6.setRequestTitle("Request Title 6");
        s6.setRequestTitle("Fixable Issue 6");
        s6.setRequestTitle("Action Taken 6");
        statusList.add(s6);

        recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(statusList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

    }
}
