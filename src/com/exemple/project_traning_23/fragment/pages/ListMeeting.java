package com.exemple.project_traning_23.fragment.pages;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Meeting;
import com.example.project_traning_23.model.Participant;
import com.example.project_traning_23.model.Restaurant;
import com.example.project_traning_23.model.Status;
import com.example.project_traning_23.utils.ExpandableListAdapterForMeeting;
import com.exemple.project_traning_23.fragment.AFragment;

public class ListMeeting  extends AFragment {

	private List<Meeting> meetings;
	@Override
	public int getMenuTitle() {
		return R.string.list_meeting;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
		testData();
		ExpandableListAdapterForMeeting elafm = new ExpandableListAdapterForMeeting(getActivity(), meetings);
		ExpandableListView expla = (ExpandableListView) view.findViewById(R.id.fragment_list_meeting_Explv);
		expla.setAdapter(elafm);
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}
	
	public void testData() {
		List<Participant> participants1 = new ArrayList<Participant>();
		participants1.add(new Participant(1, "toto", Status.CONFIRMED));
		participants1.add(new Participant(2, "titi", Status.DECLINED));
		participants1.add(new Participant(3, "tata", Status.PENDING));

		List<Participant> participants2 = new ArrayList<Participant>();
		participants2.add(new Participant(1, "mimi", Status.CONFIRMED));
		participants2.add(new Participant(2, "momo", Status.DECLINED));
		participants2.add(new Participant(3, "mama", Status.PENDING));
		
		GregorianCalendar date = new GregorianCalendar(2014, 6, 6, 6, 6);
		
		Meeting meeting1 = new Meeting(1, date.getTime(), participants1, new Restaurant("coco", 50, 30, "pas loin", "coco@html.fr", (float)75.02, (float)46.05), Status.CONFIRMED, true);
		Meeting meeting2 = new Meeting(2, date.getTime(), participants2, new Restaurant("hello", 50, 30, "par ici", "hello@html.fr", (float)75.02, (float)46.05), Status.PENDING, true);

		this.meetings = new ArrayList<Meeting>();
		this.meetings.add(meeting1);
		this.meetings.add(meeting2);
	}
}
