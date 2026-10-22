package com.example.myanimatedaffects;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    ArrayList<DemoAnimation> list;
    Context context;
    CallBackInterface callBackInterface;

    public RecyclerViewAdapter(ArrayList<DemoAnimation> list, Context context, CallBackInterface callBackInterface)
    {
        this.list = list;
        this.context = context;
        this.callBackInterface = callBackInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_animation , parent,false);
        View_Holder holder = new View_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((View_Holder) holder).title.setText(list.get(position).title);
        ((View_Holder) holder).item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i < list.size(); i++){
                    list.get(i).selected = false;
                    ((View_Holder) holder).item.setBackgroundResource(R.drawable.round_button_background);
                    ((View_Holder) holder).title.setTextColor(context.getResources().getColor(R.color.colorAccent));
                }
                list.get(position).selected = true;
                ((View_Holder) holder).item.setBackgroundResource(R.drawable.round_button_background_filled);
                ((View_Holder) holder).title.setTextColor(Color.WHITE);

                notifyDataSetChanged();

                callBackInterface.callbackfunction(position+1);
            }
        });

        if(list.get(position).selected){
            ((View_Holder) holder).item.setBackgroundResource(R.drawable.round_button_background_filled);
            ((View_Holder) holder).title.setTextColor(Color.WHITE);
        }else{
            ((View_Holder) holder).item.setBackgroundResource(R.drawable.round_button_background);
            ((View_Holder) holder).title.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class View_Holder extends RecyclerView.ViewHolder {
        TextView title;
        LinearLayout item;

        public View_Holder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            item = (LinearLayout) view.findViewById(R.id.item);
        }
    }
}
