package com.msnegi.googlecheckinoutdemo.regularupdate;


import java.io.Serializable;

public class LocData implements Serializable{
	
	private String Latitude,
			Longitude,
			Altitude,
			Speed,
            Cal_Speed,
			Distance,
			ShortestDistance,
			Angle,
			Capture_Date,
			Capture_Time,
            time_gap,
	        GPRS,
			Battery_level,
			Accuracy;
	
	public String getShortestDistance() {
		return ShortestDistance;
	}

	public void setShortestDistance(String shortestDistance) {
		ShortestDistance = shortestDistance;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getAltitude() {
		return Altitude;
	}

	public void setAltitude(String altitude) {
		Altitude = altitude;
	}

	public String getSpeed() {
		return Speed;
	}

	public void setSpeed(String speed) {
		Speed = speed;
	}

    public String getCalSpeed() {
        return Cal_Speed;
    }

    public void setCalSpeed(String cal_speed) {
        Cal_Speed = cal_speed;
    }

	public String getDistance() {
		return Distance;
	}

	public void setDistance(String distance) {
		Distance = distance;
	}

	public String getAngle() {
		return Angle;
	}

	public void setAngle(String angle) {
		Angle = angle;
	}

	public String getDate() {
		return Capture_Date;
	}

	public void setDate(String capture_Date) {
		Capture_Date = capture_Date;
	}

	public String getTime() {
		return Capture_Time;
	}

	public void setTime(String capture_Time) {
		Capture_Time = capture_Time;
	}

    public String getTimeGap() {
        return time_gap;
    }

    public void setTimeGap(String time) {
        time_gap = time;
    }

	public String getGPRS() {
		return GPRS;
	}

	public void setGPRS(String gprs) {
		GPRS = gprs;
	}

	public String getBattery_level() {
		return Battery_level;
	}

	public void setBattery_level(String battery_level) {
		Battery_level = battery_level;
	}
	public void setAccuracy(String accuracy) {
		Accuracy = accuracy;
	}
	public String getAccuracy() {
		return Accuracy;
	}
}
