package com.example.bottomsortmenu;

public class Item {
    String country = "";
    int flag = 0;

    public Item(String country, int flag){
        this.country = country;
        this.flag = flag;
    }

    public void setcountry(String country){
        this.country = country;
    }

    public String getCountry(){
        return country;
    }

    public void setFlag(int flag){
        this.flag = flag;
    }

    public int getFlag(){
        return flag;
    }

}
