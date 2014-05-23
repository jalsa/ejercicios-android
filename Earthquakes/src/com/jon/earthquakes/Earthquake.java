package com.jon.earthquakes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Earthquake implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id;
	private Date time;
	private String idStr, place, detail, url;
	private float magnitude, latitude, longitude;
	
	public Earthquake(long id, String idStr, String place, long time, String detail, float magnitude, float latitude, float longitude, String url) {
		this(idStr, place, time, detail, magnitude, latitude, longitude, url);
		this.id = id;
	}
	
	public Earthquake(String idStr, String place, long time, String detail, float magnitude, float latitude, float longitude, String url) {
		this.idStr = idStr;
		this.place = place;
		this.time = new Date(time);
		this.detail = detail;
		this.magnitude = magnitude;
		this.latitude = latitude;
		this.longitude = longitude;
		this.url = url;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getIdStr() {
		return this.idStr;
	}
	
	public String getPlace() {
		return this.place;
	}
	
	public Date getTime() {
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
	
	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);
		String fecha = df.format(this.time);
		
		return fecha + " " + this.place + this.magnitude;
	}
	
}
