package com.msnegi.websearchretrofit.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResponse {
    @SerializedName(value = "Status", alternate={"status"})
    @Expose
    private String status;
    @SerializedName(value = "Message", alternate = {"msg"})
    @Expose
    private String msg;
    @SerializedName(value = "Data", alternate = {"data"})
    @Expose
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
