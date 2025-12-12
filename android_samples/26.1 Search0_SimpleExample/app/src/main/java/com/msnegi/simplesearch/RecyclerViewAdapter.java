package com.msnegi.simplesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements Filterable
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listfiltered = backupList;
                } else {
                    ArrayList<RowItem> filteredList = new ArrayList<>();
                    for (RowItem item : backupList) {
                        if (item.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    listfiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listfiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                list = (ArrayList<RowItem>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

}
