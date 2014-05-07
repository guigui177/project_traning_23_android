package com.exemple.project_traning_23.fragment.pages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.Header;

import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.model.Good_user;
import com.example.project_traning_23.model.User;
import com.example.project_traning_23.InternalStorage;
import com.example.project_traning_23.R;
import com.example.project_traning_23.login;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.exemple.project_traning_23.fragment.AFragment;
import com.fasterxml.jackson.core.sym.Name;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ListFriend extends AFragment implements OnClickListener {

	private ListView listFriend = null;
	private List<Good_user> friend_list = null; 
	private List<Good_user> userlist = null;
	private LinkedList<String> usernamelist = new LinkedList<String>();
	private LinkedList<String> friendnamelist = new LinkedList<String>();
	private int addfriend_id = -1;
	private String addfriend_name = null;

	
	
	@Override
	public int getMenuTitle() {
		return R.string.list_friend_title;
	}

	public void getuserrequest()
	{
		usernamelist.clear();
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "users/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				userlist = new ArrayList<Good_user>();
				System.out.println(response);
				Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
				userlist = test.adaptToList(response, Good_user.class);
				Good_user t;
				for(int i = 0; i < userlist.size(); i++)
				{
					t = userlist.get(i);					
					usernamelist.add(t.getUserName());
				}		
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(getActivity().getApplicationContext(), "requette list users faild " , Toast.LENGTH_LONG).show();
			}
		});
	}

	public void getfriendrequest()
	{
		friendnamelist.clear();
		Project_traning_RestClient.getWithboddy(getActivity().getApplicationContext(), "users/friends/read", null, 
				new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				friend_list = new ArrayList<Good_user>();
				System.out.println(response);
				Project_traning_AdaptResponse<Good_user> test = new Project_traning_AdaptResponse<Good_user>();
				friend_list = test.adaptToList(response, Good_user.class);
				Good_user t;
				for(int i = 0; i < friend_list.size(); i++)
				{
					t = friend_list.get(i);					
					friendnamelist.add(t.getUserName());
				}	
				listFriend.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,friendnamelist));
			}
			@Override
			public void onFailure(Throwable error)
			{
				System.out.println(error.getLocalizedMessage());
				Toast.makeText(getActivity().getApplicationContext(), "requette list friend faild " , Toast.LENGTH_LONG).show();
			}
		});	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list_friend, container, false);
		v.findViewById(R.id.fragment_listfriend_button_addfriend).setOnClickListener(this);
		v.findViewById(R.id.fragment_listfriend_button_delfriend).setOnClickListener(this);
		listFriend = (ListView) v.findViewById(R.id.fragment_listfriend_list_listfriend);
		usernamelist.clear();
		friendnamelist.clear();
		getuserrequest();
		getfriendrequest();
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
			final  ListView listUserName = (ListView)dialog.findViewById(R.id.fragment_add_friend_list_list);
			listUserName.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, usernamelist));
			listUserName.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{	
					addfriend_id = userlist.get(position).getId();
					addfriend_name = userlist.get(position).getUserName();
					//  add friend requette post a tester -------------------------------------------------------
					JSONObject json = new JSONObject();
					Project_traning_RestClient.putWithBody(getActivity().getApplicationContext(), "users/friends/add/" + addfriend_id, json.toString(), false, 
							new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							System.out.println(response);
							Toast.makeText(getActivity().getApplicationContext(), addfriend_name + " are add to your friend list", Toast.LENGTH_LONG).show();
							getuserrequest();
							getfriendrequest();
						}
						public void onFailure(Throwable error)
						{
							System.out.println(error.getLocalizedMessage());
							Toast.makeText(getActivity().getApplicationContext(), "requette add friend faild " , Toast.LENGTH_LONG).show();
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
			dellistfriend.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,friendnamelist));
			dellistfriend.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{	
					addfriend_id = friend_list.get(position).getId();
					addfriend_name = friend_list.get(position).getUserName();
					//  add friend requette post a tester -------------------------------------------------------
					JSONObject json = new JSONObject();
					Project_traning_RestClient.putWithBody(getActivity().getApplicationContext(), "users/friends/remove/" + addfriend_id, json.toString(), false, 
							new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							System.out.println(response);
							Toast.makeText(getActivity().getApplicationContext(), addfriend_name + " are remove to your friend list", Toast.LENGTH_LONG).show();
							getuserrequest();
							getfriendrequest();
						}
						public void onFailure(Throwable error)
						{
							System.out.println(error.getLocalizedMessage());
							Toast.makeText(getActivity().getApplicationContext(), "requette add friend faild " , Toast.LENGTH_LONG).show();
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
