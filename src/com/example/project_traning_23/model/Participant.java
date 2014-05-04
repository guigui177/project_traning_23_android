package com.example.project_traning_23.model;

public class Participant {

	private User user;
	private Status status;
	public Participant(User user, Status status) {
		super();
		this.user = user;
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public Status getStatus() {
		return status;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
