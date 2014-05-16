package com.jon.preferencias;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final int PREFERENCES = 0;
	private SharedPreferences mySharedPreferences;
	private TextView refresh, intervalo;
	private boolean boolRefresh;
	private int indiceIntervalo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		refresh = (TextView) findViewById(R.id.valorAuto);
		intervalo = (TextView) findViewById(R.id.valorIntervalo);	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mySharedPreferences = getSharedPreferences(SettingsActivity.MY_PREFS, Activity.MODE_PRIVATE);
		boolRefresh = mySharedPreferences.getBoolean(SettingsActivity.VALORREFRESH, true);
		indiceIntervalo = mySharedPreferences.getInt(SettingsActivity.VALORINTERVALO, 0);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.intervalos_array_valores, android.R.layout.simple_spinner_item);
		String valorIntervalo = (String) adapter1.getItem(indiceIntervalo);
		refresh.setText(String.valueOf(boolRefresh));
		intervalo.setText(valorIntervalo);
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
			//Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
			//startActivity(intent);
			Intent i = new Intent(this, PreferencesActivity.class);
			startActivityForResult(i, PREFERENCES);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}