
package com.msnegi.nestedrecyclerview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpecializationData {

    @SerializedName("SpecialisationId")
    @Expose
    private Integer specialisationId;
    @SerializedName("SpecialisationName")
    @Expose
    private String specialisationName;
    @SerializedName("SpecialisationUrl")
    @Expose
    private String specialisationUrl;
    @SerializedName("DoctorList")
    @Expose
    private List<DoctorListData> doctorList = new ArrayList<>();

    public SpecializationData(Integer specialisationId, String specialisationName, String specialisationUrl, List<DoctorListData> doctorList) {
        this.specialisationId = specialisationId;
        this.specialisationName = specialisationName;
        this.specialisationUrl = specialisationUrl;
        this.doctorList = doctorList;
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

    public String getSpecialisationUrl() {
        return specialisationUrl;
    }

    public void setSpecialisationUrl(String specialisationUrl) {
        this.specialisationUrl = specialisationUrl;
    }

    public List<DoctorListData> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<DoctorListData> doctorList) {
        this.doctorList = doctorList;
    }

}
