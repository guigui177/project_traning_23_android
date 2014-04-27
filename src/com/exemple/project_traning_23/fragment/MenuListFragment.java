package com.exemple.project_traning_23.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import eu.justmove.R;
import eu.justmove.Justmove;
import eu.justmove.utils.FragmentList;
import eu.justmove.utils.MenuItem;

public class MenuListFragment extends ListFragment {

	public View onCreateView(LayoutInflater mInflater, ViewGroup mContainer, Bundle mSavedInstanceState) {
		return mInflater.inflate(R.layout.fragment_menu, null);
	}

	public void onActivityCreated(Bundle mSavedInstanceState) {
		super.onActivityCreated(mSavedInstanceState);
		final MenuAdapter mAdapter = new MenuAdapter(getActivity());
		for (FragmentList mFragment : FragmentList.values())
			mAdapter.add(new MenuItems(getString(mFragment.getItem().getMenuTitle())));

		setListAdapter(mAdapter);
	}

	private class MenuItems {
		public String mText;

		public MenuItems(String mText) {
			this.mText = mText;
		}
	}

	@Override
	public void onListItemClick(ListView mListView, View mView, int mPosition, long mId) {
		final Activity mActivity = getActivity();
		Log.d("rdfrag3", "1");
		if (mPosition >= 0 && mPosition <= FragmentList.values().length)
		{
			Log.d("rdfrag3", "2");
			if (FragmentList.findFragmentById(mPosition).isFragment() == true)
			{
				Log.d("rdfrag3", "3" + FragmentList.findFragmentById(mPosition).getFragment());
				setFragment(FragmentList.findFragmentById(mPosition).getFragment());
				Log.d("rdfrag3", "4");
			}
			else
			{
				final Intent i = new Intent(getActivity(), FragmentList.findFragmentById(mPosition).getActivity());
				startActivityForResult(i, FragmentList.findFragmentById(mPosition).getCallCode());
			}
		}
		Log.d("rdfrag3", "5");
		if (mActivity != null && mActivity instanceof Justmove)
		{
			((Justmove) mActivity).toggle();
			Log.d("rdfrag3", "6");
		}
		}

	public class MenuAdapter extends ArrayAdapter<MenuItems> {

		public MenuAdapter(Context mContext) {
			super(mContext, 0);
		}

		public View getView(int mPosition, View mConvertView, ViewGroup mViewGroup) {
			if (mConvertView == null)
				mConvertView = LayoutInflater.from(getContext()).inflate(R.layout.list_menu_item, null);
			TextView mRow = (TextView) mConvertView.findViewById(R.id.row_id);
			MenuItems mItem = getItem(mPosition);
			mRow.setText(mItem.mText);
			return mConvertView;
		}

	}

	private void setFragment(IFragment mFragment) {
		final Activity mActivity = getActivity();
		Log.d("rdfrag3", "7");
		if (mActivity != null && mActivity instanceof Justmove) {
			Log.d("rdfrag3", "8");
			Justmove activity = (Justmove) mActivity;
			Log.d("rdfrag3", "9");
			activity.setFragment(mFragment, false);
			Log.d("rdfrag3", "10");
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == MenuItem.PREFERENCE_ACTIVITY)
			if (resultCode == Activity.RESULT_OK)
				getActivity().finish();
	}
}
