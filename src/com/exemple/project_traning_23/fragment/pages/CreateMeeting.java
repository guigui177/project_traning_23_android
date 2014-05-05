package com.exemple.project_traning_23.fragment.pages;

import java.util.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.project_traning_23.R;
import com.exemple.project_traning_23.fragment.AFragment;

public class CreateMeeting extends AFragment {

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
		fromdp.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), null);
		fromtp.setCurrentHour(today.get(Calendar.HOUR_OF_DAY));
		fromtp.setCurrentMinute(today.get(Calendar.MINUTE));
		todp.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), null);
		totp.setCurrentHour(today.get(Calendar.HOUR_OF_DAY));
		totp.setCurrentMinute(today.get(Calendar.MINUTE));
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

}
