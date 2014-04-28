package com.exemple.project_traning_23.fragment;


import com.loopj.android.http.RequestParams;

import com.example.project_traning_23.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class AProject_TraningFragment extends Fragment {
	protected RequestParams params;
	

	protected void initParams()
	{
		params = new RequestParams();
	}
	
	protected void switchFragment(AProject_TraningFragment fragment)
	{
		final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(R.anim.out_left, R.anim.in_right, R.anim.out_right, R.anim.in_left);
		fragmentTransaction.replace(R.id.content_frame, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
}
