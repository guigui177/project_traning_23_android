package com.exemple.project_traning_23.fragment.pages;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_traning_23.InternalStorage;
import com.example.project_traning_23.R;
import com.example.project_traning_23.login;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.fasterxml.jackson.core.sym.Name;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ListFriend extends AFragment implements OnClickListener {

	private String[] listeStrings = {"toto","titi","tata"};
	private ListView listFriend = null;

	@Override
	public int getMenuTitle() {
		return R.string.list_friend_title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list_friend, container, false);
		v.findViewById(R.id.fragment_listfriend_button_addfriend).setOnClickListener(this);
		v.findViewById(R.id.fragment_listfriend_button_delfriend).setOnClickListener(this);
		listFriend = (ListView) v.findViewById(R.id.fragment_listfriend_list_listfriend);

		//  add friend requette get
		
		listFriend.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listeStrings));
		 
		return v;
	}


	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_listfriend_button_addfriend :
			final Dialog dialog = new Dialog(getActivity());
			dialog.setTitle("Add friend");
			dialog.setContentView(R.layout.fragment_add_friend);
			final  EditText editTextUserName = (EditText)dialog.findViewById(R.id.fragment_addfriend_inputtext_friendname);
			Button buttoncreate=(Button)dialog.findViewById(R.id.fragment_addfriend_button_addfriend);
			buttoncreate.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					String username = editTextUserName.getText().toString();					
					//  add friend requette post
					Toast.makeText(getActivity(), "add friend username = " + username , Toast.LENGTH_SHORT).show();
					dialog.cancel();

				}
			});
			dialog.show();
			break;
		case R.id.fragment_listfriend_button_delfriend :
			final Dialog dialog2 = new Dialog(getActivity());
			dialog2.setTitle("Delete friend");
			dialog2.setContentView(R.layout.fragment_del_friend);
			final  ListView dellistfriend = (ListView)dialog2.findViewById(R.id.fragment_delfriend_list_listfriend);
			dellistfriend.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listeStrings));
			dellistfriend.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
					String value = (String)adapter.getItemAtPosition(position);				
					Toast.makeText(getActivity(), "del friend username = " + value , Toast.LENGTH_SHORT).show();
					//  del friend requette post
					dialog2.cancel();
				}
			});
			dialog2.show();
			break;
		}

	}
}
