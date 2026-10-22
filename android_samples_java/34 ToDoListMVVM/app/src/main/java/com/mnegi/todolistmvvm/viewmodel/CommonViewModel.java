package com.mnegi.todolistmvvm.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.OnLifecycleEvent;
import com.mnegi.todolistmvvm.pojo.TaskItem;
import java.util.List;

public interface CommonViewModel extends LifecycleObserver {

    LiveData<List<TaskItem>> getToDoList();
    LiveData<String> getErrorStatus();
    LiveData<TaskItem> getToDo(int id);
    LiveData<TaskItem> getTaskItem();

    void addToDo(String todoItem, String place);
    void removeToDo(int id);
    void editTask(int id);

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void refreshData();
}
