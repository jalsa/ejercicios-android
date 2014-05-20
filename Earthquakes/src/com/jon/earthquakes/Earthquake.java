package com.jon.earthquakes;

public class Earthquake {
	
	private String id, place, time, detail, url, created_at, updated_at;
	private float magnitude, latitude, longitude;
	
	public Earthquake(String id, String place, String time, String detail, float magnitude, float latitude, float longitude, String url, String created_at, String updated_at) {
		this.id = id;
		this.place = place;
		this.time = time;
		this.detail = detail;
		this.magnitude = magnitude;
		this.latitude = latitude;
		this.longitude = longitude;
		this.url = url;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
}
