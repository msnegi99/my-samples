package com.mnegi.todolistmvp.model;

import android.content.Context;
import com.mnegi.todolistmvp.pojo.TaskItem;
import java.util.List;

public interface Model
{
    public List<TaskItem> getTaskList();
    public TaskItem getTask(int id);
    public boolean insert(String title, String description);
    public boolean updateTask(TaskItem taskItem);
    public boolean deleteTask(int id);
}
