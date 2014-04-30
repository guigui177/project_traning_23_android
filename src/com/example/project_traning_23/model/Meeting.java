package com.example.project_traning_23.model;

import java.util.Date;
import java.util.List;

public class Meeting {
	private int id;
	private Date date;
	private List<User> participants;
	private Restaurant resto;
	private Status status;
	private boolean notification;

	public Meeting(int id, Date date, List<User> participants,
			Restaurant resto, Status status, boolean notification) {
		super();
		this.id = id;
		this.date = date;
		this.participants = participants;
		this.resto = resto;
		this.status = status;
		this.notification = notification;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public List<User> getParticipants() {
		return participants;
	}

	public Restaurant getResto() {
		return resto;
	}

	public Status getStatus() {
		return status;
	}

	public boolean isNotification() {
		return notification;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	public void setResto(Restaurant resto) {
		this.resto = resto;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}
	
	public void addParcticipant(User User) {
		this.participants.add(User);
	}
	
	public void removeParticipant(int location) {
		this.participants.remove(location);
	}
}
