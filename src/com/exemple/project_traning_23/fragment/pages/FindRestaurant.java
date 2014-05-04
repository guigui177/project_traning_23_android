package com.exemple.project_traning_23.fragment.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_traning_23.R;
import com.exemple.project_traning_23.fragment.AFragment;

public class FindRestaurant extends AFragment implements OnItemClickListener {

	private ListView listresto = null;
	private String[] listeStrings = {"resto 1","resto 2","resto 3"};

	@Override
	public int getMenuTitle() {
		return R.string.findrestaurant_title;		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View v = inflater.inflate(R.layout.fragment_listresto, container, false);
		 
		 listresto = (ListView) v.findViewById(R.id.fragment_listrestaurant_list_listresto);
		//  list resto requette get
			
		 listresto.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listeStrings));		 
		 listresto.setOnItemClickListener(this);
		 return v;	 
	}
	
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		String restoName  = (String)adapter.getItemAtPosition(position);
		
		Toast.makeText(getActivity(), " resto name = " + restoName , Toast.LENGTH_SHORT).show();
		
	}
}
