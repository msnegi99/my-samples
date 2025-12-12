package com.msnegi.basicnestedrecyclerview.adapter;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msnegi.basicnestedrecyclerview.R;
import com.msnegi.basicnestedrecyclerview.model.Outer;

import java.util.ArrayList;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class OuterAdapter extends RecyclerView.Adapter<OuterAdapter.ViewHolder> {

    private final RecyclerView.RecycledViewPool recycledViewPool;
    private Context context;
    private List<Outer> outerList;

    public OuterAdapter() {
        outerList = new ArrayList<>();
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    public void addOuter(Outer outer) {
        outerList.add(outer);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_outer_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rvOuter.setRecycledViewPool(recycledViewPool);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return outerList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategory;
        RecyclerView rvOuter;
        private InnerAdapter innerAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            tvCategory = itemView.findViewById(R.id.tv_outer_category);
            rvOuter = itemView.findViewById(R.id.rv_outer);
            setupRv();
        }

        private void setupRv() {
            rvOuter.setHasFixedSize(true);
            rvOuter.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            innerAdapter = new InnerAdapter();
            rvOuter.setAdapter(innerAdapter);
        }

        public void bind(int position) {
            Outer outer = outerList.get(position);
            if (outer != null) {
                tvCategory.setText(outer.getName());
                innerAdapter.addInner(outer.getGames());
            }
        }
    }
}
