package com.exemple.project_traning_23.fragment.pages;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Meeting;
import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
		Button delete_b = (Button) view.findViewById(R.id.fragment_list_meeting_delete_b);
		delete_b.setOnClickListener(this);
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
		case R.id.fragment_list_meeting_delete_b:
			final Dialog d = new Dialog(getActivity());
			d.setTitle("Delete Meeting");
			d.setContentView(R.layout.dialog_meeting_delete);
			final LinearLayout linearl = (LinearLayout) d.findViewById(R.id.dialog_meeting_delete_l);
			linearl.removeAllViews();

			Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "meetings/read", null, 
					new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					List<Meeting> rep = new ArrayList<Meeting>();
					System.out.println(response);
					Project_traning_AdaptResponse<Meeting> test = new Project_traning_AdaptResponse<Meeting>();
					rep = test.adaptToList(response, Meeting.class);
					for (int i = 0; i < rep.size(); ++i) {
						final Meeting m = rep.get(i);
						TextView m_name_tv = new TextView(getActivity().getApplicationContext());
						m_name_tv.setText(m.getName());
						m_name_tv.setTextColor(Color.BLACK);
						m_name_tv.setClickable(true);
						m_name_tv.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Project_traning_RestClient.deleteWithboddy(getActivity(), "meetings/" + m.getId() + "/delete", null, new AsyncHttpResponseHandler() {

									@Override
									public void onSuccess(String response) {
										if (response != null)
											System.out.println(response);
									}

									@Override
									public void onFailure(Throwable error) {
										if (error != null)
											System.out.println(error.getLocalizedMessage());
										Toast.makeText(getActivity().getApplicationContext(), "DleteMeeting : failed " , Toast.LENGTH_LONG).show();
									}

								});
								d.cancel();
							}
						});
						linearl.addView(m_name_tv);
					}
					d.setContentView(linearl);
				}
				@Override
				public void onFailure(Throwable error)
				{
					System.out.println(error.getLocalizedMessage());
					Toast.makeText(getActivity().getApplicationContext(), "getAllMeetings : failed " , Toast.LENGTH_LONG).show();
				}
			});
			d.show();

		default:
			break;
		}
	}
	//
	//	public void setMeetingsDatas() {
	//		//recuperation des informations de tous les meetings
	//	}
}
