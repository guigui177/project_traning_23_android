package com.example.project_traning_23.model;

import java.util.ArrayList;
import java.util.List;

import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_Model;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.content.Context;
import android.widget.Toast;

public class Restaurant extends Project_traning_Model{
	private String id;
	private String address;
	private String postalCode;
	private String city;
	private String description;
	private String name;
	private String category;
	private String mail;
	
	public Restaurant() {
		super();
	}
	
	public Restaurant(String id, String address, String postalCode,
			String city, String description, String name,
			String category, String mail) {
		super();
		this.id = id;
		this.address = address;
		this.postalCode = postalCode;
		this.city = city;
		this.description = description;
		this.name = name;
		this.category = category;
		this.mail = mail;
	}

	public void setRestaurant(Restaurant r) {
		this.id = r.id;
		this.address = r.address;
		this.postalCode = r.postalCode;
		this.city = r.city;
		this.description = r.description;
		this.name = r.name;
		this.category = r.category;
		this.mail = r.mail;
		
	}
	
	public String getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCity() {
		return city;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getMail() {
		return mail;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public static Restaurant getById(final Context context, String id_restaurant) {
		final Restaurant restaurant = new Restaurant();
		Project_traning_RestClient.getWithboddy(context.getApplicationContext(), "restaurants/read/"+id_restaurant, null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				Restaurant rep;
				System.out.println(response);
				Project_traning_AdaptResponse<Restaurant> test = new Project_traning_AdaptResponse<Restaurant>();
				rep = test.adaptToModel(response, Restaurant.class);
				restaurant.setRestaurant(rep);
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(context, "Restaurant getById : failed " , Toast.LENGTH_LONG).show();
			}
		});	
		return restaurant;
	}
	
	public static List<Restaurant> getAllRestaurant(final Context context) {
		final List<Restaurant> restaurants = new ArrayList<Restaurant>();
		Project_traning_RestClient.getWithboddy(context.getApplicationContext(), "restaurants/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				List<Restaurant> rep = new ArrayList<Restaurant>();
				System.out.println(response);
				Project_traning_AdaptResponse<Restaurant> test = new Project_traning_AdaptResponse<Restaurant>();
				rep = test.adaptToList(response, Restaurant.class);
				Restaurant r;
				for(int i = 0; i < rep.size(); i++)
				{
					r = rep.get(i);					
					restaurants.add(r);
				}
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(context, "getAllRestaurant : failed " , Toast.LENGTH_LONG).show();
			}
		});	
		return restaurants;		
	}
}
