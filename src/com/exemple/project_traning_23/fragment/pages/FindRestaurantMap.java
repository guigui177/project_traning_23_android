package com.exemple.project_traning_23.fragment.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_traning_23.R;
import com.exemple.project_traning_23.fragment.AFragment;

public class FindRestaurantMap extends AFragment {

	@Override
	public int getMenuTitle() {
		return R.string.findrestaurantmap_title;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_restaurant_map, container, false);
		//		MapView map	= v.findViewById(R.id.)
		
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}
}
