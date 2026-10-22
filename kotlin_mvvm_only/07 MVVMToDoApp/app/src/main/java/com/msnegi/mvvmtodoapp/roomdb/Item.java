package com.msnegi.mvvmtodoapp.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item")
public class Item {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "item_id")
    private String item_id;

    @NonNull
    @ColumnInfo(name = "descripton")
    private String descripton;

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
