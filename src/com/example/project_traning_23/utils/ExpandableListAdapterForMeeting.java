package com.example.project_traning_23.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.model.Meeting;
import com.example.project_traning_23.model.Restaurant;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ExpandableListAdapterForMeeting extends BaseExpandableListAdapter{

	private List<Meeting> meetings;
	private LayoutInflater inflater;
	private Activity act;

	public ExpandableListAdapterForMeeting(Activity activity, List<Meeting> meetings) {
		super();
		this.meetings = meetings;
		this.act = activity;
		this.inflater = act.getLayoutInflater();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return meetings.get(groupPosition);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		try {
			final Meeting meeting = (Meeting) getChild(groupPosition, childPosition);
			if (convertView == null)
				convertView = inflater.inflate(R.layout.list_meeting_row_item, null);
			TextView date_tv = (TextView) convertView.findViewById(R.id.list_meeting_row_item_date_time_tv);
			String sfrom = null, sto = null;
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy'T'HH:mm:ss");

			Date dfrom = sdf.parse(meeting.getStartDate());
			sdf.applyLocalizedPattern("'Le' dd/MM 'de' HH:mm ");
			sfrom = sdf.format(dfrom);

			sdf.applyLocalizedPattern("MM-dd-yyyy'T'HH:mm:ss");
			Date dto = sdf.parse(meeting.getEndDate());
			sdf.applyLocalizedPattern("'�' HH:mm");
			sto = sdf.format(dto);

			System.out.println("LA DATE :" + sfrom + sto + "|");
			date_tv.setText(sfrom + sto);

			System.out.println(meeting.getRestaurant_id());

			//				Restaurant.getById(act.getApplicationContext(), meeting.getRestaurant_id(), convertView, null, 1);
			//				final View v_for_req = convertView.findViewById(convertView.getId());
			//				Project_traning_RestClient.getWithboddy(act.getApplicationContext(), "restaurants/read/"+ meeting.getRestaurant_id(), null, 
			//						new AsyncHttpResponseHandler() {
			//					@Override
			//					public void onSuccess(String response) {
			//						Restaurant rep;
			//						System.out.println(response);
			//						Project_traning_AdaptResponse<Restaurant> test = new Project_traning_AdaptResponse<Restaurant>();
			//						rep = test.adaptToModel(response, Restaurant.class);
			//						meeting.setRestaurant_id(rep.getId());
			//						
			TextView name_r = (TextView) convertView.findViewById(R.id.list_meeting_row_item_restaurant_tv);
			name_r.setText(meeting.getRestaurant().getName());
			//					}
			//					@Override
			//					public void onFailure(Throwable error)
			//					{
			//						System.out.println("Restaurant getById : failed ");
			//						Toast.makeText(act.getApplicationContext(), "Restaurant getById : failed " , Toast.LENGTH_LONG).show();
			//					}
			//				});	


			//			TextView restaurant_name = (TextView) convertView.findViewById(R.id.list_meeting_row_item_restaurant_tv);
			//			restaurant_name.setText(Restaurant.getById(act.getApplicationContext(), meeting.getId_restaurant()).getName());

			TextView order_tv = (TextView) convertView.findViewById(R.id.list_meeting_row_item_orders_tv);
			final String orders = new String();
			//				for (int i = 0; meeting.getOrder().get(i); ++i) {
			//					if (i != 0)
			//						orders += ", ";
			//					orders += meeting.getOrder().get(i).toString();
			//				}
			order_tv.setText(orders);

			final LinearLayout l = (LinearLayout) convertView.findViewById(R.id.list_meeting_row_item_participants_l);
			l.removeAllViews();

			//				List<Good_user> participants = meeting.getAllParticipants(act.getApplicationContext());

			Project_traning_RestClient.getWithboddy(act.getApplicationContext(), "meetings/"+ meeting.getId() +"/members/read", null, 
					new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					List<Good_user> participants = new ArrayList<Good_user>();
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

					for (int i = 0; i < participants.size(); ++i) {
						System.out.println("AFFICHE PARTICIPANT:" + participants.get(i).getUserName() + "|");
						View view = inflater.inflate(R.layout.list_meeting_row_item_participant_item, null);
						TextView name_tv = (TextView) view.findViewById(R.id.list_meeting_row_item_participant_item_name_tv);
						name_tv.setText(participants.get(i).getUserName());
						//			tv.setTextColor(Color.BLACK);
						TextView status_tv = (TextView) view.findViewById(R.id.list_meeting_row_item_participant_item_status_tv);
						status_tv.setText("");
						l.addView(view);
					}

				}
				@Override
				public void onFailure(Throwable error)
				{
					System.out.println(error.getLocalizedMessage());
					Toast.makeText(act.getApplicationContext(), "getAllParticipants : failed " , Toast.LENGTH_LONG).show();
				}
			});	


			return convertView;

		} catch (ParseException e) {
			System.out.println("NIK�");
			e.printStackTrace();
			return convertView;
		}
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (meetings.size() == 0)
			return 0;
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return meetings.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return meetings.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_meeting_group_item, null);
		final Meeting meeting = meetings.get(groupPosition);

		TextView name_meeting_tv = (TextView) convertView.findViewById(R.id.list_meeting_group_item_name_meeting_tv);
		TextView status_tv = (TextView) convertView.findViewById(R.id.list_meeting_group_item_status_tv);		
		name_meeting_tv.setText(meeting.getName());
		status_tv.setText(meeting.getStatus());

		Button modify_bt = (Button) convertView.findViewById(R.id.list_meeting_group_item_validate_bt);
		Button participant_bt = (Button) convertView.findViewById(R.id.list_meeting_group_item_participant_bt);
		Button order_bt = (Button) convertView.findViewById(R.id.list_meeting_group_item_order_bt);
		modify_bt.setFocusable(false);
		participant_bt.setFocusable(false);
		order_bt.setFocusable(false);
		modify_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				modifyMeeting(meeting);
			}
		});
		participant_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				modifyParticipants(meeting);
			}
		});
		//		order_bt.setOnClickListener(new OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View v) {
		//				modifyOrders(meeting);
		//			}
		//		});

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	private void modifyMeeting(final Meeting meeting) {
		try {
			final Dialog dialog = new Dialog(act);
			dialog.setTitle("Modification");
			dialog.setContentView(R.layout.dialog_modification_meeting);

			final EditText name_et = (EditText) dialog.findViewById(R.id.dialog_modification_meeting_name_et);
			name_et.setText(meeting.getName());

			//			Restaurant.getById(act, meeting.getRestaurant_id(), dialog, meeting, 0);
			//			Restaurant.getAllRestaurant(act.getApplicationContext());
			Project_traning_RestClient.getWithboddy(act.getApplicationContext(), "restaurants/read", null, 
					new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					final List<Restaurant> restaurants = new ArrayList<Restaurant>();
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
					AutoCompleteTextView actv = (AutoCompleteTextView) dialog.findViewById(R.id.dialog_modification_meeting_restaurant_name_actv);
					actv.setText(meeting.getRestaurant().getName());

					List<String> autocstr = new ArrayList<String>();
					for (int i = 0; i < restaurants.size(); ++i)
						autocstr.add(restaurants.get(i).getName());
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(act, R.layout.list_dropdown_item, autocstr);
					actv.setAdapter(adapter);
					actv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long rowId) {
							String r_name = ((TextView)view).getText().toString();
							for (int i = 0; i < restaurants.size(); ++i) {
								if (restaurants.get(i).getName().contentEquals(r_name))
									meeting.setRestaurant_id(restaurants.get(i).getId());
							}
							System.out.println(meeting.getRestaurant_id());
						}
					});
				}
				@Override
				public void onFailure(Throwable error)
				{
					System.out.println(error.getLocalizedMessage());
					Toast.makeText(act.getApplicationContext(), "getAllRestaurant : failed " , Toast.LENGTH_LONG).show();
				}
			});
			//
			//			AutoCompleteTextView actv = (AutoCompleteTextView) dialog.findViewById(R.id.dialog_modification_meeting_restaurant_name_actv);
			//			actv.setText(Restaurant.getById(act, meeting.getId_restaurant()).getName());
			//			final List<Restaurant> restaurants = Restaurant.getAllRestaurant(act.getApplicationContext());
			//			List<String> autocstr = new ArrayList<String>();
			//			for (int i = 0; i < restaurants.size(); ++i)
			//				autocstr.add(restaurants.get(i).getName());
			//			ArrayAdapter<String> adapter = new ArrayAdapter<String>(act, R.layout.list_dropdown_item, autocstr);
			//			actv.setAdapter(adapter);
			//			actv.setOnItemClickListener(new OnItemClickListener() {
			//
			//				@Override
			//				public void onItemClick(AdapterView<?> parent, View view,
			//						int position, long rowId) {
			//					meeting.setId_restaurant(restaurants.get(position).getId());
			//				}
			//			});

			final DatePicker from_dp = (DatePicker) dialog.findViewById(R.id.dialog_modification_meeting_from_dp);
			from_dp.setCalendarViewShown(false);

			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date d;
			d = s.parse(meeting.getStartDate());
			System.out.println(meeting.getStartDate());

			from_dp.updateDate(d.getYear() + 1900, d.getMonth(), d.getDate());

			final TimePicker from_tp = (TimePicker) dialog.findViewById(R.id.dialog_modification_meeting_from_tp);
			from_tp.setCurrentHour(d.getHours());
			from_tp.setCurrentMinute(d.getMinutes());

			final DatePicker to_dp = (DatePicker) dialog.findViewById(R.id.dialog_modification_meeting_to_dp);
			to_dp.setCalendarViewShown(false);

			d = s.parse(meeting.getEndDate());
			to_dp.updateDate(d.getYear() + 1900, d.getMonth(), d.getDate());

			final TimePicker to_tp = (TimePicker) dialog.findViewById(R.id.dialog_modification_meeting_to_tp);
			to_tp.setCurrentHour(d.getHours());
			to_tp.setCurrentMinute(d.getMinutes());

			final EditText location_et = (EditText) dialog.findViewById(R.id.dialog_modification_meeting_location_et);
			location_et.setText(meeting.getAddress());

			Button validate_bt = (Button) dialog.findViewById(R.id.dialog_modification_meeting_validate_bt);
			validate_bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					meeting.setAddress(location_et.getText().toString());
					//MM-dd-yyyy HH:mm:ss
					Date d = new Date();
					d.setDate(to_dp.getDayOfMonth());
					d.setMonth(to_dp.getMonth());
					d.setYear(to_dp.getYear() - 1900);
					d.setHours(to_tp.getCurrentHour() + 2);
					d.setMinutes(to_tp.getCurrentMinute());
					SimpleDateFormat s = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
					meeting.setEndDate(s.format(d));
					d.setDate(from_dp.getDayOfMonth());
					d.setMonth(from_dp.getMonth());
					d.setYear(from_dp.getYear() - 1900);
					d.setHours(from_tp.getCurrentHour() + 2);
					d.setMinutes(from_tp.getCurrentMinute());
					meeting.setStartDate(s.format(d));

					meeting.setName(name_et.getText().toString());
					System.out.println("Test 2:" + meeting.getRestaurant_id());
					meeting.updateMeeting(act.getApplicationContext());
					dialog.cancel();
				}
			});

			dialog.show();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void modifyParticipants(final Meeting meeting) {
		final Dialog dialog = new Dialog(act);
		dialog.setTitle("Choose Participants");
		dialog.setContentView(R.layout.dialog_meeting_manage_participant);

		final List<Good_user> friends = new ArrayList<Good_user>();
		Project_traning_RestClient.getWithboddy(act.getApplicationContext(), "users/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				final List<List<Integer>> actv_id = new ArrayList<List<Integer>>();
				List<Good_user> friend_list = new ArrayList<Good_user>();
				System.out.println(response);
				Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
				friend_list = test.adaptToList(response, Good_user.class);
				for(int i = 0; i < friend_list.size(); i++)
					friends.add(friend_list.get(i));
				ArrayAdapter<Good_user> adapter = new ArrayAdapter<Good_user>(act.getApplicationContext(), R.layout.dialog_meeting_manage_participant_item, friends) {

					@Override
					public View getView(int position, View convertView,
							ViewGroup parent) {
						if (convertView == null)
							convertView = inflater.inflate(R.layout.dialog_meeting_manage_participant_item, null);
						CheckBox name_actv = (CheckBox) convertView.findViewById(R.id.dialog_meeting_manage_participant_item_name_check_cb);
						name_actv.setText(getItem(position).getUserName());
						name_actv.setChecked(false);

						List<Integer> l = new ArrayList<Integer>();
						l.add(name_actv.getId());
						l.add(getItem(position).getId());
						actv_id.add(l);

						return convertView;
					}

					@Override
					public Good_user getItem(int position) {
						return friends.get(position);
					}
				};
				ListView friends_lv = (ListView) dialog.findViewById(R.id.dialog_meeting_manage_participant_friends_lv);
				friends_lv.setAdapter(adapter);

				Button validate_bt = (Button) dialog.findViewById(R.id.dialog_meeting_manage_participant_validate_bt);
				validate_bt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						//update and reload the data
						List<Good_user> participants = meeting.getAllParticipants(act.getApplicationContext());
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						for (int i = 0; i < actv_id.size(); ++i) {
							CheckBox cb = (CheckBox) v.findViewById(actv_id.get(i).get(0));
							if (cb.isChecked()) {
								for (int j = 0; j < participants.size(); ++j) {
									if (cb.getText().toString().contentEquals(participants.get(j).getUserName()))
										break;
									else if (j == participants.size())
										meeting.addParticipantToMeeting(act.getApplicationContext(), String.valueOf(actv_id.get(i).get(1)));
								}
							}
						}
						dialog.cancel();
					}
				});
			}
			@Override
			public void onFailure(Throwable error) {
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(act.getApplicationContext(), "modifyParticipants : requette list users/read " , Toast.LENGTH_LONG).show();
			}
		});

		dialog.show();
	}

	protected void modifyOrders(Meeting meeting) {
	}
}
