package com.exemple.project_traning_23.fragment.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_traning_23.R;
import com.example.project_traning_23.model.Meeting;
import com.exemple.project_traning_23.fragment.AFragment;

public class ListMeeting  extends AFragment {

	private Meeting meeting;
	@Override
	public int getMenuTitle() {
		return R.string.list_meeting;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list_meeting, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}
	
	
}