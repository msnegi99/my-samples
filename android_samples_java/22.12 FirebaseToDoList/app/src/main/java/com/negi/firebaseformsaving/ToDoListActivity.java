package com.negi.firebaseformsaving;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class ToDoListActivity extends AppCompatActivity  implements FragmentCommunicationInterface
{
    Button addTodoBtn;
    SynchronizedToDoItemArray mSynchronizedToDoItemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        Firebase.setAndroidContext(this);

        mSynchronizedToDoItemArray = new SynchronizedToDoItemArray();

        addTodoBtn = (Button) findViewById(R.id.addToDoBtn);
        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(getLayoutInflater(), ToDoListActivity.this);
            }
        });

        ToDoItemFragment toDoItemFragment;
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, toDoItemFragment = new ToDoItemFragment())
                    .commit();
            mSynchronizedToDoItemArray.setToDoItemFragment(toDoItemFragment);
        }
    }

    private void createDialog(LayoutInflater inflater, final Activity activity)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Your Next ToDo");

        final View dialogView = inflater.inflate(R.layout.dialog_add_todo, null, false);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.addTodo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText todoEditText = (EditText)dialogView.findViewById(R.id.todoEditText);
                mSynchronizedToDoItemArray.addTodo(new ToDoItem(todoEditText.getText().toString()));
            }
        });

        builder.setNegativeButton(R.string.cancleTodoAdd, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Fragment Communication Interface
    public boolean addToDo(ToDoItem toDoItem)
    {
        mSynchronizedToDoItemArray.addTodo(toDoItem);
        //updateToDoFragmentUI();
        return true;
    }

    public SynchronizedToDoItemArray getToDoItemArray(){
        return mSynchronizedToDoItemArray;
    }
}
