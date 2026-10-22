package com.mnegi.todolistmvp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mnegi.todolistmvp.pojo.TaskItem;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler implements DBConstants {

    private Context context;
    private SQLiteHelper helper;

    private static SQLiteHandler INSTANCE;

    public static SQLiteHandler getInstance(Context context) {
        if(INSTANCE == null){
            INSTANCE = new SQLiteHandler();
            INSTANCE.context = context;
            INSTANCE.helper = new SQLiteHelper(context, DBConstants.DB_NAME, null, DB_VERSION);
        }
        return INSTANCE;
    }

    private void CloseConnection(SQLiteDatabase database, Cursor cursor){
        if(cursor != null) cursor.close();
        if(database != null) database.close();
    }

    //-- add task
    public boolean insert(String title, String description){
        boolean success = false;
        SQLiteDatabase database = helper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(colTaskName, title);
            values.put(colTaskDescription, description);

            if(isRecordExists(database, title)){
                TaskItem taskItem = new TaskItem();
                taskItem.setID(getRecordId(database, title));
                taskItem.setTaskName(title);
                taskItem.setTaskDescription(description);

                if(updateTask(taskItem))   //rows affected
                    success = true;
                else
                    success = false;
            }else{
                if(database.insert(TO_DO_TABLE, null, values) > 0)   //rows affected
                    success = true;
                else
                    success = false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            CloseConnection(database, null);
        }

        return success;
    }

    public boolean isRecordExists(SQLiteDatabase database, String title) {
        Cursor cursor = null;
        boolean isExist = false;
        try {
            cursor = database.query(TO_DO_TABLE, null, colTaskName + "=?", new String[]{title}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                isExist = true;
            }
        }catch (Exception e){}
        finally {
            CloseConnection(null, cursor);
        }
        return isExist;
    }

    public int getRecordId(SQLiteDatabase database, String title){
        Cursor cursor = null;
        int _id = 0;
        try {
            cursor = database.query(TO_DO_TABLE, null, colTaskName + "=?", new String[]{title}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                _id = cursor.getInt(cursor.getColumnIndex(colID));
            }
        }catch (Exception e){}
        finally {
            CloseConnection(null, cursor);
        }
        return _id;
    }

    //-- read task
    public TaskItem getTask(int id){
        SQLiteDatabase database = helper.getReadableDatabase();
        TaskItem taskItem = new TaskItem();
        Cursor cursor = null;
        try {
            cursor = database.query(TO_DO_TABLE, null, colID + "=?", new String[]{String.valueOf(id)}, null, null, null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    taskItem.setID(cursor.getInt(cursor.getColumnIndexOrThrow(colID)));
                    taskItem.setTaskName(cursor.getString(cursor.getColumnIndexOrThrow(colTaskName)));
                    taskItem.setTaskDescription(cursor.getString(cursor.getColumnIndexOrThrow(colTaskDescription)));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            CloseConnection(database, cursor);
        }
        return taskItem;
    }

    //-- read all task
    public List<TaskItem> getTaskList(){
        SQLiteDatabase database = helper.getReadableDatabase();

        List<TaskItem> taskItemArr = new ArrayList<TaskItem>();
        Cursor cursor = null;
        try {
            cursor = database.query(TO_DO_TABLE, null, null, null, null, null, colID+" ASC");
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    TaskItem taskItem = new TaskItem();
                    taskItem.setID(cursor.getInt(cursor.getColumnIndexOrThrow(colID)));
                    taskItem.setTaskName(cursor.getString(cursor.getColumnIndexOrThrow(colTaskName)));
                    taskItem.setTaskDescription(cursor.getString(cursor.getColumnIndexOrThrow(colTaskDescription)));
                    taskItemArr.add(taskItem);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            CloseConnection(database, cursor);
        }
        return taskItemArr;
    }

    //-- update task
    public boolean updateTask(TaskItem taskItem){
        boolean success = false;
        SQLiteDatabase database = helper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(colID, taskItem.getID());
            values.put(colTaskName, taskItem.getTaskName());
            values.put(colTaskDescription, taskItem.getTaskDescription());

            if(database.update(TO_DO_TABLE, values, colID + " = ?", new String[]{String.valueOf(taskItem.getID())}) > 0)  //rows affected
                success = true;
            else
                success = false;
        }catch (Exception e){
            System.out.println("test");
        }finally {
            CloseConnection(database, null);
        }
        return success;
    }

    //-- delete task
    public boolean deleteTask(int id) {
        boolean success = false;
        SQLiteDatabase database = helper.getWritableDatabase();
        try {
            if(database.delete(TO_DO_TABLE, colID + "=?", new String[]{String.valueOf(id)}) > 0)   //rows affected
                success = true;
            else
                success = false;
        }catch (Exception e){
            System.out.println("test");
        }finally {
            CloseConnection(database, null);
        }

        return success;
    }

}