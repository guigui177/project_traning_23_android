package com.exemple.project_traning_23.fragment.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.model.Meeting;
import com.example.project_traning_23.model.Order;
import com.example.project_traning_23.model.Restaurant;
import com.example.project_traning_23.model.User;
import com.example.project_traning_23.utils.ExpandableListAdapterForMeeting;
import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class OrderMeal extends AFragment implements OnClickListener {

	private ListView list;
	private ListView list_list;
	
	private List<Map<String, Object>> orderlist = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> orderlinelist = new ArrayList<Map<String, Object>>();

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

				orderlist = test.adaptToMap(response);

				Map<String, Object> t = new HashMap<String, Object>();

				for(int i = 0; i < orderlist.size(); i++)
				{
					t = orderlist.get(i);					
					HashMap<String, Object> res = (HashMap<String, Object>) t.get("restaurant");
					HashMap<String, Object> onw = (HashMap<String, Object>) t.get("owner");
					Log.d("API order", (String) res.get("name"));	
					t.put("restoName", res.get("name"));
					t.put("onwerName", onw.get("userName"));
					
				}
				//get_order_line(t.get("id"));
				
				SimpleAdapter mSchedule = new SimpleAdapter (getActivity(), orderlist, R.layout.fragment_list_order,
						new String[] {"restoName", "onwerName", "date"}, new int[] {R.id.fragment_list_order_textView_restoname, R.id.fragment_list_order_textView_username, R.id.fragment_list_order_textView_date});
				list.setAdapter(mSchedule);
			}
		});
	}

	public void get_order_line(Object object)
	{	
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "order_lines/read/" + object.toString() , null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				System.out.println(response);

				Project_traning_AdaptResponse<Order> test = new Project_traning_AdaptResponse<Order>();

				orderlinelist = test.adaptToMap(response);

				Map<String, Object> t = new HashMap<String, Object>();

				for(int i = 0; i < orderlinelist.size(); i++)
				{
					t = orderlinelist.get(i);					
					HashMap<String, Object> usr = (HashMap<String, Object>) t.get("user");
					HashMap<String, Object> dis = (HashMap<String, Object>) t.get("dish");
					t.put("userName", usr.get("userName"));
					t.put("dishName", dis.get("name"));
					t.put("dishPrice", dis.get("price"));
					Log.d("API order Line ", "name = " + dis.get("name") + "price = " + dis.get("price") + " username = " + usr.get("userName"));
				}
				SimpleAdapter mSchedule2 = new SimpleAdapter (getActivity(), orderlinelist, R.layout.fragment_list_list_order,
						new String[] {"dishName", "quantity", "userName", "dishPrice"}, new int[] {R.id.fragment_list_list_textView_dishname, R.id.fragment_list_list_textView_dishkb, R.id.fragment_list_list_textView_username, R.id.fragment_list_list_textView_dishprice});
				list_list.setAdapter(mSchedule2);
			}
		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		                              
		View v = inflater.inflate(R.layout.fragment_order_meal, container, false);
		View v2 = inflater.inflate(R.layout.fragment_list_order, container, false);
		v.findViewById(R.id.fragment_order_list_add).setOnClickListener(this);
		
//		list  = (ListView) v.findViewById(R.id.fragment_order_listView);
//		list_list  = (ListView) v2.findViewById(R.id.fragment_list_order_listmeal);
		
//		ExpandableListAdapterForMeeting elafm = new ExpandableListAdapterForMeeting();
		Log.d("debug", "apres expandable");
//		ExpandableListView expla = (ExpandableListView) view.findViewById(R.id.fragment_list_meeting_Explv);
//		expla.setAdapter(elafm);

		
		orderlist.clear();
		orderlinelist.clear();
		
		request_get_order();
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		
		case R.id.fragment_order_list_add:
/*			final FragmentManager fm = getActivity().getSupportFragmentManager();
			final FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.content_frame, new CreateOrder());
			// Null on the back stack to return on the previous fragment when user
			// press on back button.
			ft.addToBackStack(null);
			ft.commit();*/
			break;
		}
	}
}
