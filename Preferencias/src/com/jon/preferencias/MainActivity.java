package com.jon.preferencias;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SharedPreferences mySharedPreferences;
	public static final String MY_PREFS = "PREFERENCIAS";
	public static final String VALORREFRESH = "valorRefresh";
	public static final String VALORINTERVALO = "valorIntervalo";
	TextView refresh, intervalo;
	boolean boolRefresh;
	int indiceIntervalo;
	
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
		mySharedPreferences = getSharedPreferences(MY_PREFS, Activity.MODE_PRIVATE);
		boolRefresh = mySharedPreferences.getBoolean(VALORREFRESH, true);
		indiceIntervalo = mySharedPreferences.getInt(VALORINTERVALO, 0);
		refresh.setText(String.valueOf(boolRefresh));
		intervalo.setText(String.valueOf(indiceIntervalo));
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
			Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}