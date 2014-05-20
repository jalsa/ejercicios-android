package com.jon.preferencias;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int SETTINGS = 1;
	private static final int PREFERENCES = 2;
	
	private SharedPreferences mySharedPreferences, fwSharedPreferences;
	private TextView refresh, intervalo, magnitud;
	private boolean boolRefresh;
	private int indiceIntervalo, indiceMagnitud;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		refresh = (TextView) findViewById(R.id.valorAuto);
		intervalo = (TextView) findViewById(R.id.valorIntervalo);
		magnitud = (TextView) findViewById(R.id.valorMagnitud);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		mySharedPreferences = getSharedPreferences(SettingsActivity.MY_PREFS, Activity.MODE_PRIVATE);
		fwSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolRefresh = mySharedPreferences.getBoolean(SettingsActivity.VALORREFRESH, true);
		indiceIntervalo = mySharedPreferences.getInt(SettingsActivity.VALORINTERVALO, 0);
		
		/*boolRefresh = fwSharedPreferences.getBoolean(getResources().getString(R.string.PREF_CHECK_BOX), true);
		indiceIntervalo = fwSharedPreferences.getInt(getResources().getString(R.string.PREF_LIST_INTERVALOS), 0);
		indiceMagnitud = fwSharedPreferences.getInt(getResources().getString(R.string.PREF_LIST_MAGNITUDES), 0);*/
		
		String valoresIntervalo[] = getResources().getStringArray(R.array.intervalos_array);
		String valoresMagnitud[] = getResources().getStringArray(R.array.magnitudes_array);
		refresh.setText(String.valueOf(boolRefresh));
		intervalo.setText(valoresIntervalo[indiceIntervalo]);
		magnitud.setText(valoresMagnitud[indiceMagnitud]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivityForResult(i, SETTINGS);
			return true;
		}
		else if(id == R.id.action_preferences) {
			Intent i = new Intent(this, PreferencesActivity.class);
			startActivityForResult(i, PREFERENCES);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}