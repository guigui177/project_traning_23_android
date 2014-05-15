package com.example.project_traning_23.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_Model;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Meeting extends Project_traning_Model{
	private String id;
	private String startDate;
	private String endDate;
	private String address;
	private String name;
	private String status;
	private String restaurant_id;
	private String owner_id;

	public Meeting(String id, String startDate, String endDate, String address,
			String name, String status, String owner_id, String id_restaurant) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.name = name;
		this.status = status;
		this.owner_id = owner_id;
		this.restaurant_id = id_restaurant;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getAddress() {
		return address;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId_restaurant() {
		return restaurant_id;
	}

	public void setId_restaurant(String id_restaurant) {
		this.restaurant_id = id_restaurant;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public static List<Meeting> getAllMeetings(final Context context) {
		final List<Meeting> meetings = new ArrayList<Meeting>();
		Project_traning_RestClient.getWithboddy(context.getApplicationContext(), "meetings/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				List<Meeting> rep = new ArrayList<Meeting>();
				System.out.println(response);
				Project_traning_AdaptResponse<Meeting> test = new Project_traning_AdaptResponse<Meeting>();
				rep = test.adaptToList(response, Meeting.class);
				Meeting m;
				for(int i = 0; i < rep.size(); i++)
				{
					m = rep.get(i);					
					meetings.add(m);
				}
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(context, "getAllMeetings : failed " , Toast.LENGTH_LONG).show();
			}
		});	
		return meetings;
	}

	public List<Good_user> getAllParticipants(final Context context) {
		final List<Good_user> participants = new ArrayList<Good_user>();
		Project_traning_RestClient.getWithboddy(context.getApplicationContext(), "meetings/"+ this.getId() +"/members/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				List<Good_user> rep = new ArrayList<Good_user>();
				System.out.println(response);
				Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
				rep = test.adaptToList(response, Good_user.class);
				Good_user gu;
				for(int i = 0; i < rep.size(); i++)
				{
					gu = rep.get(i);					
					participants.add(gu);
				}
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(context, "getAllParticipants : failed " , Toast.LENGTH_LONG).show();
			}
		});	
		return participants;		
	}

	public void updateMeeting(final Context context) {
		try {
			JSONObject json = new JSONObject();
			json.put("address", this.address);
			json.put("startDate", this.startDate);
			json.put("endDate", this.endDate);
			json.put("restaurant_id", this.restaurant_id);
			json.put("name", this.name);

			Project_traning_RestClient.putWithBody(context, "meetings/" + this.id + "/update", json.toString(), false, 
					new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					System.out.println(response);
					Toast.makeText(context, "the meeting is updated!", Toast.LENGTH_LONG).show();
				}
				public void onFailure(Throwable error)
				{
					System.out.println(error.getLocalizedMessage());
					Toast.makeText(context, "updateMeeting : failed " , Toast.LENGTH_LONG).show();
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void createMeeting(final Context context) {
		try {
			JSONObject json = new JSONObject();
			json.put("address", this.address);
			json.put("startDate", this.startDate);
			json.put("endDate", this.endDate);
			json.put("restaurant_id", this.restaurant_id);
			json.put("name", this.name);
			json.put("owner_id", this.owner_id);

			Project_traning_RestClient.putWithBody(context, "meetings/create", json.toString(), false, 
					new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					System.out.println(response);
					Toast.makeText(context, "the meeting is created!", Toast.LENGTH_LONG).show();
				}
				public void onFailure(Throwable error)
				{
					System.out.println(error.getLocalizedMessage());
					Toast.makeText(context, "createMeeting : failed " , Toast.LENGTH_LONG).show();
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void addParticipantToMeeting(final Context context, String p_id) {
		JSONObject json = new JSONObject();
		Project_traning_RestClient.putWithBody(context, "meetings/" + this.id + "/members/add/" + p_id, json.toString(), false, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				System.out.println(response);
				Toast.makeText(context, "the friend is add!", Toast.LENGTH_LONG).show();
			}
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(context, "addParticipantToMeeting : faild " , Toast.LENGTH_LONG).show();
			}
		});
	}
}