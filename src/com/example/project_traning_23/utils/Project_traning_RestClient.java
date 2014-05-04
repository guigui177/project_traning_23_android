package com.example.project_traning_23.utils;

import org.apache.http.entity.StringEntity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.example.project_traning_23.R;

import android.content.Context;


public class Project_traning_RestClient {
	private static String BASE_URL = "http://192.168.0.101:3000";
	private static String API_URL = null;

	private final static AsyncHttpClient client = new AsyncHttpClient();
	static {
		client.setTimeout(Constants.DEFAULT_TIMEOUT);
	}

	public static AsyncHttpClient getClient() {
		return client;
	}
	
	/**
	* This method allow use to launch an HTTP GET Request
	*
	* @param context	The application context
	* @param url		The url of the request without the domain
	* @param params		The http params send during the request
	* @param responseHandler	The handler for the return of the request
	*/
	public static void get(final Context context, final String url, final RequestParams params, final AsyncHttpResponseHandler responseHandler) {
		if (params != null)
			Log.d("api", getAbsoluteUrl(context, url) + "?"+ params.toString());
		else
			Log.d("api", getAbsoluteUrl(context, url));
		client.get(getAbsoluteUrl(context, url), params, responseHandler);
	}

	/**
	* This method allow use to launch an HTTP POST Request
	*
	* @param context	The application context
	* @param url		The url of the request without the domain
	* @param params		The http params send during the request
	* @param responseHandler	The handler for the return of the request
	*/
	public static void post(final Context context, final String url, final RequestParams params, final AsyncHttpResponseHandler responseHandler) {
		if (params != null)
			Log.d("api", getAbsoluteUrl(context, url) + "?"+ params.toString());
		else
			Log.d("api", getAbsoluteUrl(context, url));
		client.post(getAbsoluteUrl(context, url), params, responseHandler);
	}
	
	/**
	* This method allow use to launch an HTTP POST Request
	*
	* @param context	The application context
	* @param url		The url of the request without the domain
	* @param params		The http params send during the request
	* @param contentType	The content-type of the request
	* @param responseHandler	The handler for the return of the request
	*/
	public static void post(final Context context, final String url, final StringEntity params, final String contentType, final AsyncHttpResponseHandler responseHandler) {
		if (params != null)
			Log.d("api", getAbsoluteUrl(context, url) + "?"+ params.toString());
		else
			Log.d("api", getAbsoluteUrl(context, url));
		client.post(context, getAbsoluteUrl(context, url), params, contentType, responseHandler);
	}

	private static String getAbsoluteUrl(final Context context, final String relativeUrl) {
		if (BASE_URL == null)
			BASE_URL = context.getString(R.string.base_url);
		if (API_URL == null)
			API_URL = context.getString(R.string.api_url);
		return BASE_URL + API_URL  + relativeUrl;
	}
//	public static void loadImage(Context context,String img_path, ImageView img)
//	{
//		loadImage(context, img_path, img, null);
//	}
//	
//	public static void loadImage(Context context, String img_path, ImageView img, DisplayImageOptions options)
//	{
//		if (BASE_URL == null)
//			BASE_URL = context.getString(R.string.base_url);
//		if (img_path != null)
//			ImageLoader.getInstance().displayImage(BASE_URL + img_path, img, options);
//	}
}
