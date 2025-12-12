package com.example.bluetoothsample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("DeviceName")
    @Expose
    private String deviceName;

    @SerializedName("DeviceMACAddress")
    @Expose
    private String deviceMACAddress;

    @SerializedName("DeviceDistance")
    @Expose
    private int deviceDistance;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceMACAddress() {
        return deviceMACAddress;
    }

    public void setDeviceMACAddress(String deviceMACAddress) {
        this.deviceMACAddress = deviceMACAddress;
    }

    public int getDeviceDistance() {
        return deviceDistance;
    }

    public void setDeviceDistance(int deviceDistance) {
        this.deviceDistance = deviceDistance;
    }

}
