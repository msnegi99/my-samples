package com.example.roomdbexample.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { TaskItem.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();

}