package com.example.downloadnunziprecords;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IPGMasterDetailsModel  {
    @SerializedName("CityCode")
    @Expose
    private String cityCode;

    @SerializedName("CityName")
    @Expose
    private String cityName;

    @SerializedName("DistrictCode")
    @Expose
    private String districtCode;

    @SerializedName("DistrictName")
    @Expose
    private String districtName;

    @SerializedName("IPGMasterID")
    @Expose
    private int iPGMasterID;

    @SerializedName("IsActive")
    @Expose
    private boolean isActive;

    @SerializedName("PinCode")
    @Expose
    private String pinCode;

    @SerializedName("RegionCode")
    @Expose
    private String regionCode;

    @SerializedName("RegionName")
    @Expose
    private String regionName;

    @SerializedName("StateCode")
    @Expose
    private String stateCode;

    @SerializedName("StateName")
    @Expose
    private String stateName;

    @SerializedName("SO")
    @Expose
    private String SO;


    /**
     * @return The cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * @param cityCode The CityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * @return The cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName The CityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return The districtCode
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * @param districtCode The DistrictCode
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * @return The districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * @param districtName The DistrictName
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * @return The iPGMasterID
     */
    public int getIPGMasterID() {
        return iPGMasterID;
    }

    /**
     * @param iPGMasterID The IPGMasterID
     */
    public void setIPGMasterID(int iPGMasterID) {
        this.iPGMasterID = iPGMasterID;
    }

    /**
     * @return The isActive
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive The IsActive
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return The pinCode
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * @param pinCode The PinCode
     */
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    /**
     * @return The regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * @param regionCode The RegionCode
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * @return The regionName
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * @param regionName The RegionName
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * @return The stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @param stateCode The StateCode
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    /**
     * @return The stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName The StateName
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getSo() {
        return SO;
    }

    public void setSo(String so) {
        this.SO = so;
    }

    @Override
    public String toString() {
        return "IPGMasterDetailsModel{" +
                "cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", districtName='" + districtName + '\'' +
                ", iPGMasterID=" + iPGMasterID +
                ", isActive=" + isActive +
                ", pinCode='" + pinCode + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", regionName='" + regionName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", stateName='" + stateName + '\'' +
                '}';
    }
}
