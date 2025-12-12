package com.msnegi.nestedrecyclerview;

import android.content.Context;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msnegi.nestedrecyclerview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class DoctorBySpecialityAdapter extends RecyclerView.Adapter<DoctorBySpecialityAdapter.ViewHolder>{

    public static final String IS_SUGGESTED_DOCTOR = "is_suggested_doctor";
    public static final String SELECTED_DATE = "";
    List<DoctorListData> doctorList = new ArrayList<>();
    String cameFrom;
    int selectedHospital = 0;
    String selectedHospitalName = "";
    private Context context;

    public DoctorBySpecialityAdapter(Context context){
       this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doctor_by_speciality_row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_name.setText(doctorList.get(position).getDoctorName());
        holder.tv_designation.setText(doctorList.get(position).getDesignation());

        Picasso.with(context)
                //.load(doctorList.get(position).getDoctorPic())
                .load(R.drawable.profile_button)
                .error(R.drawable.profile)
                .placeholder(R.drawable.profile)
                .into(holder.iv_doctor);

        if(cameFrom.equalsIgnoreCase(RecyclerViewNestedFragment.FOR_APPOINTMENT)){
            holder.ll_appointment.setVisibility(View.VISIBLE);
            holder.bt_view_profile.setVisibility(View.GONE);
        }else{
            holder.ll_appointment.setVisibility(View.GONE);
            holder.bt_view_profile.setVisibility(View.VISIBLE);
        }
        holder.ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected(context)) {
                    DoctorListData doctorListData = new DoctorListData();
                    doctorListData.setDoctorId(doctorList.get(position).getDoctorId());
                    doctorListData.setDesignation(doctorList.get(position).getDesignation());
                    doctorListData.setDoctorName(doctorList.get(position).getDoctorName());
                    doctorListData.setDoctorPic(doctorList.get(position).getDoctorPic());
                    doctorListData.setSpecialisationId(doctorList.get(position).getSpecialisationId());
                    doctorListData.setSpecialisationName(doctorList.get(position).getSpecialisationName());

                    /*Intent intent = new Intent(context, AppointmentActivity.class);
                    intent.putExtra(Constants.SELECTED_DOCTOR, doctorListData);
                    intent.putExtra(Constants.FACILITY_ID, selectedHospital);
                    intent.putExtra(Constants.HOSPITAL_NAME, selectedHospitalName);
                    intent.putExtra(IS_SUGGESTED_DOCTOR,false);
                    intent.putExtra(SELECTED_DATE,"");
                    context.startActivityForResult(intent,MainActivity.APPOINTMENT_ACTIVITY_REQ_CODE);*/
                } else {
                    //context.noInternet();
                }
            }
        });

        holder.ll_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDoctorProfileActivity(context.getString(R.string.doctor_profile),doctorList.get(position).getDoctorId()+"");
            }
        });

        holder.bt_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDoctorProfileActivity(context.getString(R.string.doctor_profile),doctorList.get(position).getDoctorId()+"");
            }
        });

    }

    public static boolean isNetworkConnected(Context con) {
        ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_designation;
        CircularRoundImage iv_doctor;
        ImageView iv_left;
        ImageView iv_right;
        LinearLayout ll_appointment;
        Button bt_view_profile;
        LinearLayout ll_right;
        LinearLayout ll_left;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //ButterKnife.bind(this,itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_designation = itemView.findViewById(R.id.tv_designation);
            iv_doctor = itemView.findViewById(R.id.iv_doctor);
            iv_left = itemView.findViewById(R.id.iv_left);
            iv_right = itemView.findViewById(R.id.iv_right);
            ll_appointment = itemView.findViewById(R.id.ll_appointment);
            bt_view_profile = itemView.findViewById(R.id.bt_view_profile);
            ll_right = itemView.findViewById(R.id.ll_right);
            ll_left = itemView.findViewById(R.id.ll_left);
        }
    }

    void updateData(List<DoctorListData> doctorList, String cameFrom, int selectedHospital, String selectedHospitalName){
        this.doctorList.clear();
        this.doctorList.addAll(doctorList);
        this.cameFrom =cameFrom;
        this.selectedHospital =selectedHospital;
        this.selectedHospitalName = selectedHospitalName;
        notifyDataSetChanged();
    }

    public void openDoctorProfileActivity(String title, String doctorId) {
        /*if (Utils.isNetworkConnected(context)) {
            Intent intent = new Intent(context, DoctorProfileActivity.class);
            intent.putExtra("title", title);
            intent.putExtra(KnowYourDoctorActivity.DOCTOR_ID, doctorId);
            context.startActivityForResult(intent,MainActivity.PROFILE_ACTIVITY_REQ_CODE);
        } else {
            context.noInternet();
        }*/
    }
}
