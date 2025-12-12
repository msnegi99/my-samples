package com.example.mvvmroomexample.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
//import androidx.paging.LivePagedListBuilder;
//import androidx.paging.PagedList;

import com.example.mvvmroomexample.model.Model;
import com.example.mvvmroomexample.pojo.TaskItem;
import com.example.mvvmroomexample.MyApplication;
import com.example.mvvmroomexample.repo.Repository;

import java.util.ArrayList;
import java.util.List;

public class CommonViewModelImplementor extends ViewModel implements CommonViewModel {

    private static final String TAG = CommonViewModel.class.getSimpleName();
    private Model model;

    private MutableLiveData<List<TaskItem>> mutableToDoList;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<TaskItem> toDoMutableLiveData;
    private MutableLiveData<TaskItem> taskMutableLiveData;

    public CommonViewModelImplementor() {
        model = MyApplication.getToDosRepositoryImpl();
        mutableToDoList = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        toDoMutableLiveData = new MutableLiveData<>();
        taskMutableLiveData = new MutableLiveData<>();

        try{
            mutableToDoList = model.getAllToDos();
        }catch (Exception e){
            mutableToDoList.setValue(new ArrayList<>());
        }
    }

    public void addToDo(String todoItem, String place){
        try{
            model.addToDoItem(todoItem, place);
            mutableToDoList.setValue(model.getAllToDos().getValue());
        }catch (Exception e){
            errorMessage.setValue(e.getMessage());
        }
    }

    public void removeToDo(TaskItem taskItem){
        try{
            model.removeToDoItem(taskItem);
            toDoMutableLiveData.setValue(null);
            mutableToDoList = model.getAllToDos();
        }catch (Exception e){
            this.errorMessage.setValue(e.getMessage());
        }
    }

    public LiveData<List<TaskItem>> getToDoList(){
        return mutableToDoList;
    }

    public LiveData<TaskItem> getTaskItem(){
        return taskMutableLiveData;
    }

    public LiveData<String> getErrorStatus(){
        return errorMessage;
    }

    public LiveData<TaskItem> getToDo(int id){
        try{
            this.toDoMutableLiveData = model.getToDo(id);
        }catch (Exception e){
            this.errorMessage = new MutableLiveData<>(e.getMessage());
        }
        return this.toDoMutableLiveData;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void refreshData(){
        try{
            mutableToDoList.setValue(model.getAllToDos().getValue());
        }catch (Exception e){
            errorMessage.setValue(e.getMessage());
            mutableToDoList.setValue(new ArrayList<>());
        }
    }

    @Override
    public void editTask(TaskItem taskItem) {
        try{
            taskMutableLiveData.setValue(model.getToDo(taskItem.getId()).getValue());
        }catch (Exception e){
            this.errorMessage.setValue(e.getMessage());
        }
    }
}
