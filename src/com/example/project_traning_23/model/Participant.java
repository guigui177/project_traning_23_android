package com.example.project_traning_23.model;

public class Participant {

	private int id_user;
	private String user_name;
	private String status;
	public Participant(int id_user, String user_name, String status) {
		super();
		this.id_user = id_user;
		this.user_name = user_name;
		this.status = status;
	}
	public int getIdUser() {
		return id_user;
	}
	public String getStatus() {
		return status;
	}
	public void setIdUser(int user) {
		this.id_user = user;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return user_name;
	}
	public void setUserName(String user_name) {
		this.user_name = user_name;
	}
}
