package com.example.mvvmroomexample.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.mvvmroomexample.pojo.TaskItem;

import java.util.List;

public interface CommonViewModel extends LifecycleObserver {

    LiveData<List<TaskItem>> getToDoList();
    LiveData<String> getErrorStatus();
    LiveData<TaskItem> getToDo(int id);
    LiveData<TaskItem> getTaskItem();

    void addToDo(String todoItem, String place);
    void removeToDo(TaskItem taskItem);
    void editTask(TaskItem taskItem);

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void refreshData();
}
