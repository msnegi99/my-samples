package com.example.roomdbexample.roomdb;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
 
    @Query("SELECT * FROM TaskItem")
    List<TaskItem> getAll();
 
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskItem task);
 
    @Delete
    void delete(TaskItem task);
 
    @Update
    void update(TaskItem task);

    @Insert(onConflict = REPLACE)
    void save(TaskItem user);

    @Query("SELECT * FROM TaskItem WHERE taskName = :title")
    TaskItem findTaskByTitle(String title);

}