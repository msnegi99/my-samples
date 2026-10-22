package com.msnegi.nestedrecyclerview;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.msnegi.nestedrecyclerview.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import butterknife.BindView;
//import butterknife.ButterKnife;

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.ViewHolder> {

    private List<SpecializationData> specializationDataList = new ArrayList<>();
    private Context context;
    private String cameFrom;
    private HashMap<Integer, Boolean> shrinkHashMap = new HashMap<>();
    private RecyclerView rv_speciality;
    DoctorBySpecialityAdapter adapter;
    int selectedHospital = 0;
    String selectedHospitalName = "";

    public SpecialityAdapter(RecyclerView rv_speciality) {
        this.rv_speciality = rv_speciality;
        shrinkAll();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.speciality_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.tv_speciality.setText(specializationDataList.get(position).getSpecialisationName());
        String imageUrl = "xyz";
        if (specializationDataList.get(position).getSpecialisationUrl().trim().length() > 0) {
            imageUrl = specializationDataList.get(position).getSpecialisationUrl();
            holder.iv_icon.setVisibility(View.VISIBLE);
        }else{
            holder.iv_icon.setVisibility(View.GONE);
        }
        Picasso.with(context)
                .load((imageUrl))
                .error(R.drawable.preventive_holder_image)
                .placeholder(R.drawable.preventive_holder_image)
                .into(holder.iv_icon);

        holder.setUpRecyclerView();
        if (specializationDataList.get(position).getDoctorList().size() > 0) {
            adapter.updateData(specializationDataList.get(position).getDoctorList(), cameFrom, selectedHospital, selectedHospitalName);
            holder.tv_no_data.setVisibility(View.GONE);
            holder.rv_doctors_by_speciality.setVisibility(View.VISIBLE);
        } else {
            holder.tv_no_data.setVisibility(View.VISIBLE);
            holder.rv_doctors_by_speciality.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shrinkAll();

                if (holder.ll_doctors_by_speciality.getVisibility() == View.VISIBLE) {
                    shrinkHashMap.put(holder.getAdapterPosition(), true);
                    holder.shrinkView();
                } else {
                    shrinkHashMap.put(holder.getAdapterPosition(), false);
                    holder.expandView(holder.getAdapterPosition());
                }
                notifyDataSetChanged();
            }
        });

        if (shrinkHashMap.get(position)) {
            holder.shrinkView();
        } else {
            holder.expandView(position);
        }

    }

    @Override
    public int getItemCount() {
        return specializationDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rv_doctors_by_speciality;
        ImageView iv_icon;
        TextView tv_speciality;
        ImageView img_plus;
        LinearLayout ll_doctors_by_speciality;
        TextView tv_no_data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            rv_doctors_by_speciality = itemView.findViewById(R.id.rv_doctors_by_speciality);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_speciality = itemView.findViewById(R.id.tv_speciality);
            img_plus = itemView.findViewById(R.id.img_plus);
            ll_doctors_by_speciality = itemView.findViewById(R.id.ll_doctors_by_speciality);
            tv_no_data = itemView.findViewById(R.id.tv_no_data);
        }

        void setUpRecyclerView() {
            adapter = new DoctorBySpecialityAdapter(context);
            rv_doctors_by_speciality.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            rv_doctors_by_speciality.setAdapter(adapter);
        }

        private void shrinkView() {
            ll_doctors_by_speciality.setVisibility(View.GONE);
            img_plus.setImageResource(R.drawable.down_arrow);
        }

        private void expandView(final int position) {
            ll_doctors_by_speciality.setVisibility(View.VISIBLE);
            img_plus.setImageResource(R.drawable.up_arrow);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rv_speciality.scrollToPosition(position);
                }
            }, 200);

        }

    }

    public void updateData(List<SpecializationData> specializationDataList, String cameFrom, int selectedHospital, String selectedHospitalName) {
        this.specializationDataList.clear();
        this.specializationDataList.addAll(specializationDataList);
        this.cameFrom = cameFrom;
        this.selectedHospital = selectedHospital;
        this.selectedHospitalName = selectedHospitalName;
        shrinkAll();
        notifyDataSetChanged();
    }

    private void shrinkAll() {
        for (int i = 0; i < specializationDataList.size(); i++) {
            shrinkHashMap.put(i, true);
        }
    }

}
