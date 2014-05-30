package com.jon.earthquakes.listener;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {

	private	Fragment fragment;
	private	Activity activity;
	private	Class<T> fragmentClass;
	private	int	fragmentContainer;

	public TabListener(Activity	activity, int fragmentContainer, Class<T> fragmentClass) {
		this.activity =	activity;
		this.fragmentContainer = fragmentContainer;
		this.fragmentClass = fragmentClass;
	}	

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (fragment ==	null) {
			String fragmentName	= fragmentClass.getName();
			fragment = Fragment.instantiate(activity, fragmentName);
			ft.add(fragmentContainer, fragment, fragmentName);
		}
		else {
			ft.attach(fragment);
		}
	}
	
	public void	onTabUnselected(Tab	tab, FragmentTransaction ft) {
		if (fragment != null)
			ft.detach(fragment);
	}		
	
	public void onTabReselected(Tab	tab, FragmentTransaction ft) {
		if (fragment !=	null)
			ft.attach(fragment);
	}
	
}
