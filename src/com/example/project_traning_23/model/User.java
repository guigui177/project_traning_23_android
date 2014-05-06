package com.example.project_traning_23.model;

import java.util.Date;
import java.util.List;

import com.example.project_traning_23.utils.Project_traning_Model;

import android.media.Image;

public class User extends Project_traning_Model {
	private int id;
	private String userName;
	private String mail;
	private Date Birthday;
	private Image avatar;
	private List<Friend> friends;
	private List<Coupon> coupons;
	private List<Meeting> meetings;
	
	public User(int id, String name, Date birthday, Image avatar,
			List<Friend> friends, List<Coupon> coupons, List<Meeting> meetings) {
		super();
		this.id = id;
		this.userName = name;
		Birthday = birthday;
		this.avatar = avatar;
		this.friends = friends;
		this.coupons = coupons;
		this.meetings = meetings;
	}

	public int getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public Date getBirthday() {
		return Birthday;
	}
	
	public Image getAvatar() {
		return avatar;
	}
	
	public List<Coupon> getCoupons() {
		return coupons;
	}
	
	public List<Meeting> getMeetings() {
		return meetings;
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
	
	public void setBirthday(Date birthday) {
		Birthday = birthday;
	}
	
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}
	
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
	
	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}
	
	public void addFriend(Friend friend) {
		this.friends.add(friend);
	}

	public void removeFriend(int location) {
		this.friends.remove(location);
	}
	
	public void addmeeting(Meeting meeting) {
		this.meetings.add(meeting);
	}

	public void removemeeting(int location) {
		this.meetings.remove(location);
	}
	
	public void addcoupon(Coupon coupon) {
		this.coupons.add(coupon);
	}

	public void removecoupon(int location) {
		this.coupons.remove(location);
	}
}
