package com.msnegi.recyclerviewmultiviewholder;

import android.content.Context;

import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.appcompat.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private String type;
    private T item;
    private List<T> list;
    //private ArrayList<T> list;
    private int sectionType;
    private SparseArray<Object> holders;

    public RecyclerViewAdapter(Context context, String type, List<T> list) {
        this.context = context;
        this.type = type;
        this.list = list;
        holders = new SparseArray<>();
    }

    public RecyclerViewAdapter(Context context, String type, List<T> list, int sectionType) {
        this.context = context;
        this.type = type;
        this.list = list;
        this.sectionType = sectionType;
        holders = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        switch (type) {
            case "home":
                switch (viewType) {
                    case Constant.SINGLE_IMAGE_SECTION:{
                        holder = new SingleImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_image_row, parent, false));
                        break;
                    }
                    case Constant.TWO_IMAGE_SECTION: {
                        holder = new TwoImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_two_image_row, parent, false));
                        break;
                    }
                    case Constant.THREE_IMAGE_SECTION: {
                        holder = new ThreeImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_three_image_row, parent, false));
                        break;
                    }
                    case Constant.FOUR_IMAGE_SECTION: {
                        holder = new FourImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_four_image_row, parent, false));
                        break;
                    }
                }
                break;
            case "Product":

                break;
            case "categoryList":

                break;
        }

        //holder = new SingleImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_image_row, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (type) {
            case "home":
                switch (sectionType) {
                    case Constant.BANNER_SECTION: {

                        break;
                    }
                    case Constant.SLIDER_SECTION: {

                        break;
                    }
                    case Constant.SINGLE_IMAGE_SECTION:{

                        break;
                    }
                    case Constant.TWO_IMAGE_SECTION: {

                        break;
                    }
                    case Constant.THREE_IMAGE_SECTION: {

                        break;
                    }
                    case Constant.FOUR_IMAGE_SECTION: {

                        break;
                    }
                }
                break;

            case "Product":

                break;

            case "categoryList":

                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        final Section dataObj = (Section) list.get(position);
        return dataObj.getSectionType();
    }

    class SingleImageViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;

        public SingleImageViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //description = (TextView) view.findViewById(R.id.description);
            //imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    class TwoImageViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;

        public TwoImageViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //description = (TextView) view.findViewById(R.id.description);
            //imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    class ThreeImageViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;

        public ThreeImageViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //description = (TextView) view.findViewById(R.id.description);
            //imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    class FourImageViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;

        public FourImageViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //description = (TextView) view.findViewById(R.id.description);
            //imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
