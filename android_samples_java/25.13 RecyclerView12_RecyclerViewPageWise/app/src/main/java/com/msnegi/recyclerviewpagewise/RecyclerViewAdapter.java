package com.msnegi.recyclerviewpagewise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    ArrayList<Data> list;
    Context context;
    RecyclerViewCallBack recyclerViewCallBack;

    public RecyclerViewAdapter(ArrayList<Data> list, Context context, RecyclerViewCallBack recyclerViewCallBack)
    {
        this.list = list;
        this.context = context;
        this.recyclerViewCallBack = recyclerViewCallBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_linear , parent,false);
        View_Holder holder = new View_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ((View_Holder) holder).title.setText(list.get(position).title);
        ((View_Holder) holder).description.setText(list.get(position).title);
        ((View_Holder) holder).imageView.setImageResource(list.get(position).imageId);
        ((View_Holder) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewCallBack.onItemClicked(position);
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
        CardView cardView;
        TextView title;
        TextView description;
        ImageView imageView;

        public View_Holder(View view)
        {
            super(view);

            cardView = (CardView) view.findViewById(R.id.cardView);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    public void setAdapterArray(ArrayList<Data> searchResult){
        this.list = searchResult;
    }
}
