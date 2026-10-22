package com.example.mvvmroomexample.model;

import androidx.lifecycle.MutableLiveData;

import com.example.mvvmroomexample.pojo.TaskItem;

import java.util.List;

public interface Model {
    MutableLiveData<List<TaskItem>> getAllToDos();
    MutableLiveData<TaskItem> getToDo(int id);

    void addToDoItem(String toDoItem, String place);
    void removeToDoItem(TaskItem taskItem);
}
