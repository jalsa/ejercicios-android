package com.jon.earthquakes;

public class Earthquake {
	
	private long id;
	private String idStr, place, time, detail, url;
	private float magnitude, latitude, longitude;
	
	public Earthquake(String idStr, String place, String time, String detail, float magnitude, float latitude, float longitude, String url) {
		this.idStr = idStr;
		this.place = place;
		this.time = time;
		this.detail = detail;
		this.magnitude = magnitude;
		this.latitude = latitude;
		this.longitude = longitude;
		this.url = url;
	}
	
	public String getIdStr() {
		return this.idStr;
	}
	
	public String getPlace() {
		return this.place;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public String getDetail() {
		return this.detail;
	}
	
	public float getMagnitude() {
		return this.magnitude;
	}
	
	public float getLatitude() {
		return this.latitude;
	}
	
	public float getLongitude() {
		return this.longitude;
	}
	
	public String getUrl() {
		return this.url;
	}
	
}
