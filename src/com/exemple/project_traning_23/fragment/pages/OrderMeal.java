package com.exemple.project_traning_23.fragment.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.model.Order;
import com.example.project_traning_23.model.User;
import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class OrderMeal extends AFragment {

	private ListView list;
	private List<Order> orderList = new ArrayList<Order>();
	private List<Map<String, Object>> restolist = new ArrayList<Map<String, Object>>();
	private List<Good_user> userlist = null;
	private LinkedList<String> usernamelist = new LinkedList<String>();

	@Override
	public int getMenuTitle() {
		return R.string.order_meal_title;
	}

	public void request_get_order()
	{
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "orders/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				System.out.println(response);

				Project_traning_AdaptResponse<Order> test = new Project_traning_AdaptResponse<Order>();

				orderList = test.adaptToList(response, Order.class);

				Order t = new Order();

				for(int i = 0; i < orderList.size(); i++)
				{
					t = orderList.get(i);
					Log.d("API owner" , "owner = -" + t.getOwner_id() + "- resto id = -" + t.getRestaurant_id() + "- date = -" + t.getDate() + "- id = -" + t.getId() + "- restoname =-" + t.restaurant.getName() + "user mane = " + t.owner.getUserName());
				}
				
			}
		});
	}

	public void getrestaurant()
	{
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "restaurants/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				System.out.println(response);

				Project_traning_AdaptResponse<User> test = new Project_traning_AdaptResponse<User>();
				restolist = test.adaptToMap(response);

				Map<String, Object> t = new HashMap<String, Object>();
				for(int i = 0; i < restolist.size(); i++)
				{
					t = restolist.get(i);
					Log.d("API resto", "resto name = -" + t.get("name") + "- id = -" + t.get("id") + "-");
				}
			}

			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(getActivity().getApplicationContext(), "requette list users faild " , Toast.LENGTH_LONG).show();
			}
		});
	}

	public void getuserrequest()
	{
		usernamelist.clear();
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "users/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				userlist = new ArrayList<Good_user>();
				System.out.println(response);
				Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
				userlist = test.adaptToList(response, Good_user.class);
				Good_user t;
				for(int i = 0; i < userlist.size(); i++)
				{
					t = userlist.get(i);					
					usernamelist.add(t.getUserName());
					Log.d("API user ", "user name = -" + t.getUserName()+ "-");	
				}		
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(getActivity().getApplicationContext(), "requette list users faild " , Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_order_meal, container, false);
		list  = (ListView) v.findViewById(R.id.fragment_order_listView);
		getrestaurant();
		getuserrequest();
		request_get_order();
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}
}
