package com.example.project_traning_23.model;

public class Friend {

	private int user_id;
	private String name;

	public Friend(int user_id, String name) {
		super();
		this.user_id = user_id;
		this.name = name;
	}
	public int getUser_id() {
		return user_id;
	}
	public String getName() {
		return name;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setName(String name) {
		this.name = name;
	}
}
