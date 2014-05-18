package com.exemple.project_traning_23.fragment.pages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.net.MailTo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.Header;

import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.model.Order;
import com.example.project_traning_23.model.User;
import com.example.project_traning_23.InternalStorage;
import com.example.project_traning_23.Project_traning_2_3;
import com.example.project_traning_23.R;
import com.example.project_traning_23.login;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.fasterxml.jackson.core.sym.Name;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ListDish extends AFragment implements OnItemClickListener {

	private ListView listFriend = null;
	private ListView listDishes = null;
	private EditText quantityET;
	
	private List<Good_user> friend_list = null; 
	private List<Map<String, Object>> dishes = null;
	private List<Good_user> userlist = null;
	private LinkedList<String> usernamelist = new LinkedList<String>();
	private LinkedList<String> friendnamelist = new LinkedList<String>();
	private int addfriend_id = -1;
	private String addfriend_name = null;

	
	
	@Override
	public int getMenuTitle() {
		return R.string.list_friend_title;
	}
	
	public void getRestaurantRequest()
	{	Project_traning_2_3 main = (Project_traning_2_3) getActivity();
	
	//Integer qty = Integer.valueOf(quantity);
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "restaurants/read/" + main.getOrder().get("restaurant_id"), null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) 
			{
				Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
				 List<Map<String, Object>> restau = test.adaptToMap(response);
		
				dishes = (List<Map<String, Object>>) restau.get(0).get("dishes");
				
				SimpleAdapter mSchedule = new SimpleAdapter (getActivity(), dishes, R.layout.fragment_list_list_order,
						new String[] {"name", "description", "price"}, new int[] {R.id.fragment_list_list_textView_dishname, R.id.fragment_list_list_textView_dishkb, R.id.fragment_list_list_textView_username});
				listDishes.setAdapter(mSchedule);
				
			}
			});
			
	
	}
	
	public void addOrderLineRequest(int order_id, int position)
	{	
		Project_traning_2_3 main = (Project_traning_2_3) getActivity();
		
		JSONObject json = new JSONObject();
		try {
			
			json.put("order_id",  main.getOrder().get("id"));
		json.put("dish_id", dishes.get(position).get("id"));
		json.put("quantity", quantityET.getText().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//Integer qty = Integer.valueOf(quantity);
		Project_traning_RestClient.postWithBody(getActivity().getApplicationContext(), "order_lines/createForOrder", json.toString(), 
				true, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) 
			{
				Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
				 List<Map<String, Object>> restau = test.adaptToMap(response);
					Toast.makeText(getActivity(), "orderline added", Toast.LENGTH_SHORT).show();
					final FragmentManager fm = getActivity().getSupportFragmentManager();
					final FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.content_frame, new OrderLine());
					// Null on the back stack to return on the previous fragment when user
					// press on back button.
					ft.addToBackStack(null);
					ft.commit();
			}
			});
			
	
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list_dish, container, false);
		
		
		listDishes = (ListView) v.findViewById(R.id.fragment_list_choose_dishes);
		listDishes.setOnItemClickListener(this);
		quantityET = (EditText)v.findViewById(R.id.fragment_list_choose_dishes_quantity);
		getRestaurantRequest();
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			Project_traning_2_3 main = (Project_traning_2_3) getActivity();
			main.setSelectedDish(dishes.get(position));
			
		//	String quantity = quantityET.getText();
			addOrderLineRequest(3, position);
		
	}


}
