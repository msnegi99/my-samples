package com.example.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class TextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Integer> viewType;
    Context context;
    public TextAdapter(@NonNull Context context, ArrayList<Integer> viewType) {
        this.context = context;
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(getItemViewType(i)==1){
            // gradient view type
            return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_view, viewGroup,false));
        }else if (getItemViewType(i) == 2){
            // detail view type
            return new viewHolderDetail(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_view_detail, viewGroup,false));
        }else if (getItemViewType(i) ==3){
            // only image view type
            return new viewHolderBig(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_view_gradient, viewGroup,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder.getItemViewType() == 3){
            viewHolderBig vb = (viewHolderBig) viewHolder;
            Picasso.get().load("https://images.pexels.com/photos/1113272/pexels-photo-1113272.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vb.show_view);
        }else if (viewHolder.getItemViewType() == 2){

        }
    }

    @Override
    public int getItemCount() {
        return 24;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
    TextView idess;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class viewHolderBig extends RecyclerView.ViewHolder{
        ImageView show_view;
        public viewHolderBig(@NonNull View itemView) {
            super(itemView);
            show_view = itemView.findViewById(R.id.show_view);
        }
    }

    public class viewHolderDetail extends RecyclerView.ViewHolder{
        TextView idess;
        public viewHolderDetail(@NonNull View itemView) {
            super(itemView);

        }
    }

}
