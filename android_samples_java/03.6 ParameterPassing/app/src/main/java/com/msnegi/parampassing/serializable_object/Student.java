package com.msnegi.parampassing.serializable_object;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private int age;
    private boolean result;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age=age;
    }

    public boolean getResult(){
        return result;
    }

    public void setResult(boolean result){
        this.result = result;
    }
}
