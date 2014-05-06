package com.example.project_traning_23.model;

import com.example.project_traning_23.utils.Project_traning_Model;

public class Good_user extends Project_traning_Model {
	private int id;
	private String userName;
	private String mail;
	
	public int getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setUserName(String name) {
		this.userName = name;
	}
}
