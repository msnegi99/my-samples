package com.msnegi.recyclerviewheaderfooter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class Recycler_View_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Data> list = Collections.emptyList();
    Context context;

    public Recycler_View_Adapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Data.HEADER_TYPE:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_header, parent, false);
                View_Holder holder1 = new View_Holder(v1);
                return holder1;
            case Data.FOOTER_TYPE:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_footer, parent, false);
                View_Holder holder2 = new View_Holder(v2);
                return holder2;
            case Data.ITEM_TYPE:
                View v3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
                View_Holder holder3 = new View_Holder(v3);
                return holder3;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (list.get(position).type) {
            case 0:
                return Data.HEADER_TYPE;
            case 1:
                return Data.FOOTER_TYPE;
            case 2:
                return Data.ITEM_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Data object = list.get(position);
        if (object != null)
        {
            switch (object.type)
            {
                case Data.HEADER_TYPE:
                    ((View_Holder) holder).title.setText(list.get(position).title);
                    //((View_Holder) holder).description.setText(list.get(position).description);
                    //((View_Holder) holder).imageView.setImageResource(list.get(position).imageId);
                    break;

                case Data.FOOTER_TYPE:
                    ((View_Holder) holder).title.setText(list.get(position).title);
                    //((View_Holder) holder).description.setText(list.get(position).description);
                    //((View_Holder) holder).imageView.setImageResource(list.get(position).imageId);
                    break;

                case Data.ITEM_TYPE:
                    ((View_Holder) holder).title.setText(list.get(position).title);
                    ((View_Holder) holder).description.setText(list.get(position).description);
                    ((View_Holder) holder).imageView.setImageResource(list.get(position).imageId);
                    break;
            }
        }

        //animate(holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Data data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    public void remove(Data data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout base;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.base = (LinearLayout) itemView;
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout base;
        public FooterViewHolder(View itemView) {
            super(itemView);
            this.base = (LinearLayout) itemView;
        }
    }

    public class View_Holder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView title;
        TextView description;
        ImageView imageView;

        public View_Holder(View itemView)
        {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }
}


