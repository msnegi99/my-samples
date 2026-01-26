package com.msnegi.websearchvolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msnegi.websearchvolley.tools.Tools;
import com.msnegi.websearchvolley.views.CustomTextView;

import java.util.ArrayList;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<T> list = new ArrayList<>();
    private String type;
    private LayoutInflater inflater;
    private ItemOnClick itemOnClick;
    private String listType = "grid";

    //private InternetStatusReceiver iReceiver = new InternetStatusReceiver();
    private String internetStatus = "";

    public RecyclerAdapter(Context context, ArrayList<T> list, String type, ItemOnClick itemOnClick) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.itemOnClick = itemOnClick;

        inflater = LayoutInflater.from(context);
    }

    public void setListType(String listType){
        this.listType = listType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (type){
            case "search":
                return new SearchProductViewHolder(inflater.inflate(R.layout.view_search_item, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (type){

            case "search":
                //region Search Products
            {
                final SearchItem searchItem = (SearchItem) list.get(i);
                ((SearchProductViewHolder) viewHolder).bindView(searchItem);

                ((SearchProductViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemOnClick != null)
                            itemOnClick.OnItemClick(searchItem, i, 0);

                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                });
                ((SearchProductViewHolder) viewHolder).itemView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Tools.dpToPx(context, 40)));
            }
            //endregion
            break;

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class SearchProductViewHolder extends RecyclerView.ViewHolder{
        private CustomTextView txtTitle, txtItems;

        public SearchProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtItems = itemView.findViewById(R.id.txtItems);
        }

        public void bindView(SearchItem searchItem){
            txtTitle.setText(searchItem.getTitle());
            txtItems.setText(searchItem.getNum_result() + " Items");
        }
    }


}
