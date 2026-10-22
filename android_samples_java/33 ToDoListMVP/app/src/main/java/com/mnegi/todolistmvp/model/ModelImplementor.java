package com.mnegi.todolistmvp.model;

import android.content.Context;

import com.mnegi.todolistmvp.db.SQLiteHandler;
import com.mnegi.todolistmvp.pojo.TaskItem;

import java.util.ArrayList;
import java.util.List;

public class ModelImplementor implements Model
{
    Context context;

    public ModelImplementor(Context context){
        this.context = context;
    }

    @Override
    public List<TaskItem> getTaskList()  {
        return (ArrayList) SQLiteHandler.getInstance(context).getTaskList();
    }

    @Override
    public TaskItem getTask(int id)  {
        TaskItem taskItem = (TaskItem) SQLiteHandler.getInstance(context).getTask(id);
        return taskItem;
    }

    @Override
    public boolean insert(String title, String description)  {
        if(SQLiteHandler.getInstance(context).insert(title,description))
            return true;
        else
            return false;
    }

    @Override
    public boolean updateTask(TaskItem taskItem) {
        return false;
    }

    @Override
    public boolean deleteTask(int id)  {
        if(SQLiteHandler.getInstance(context).deleteTask(id))
            return true;
        else
            return false;
    }
}
