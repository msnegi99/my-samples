package com.mnegi.todolistmvp.views;

import com.mnegi.todolistmvp.pojo.TaskItem;
import java.util.List;

public interface MainActivityContract {
    interface View {
        void showAllToDos(List<TaskItem> taskItemsArr);
        void updateViewOnAdd(List<TaskItem> taskItem);
        void showError(String errorMessage);
        void updateViewForEdit(TaskItem taskItem);
    }

    interface Presenter {
        void insertRecordUpdateView(String toDoItem, String place);
        void editTask(int id);
        void deleteTask(int id);
        void start();
    }
}
