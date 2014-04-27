package com.example.project_traning_23.utils;

public class Log {
	public static void d(final String l, final String msg)
	{
		android.util.Log.d("Justmove: "+l, msg);
	}
	
	public static void e(final String l, final String msg, final Throwable e)
	{
		android.util.Log.e("Justmove: "+l, msg, e);
	}
}
