package com.negi.firebaseformsaving;

public interface FragmentCommunicationInterface
{
    boolean addToDo(ToDoItem toDoItem);
    SynchronizedToDoItemArray getToDoItemArray();
}
