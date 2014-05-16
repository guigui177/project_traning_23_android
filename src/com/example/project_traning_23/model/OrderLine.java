package com.example.project_traning_23.model;

import java.util.List;

import com.example.project_traning_23.utils.Project_traning_Model;

public class OrderLine extends Project_traning_Model {
	
	private int id;
	private int dish_id; 
	private int order_id ; 
	private int quantity ; 
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getDish_id() {
		return dish_id;
	}
	
	public void setDish_id(int dish_id) {
		this.dish_id = dish_id;
	}
	
	public int getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
