package com.example.project_traning_23;

import com.exemple.project_traning_23.fragment.IFragment;
import com.example.project_traning_23.utils.FragmentList;
//import eu.justmove.utils.MenuItem;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Project_traning_2_3 extends ActionBarActivity {

	private ActionBar supportActionBar;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private IFragment mContent;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private View layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_traning_23);

		layout = findViewById(R.id.drawer_layout);
		supportActionBar = getSupportActionBar();
		mTitle = mDrawerTitle = getTitle();

		setFragment(FragmentList.findFragmentById(0).getFragment(), false);
		if (layout instanceof DrawerLayout) {
			mDrawerLayout = (DrawerLayout) layout;
			mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
			supportActionBar.setDisplayHomeAsUpEnabled(true);
			supportActionBar.setHomeButtonEnabled(true);
			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
				public void onDrawerClosed(View view) {
					if (supportActionBar.getTitle().equals(mDrawerTitle))
						supportActionBar.setTitle(mTitle);
					supportInvalidateOptionsMenu();
				}
				public void onDrawerOpened(View drawerView) {
					mTitle = supportActionBar.getTitle();
					supportActionBar.setTitle(mDrawerTitle);
					supportInvalidateOptionsMenu();
				}
			};

			mDrawerLayout.setDrawerListener(mDrawerToggle);


		} else {
			getSupportActionBar().setHomeButtonEnabled(false);
		}

	}

	public void toggle() {

		if (layout instanceof DrawerLayout) {
			if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			else
				mDrawerLayout.openDrawer(Gravity.LEFT);
			Log.d("rdfrag", FragmentList.values().length +"");
		}
	}

	public void setFragment(IFragment mFragment, boolean mAnimate) {
		final FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		mContent = mFragment;
		if (mAnimate)
			mFragmentTransaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out);
		mFragmentTransaction.replace(R.id.content_frame, (Fragment) mContent).commitAllowingStateLoss();
		supportActionBar.setTitle(mContent.getMenuTitle());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu mMenu) {
		getMenuInflater().inflate(R.menu.projec_traning_23, mMenu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem mItem) {
		if (layout instanceof DrawerLayout) {
			if (mDrawerToggle.onOptionsItemSelected(mItem)) {
				return true;
			}
		}
		switch (mItem.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(mItem);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (layout instanceof DrawerLayout)
			mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (layout instanceof DrawerLayout)
			mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		supportActionBar.setTitle(mTitle);
	}
	
	@Override
	public boolean onKeyDown(int mKeyCode, KeyEvent mKeyEvent) {
		if (mKeyCode == KeyEvent.KEYCODE_MENU) {
			toggle();
			return true;
		}
		return super.onKeyDown(mKeyCode, mKeyEvent);
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			if (resultCode == RESULT_CANCELED) {
				finish();
			}
		}
	}
	@Override
	public void onStart() {
		super.onStart();
		Log.d("rdfrag", FragmentList.values().length +"");
	}
	@Override
	public void onResumeFragments() {
		super.onResumeFragments();
		Log.d("rdfrag2", FragmentList.values().length +"");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	}

}

