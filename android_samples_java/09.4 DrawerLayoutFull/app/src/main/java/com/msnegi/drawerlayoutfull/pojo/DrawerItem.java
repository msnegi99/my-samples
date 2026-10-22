package com.msnegi.drawerlayoutfull.pojo;

public class DrawerItem {
    private int id;
    private String name;
    private int imageRes;

    public int getId() {
        return id;
    }

    public DrawerItem setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DrawerItem setName(String name) {
        this.name = name;
        return this;
    }

    public int getImageRes() {
        return imageRes;
    }

    public DrawerItem setImageRes(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }
}
