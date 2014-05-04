package com.example.project_traning_23;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class InternalStorage {

	private static InternalStorage me;
	private String PREFS_NAME = "OurPrefs";
	private String username;	
	private String userpass;
	private Context context;

	InternalStorage(Context context){
		this.context = context;
	  }
	
	public static InternalStorage getInstance(Context context) {
        if (me == null) {
            me = new InternalStorage(context);
        }
        return me;
    }
	
	public String getUsername() {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		
		//return settings.getString("username", "");
		return "";
	}
	
	public String getUserpass() {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString("userpass", ""); 
	}
	
	
	public void setUsername(String username) {
	SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	SharedPreferences.Editor editor = settings.edit();
	editor.putString("username", username);
	this.username = username;
	editor.commit();
}

public void setUserpass(String userpass) {
	SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	SharedPreferences.Editor editor = settings.edit();
	editor.putString("userpass", userpass);
	this.userpass = userpass;
	editor.commit();
}



}
