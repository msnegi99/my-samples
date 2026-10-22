package com.example.roomdbexample;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roomdbexample.roomdb.TaskItem;
import com.example.roomdbexample.roomdb.TaskRepository;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackInterface  {

    RecyclerView recyclerView;
    AppCompatEditText titletxt, descriptiontxt;
    AppCompatButton addBtn;

    private List<TaskItem> toDoList;
    Context context;
    TaskRepository taskRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskRepository = new TaskRepository(getApplication());
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
                    TaskItem taskItem1 = taskRepository.findTaskByTitle(title);

                    if(taskItem1 != null) {
                        TaskItem taskItem = new TaskItem();
                        taskItem.setId(taskItem1.getId());
                        taskItem.setTaskName(title);
                        taskItem.setTaskDescription(description);

                        taskRepository.update(taskItem);

                    }else {
                        TaskItem taskItem = new TaskItem();
                        taskItem.setTaskName(title);
                        taskItem.setTaskDescription(description);

                        taskRepository.insert(taskItem);

                    }
                    clearEditTexts();

                    List<TaskItem> taskItemsArr = taskRepository.getAll();

                    showAllToDos(taskItemsArr);
                }
            }
        });

        List<TaskItem> taskItemsArr = taskRepository.getAll();

        showAllToDos(taskItemsArr);

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
        titletxt.setText(taskItem.getTaskName());
        descriptiontxt.setText(taskItem.getTaskDescription());
    }

    @Override
    public void deleteItem(TaskItem taskItem) {           //-- called from adapter through callback
        taskRepository.delete(taskItem);

        List<TaskItem> taskItemsArr = taskRepository.getAll();

        updateViewOnAdd(taskItemsArr);
    }
}