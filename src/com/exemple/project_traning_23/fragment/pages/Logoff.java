package com.exemple.project_traning_23.fragment.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_traning_23.InternalStorage;
import com.example.project_traning_23.R;
import com.example.project_traning_23.login;
import com.exemple.project_traning_23.fragment.AFragment;

public class Logoff extends AFragment {

	@Override
	public int getMenuTitle() {
		return R.string.logoff_title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Intent intent = new Intent(getActivity(), login.class);	
		startActivity(intent);
		return inflater.inflate(R.layout.fragment_about, container, false);	
	}
	
	
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}
}
