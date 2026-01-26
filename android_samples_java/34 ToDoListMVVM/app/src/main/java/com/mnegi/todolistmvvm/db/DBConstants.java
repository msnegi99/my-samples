package com.mnegi.todolistmvvm.db;

public interface DBConstants
{
    String DB_NAME = "ToDo_db";
    int DB_VERSION = 1;

    String TO_DO_TABLE = "tblToDo";

    String colID = "_id";
    String colTaskName = "TaskName";
    String colTaskDescription = "TaskDescription";

    String CREATE_TO_DO_TABLE = "CREATE TABLE " + TO_DO_TABLE+"( "
            + colID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
            + colTaskName + " varchar2(20),"
            + colTaskDescription + " varchar2(50))";

}

