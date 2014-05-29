package com.jon.earthquakes.activity;

import com.jon.earthquakes.R;
import com.jon.earthquakes.fragment.FragmentoLista;
import com.jon.earthquakes.service.MyService;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	FragmentManager fragmentManager;
	private static final int PREFERENCES = 1;
	private SharedPreferences mySharedPreferences;
	public static final String MY_PREFS = "PREFERENCIAS";
	public static final String PRIMERAVEZ = "primeraVez";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mySharedPreferences = getSharedPreferences(MY_PREFS, Activity.MODE_PRIVATE);
		boolean primero = mySharedPreferences.getBoolean(PRIMERAVEZ, true);

		if (primero) {
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putBoolean(PRIMERAVEZ, false);
			editor.apply();
			Intent intentAlarma = new Intent(this, MyService.class);
			intentAlarma.putExtra("Url", FragmentoLista.enlace);
			startService(intentAlarma);
		}
		
		if (savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.lista, new FragmentoLista(), "list");
			fragmentTransaction.commit();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_preferences) {
			Intent intent = new Intent(this, PreferencesActivity.class);
			startActivityForResult(intent, PREFERENCES);
			return true;
		}
		else if (id == R.id.action_refresh) {
			((FragmentoLista)getFragmentManager().findFragmentByTag("list")).refrescarTerremotos();
		}
		return super.onOptionsItemSelected(item);
	}
	
}