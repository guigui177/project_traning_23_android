package com.example.project_traning_23.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.project_traning_23.R;
import com.example.project_traning_23.R.id;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.model.Meeting;
import com.example.project_traning_23.model.Participant;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
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
		final Meeting meeting = (Meeting) getChild(groupPosition, childPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_meeting_row_item, null);
			TextView date_tv = (TextView) convertView.findViewById(R.id.list_meeting_row_item_date_time_tv);
			date_tv.setText("Le : " + DateFormat.format("MMMd kk:mm", meeting.getDate()));

			TextView restaurant_name = (TextView) convertView.findViewById(R.id.list_meeting_row_item_restaurant_tv);
			restaurant_name.setText(meeting.getResto().getName());

			TextView order_tv = (TextView) convertView.findViewById(R.id.list_meeting_row_item_orders_tv);
			final String orders = new String();
			//				for (int i = 0; meeting.getOrder().get(i); ++i) {
			//					if (i != 0)
			//						orders += ", ";
			//					orders += meeting.getOrder().get(i).toString();
			//				}
			order_tv.setText(orders);

			LinearLayout l = (LinearLayout) convertView.findViewById(R.id.list_meeting_row_item_participants_l);
			for (int i = 0; i < meeting.getParticipants().size(); ++i) {
				View view = inflater.inflate(R.layout.list_meeting_row_item_participant_item, null);
				TextView name_tv = (TextView) view.findViewById(R.id.list_meeting_row_item_participant_item_name_tv);
				name_tv.setText(meeting.getParticipants().get(i).getUserName());
				//			tv.setTextColor(Color.BLACK);
				TextView status_tv = (TextView) view.findViewById(R.id.list_meeting_row_item_participant_item_status_tv);
				status_tv.setText(meeting.getParticipants().get(i).getStatus());
				l.addView(view);
			}
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
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

	private void modifyMeeting(Meeting meeting) {
		final Dialog dialog = new Dialog(act);
		dialog.setTitle("Modification");
		dialog.setContentView(R.layout.dialog_modification_meeting);

		EditText name_et = (EditText) dialog.findViewById(R.id.dialog_modification_meeting_name_et);
		name_et.setText(meeting.getName());
		
		AutoCompleteTextView actv = (AutoCompleteTextView) dialog.findViewById(R.id.dialog_modification_meeting_restaurant_name_actv);
		actv.setText("");
		final String[] autocstr = {"resto1", "resto2"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(act, R.layout.list_dropdown_item, autocstr);
		actv.setAdapter(adapter);

		DatePicker from_dp = (DatePicker) dialog.findViewById(R.id.dialog_modification_meeting_from_dp);
		from_dp.setCalendarViewShown(false);
		Toast.makeText(act, String.valueOf(meeting.getDate().getYear()), Toast.LENGTH_SHORT).show();
		from_dp.updateDate(meeting.getDate().getYear() + 1900, meeting.getDate().getMonth() - 1, meeting.getDate().getDate());

		TimePicker from_tp = (TimePicker) dialog.findViewById(R.id.dialog_modification_meeting_from_tp);
		from_tp.setCurrentHour(meeting.getDate().getHours());
		from_tp.setCurrentMinute(meeting.getDate().getMinutes());

		DatePicker to_dp = (DatePicker) dialog.findViewById(R.id.dialog_modification_meeting_to_dp);
		to_dp.updateDate(meeting.getDate().getYear() + 1900, meeting.getDate().getMonth() - 1, meeting.getDate().getDate());
		to_dp.setCalendarViewShown(false);

		TimePicker to_tp = (TimePicker) dialog.findViewById(R.id.dialog_modification_meeting_to_tp);
		to_tp.setCurrentHour(meeting.getDate().getHours());
		to_tp.setCurrentMinute(meeting.getDate().getMinutes());

		Button validate_bt = (Button) dialog.findViewById(R.id.dialog_modification_meeting_validate_bt);
		validate_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//send to server
				dialog.cancel();
			}
		});

		dialog.show();
	}

	protected void modifyParticipants(Meeting meeting) {
		final Dialog dialog = new Dialog(act);
		dialog.setTitle("Choose Participants");
		dialog.setContentView(R.layout.dialog_meeting_manage_participant);

		final List<Good_user> friends = new ArrayList<Good_user>();
		Project_traning_RestClient.getWithboddy(act.getApplicationContext(), "users/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				List<Good_user> friend_list = new ArrayList<Good_user>();
				System.out.println(response);
				Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
				friend_list = test.adaptToList(response, Good_user.class);
				for(int i = 0; i < friend_list.size(); i++)
					friends.add(friend_list.get(i));
			}
			@Override
			public void onFailure(Throwable error) {
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(act.getApplicationContext(), "requette list users faild " , Toast.LENGTH_LONG).show();
			}
		});

		Good_user gu1 = new Good_user();
		gu1.setUserName("toto");
		Good_user gu2 = new Good_user();
		gu2.setUserName("titi");
		friends.add(gu1);
		friends.add(gu2);
		final List<List<Integer>> ctv_id = new ArrayList<List<Integer>>();
		ArrayAdapter<Good_user> adapter = new ArrayAdapter<Good_user>(act.getApplicationContext(), R.layout.dialog_meeting_manage_participant_item, friends) {

			@Override
			public View getView(int position, View convertView,
					ViewGroup parent) {
				if (convertView == null)
					convertView = inflater.inflate(R.layout.dialog_meeting_manage_participant_item, null);
				CheckBox name_ctv = (CheckBox) convertView.findViewById(R.id.dialog_meeting_manage_participant_item_name_check_cb);
				name_ctv.setText(getItem(position).getUserName());
				name_ctv.setChecked(false);

				List<Integer> l = new ArrayList<Integer>();
				l.add(name_ctv.getId());
				l.add(getItem(position).getId());
				ctv_id.add(l);
				
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
				dialog.cancel();
			}
		});
		dialog.show();
	}

	protected void modifyOrders(Meeting meeting) {
	}
}
