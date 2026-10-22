package com.example.roomdbexample;

import com.example.roomdbexample.roomdb.TaskItem;

public interface CallBackInterface {
    void editItem(TaskItem taskItem);
    void deleteItem(TaskItem taskItem);
}
