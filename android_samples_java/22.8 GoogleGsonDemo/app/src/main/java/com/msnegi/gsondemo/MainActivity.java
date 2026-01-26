package com.msnegi.gsondemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gson gson = new Gson();

        //Address address = new Address("India", "New Delhi");

        //Gson gson = new Gson();

        /*List<FamilyMember> family = new ArrayList<>();
        family.add(new FamilyMember("wife", 30));
        family.add(new FamilyMember("Daughter", 5));

        String json = gson.toJson(family);*/

        //Employee employee = new Employee("John", 30, "john@gmail.com", address, family);
        //String json = gson.toJson(family);

        /*List<FamilyMember> family = new ArrayList<>();
        family.add(new FamilyMember("wife", 30));
        family.add(new FamilyMember("Daughter", 5));

        String json = gson.toJson(family);*/

        /*Gson gson = new Gson();

        String json = "[{\"age\":30,\"role\":\"wife\"},{\"age\":5,\"role\":\"Daughter\"}]";
        FamilyMember[] family = gson.fromJson(json, FamilyMember[].class);*/

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        String json = "{\"age\":30,\"firstName\":\"John\",\"mail\":\"john@mail.com\",\"password\":\"adfsa\"}";
        Employee employee1 = gson.fromJson(json, Employee.class);

        /*Employee employee = new Employee("John",30,"john@mail.com", "adfsa");
        String jsonResult = gson.toJson(employee);*/

        /*String json = "[{\"age\":30,\"role\":\"wife\"},{\"age\":5,\"role\":\"Daughter\"}]";
        Type familyType = new TypeToken<ArrayList<FamilyMember>>(){}.getType();
        ArrayList<FamilyMember> family = gson.fromJson(json, familyType);*/
    }
}
