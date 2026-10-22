package com.mnegi.todolistmvvm.model;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.mnegi.todolistmvvm.db.SQLiteHandler;
import com.mnegi.todolistmvvm.pojo.TaskItem;
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
        this.toDoList = SQLiteHandler.getInstance(context).getTaskList();
        mutableToDoItems.setValue(this.toDoList);
        Log.i(TAG," mutableToDoItems reference: "+mutableToDoItems);
        return mutableToDoItems;
    }

    @Override
    public void addToDoItem(String title, String description){
        boolean addSuccess = SQLiteHandler.getInstance(context).insert(title,description);
        if (!addSuccess){
            //throw new Exception("Some thing went wrong!!!");
        }else{
            toDoList = SQLiteHandler.getInstance(context).getTaskList();
            mutableToDoItems.setValue(toDoList);
        }
    }

    @Override
    public void removeToDoItem(int id){
        boolean deleteSuccess = SQLiteHandler.getInstance(context).deleteTask(id);
        if(!deleteSuccess){
            //throw new ToDoNotFoundException("Id is wrong");
        }else {
            this.toDoMutableLiveData.setValue(null);
            this.toDoList = SQLiteHandler.getInstance(context).getTaskList();
            this.mutableToDoItems.setValue(this.toDoList);
        }
    }

    public MutableLiveData<TaskItem> getToDo(int id){
        TaskItem toDo = null;
        for(TaskItem toDo1: mutableToDoItems.getValue()){
            if(toDo1.getID()==id){
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
