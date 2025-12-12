package com.msnegi.recyclerviewstate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    ArrayList<Data> statusList;
    Context context;

    public RecyclerViewAdapter(ArrayList<Data> statusList, Context context) {
        this.statusList = statusList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_state , parent,false);
        View_Holder holder = new View_Holder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(statusList.get(position).status) {
            ((View_Holder) holder).descriptionSwt.setChecked(true);
        }else{
            ((View_Holder) holder).descriptionSwt.setChecked(false);
        }
        ((View_Holder) holder).descriptionSwt.setId(position);
        ((View_Holder) holder).descriptionSwt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusList.get(((View_Holder) holder).descriptionSwt.getId()).status = ((View_Holder) holder).descriptionSwt.isChecked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class View_Holder extends RecyclerView.ViewHolder {
        SwitchCompat descriptionSwt;
        public View_Holder(View view)
        {
            super(view);
            descriptionSwt = (SwitchCompat) view.findViewById(R.id.descriptionSwt);
        }
    }
}
