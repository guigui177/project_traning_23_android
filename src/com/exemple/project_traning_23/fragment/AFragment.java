package com.exemple.project_traning_23.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;

//import eu.justmove.utils.AnalyticsManager;

public abstract class AFragment extends Fragment implements IFragment {
	@Override
	public void onResume() {
		super.onResume();
		//AnalyticsManager.getmGaTracker().sendView("/" + getString(getMenuTitle()));
		Log.d("analytics", "/" + getString(getMenuTitle()));
	}

	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		
	}
}
