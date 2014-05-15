package com.example.project_traning_23.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_Model;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.pages.CreateMeeting;
import com.loopj.android.http.AsyncHttpResponseHandler;

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

	public static void getUser(final Activity act, final Meeting meeting, final List<Object> o) {
		final Good_user user = new Good_user();
		Project_traning_RestClient.getWithboddy(act.getApplicationContext(), "auth", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				Good_user rep = new Good_user();
				System.out.println(response);
				Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
				rep = test.adaptToModel(response, Good_user.class);
				user.setId(rep.getId());
				user.setUserName(rep.getUserName());
				meeting.setOwner_id(String.valueOf(user.getId()));
				meeting.createMeeting(act, o);
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(act.getApplicationContext(), "requette list friend faild " , Toast.LENGTH_LONG).show();
			}
		});
	}

}
