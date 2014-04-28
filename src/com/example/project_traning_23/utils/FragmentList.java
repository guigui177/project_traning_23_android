package com.example.project_traning_23.utils;

import com.exemple.project_traning_23.fragment.pages.About;

import com.example.project_traning_23.utils.MenuItem;

public enum FragmentList {
	
	
	
	APROPOS(new MenuItem(new About()));
		
	private final MenuItem mItem;
	
	private FragmentList(MenuItem mItem) {
		this.mItem = mItem;
	}
	
	public MenuItem getItem() {
		return mItem;
	}

	public static MenuItem findFragmentById(int mPosition) {
		return values()[mPosition].getItem();
	}
}
