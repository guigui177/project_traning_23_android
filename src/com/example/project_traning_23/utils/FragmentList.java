package com.example.project_traning_23.utils;

import com.exemple.project_traning_23.fragment.pages.About;
import com.exemple.project_traning_23.fragment.pages.NfcReader;
import com.exemple.project_traning_23.fragment.pages.FindRestaurant;
import com.exemple.project_traning_23.fragment.pages.FindRestaurantMap;
import com.exemple.project_traning_23.fragment.pages.OrderMeal;
import com.exemple.project_traning_23.fragment.pages.ListFriend;
import com.exemple.project_traning_23.fragment.pages.Webrestaurateur;

import com.example.project_traning_23.utils.MenuItem;

public enum FragmentList {
	

	//	
	FINDRESTAURANT(new MenuItem(new FindRestaurant())),
	FINDRESTAURANTMAP(new MenuItem(new FindRestaurantMap())),
	ORDERMEAL(new MenuItem(new OrderMeal())),
	LISTFRIEND(new MenuItem(new ListFriend())),
	NFCREADER(new MenuItem(new NfcReader())),
	WEB(new MenuItem(new Webrestaurateur())),
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
