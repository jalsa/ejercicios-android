package com.jon.earthquakes.fragment;

import com.jon.earthquakes.R;
import com.jon.earthquakes.R.xml;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class FragmentoPreferencia extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
	}
	
}