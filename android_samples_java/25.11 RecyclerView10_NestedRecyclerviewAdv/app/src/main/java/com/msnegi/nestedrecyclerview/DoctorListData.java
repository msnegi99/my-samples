
package com.msnegi.nestedrecyclerview;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorListData implements Parcelable {

    @SerializedName("DoctorId")
    @Expose
    private Integer doctorId;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("DoctorPic")
    @Expose
    private String doctorPic;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("SpecialisationId")
    @Expose
    private Integer specialisationId;
    @SerializedName("SpecialisationName")
    @Expose
    private String specialisationName;

    public DoctorListData(){}

    protected DoctorListData(Parcel in) {
        if (in.readByte() == 0) {
            doctorId = null;
        } else {
            doctorId = in.readInt();
        }
        doctorName = in.readString();
        doctorPic = in.readString();
        designation = in.readString();
        if (in.readByte() == 0) {
            specialisationId = null;
        } else {
            specialisationId = in.readInt();
        }
        specialisationName = in.readString();
    }

    public static final Creator<DoctorListData> CREATOR = new Creator<DoctorListData>() {
        @Override
        public DoctorListData createFromParcel(Parcel in) {
            return new DoctorListData(in);
        }

        @Override
        public DoctorListData[] newArray(int size) {
            return new DoctorListData[size];
        }
    };

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPic() {
        return doctorPic;
    }

    public void setDoctorPic(String doctorPic) {
        this.doctorPic = doctorPic;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getSpecialisationId() {
        return specialisationId;
    }

    public void setSpecialisationId(Integer specialisationId) {
        this.specialisationId = specialisationId;
    }

    public String getSpecialisationName() {
        return specialisationName;
    }

    public void setSpecialisationName(String specialisationName) {
        this.specialisationName = specialisationName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (doctorId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(doctorId);
        }
        dest.writeString(doctorName);
        dest.writeString(doctorPic);
        dest.writeString(designation);
        if (specialisationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(specialisationId);
        }
        dest.writeString(specialisationName);
    }
}
