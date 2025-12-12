package com.mnegi.todolistmvp.presenter;

import com.mnegi.todolistmvp.views.MainActivityContract;
import com.mnegi.todolistmvp.MyApplication;
import com.mnegi.todolistmvp.model.Model;
import com.mnegi.todolistmvp.pojo.TaskItem;

public class MainActivityPresenterImp implements MainActivityContract.Presenter {

    MainActivityContract.View view;
    Model model;

    public MainActivityPresenterImp(MainActivityContract.View view){
        this.view = view;
        this.model = MyApplication.getModel();
    }

    @Override
    public void start() {
        try{
            this.view.showAllToDos(model.getTaskList());
        }catch (Exception e){
            view.showError(e.getMessage());
        }
    }

    @Override
    public void insertRecordUpdateView(String title, String description) {
        try{
            boolean success = model.insert( title,  description);
            if(success){
                view.updateViewOnAdd(model.getTaskList());
            }
        }catch (Exception e){
            view.showError(e.getMessage());
        }
    }

    @Override
    public void editTask(int id) {
        try{
            TaskItem taskItem = model.getTask(id);
            if(taskItem.getID() != -1){
                view.updateViewForEdit(taskItem);
            }else{
                view.showError("Record Not Found");
            }
        }catch (Exception e){
            view.showError(e.getMessage());
        }
    }

    @Override
    public void deleteTask(int id) {
        try{
            boolean isTaskDeleted = model.deleteTask(id);
            if(isTaskDeleted){
                view.updateViewOnAdd(model.getTaskList());
            }else{
                view.showError("Record Not Deleted");
            }
        }catch (Exception e){
            view.showError(e.getMessage());
        }
    }

}
