package com.msnegi.recyclerviewexpandable;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    HashSet<Integer> expandedPositionSet;
    ArrayList<ServiceStatus> statusList;
    Context context;

    public RecyclerViewAdapter(ArrayList<ServiceStatus> statusList, Context context)
    {
        this.statusList = statusList;
        this.context = context;
        expandedPositionSet = new HashSet<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_expandable, parent,false);
        View_Holder holder = new View_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((View_Holder) holder).title.setText(statusList.get(position).getRequestTitle());
        ((View_Holder) holder).description.setText(statusList.get(position).getRequestTitle() + "\n"
                + statusList.get(position).getFixableIssue() + "\n"
                + statusList.get(position).getActionTaken()
        );
        if(statusList.size()==1){
            ((View_Holder) holder).separator.setBackgroundColor(Color.WHITE);
        }

        if(position == statusList.size()-1){
            ((View_Holder) holder).separator.setBackgroundColor(Color.WHITE);
        }
        ((View_Holder) holder).position.setText(String.valueOf(position + 1));

        ((View_Holder) holder).updateItem(position);
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class View_Holder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView description;
        TextView position;
        LinearLayout separator;
        ExpandableLayout expandableLayout;
        ImageButton toggleDescriptionBtn;

        public View_Holder(View view)
        {
            super(view);
            expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);
            position = (TextView) view.findViewById(R.id.position);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            separator = (LinearLayout) view.findViewById(R.id.separator);
            toggleDescriptionBtn = (ImageButton) view.findViewById(R.id.toggleDescriptionBtn);

            toggleDescriptionBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });
        }

        private void updateItem(final int position) {

            expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position,toggleDescriptionBtn);
                }
            });
            expandableLayout.setExpand(expandedPositionSet.contains(position));
        }
    }

    private void registerExpand(int position,ImageButton toggleDescriptionBtn) {
        if (expandedPositionSet.contains(position)) {
            removeExpand(position);
            //--show down arraw btn
            toggleDescriptionBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_downward));
        } else {
            addExpand(position);
            //--show up arraw btn
            toggleDescriptionBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_upward));
        }
    }

    private void removeExpand(int position) {
        expandedPositionSet.remove(position);
    }

    private void addExpand(int position) {
        expandedPositionSet.add(position);
    }
}
