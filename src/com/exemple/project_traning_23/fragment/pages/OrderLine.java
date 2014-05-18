package com.exemple.project_traning_23.fragment.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.project_traning_23.Project_traning_2_3;
import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.model.Meeting;
import com.example.project_traning_23.model.Order;
import com.example.project_traning_23.model.Restaurant;
import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class OrderLine extends AFragment implements OnClickListener {

	private ListView list;
	private Dialog dialog;
	private Meeting meeting = new Meeting();
//	private List<List<Integer>> actv_id = new ArrayList<List<Integer>>();
	private List<Map<String, Object>> orderlinelist = new ArrayList<Map<String, Object>>();
	
	@Override
	public int getMenuTitle() {
		return R.string.create_meeting_title;
	}

	public void get_order_line(String object)
	{	
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "order_lines/read/" + object, null, 
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
				list.setAdapter(mSchedule2);
			}
		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_orderline, container, false);
		orderlinelist.clear();
		
		list  = (ListView) view.findViewById(R.id.fragment_listdish_listview);
		View test = view.findViewById(R.id.fragment_orderline_button);
		view.findViewById(R.id.fragment_orderline_button).setOnClickListener(this);
		
		Project_traning_2_3 main = (Project_traning_2_3) getActivity();
		Log.d("test",  main.getOrder().get("id").toString());
		get_order_line((String) main.getOrder().get("id").toString());
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_add_meeting, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(final View v) {
		Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
		switch (v.getId()) {
		case R.id.fragment_orderline_button :
			Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
			final FragmentManager fm = getActivity().getSupportFragmentManager();
			final FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.content_frame, new ListDish());
			// Null on the back stack to return on the previous fragment when user
			// press on back button.
			ft.addToBackStack(null);
			ft.commit();
		break;
		}
	}
	
	
}
