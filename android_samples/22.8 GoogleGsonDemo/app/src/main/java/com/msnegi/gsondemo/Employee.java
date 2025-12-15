package com.msnegi.gsondemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {
    @Expose                         //(serialize = true, deserialize = true)
    private String firstName;
    @Expose(serialize = false)     //default: true
    private int age;
    @Expose(deserialize = false)
    private String mail;

    private String password;      //(serialize = false, deserialize = false)

    public Employee(String firstName, int age, String mail, String password) {
        this.firstName = firstName;
        this.age = age;
        this.mail = mail;
        this.password = password;
    }
}