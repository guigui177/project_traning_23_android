package com.exemple.project_traning_23.fragment.pages;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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

import org.apache.http.Header;
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
	private String listuser_json; 
	private String listfriend_json;
 
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

		// requette list Users
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "users/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				System.out.println(response);
				listuser_json = response;
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(getActivity().getApplicationContext(), "requette list users faild " , Toast.LENGTH_LONG).show();
			}
		});
		
		//  list friend requette get
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "users/friends/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				System.out.println(response);
				listfriend_json = response;
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(getActivity().getApplicationContext(), "requette list friend faild " , Toast.LENGTH_LONG).show();
			}
		});
		
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
					String friendname = editTextUserName.getText().toString();
					//  add friend requette post a tester -------------------------------------------------------
					JSONObject json = new JSONObject();
					try {
						json.put("friend_id", friendname);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					Log.d("api",json.toString());
					Project_traning_RestClient.postWithBody(getActivity().getApplicationContext(), "/users/friends/add", json.toString(), false, 
							new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							System.out.println(response);
							Toast.makeText(getActivity().getApplicationContext(), "starting add friend reponce = " + response , Toast.LENGTH_LONG).show();
						}
						@Override
						public void onStart()
						{
							Toast.makeText(getActivity().getApplicationContext(), "starting add friend request" , Toast.LENGTH_LONG).show();
						}
					});
					
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
					String friendname = (String)adapter.getItemAtPosition(position);				
					//Toast.makeText(getActivity(), "del friend username = " + friendname , Toast.LENGTH_SHORT).show();
					//  del friend requette post  a tester ----------------------------------------------------------------
					JSONObject json = new JSONObject();
					try {
						json.put("friend_id", friendname);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					Log.d("api",json.toString());
					Project_traning_RestClient.postWithBody(getActivity().getApplicationContext(), "/users/friends/remove", json.toString(), false, 
							new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							System.out.println(response);
							Toast.makeText(getActivity().getApplicationContext(), "starting remove friend reponce = " + response , Toast.LENGTH_LONG).show();
						}
						@Override
						public void onStart()
						{
							Toast.makeText(getActivity().getApplicationContext(), "starting remove friend request" , Toast.LENGTH_LONG).show();
						}
					});
					
					dialog2.cancel();
				}
			});
			dialog2.show();
			break;
		}

	}
}
