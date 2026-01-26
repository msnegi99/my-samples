package com.example.mvvmroomexample.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.mvvmroomexample.R;
import com.example.mvvmroomexample.pojo.TaskItem;
import com.example.mvvmroomexample.repo.Repository;
import com.example.mvvmroomexample.viewmodel.CommonViewModel;
import com.example.mvvmroomexample.viewmodel.CommonViewModelImplementor;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackInterface  {

    RecyclerView recyclerView;
    AppCompatEditText titletxt, descriptiontxt;
    AppCompatButton addBtn;

    private CommonViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        titletxt = findViewById(R.id.titletxt);
        descriptiontxt = findViewById(R.id.descriptiontxt);

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titletxt.getText().toString();
                String description = descriptiontxt.getText().toString();
                if(TextUtils.isEmpty(title) && TextUtils.isEmpty(description)){
                    Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }else{
                    viewModel.addToDo(title, description);
                    clearEditTexts();
                }
            }
        });

        viewModel = ViewModelProviders.of(this).get(CommonViewModelImplementor.class);
        getLifecycle().addObserver(viewModel);

        viewModel.getToDoList().observe(this, new Observer<List<TaskItem>>() {
            @Override
            public void onChanged(List<TaskItem> toDos) {
                updateViewOnAdd(toDos);
            }
        });

        viewModel.getTaskItem().observe(this, new Observer<TaskItem>() {
            @Override
            public void onChanged(TaskItem taskItem) {
                titletxt.setText(taskItem.getTaskName());
                descriptiontxt.setText(taskItem.getTaskDescription());
            }
        });

        viewModel.getErrorStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this,s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void updateViewOnAdd(List<TaskItem> taskItemArr) {
        if(taskItemArr != null) {
            showAllToDos(taskItemArr);
            clearEditTexts();
        }else{
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_LONG).show();
        }
    }

    public void showAllToDos(List<TaskItem> taskItemsArr) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(taskItemsArr, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }

    private void clearEditTexts(){
        titletxt.setText("");
        descriptiontxt.setText("");
    }

    @Override
    public void editItem(TaskItem taskItem) {             //-- called from adapter through callback
        viewModel.editTask(taskItem);
    }

    @Override
    public void deleteItem(TaskItem taskItem) {           //-- called from adapter through callback
        viewModel.removeToDo(taskItem);
    }

    public void updateViewForEdit(TaskItem taskItem) {
        titletxt.setText(taskItem.getTaskName());
        descriptiontxt.setText(taskItem.getTaskDescription());
    }
}