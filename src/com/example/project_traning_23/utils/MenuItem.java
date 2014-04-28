package com.example.project_traning_23.utils;

import com.exemple.project_traning_23.fragment.IFragment;;

public class MenuItem 
{
	public static final int PREFERENCE_ACTIVITY = 1;
	
	private final IFragment 	mFragment;
	private final int 			mTitle;
	private final int 			mCallCode;
	private final Class<?>			mActivity;
	
	public MenuItem(IFragment mFragment)
	{
		this.mFragment = mFragment;
		this.mTitle = 0;
		this.mActivity = null;
		this.mCallCode = 0;
	}
	
	public MenuItem(IFragment mFragment, int mTitle, Class<?> mActivity, int mCallCode)
	{
		this.mFragment = mFragment;
		this.mTitle = mTitle;
		this.mActivity = mActivity;
		this.mCallCode = mCallCode;
	}
	
	public IFragment getFragment() {
		return mFragment;
	}
	
	public int getMenuTitle()
	{
		if (isFragment() == true)
			return mFragment.getMenuTitle();
		return mTitle;
	}
	
	public int getCallCode()
	{
		return mCallCode;
	}
	
	public Class<?> getActivity()
	{
		return mActivity;
	}
	
	public boolean isFragment()
	{
		if (mFragment == null)
			return false;
		else
			return true;
	}
}
