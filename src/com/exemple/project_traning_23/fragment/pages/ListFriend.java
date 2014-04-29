package com.exemple.project_traning_23.fragment.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_traning_23.R;
import com.exemple.project_traning_23.fragment.AFragment;

public class ListFriend extends AFragment {

	@Override
	public int getMenuTitle() {
		return R.string.list_friend_title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list_friend, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}
}
