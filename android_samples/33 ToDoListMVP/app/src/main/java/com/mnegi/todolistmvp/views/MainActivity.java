package com.mnegi.todolistmvp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mnegi.todolistmvp.R;
import com.mnegi.todolistmvp.pojo.TaskItem;
import com.mnegi.todolistmvp.presenter.MainActivityPresenterImp;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View, CallBackInterface {

    MainActivityContract.Presenter presenter;
    RecyclerView recyclerView;
    AppCompatEditText titletxt, descriptiontxt;
    AppCompatButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenterImp(this);

        titletxt = findViewById(R.id.titletxt);
        descriptiontxt = findViewById(R.id.descriptiontxt);
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.insertRecordUpdateView(titletxt.getText().toString(), descriptiontxt.getText().toString());
            }
        });

        recyclerView = findViewById(R.id.recyclerview);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void updateViewOnAdd(List<TaskItem> taskItemArr) {
        showAllToDos(taskItemArr);
        clearEditTexts();
    }

    @Override
    public void showAllToDos(List<TaskItem> taskItemsArr) {

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(taskItemsArr, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

//        SpacesItemDecoration decoration = new SpacesItemDecoration(15);
//        recyclerView.addItemDecoration(decoration);
    }

    private void clearEditTexts(){
        titletxt.setText("");
        descriptiontxt.setText("");
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void editItem(int id) {             //-- called from adapter through callback
        presenter.editTask(id);
    }

    @Override
    public void deleteItem(int id) {           //-- called from adapter through callback
        presenter.deleteTask(id);
    }

    @Override
    public void updateViewForEdit(TaskItem taskItem) {
        titletxt.setText(taskItem.getTaskName());
        descriptiontxt.setText(taskItem.getTaskDescription());
    }
}