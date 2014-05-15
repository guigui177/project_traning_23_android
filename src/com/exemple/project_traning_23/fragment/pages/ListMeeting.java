package com.exemple.project_traning_23.fragment.pages;

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
import android.widget.Button;
import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Meeting;
import com.exemple.project_traning_23.fragment.AFragment;

public class ListMeeting  extends AFragment implements OnClickListener {

//	private List<Meeting> meetings;
	@Override
	public int getMenuTitle() {
		return R.string.list_metting_title;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
//		testData();
//		setMeetingsDatas();
		Log.d("debug", "avant expandable");
		Meeting.getAllMeetings(getActivity(), 0, view);
//		ExpandableListAdapterForMeeting elafm = new ExpandableListAdapterForMeeting(getActivity(), Meeting.getAllMeetings(getActivity().getApplicationContext()));
		Log.d("debug", "apres expandable");
//		ExpandableListView expla = (ExpandableListView) view.findViewById(R.id.fragment_list_meeting_Explv);
//		expla.setAdapter(elafm);
		Button add_b = (Button) view.findViewById(R.id.fragment_list_meeting_add_b);
		add_b.setOnClickListener(this);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

//	public void testData() {
//		List<Participant> participants1 = new ArrayList<Participant>();
//		participants1.add(new Participant(1, "toto", Status.CONFIRMED));
//		participants1.add(new Participant(2, "titi", Status.DECLINED));
//		participants1.add(new Participant(3, "tata", Status.PENDING));
//
//		List<Participant> participants2 = new ArrayList<Participant>();
//		participants2.add(new Participant(1, "mimi", Status.CONFIRMED));
//		participants2.add(new Participant(2, "momo", Status.DECLINED));
//		participants2.add(new Participant(3, "mama", Status.PENDING));
//
//		GregorianCalendar date = new GregorianCalendar(2014, 1, 1, 1, 0);
//		
//		Meeting meeting1 = new Meeting(1, "anniv", date.getTime(), participants1, new Restaurant("coco", 50, 30, "pas loin", "coco@html.fr", (float)75.02, (float)46.05), Status.CONFIRMED, true);
//		Meeting meeting2 = new Meeting(2, "boulot", date.getTime(), participants2, new Restaurant("hello", 50, 30, "par ici", "hello@html.fr", (float)75.02, (float)46.05), Status.PENDING, true);
//
//		this.meetings = new ArrayList<Meeting>();
//		this.meetings.add(meeting1);
//		this.meetings.add(meeting2);
//	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_meeting_list, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_list_meeting_add_b:
			//appel du fragment ajout meeting
			final FragmentManager fm = getActivity().getSupportFragmentManager();
			final FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.content_frame, new CreateMeeting());
			// Null on the back stack to return on the previous fragment when user
			// press on back button.
			ft.addToBackStack(null);
			ft.commit();
			break;
		default:
			break;
		}
	}
//
//	public void setMeetingsDatas() {
//		//recuperation des informations de tous les meetings
//	}
}
