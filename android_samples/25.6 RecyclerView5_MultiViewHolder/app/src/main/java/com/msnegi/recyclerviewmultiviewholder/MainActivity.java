package com.msnegi.recyclerviewmultiviewholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Section> list =  new ArrayList<Section>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            JSONObject jsonObject = new JSONObject(readHomePageData());
            JSONArray jsonArray = jsonObject.getJSONArray("Data");
            for (int j=0; j<jsonArray.length(); j++) {
                list.add(Section.build(jsonArray.getJSONObject(j)));
            }
        } catch (JSONException e) {
        }

        recyclerView = findViewById(R.id.recyclerview);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, "home", list);
        recyclerView.setAdapter(adapter);
        //recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

    }

    public String readHomePageData() {
        try {
            AssetManager assets = getResources().getAssets();
            InputStream is=assets.open("homepage.json");
            byte[] bytes=new byte[is.available()];
            is.read(bytes);
            return new String(bytes,"UTF8");
        }
        catch (  Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
