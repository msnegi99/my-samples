package com.example.roomdbexample.roomdb;

import android.app.Application;
import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.taskDao();
    }

    public List<TaskItem> getAll() { return taskDao.getAll(); }

    public void insert(TaskItem task) {
        taskDao.insert(task);
    }

    public void delete(TaskItem task) {
        taskDao.delete(task);
    }

    public void update(TaskItem task) {
        taskDao.update(task);
    }

    public TaskItem findTaskByTitle(String title){ return taskDao.findTaskByTitle(title); }

}