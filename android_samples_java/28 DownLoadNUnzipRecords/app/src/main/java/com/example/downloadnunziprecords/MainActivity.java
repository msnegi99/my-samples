package com.example.downloadnunziprecords;

import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<IPGMasterDetailsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fillBtn = findViewById(R.id.fillBtn);
        fillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readZipFile();
            }
        });
    }

    private void readZipFile() {
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/Android/media/" + getPackageName() + "/");
            if (!myDir.exists())
                myDir.mkdirs();
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/Android/media/" + getPackageName() + "/", "IPGMASTER.txt");
            String apkFile = getPackageManager().getApplicationInfo(getPackageName(), 0).sourceDir;
            ZipFile zipFile = new ZipFile(apkFile);
            ZipEntry entry = zipFile.getEntry("assets/pwdata/IPGMaster.zip");
            unzip(zipFile.getInputStream(entry), file);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private void unzip(InputStream stream, File f) throws Exception {
        //if (f.exists() && !f.isDirectory()) {
        byte[] buffer = new byte[2024 * 4];
        ZipInputStream zin = new ZipInputStream(stream);
        while (zin.getNextEntry() != null) {
            FileOutputStream fout = new FileOutputStream(f.getAbsolutePath());
            int count;
            while ((count = zin.read(buffer)) != -1) {
                fout.write(buffer, 0, count);
            }
            zin.closeEntry();
            fout.close();
        }
        zin.close();
        readFile(f.getAbsolutePath());
        // }

    }

    private void readFile(String absolutePath) {
        BufferedReader br = null;
        list = new ArrayList<>();
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(absolutePath));
            StringBuilder builder = new StringBuilder();
            while ((sCurrentLine = br.readLine()) != null) {

                builder.append(sCurrentLine);
            }

            JSONObject jsonObject = new JSONObject(builder.toString());
            if (jsonObject.getBoolean("IsSuccess")) {
                JSONArray jsonArray = jsonObject.getJSONArray("Results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    IPGMasterDetailsModel iPGMasterDetails = new IPGMasterDetailsModel();
                    iPGMasterDetails.setIsActive(jsonObject1.getBoolean("IsActive"));
                    iPGMasterDetails.setCityCode(jsonObject1.getString("CityCode"));
                    iPGMasterDetails.setCityName(jsonObject1.getString("CityName"));
                    iPGMasterDetails.setDistrictCode(jsonObject1.getString("DistrictCode"));
                    iPGMasterDetails.setDistrictName(jsonObject1.getString("DistrictName"));
                    iPGMasterDetails.setIPGMasterID(jsonObject1.getInt("IPGMasterID"));
                    iPGMasterDetails.setPinCode(jsonObject1.getString("PinCode"));
                    iPGMasterDetails.setRegionCode(jsonObject1.getString("RegionCode"));
                    iPGMasterDetails.setRegionName(jsonObject1.getString("RegionName"));
                    iPGMasterDetails.setStateCode(jsonObject1.getString("StateCode"));
                    iPGMasterDetails.setStateName(jsonObject1.getString("StateName"));
                    iPGMasterDetails.setSo(jsonObject1.getString("SO"));
                    list.add(iPGMasterDetails);
                }

                recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(list, this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false));

                SpacesItemDecoration decoration = new SpacesItemDecoration(16);
                recyclerView.addItemDecoration(decoration);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
