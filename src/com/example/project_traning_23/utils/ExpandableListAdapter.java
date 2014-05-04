package com.example.project_traning_23.utils;

import java.util.List;

import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Meeting;
import com.example.project_traning_23.model.Participant;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter{

	private List<Meeting> meetings;
	private LayoutInflater inflater;

	public ExpandableListAdapter(List<Meeting> meetings, Activity activity) {
		super();
		this.meetings = meetings;
		this.inflater = activity.getLayoutInflater();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return meetings.get(groupPosition).getParticipants().get(childPosition).getUser().getName();
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
		      boolean isLastChild, View convertView, ViewGroup parent) {
		final Participant participant = (Participant) getChild(groupPosition, childPosition);
		TextView participant_name_tv = null;
		TextView participant_status_tv = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_meeting_row_item, null);
		}
		participant_name_tv = (TextView) convertView.findViewById(R.id.list_meeting_row_item_participant_name_tv);
		participant_status_tv = (TextView) convertView.findViewById(R.id.list_meeting_row_item_participant_status_tv);
		
		participant_name_tv.setText(participant.getUser().getName());
		participant_status_tv.setText(participant.getStatus().toString());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.meetings.get(groupPosition).getParticipants().size();
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
		Meeting meeting = meetings.get(groupPosition);
		TextView name_restaurant_tv = (TextView) convertView.findViewById(R.id.list_meeting_group_item_name_restaurant_tv);
		TextView date_tv = (TextView) convertView.findViewById(R.id.list_meeting_group_item_date_tv);
		TextView nb_participant = (TextView) convertView.findViewById(R.id.list_meeting_group_item_nb_participant_tv);
		TextView status = (TextView) convertView.findViewById(R.id.list_meeting_group_item_meeting_status_tv);
		
		name_restaurant_tv.setText(meeting.getResto().getName());
		date_tv.setText(DateFormat.format("D HH:mm", meeting.getDate()));
		nb_participant.setText(String.valueOf(meeting.getParticipants().size()));
		status.setText(meeting.getStatus().toString());
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
}
