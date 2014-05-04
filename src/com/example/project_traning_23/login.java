package com.example.project_traning_23;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.StringEntity;

import com.example.project_traning_23.utils.Project_traning_AdaptResponse;
import com.example.project_traning_23.utils.Project_traning_RestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;



public class login extends Activity implements OnClickListener {

	private EditText userName;
	private EditText userPass;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login);

		findViewById(R.id.login_layout_button_connection).setOnClickListener(this);
		findViewById(R.id.login_layout_button_create_acc).setOnClickListener(this);
		userName = (EditText) findViewById(R.id.fragment_login_textfield_username);
		userPass = (EditText) findViewById(R.id.fragment_login_textfield_mdp);
	}
	public void makeToast(String str)
	{
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.login_layout_button_connection :
			Toast.makeText(this, "login button", Toast.LENGTH_SHORT).show();
			String name= userName.getText().toString();
			String password= userPass.getText().toString();

			Toast.makeText(this, "user = " + name + " MDP = " + password , Toast.LENGTH_SHORT).show();
			// send conection request post

			InternalStorage internal = new InternalStorage(getApplicationContext());
			internal.setUsername(name);
			internal.setUserpass(password);

			Toast.makeText(this, internal.getUsername(), Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(this, Project_traning_2_3.class);
			startActivity(intent);
			break;
		case R.id.login_layout_button_create_acc :
			// create acount 
			final Dialog dialog = new Dialog(this);
			dialog.setTitle("Create account");
			dialog.setContentView(R.layout.fragment_createaccount);
			final  EditText editTextUsermail=(EditText)dialog.findViewById(R.id.fragment_create_login_textfield_mail);
			final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.fragment_create_login_textfield_mdp);
			final  EditText editTextPasswordconf=(EditText)dialog.findViewById(R.id.fragment_create_login_textfield_mdp_confirmation);
			final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.fragment_create_login_textfield_name);
			Button buttoncreate=(Button)dialog.findViewById(R.id.fragment_create_login_button_valider);
			buttoncreate.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					String usermail=editTextUserName.getText().toString();
					String username=editTextUserName.getText().toString();
					String password=editTextPassword.getText().toString();
					String passwordconf=editTextPasswordconf.getText().toString();

					Toast.makeText(login.this, userName + " " + password + " " + passwordconf , Toast.LENGTH_LONG).show();
					// create account requette post

					// test
					RequestParams users = new RequestParams();
					users.put("user",  username);
					users.put("pass",  password);
					Project_traning_RestClient.post(getApplicationContext(), "users/create", users, new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							System.out.println(response);
						}
						@Override
						public void onStart()
						{
							Toast.makeText(getApplicationContext(), "starting" + this.toString(), Toast.LENGTH_LONG).show();
						}
					});

					/*
					Map<String, String> entry = new HashMap<String , String>();
					entry.put("userName", username);
					entry.put("password", password);
					entry.put("mail", usermail);

					try {
						Project_traning_RestClient.post(getApplicationContext(), "users/create",
								new StringEntity(Project_traning_AdaptResponse.toJson(entry)), "application/json",
								new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(String response) {
								System.out.println(response);
							}
							@Override
							public void onStart()
							{
								Toast.makeText(getApplicationContext(), "starting" + this.toString(), Toast.LENGTH_LONG).show();
							}


						});
					} catch (UnsupportedEncodingException
							| JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 */

					InternalStorage internal = new InternalStorage(getApplicationContext());
					internal.setUsername(username);
					internal.setUserpass(password);
					dialog.cancel();
				}
			});
			dialog.show();
			break;
		}
	}
}
