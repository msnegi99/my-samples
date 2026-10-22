package com.example.mvvmroomexample.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.mvvmroomexample.pojo.TaskItem;
import java.util.List;
//import io.reactivex.rxjava3.core.Flowable;
import static androidx.room.OnConflictStrategy.REPLACE;

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

    /*@Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    Single<User> findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);*/

}