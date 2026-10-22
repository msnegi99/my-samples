package com.msnegi.recyclerviewsubmenu;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    ArrayList<Data> list;
    Context context;

    public RecyclerViewAdapter(ArrayList<Data> list, Context context)
    {
        this.list = list;
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

        ((View_Holder) holder).title.setText(list.get(position).title);
        ((View_Holder) holder).description.setText(list.get(position).title);
        ((View_Holder) holder).imageView.setImageResource(list.get(position).imageId);
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

        TextView buttonViewOption;

        public View_Holder(final View view)
        {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            imageView = (ImageView) view.findViewById(R.id.imageView);

            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            buttonViewOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, v );
                    popup.inflate(R.menu.options_menu);

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu1:
                                    //handle menu1 click
                                    break;
                                case R.id.menu2:
                                    //handle menu2 click
                                    break;
                                case R.id.menu3:
                                    //handle menu3 click
                                    break;
                            }
                            return false;
                        }
                    });

                    popup.show();
                }
            });
        }
    }

}
