package com.jon.earthquakes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

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
		boolean autorefresh = prefs.getBoolean(getString(R.string.keyCheckbox), true);
		int mag = Integer.parseInt(prefs.getString(getString(R.string.keyListaMagnitudes), "0"));
		if (key.equals(R.string.PREF_CHECK_BOX)) {
			Log.d("PREFERENCIAS", "Autorefresh cambiado");
			Log.d("PREFERENCIAS", "" + autorefresh);
		}
		else if (key.equals(R.string.keyListaMagnitudes)) {
			Toast toast = Toast.makeText(getApplicationContext(), "Magnitud cambiada: " + mag, Toast.LENGTH_LONG);
			toast.show();
		}
	}

}