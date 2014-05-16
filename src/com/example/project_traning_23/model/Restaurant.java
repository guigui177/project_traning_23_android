package com.example.project_traning_23.model;

import java.util.ArrayList;
import java.util.List;

import com.example.project_traning_23.R;
import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_Model;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Restaurant extends Project_traning_Model{
	private String id;
	private String address;
	private String postalCode;
	private String city;
	private String description;
	private String name;
	private String category;
	private String mail;
	private String userName;
	private String password;
	
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

	public static void getById(final Context context, String id_restaurant, final Object d, final Meeting meeting, final int choice) {
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
				if (choice == 0) {
					List<Object> no = new ArrayList<Object>();
					no.add(d);
					no.add(restaurant.getName());
					Restaurant.getAllRestaurant(context, no, meeting, 1);
				}
				if (choice == 1) {
					TextView restaurant_name = (TextView) ((View) d).findViewById(R.id.list_meeting_row_item_restaurant_tv);
					System.out.println("restaurant :" + restaurant.getName() + "|");
					restaurant_name.setText(restaurant.getName());
				}
			}
			@Override
			public void onFailure(Throwable error)
			{
			//	System.out.println(error.getLocalizedMessage());
				Toast.makeText(context, "Restaurant getById : failed " , Toast.LENGTH_LONG).show();
			}
		});	
	}

	public static void getAllRestaurant(final Context context, final Object o, final Meeting meeting, final int choice) {
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
				if (choice == 0) {
					final AutoCompleteTextView actv = (AutoCompleteTextView) ((View)o).findViewById(R.id.fragment_create_meeting_restaurant_actv);
					actv.setText("");
					List<String> autocstr = new ArrayList<String>();
					for (int i = 0; i < restaurants.size(); ++i)
						autocstr.add(restaurants.get(i).getName());
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.list_dropdown_item, autocstr);
					actv.setAdapter(adapter);
					actv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long rowId) {
							String r_name = ((TextView)view).getText().toString();
							for (int i = 0; i < restaurants.size(); ++i) {
								if (restaurants.get(i).getName().contentEquals(r_name))
									meeting.setRestaurant_id(restaurants.get(i).getId());
								System.out.println("ID RESTAU:" + restaurants.get(i).getId() + "|NAME RESTAU:" + restaurants.get(i).getName() + "|");
							}
							System.out.println("MEETING RESTAU ID:" + meeting.getRestaurant_id() + "|");
						}
					});
				}
				else if (choice == 1) {
					Dialog d = (Dialog) ((List<Object>)o).get(0);
					String resto_name = (String) ((List<Object>)o).get(1);
					AutoCompleteTextView actv = (AutoCompleteTextView) d.findViewById(R.id.dialog_modification_meeting_restaurant_name_actv);
					actv.setText(resto_name);
					List<String> autocstr = new ArrayList<String>();
					for (int i = 0; i < restaurants.size(); ++i)
						autocstr.add(restaurants.get(i).getName());
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.list_dropdown_item, autocstr);
					actv.setAdapter(adapter);
					actv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long rowId) {
							meeting.setRestaurant_id(restaurants.get(position).getId());
						}
					});
				}
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(context, "getAllRestaurant : failed " , Toast.LENGTH_LONG).show();
			}
		});
	}
}
