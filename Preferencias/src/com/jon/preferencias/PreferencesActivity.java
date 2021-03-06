package com.jon.preferencias;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

/*public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.userpreferenceheaders, target);
	}

}*/

public class PreferencesActivity extends Activity implements OnSharedPreferenceChangeListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new FragmentoPreferencia()).commit();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		boolean autorefresh = prefs.getBoolean(getString(R.string.PREF_CHECK_BOX), true);
		if (autorefresh) {
			Log.d("PREFERENCIAS", "Autorefresh cambiado");
		}
	}

}
