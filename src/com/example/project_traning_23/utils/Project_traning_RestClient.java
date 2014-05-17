package com.example.project_traning_23.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.example.project_traning_23.InternalStorage;
import com.example.project_traning_23.R;

import android.content.Context;
import android.widget.Toast;


public class Project_traning_RestClient {
	private static String BASE_URL = "http://192.168.173.1:3000";
	private static String API_URL = null;

	private final static AsyncHttpClient client = new AsyncHttpClient();
	static {
		client.setTimeout(Constants.DEFAULT_TIMEOUT);
	}

	public static AsyncHttpClient getClient() {
		return client;
	}

	public static void deleteWithboddy(final Context context, final String url, final RequestParams resquestparams, final AsyncHttpResponseHandler responseHandler)
	{
		Header[] headers = {
				new BasicHeader("userName", InternalStorage.getInstance(context).getUsername()),
				new BasicHeader("password", InternalStorage.getInstance(context).getUserpass()),
				new BasicHeader("Content-type", "application/json")
		};	
		android.util.Log.d("api", InternalStorage.getInstance(context).getUsername() + " , " + InternalStorage.getInstance(context).getUserpass());
		client.delete(context, getAbsoluteUrl(context, url), headers, responseHandler);
	}
	
	public static void getWithboddy(final Context context, final String url, final RequestParams resquestparams, final AsyncHttpResponseHandler responseHandler)
	{
		Header[] headers = {
				new BasicHeader("userName", InternalStorage.getInstance(context).getUsername()),
				new BasicHeader("password", InternalStorage.getInstance(context).getUserpass()),
				new BasicHeader("Content-type", "application/json")
		};	
		android.util.Log.d("api", InternalStorage.getInstance(context).getUsername() + " , " + InternalStorage.getInstance(context).getUserpass());
		client.get(context, getAbsoluteUrl(context, url), headers, resquestparams, responseHandler);
	}

	
	public static void postWithBody(final Context context, final String url, final String content, Boolean auth, final AsyncHttpResponseHandler responseHandler)
	{
		Header[] headers = {
				new BasicHeader("userName", InternalStorage.getInstance(context).getUsername()),
				new BasicHeader("password", InternalStorage.getInstance(context).getUserpass()),
				new BasicHeader("Content-type", "application/json")
		};	
		try {
			client.post(context,  getAbsoluteUrl(context, url), headers, new StringEntity(content), "application/json", responseHandler);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void putWithBody(final Context context, final String url, final String content, Boolean auth, final AsyncHttpResponseHandler responseHandler)
	{
		Header[] headers = {
				new BasicHeader("userName", InternalStorage.getInstance(context).getUsername()),
				new BasicHeader("password", InternalStorage.getInstance(context).getUserpass()),
				new BasicHeader("Content-type", "application/json")
		};	
		try {
			client.put(context,  getAbsoluteUrl(context, url), headers, new StringEntity(content), "application/json", responseHandler);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String getAbsoluteUrl(final Context context, final String relativeUrl) {
		if (BASE_URL == null)
			BASE_URL = context.getString(R.string.base_url);
		if (API_URL == null)
			API_URL = context.getString(R.string.api_url);
		return BASE_URL + API_URL  + relativeUrl;
	}
}
