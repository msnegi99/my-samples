package com.mnegi.todolistmvp.views;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mnegi.todolistmvp.R;
import com.mnegi.todolistmvp.pojo.TaskItem;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    List<TaskItem> list;
    CallBackInterface callBackInterface;

    public RecyclerViewAdapter(List<TaskItem> list, CallBackInterface callBackInterface) {
        this.list = list;
        this.callBackInterface = callBackInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rv_item , parent,false);
        View_Holder holder = new View_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((View_Holder) holder).title.setText(list.get(position).getTaskName());
        ((View_Holder) holder).description.setText(list.get(position).getTaskDescription());
        ((View_Holder) holder).editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.editItem(list.get(position).getID());
            }
        });
        ((View_Holder) holder).deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.deleteItem(list.get(position).getID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class View_Holder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView description;
        ImageView editBtn, deleteBtn;

        public View_Holder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titletxt);
            description = (TextView) view.findViewById(R.id.descriptiontxt);
            editBtn = (ImageView) view.findViewById(R.id.editBtn);
            deleteBtn = (ImageView) view.findViewById(R.id.deleteBtn);
        }
    }
}
