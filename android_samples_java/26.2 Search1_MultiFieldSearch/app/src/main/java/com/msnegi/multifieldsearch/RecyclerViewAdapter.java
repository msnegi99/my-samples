package com.msnegi.multifieldsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    ArrayList<RowItem> listfiltered;
    ArrayList<RowItem> list;
    ArrayList<RowItem> backupList;
    Context context;

    public RecyclerViewAdapter(ArrayList<RowItem> list, Context context)
    {
        this.list = list;
        this.backupList = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout , parent,false);
        View_Holder holder = new View_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((View_Holder) holder).title.setText(list.get(position).getTitle());
        ((View_Holder) holder).description.setText(list.get(position).getDesc());
        ((View_Holder) holder).imageView.setImageResource(list.get(position).getImageId());
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
        ImageView imageView;

        public View_Holder(View view)
        {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    public void updateData(ArrayList<RowItem> rowItemsTemp){
        list.clear();
        list.addAll(rowItemsTemp);
        notifyDataSetChanged();
    }
}
