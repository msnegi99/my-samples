package com.example.mvvmroomexample.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.example.mvvmroomexample.pojo.TaskItem;
import com.example.mvvmroomexample.roomdb.DatabaseClient;

import java.util.ArrayList;
import java.util.List;

public class ModelImplementor implements Model {

    private static final String TAG = "ModelImplementor";

    private MutableLiveData<List<TaskItem>> mutableToDoItems;
    private MutableLiveData<TaskItem> toDoMutableLiveData;

    private List<TaskItem> toDoList;
    Context context;

    public ModelImplementor(Context context){
        this.context = context;
        this.mutableToDoItems = new MutableLiveData<>();
        this.mutableToDoItems.setValue(new ArrayList<TaskItem>());
    }

    @Override
    public MutableLiveData<List<TaskItem>> getAllToDos(){
        /*this.toDoList = SQLiteHandler.getInstance(context).getTaskList(); */
        this.toDoList = DatabaseClient
                .getInstance(context)
                .getAppDatabase()
                .taskDao()
                .getAll();
        mutableToDoItems.setValue(this.toDoList);
        Log.i(TAG," mutableToDoItems reference: "+mutableToDoItems);
        return mutableToDoItems;
    }

    @Override
    public void addToDoItem(String title, String description){
        /*boolean addSuccess = SQLiteHandler.getInstance(context).insert(title,description);
        if (!addSuccess){
            //throw new Exception("Some thing went wrong!!!");
        }else{
            toDoList = SQLiteHandler.getInstance(context).getTaskList();
            mutableToDoItems.setValue(toDoList);
        }*/

        TaskItem taskItem1 = DatabaseClient.getInstance(context).getAppDatabase()
                .taskDao()
                .findTaskByTitle(title);

        if(taskItem1 != null) {
            //update
            //creating a task
            TaskItem taskItem = new TaskItem();
            taskItem.setId(taskItem1.getId());
            taskItem.setTaskName(title);
            taskItem.setTaskDescription(description);

            //adding to database
            DatabaseClient.getInstance(context).getAppDatabase()
                    .taskDao()
                    .update(taskItem);

        }else {
            //insert
            //creating a task
            TaskItem taskItem = new TaskItem();
            taskItem.setTaskName(title);
            taskItem.setTaskDescription(description);

            //adding to database
            DatabaseClient.getInstance(context).getAppDatabase()
                    .taskDao()
                    .insert(taskItem);
        }

    }

    @Override
    public void removeToDoItem(TaskItem taskItem){
        /*boolean deleteSuccess = SQLiteHandler.getInstance(context).deleteTask(id);
        if(!deleteSuccess){
            //throw new ToDoNotFoundException("Id is wrong");
        }else {
            this.toDoMutableLiveData.setValue(null);
            this.toDoList = SQLiteHandler.getInstance(context).getTaskList();
            this.mutableToDoItems.setValue(this.toDoList);
        }*/

        DatabaseClient.getInstance(context).getAppDatabase()
                .taskDao()
                .delete(taskItem);
    }

    public MutableLiveData<TaskItem> getToDo(int id){
        TaskItem toDo = null;
        for(TaskItem toDo1: mutableToDoItems.getValue()){
            if(toDo1.getId()==id){
                toDo = toDo1;
                break;
            }
        }
        if(toDo==null){
            //throw new ToDoNotFoundException("Id is wrong");
        }
        toDoMutableLiveData = new MutableLiveData<>(toDo);
        return this.toDoMutableLiveData;
    }
}
