package com.exemple.project_traning_23.fragment.pages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CreateMeeting extends AFragment implements OnClickListener {

	static final int TIME_DIALOG_ID = 999;
	private Dialog dialog;

	@Override
	public int getMenuTitle() {
		return R.string.create_meeting_title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final String[] autocstr = {"resto1", "resto2"};
		View view = inflater.inflate(R.layout.fragment_create_meeting, container, false);

		final AutoCompleteTextView actv = (AutoCompleteTextView) view.findViewById(R.id.fragment_create_meeting_restaurant_actv);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_create_meeting, autocstr);
		actv.setAdapter(adapter);

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
		totp.setCurrentHour(today.get(Calendar.HOUR_OF_DAY));
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
		switch (item.getItemId()) {
		case R.id.menu_add_meeting_validate_bt:
			//envoyer la requete au serveur
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
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
				}
				@Override
				public void onFailure(Throwable error) {
					System.out.println(error.getLocalizedMessage());
					Toast.makeText(getActivity().getApplicationContext(), "requette list users faild " , Toast.LENGTH_LONG).show();
				}
			});


			ArrayAdapter<Good_user> adapter = new ArrayAdapter<Good_user>(getActivity(), R.layout.dialog_meeting_manage_participant_item, friends) {

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					CheckedTextView name_ctv = (CheckedTextView) convertView.findViewById(R.id.dialog_meeting_manage_participant_item_name_ct);
					name_ctv.setText(getItem(position).getUserName());
					name_ctv.setChecked(false);
					return convertView;
				}
			};
			ListView friends_lv = (ListView) dialog.findViewById(R.id.dialog_meeting_manage_participant_friends_lv);
			friends_lv.setAdapter(adapter);
			
			Button validate_bt = (Button) dialog.findViewById(R.id.dialog_meeting_manage_participant_validate_bt);
			validate_bt.setOnClickListener(this);
			dialog.show();
			break;
			case R.id.fragment_create_meeting_validate_bt:
				//envoi de la requete au server
				//retour au fragment list meeting
				break;
			case R.id.dialog_meeting_manage_participant_validate_bt:
				//send the new information to the api
				dialog.cancel();
				break;
			default:
				break;
		}
	}

}
