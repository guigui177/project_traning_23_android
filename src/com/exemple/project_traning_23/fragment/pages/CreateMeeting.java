package com.exemple.project_traning_23.fragment.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.model.Meeting;
import com.example.project_traning_23.model.Restaurant;
import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CreateMeeting extends AFragment implements OnClickListener {

	private Dialog dialog;
	private Meeting meeting = new Meeting(null, null, null, null, null, null, null, null);
	private List<List<Integer>> actv_id = new ArrayList<List<Integer>>();
	private List<String> new_participants = new ArrayList<String>();


	@Override
	public int getMenuTitle() {
		return R.string.create_meeting_title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_create_meeting, container, false);

//		final AutoCompleteTextView actv = (AutoCompleteTextView) view.findViewById(R.id.fragment_create_meeting_restaurant_actv);
//		actv.setText("");
		Restaurant.getAllRestaurant(getActivity().getApplicationContext(), view, meeting, 0);
//		final List<Restaurant> restaurants = Restaurant.getAllRestaurant(getActivity().getApplicationContext());
//		List<String> autocstr = new ArrayList<String>();
//		for (int i = 0; i < restaurants.size(); ++i)
//			autocstr.add(restaurants.get(i).getName());
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_dropdown_item, autocstr);
//		actv.setAdapter(adapter);
//		actv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long rowId) {
//				meeting.setId_restaurant(restaurants.get(position).getId());
//			}
//		});

		Calendar today = Calendar.getInstance();
		final DatePicker fromdp = (DatePicker) view.findViewById(R.id.fragment_create_meeting_from_dp);
		final TimePicker fromtp = (TimePicker) view.findViewById(R.id.fragment_create_meeting_from_tp);
		final DatePicker todp = (DatePicker) view.findViewById(R.id.fragment_create_meeting_to_dp);
		final TimePicker totp = (TimePicker) view.findViewById(R.id.fragment_create_meeting_to_tp);
		fromdp.setCalendarViewShown(false);
		todp.setCalendarViewShown(false);
		fromdp.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), null);
		fromtp.setCurrentHour(today.get(Calendar.HOUR_OF_DAY));
		fromtp.setCurrentMinute(today.get(Calendar.MINUTE));
		todp.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), null);
		totp.setCurrentHour(today.get(Calendar.HOUR_OF_DAY) + 1);
		totp.setCurrentMinute(today.get(Calendar.MINUTE));

		final Button add_participant_b = (Button) view.findViewById(R.id.fragment_create_meeting_add_participant_bt);
		final Button validate_b = (Button) view.findViewById(R.id.fragment_create_meeting_validate_bt);
		add_participant_b.setOnClickListener(this);
		validate_b.setOnClickListener(this);

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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_create_meeting_add_participant_bt:
			//appel du pop_up add_participant
			this.dialog = new Dialog(getActivity());
			dialog.setTitle("Choose Participants");
			dialog.setContentView(R.layout.dialog_meeting_manage_participant);

			final List<Good_user> friends = new ArrayList<Good_user>();
			Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "users/read", null, 
					new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					List<Good_user> friend_list = new ArrayList<Good_user>();
					System.out.println(response);
					Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
					friend_list = test.adaptToList(response, Good_user.class);
					for(int i = 0; i < friend_list.size(); i++)
						friends.add(friend_list.get(i));
					
					ArrayAdapter<Good_user> adapter = new ArrayAdapter<Good_user>(getActivity(), R.layout.dialog_meeting_manage_participant_item, friends) {

						@Override
						public View getView(int position, View convertView,
								ViewGroup parent) {
							CheckBox name_actv = (CheckBox) convertView.findViewById(R.id.dialog_meeting_manage_participant_item_name_check_cb);
							name_actv.setText(getItem(position).getUserName());
							name_actv.setChecked(false);

							List<Integer> l = new ArrayList<Integer>();
							l.add(name_actv.getId());
							l.add(getItem(position).getId());
							actv_id.add(l);

							return convertView;
						}
					};
					ListView friends_lv = (ListView) dialog.findViewById(R.id.dialog_meeting_manage_participant_friends_lv);
					friends_lv.setAdapter(adapter);
				}
				@Override
				public void onFailure(Throwable error) {
					System.out.println(error.getLocalizedMessage());
					Toast.makeText(getActivity().getApplicationContext(), "requette list users faild " , Toast.LENGTH_LONG).show();
				}
			});


			Button validate_bt = (Button) dialog.findViewById(R.id.dialog_meeting_manage_participant_validate_bt);
			validate_bt.setOnClickListener(this);
			dialog.show();
			break;
		case R.id.fragment_create_meeting_validate_bt:
			//envoi de la requete au server
			//			Meeting new_meeting = new Meeting(id, name, date, participants, resto, status, notification);

			View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_create_meeting, null);
			EditText meeting_name = (EditText) view.findViewById(R.id.fragment_create_meeting_name_et);
			DatePicker from_dp = (DatePicker) view.findViewById(R.id.fragment_create_meeting_from_dp);
			TimePicker from_tp = (TimePicker) view.findViewById(R.id.fragment_create_meeting_from_tp);
			DatePicker to_dp = (DatePicker) view.findViewById(R.id.fragment_create_meeting_to_dp);
			TimePicker to_tp = (TimePicker) view.findViewById(R.id.fragment_create_meeting_to_tp);
			EditText location = (EditText) view.findViewById(R.id.fragment_create_meeting_location_et);
			
			meeting.setAddress(location.getText().toString());
			meeting.setName(meeting_name.getText().toString());
			Date d = new Date();
			d.setDate(to_dp.getDayOfMonth());
			d.setMonth(to_dp.getMonth());
			d.setYear(to_dp.getYear());
			d.setHours(to_tp.getCurrentHour());
			d.setMinutes(to_tp.getCurrentMinute());
			SimpleDateFormat s = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			meeting.setEndDate(s.format(d));
			d.setDate(from_dp.getDayOfMonth());
			d.setMonth(from_dp.getMonth());
			d.setYear(from_dp.getYear());
			d.setHours(from_tp.getCurrentHour());
			d.setMinutes(from_tp.getCurrentMinute());
			meeting.setStartDate(s.format(d));
			
//			RECUPERER ID USER POUR CREATION DU MEETING
//			ET POUR LA LISTE DES PARTICIPANTS DU MEETING
			List<Object> o = new ArrayList<Object>();
			o.add(meeting);
			o.add(new_participants);
			Good_user.getUser(getActivity(), meeting, o);
//
//			meeting.setOwner_id(String.valueOf(Good_user.getUser(getActivity().getApplicationContext(), meeting).getId()));
//			meeting.createMeeting(getActivity(), new_participants);
//			
//			List<Meeting> meetings= Meeting.getAllMeetings(getActivity(), 1, null);
//			for (int i = 0; i < meetings.size(); ++i)
//				if (meeting.getName().equalsIgnoreCase(meetings.get(i).getName())) {
//					meeting = meetings.get(i);
//					break;
//				}
//			
//			for (int i = 0; i < new_participants.size(); ++i) {
//				meeting.addParticipantToMeeting(getActivity(), new_participants.get(i));
//			}
			
			//retour au fragment list meeting
			final FragmentManager fm = getActivity().getSupportFragmentManager();
			final FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.content_frame, new ListMeeting());
			// Null on the back stack to return on the previous fragment when user
			// press on back button.
			ft.addToBackStack(null);
			ft.commit();
			break;
		case R.id.dialog_meeting_manage_participant_validate_bt:
			List<Good_user> participants = meeting.getAllParticipants(getActivity().getApplicationContext());
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
							this.new_participants.add(String.valueOf(actv_id.get(i).get(1)));
					}
				}
			}
			dialog.cancel();
			break;
		default:
			break;
		}
	}

}
