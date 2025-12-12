package com.msnegi.recyclerviewpagewise;

public class Data {
    int itemID = 0;
    String title = "";
    String description = "";
    int imageId = 0;

    public Data(int itemID, String title, String description, int imageId){
        this.itemID = itemID;
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }

    public void setItemID(int itemID){
        this.itemID = itemID;
    }
    public int getItemID(){
        return itemID;
    }
}
