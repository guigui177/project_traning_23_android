package com.exemple.project_traning_23.fragment.pages;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;

import com.example.project_traning_23.R;
import com.exemple.project_traning_23.fragment.AFragment;

public class Webrestaurateur extends AFragment  {

	@Override
	public int getMenuTitle() {
		return R.string.web_title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_web, container, false);
		try {
		    Intent i = new Intent("android.intent.action.MAIN");
		    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
		    i.addCategory("android.intent.category.LAUNCHER");
		    i.setData(Uri.parse("http://mail.bjtu.edu.cn"));
		    startActivity(i);
		}
		catch(ActivityNotFoundException e) {
		    // Chrome is probably not installed
		}
		return v;
	}
	
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}
}
