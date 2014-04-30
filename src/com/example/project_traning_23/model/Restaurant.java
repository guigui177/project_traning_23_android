package com.example.project_traning_23.model;

public class Restaurant {
	private String name;
	private int total_seat;
	private int avaliable_seat;
	private String adresse;
	private String mail;
	private float latitude;
	private float longitude;
	
	public Restaurant(String name, int total_seat, int avaliable_seat,
			String adresse, String mail, float latitude, float longitude) {
		super();
		this.name = name;
		this.total_seat = total_seat;
		this.avaliable_seat = avaliable_seat;
		this.adresse = adresse;
		this.mail = mail;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}
	
	public int getTotal_seat() {
		return total_seat;
	}
	
	public int getAvaliable_seat() {
		return avaliable_seat;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public String getMail() {
		return mail;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTotal_seat(int total_seat) {
		this.total_seat = total_seat;
	}
	
	public void setAvaliable_seat(int avaliable_seat) {
		this.avaliable_seat = avaliable_seat;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
}
