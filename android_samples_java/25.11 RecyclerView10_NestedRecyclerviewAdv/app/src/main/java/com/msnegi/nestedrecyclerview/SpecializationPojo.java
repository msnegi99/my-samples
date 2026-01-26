package com.msnegi.nestedrecyclerview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecializationPojo {
    @SerializedName("Data")
    @Expose
    private List<SpecializationData> data = null;
    @SerializedName("IsSuccessful")
    @Expose
    private Boolean isSuccessful;
    @SerializedName("FriendlyMessage")
    @Expose
    private String friendlyMessage;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("ClientToken")
    @Expose
    private String clientToken;
    @SerializedName("ClientId")
    @Expose
    private String clientId;
    @SerializedName("ApiCode")
    @Expose
    private Integer apiCode;

    public List<SpecializationData> getData() {
        return data;
    }

    public void setData(List<SpecializationData> data) {
        this.data = data;
    }

    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }

    public void setFriendlyMessage(String friendlyMessage) {
        this.friendlyMessage = friendlyMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getApiCode() {
        return apiCode;
    }

    public void setApiCode(Integer apiCode) {
        this.apiCode = apiCode;
    }
}
