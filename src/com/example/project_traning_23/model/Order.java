package com.example.project_traning_23.model;

import java.util.ArrayList;
import java.util.List;

import com.example.project_traning_23.utils.Project_traning_Model;

public class Order extends Project_traning_Model {
	
	private String id;
	private String date; 
	private String owner_id;
	public User owner;
	private String restaurant_id;
	public Restaurant restaurant;
	private List<OrderLine> orderLine = new ArrayList<OrderLine>();
	
	public List<OrderLine> getOrderline() {
		return orderLine;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getOwner_id() {
		return owner_id;
	}
	
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	
	public String getRestaurant_id() {
		return restaurant_id;
	}
	
	public void setRestaurant_id(String restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	
	public void setOrderline(List<OrderLine> orderline) {
		this.orderLine = orderline;
	}
}
