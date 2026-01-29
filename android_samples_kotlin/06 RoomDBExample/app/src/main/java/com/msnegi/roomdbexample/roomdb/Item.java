package com.msnegi.roomdbexample.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item")
public class Item {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")   // order id
    private int id;

    @ColumnInfo(name = "item_id")
    private String item_id;

    @NonNull
    @ColumnInfo(name = "descripton")
    private String descripton;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public String getDescripton() {
        return descripton;
    }

}
