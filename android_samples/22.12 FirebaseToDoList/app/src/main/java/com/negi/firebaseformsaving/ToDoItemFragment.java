package com.negi.firebaseformsaving;

import android.os.Bundle;
import androidx.fragment.app.ListFragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoItemFragment extends ListFragment implements NotifierListener
{
    public static final String TAG = "ToDoItemFragment";
    private FragmentCommunicationInterface mFragmentCommunicationInterface;
    private SynchronizedToDoItemArray mSynchronizedToDoItemArray;
    public static boolean firstTimeLoad = true;

    public ToDoItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentCommunicationInterface = (ToDoListActivity)getActivity();

        mSynchronizedToDoItemArray = mFragmentCommunicationInterface.getToDoItemArray();
        updateListAdapter();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        ToDoItem todo = ((ToDoAdapter)getListAdapter()).getItem(position);
    }

    private class ToDoAdapter extends ArrayAdapter<ToDoItem>
    {
        public ToDoAdapter(ArrayList<ToDoItem> toDoItems){
            super(getActivity(),0,toDoItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.fragment_to_do_list,null);
            }

            ToDoItem toDoItem = getItem(position);

            TextView descriptionTextView = (TextView)convertView.findViewById(R.id.todoItemDescriptionTextView);
            TextView dateTextView = (TextView)convertView.findViewById(R.id.todoItemDateTextView);
            CheckBox doneCheckBox = (CheckBox)convertView.findViewById(R.id.todoItemCheckBox);

            descriptionTextView.setText(toDoItem.getDescription());
            dateTextView.setText(toDoItem.getTimestamp().toString());
            doneCheckBox.setChecked(toDoItem.isCompleted());

            return convertView;
        }
    }

    public void notifyChanges(){
        ((ArrayAdapter<ToDoItem>)getListAdapter()).notifyDataSetChanged();
    }

    public void updateListAdapter(){
        ToDoAdapter adapter = new ToDoAdapter(mSynchronizedToDoItemArray.getToDoItems());
        setListAdapter(adapter);
    }
}
