package com.jon.earthquakes;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class FragmentoPreferencia extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
	}
	
}