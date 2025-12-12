package com.msnegi.nestedrecyclerview;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.msnegi.nestedrecyclerview.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//import butterknife.ButterKnife;
//import butterknife.Unbinder;

public class RecyclerViewNestedFragment extends Fragment {

    public static final String FOR_APPOINTMENT = "for_appointment";

    public TextView select_hosp_page_hint;
    public EditText et_search;
    public RecyclerView rv_speciality;
    List<SpecializationData> specializationDataList = new ArrayList<>();
    SpecialityAdapter specialityAdapter;

    String isFrom = "for_speciality";
    int selectedHospitalIdToPass = 0;
    String selectedHospToPass = "";

    //Unbinder unbinder;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recycler_view_nested, container, false);


        //unbinder = ButterKnife.bind(this,view);

        select_hosp_page_hint = view.findViewById(R.id.select_hosp_page_hint);
        et_search = view.findViewById(R.id.et_search);
        rv_speciality = view.findViewById(R.id.rv_speciality);

        setUpRecyclerView();
        loadSpecialityList();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence searchText, int start, int before, int count) {
                List<SpecializationData> specializationListDataTemp = new ArrayList<SpecializationData>();

                Pattern pattern = Pattern.compile("(?i)" + searchText.toString());
                for (int i = 0; i < specializationDataList.size(); i++) {
                    if(pattern.matcher(specializationDataList.get(i).getSpecialisationName()).find()){
                        specializationListDataTemp.add(specializationDataList.get(i));
                    }else {
                        List<DoctorListData> doctorList = new ArrayList<>();

                        for (int j = 0; j < specializationDataList.get(i).getDoctorList().size(); j++) {
                            if (pattern.matcher(specializationDataList.get(i).getDoctorList().get(j).getDoctorName()).find()) {
                                doctorList.add(specializationDataList.get(i).getDoctorList().get(j));
                            }
                        }

                        if(doctorList.size()>0) {
                            SpecializationData specializationData = new SpecializationData(
                                    specializationDataList.get(i).getSpecialisationId(),
                                    specializationDataList.get(i).getSpecialisationName(),
                                    specializationDataList.get(i).getSpecialisationUrl(),
                                    doctorList
                            );

                            specializationListDataTemp.add(specializationData);
                        }
                    }
                }

                specialityAdapter.updateData(specializationListDataTemp, isFrom, selectedHospitalIdToPass, selectedHospToPass);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    void setUpRecyclerView() {
        specialityAdapter = new SpecialityAdapter(rv_speciality);
        rv_speciality.setLayoutManager(new LinearLayoutManager(context));
        rv_speciality.setAdapter(specialityAdapter);
    }


    public void loadSpecialityList() {
        specializationDataList.clear();

        try {
            String jsonString = loadJSONFromAsset();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("Data");

            specializationDataList = new ArrayList<SpecializationData>();
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject obj = (JSONObject) jsonArray.get(i);
                Gson gson = new Gson();
                SpecializationData object = gson.fromJson(obj.toString(), SpecializationData.class);
                specializationDataList.add(object);
            }

            updateUi(specializationDataList);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    void updateUi(List<SpecializationData> specializationDataList) {
        if (specializationDataList != null && specializationDataList.size() > 0) {
            specialityAdapter.updateData(specializationDataList, isFrom, selectedHospitalIdToPass, selectedHospToPass);
            select_hosp_page_hint.setVisibility(View.GONE);
            rv_speciality.setVisibility(View.VISIBLE);
            rv_speciality.setLayoutManager(new LinearLayoutManager(context));
            rv_speciality.setAdapter(specialityAdapter);
        } else {
            select_hosp_page_hint.setVisibility(View.VISIBLE);
            select_hosp_page_hint.setText("Record Not Found");
            rv_speciality.setVisibility(View.GONE);
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("specialities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /*public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("speciality.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }*/

    //--for kotlin
    /*var obj = JSONObject(readJSONFromAsset())

    fun readJSONFromAsset(): String? {
        var json: String? = null
        try {
            val  inputStream:InputStream = assets.open("yourFile.json")
            json = inputStream.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        //unbinder.unbind();
    }
}