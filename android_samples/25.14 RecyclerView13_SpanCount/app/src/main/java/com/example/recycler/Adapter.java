package com.example.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Integer> viewType;
    Context context;

    public Adapter(@NonNull Context context, ArrayList<Integer> viewType) {
        this.context = context;
        this.viewType = viewType;
    }
    @Override
    public int getItemViewType(int position) {
        return viewType.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if(viewType==3){
            // gradient view type
            return new viewHolderDetail_(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_view_gradient, viewGroup,false));
        }else if (viewType == 2){
            // detail view type
            return new viewHolderDetail(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_view_detail, viewGroup,false));
        }else if (viewType ==1){
            // only image view type
            return new viewHolder1(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_view, viewGroup,false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.e( "onCreateViewHolder: ",viewHolder.getItemViewType() +" "+i);
        if(viewHolder.getItemViewType()==3){
            viewHolderDetail_ vb = (viewHolderDetail_) viewHolder;
           if(i/6 == 0 ){
               Picasso.get().load("https://images.pexels.com/photos/9250/green-attraction-war-museum.jpg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vb.show_view);
           }else if(i/6 ==1){
               Picasso.get().load("https://images.pexels.com/photos/886454/pexels-photo-886454.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vb.show_view);
           }else {
               Picasso.get().load("https://images.pexels.com/photos/274743/pexels-photo-274743.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vb.show_view);
           }

        }else if (viewHolder.getItemViewType() == 2){
            viewHolderDetail vh = (viewHolderDetail) viewHolder;
           if(i%2==0){
               Picasso.get().load("https://images.pexels.com/photos/1164985/pexels-photo-1164985.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vh.image_detail);
               Picasso.get().load("https://images.pexels.com/photos/2584297/pexels-photo-2584297.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vh.face);
           }else {
               Picasso.get().load("https://images.pexels.com/photos/70365/forest-sunbeams-trees-sunlight-70365.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vh.image_detail);
               Picasso.get().load("https://images.pexels.com/photos/1452954/pexels-photo-1452954.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vh.face);
           }
        }else if(viewHolder.getItemViewType() == 1){
            viewHolder1 vf = (viewHolder1) viewHolder;
           if(i%3==0){
               Picasso.get().load("https://images.pexels.com/photos/577514/pexels-photo-577514.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vf.idess);
           }else if(i%3==2){
               Picasso.get().load("https://images.pexels.com/photos/372490/pexels-photo-372490.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vf.idess);
           }else {
               Picasso.get().load("https://images.pexels.com/photos/1531677/pexels-photo-1531677.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").into(vf.idess);
           }
        }
    }

    @Override
    public int getItemCount() {
        return 24;
    }
    public class viewHolder1 extends RecyclerView.ViewHolder{
        ImageView idess;
        public viewHolder1(@NonNull View itemView) {
            super(itemView);
            idess = itemView.findViewById(R.id.only);
        }
    }



    public class viewHolderDetail_ extends RecyclerView.ViewHolder{
        ImageView show_view;
        public viewHolderDetail_(@NonNull View itemView) {
            super(itemView);
            show_view = itemView.findViewById(R.id.show_view);
        }
    }

    public class viewHolderDetail extends RecyclerView.ViewHolder{
       ImageView face,image_detail;
        public viewHolderDetail(@NonNull View itemView) {
            super(itemView);
            image_detail = itemView.findViewById(R.id.image_detail);
            face = itemView.findViewById(R.id.face);
        }
    }

}
