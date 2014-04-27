package com.exemple.project_traning_23.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;

//import eu.justmove.utils.AnalyticsManager;

public abstract class AFragment extends Fragment implements IFragment {
	@Override
	public void onResume() {
		super.onResume();
		//AnalyticsManager.getmGaTracker().sendView("/" + getString(getMenuTitle()));
		Log.d("analytics", "/" + getString(getMenuTitle()));
	}
}
