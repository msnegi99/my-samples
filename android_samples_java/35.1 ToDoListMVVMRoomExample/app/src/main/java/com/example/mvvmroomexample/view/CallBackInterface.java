package com.example.mvvmroomexample.view;

import com.example.mvvmroomexample.pojo.TaskItem;

public interface CallBackInterface {
    void editItem(TaskItem taskItem);
    void deleteItem(TaskItem taskItem);
}
