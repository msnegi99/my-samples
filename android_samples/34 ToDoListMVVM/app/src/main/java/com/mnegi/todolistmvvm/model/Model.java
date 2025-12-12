package com.mnegi.todolistmvvm.model;

import androidx.lifecycle.MutableLiveData;
import com.mnegi.todolistmvvm.pojo.TaskItem;
import java.util.List;

public interface Model {
    MutableLiveData<List<TaskItem>> getAllToDos();
    MutableLiveData<TaskItem> getToDo(int id);

    void addToDoItem(String toDoItem, String place);
    void removeToDoItem(int id);
}
