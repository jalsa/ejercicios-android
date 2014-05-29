package com.jon.earthquakes.activity;

import com.jon.earthquakes.R;
import com.jon.earthquakes.fragment.FragmentoPreferencia;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class PreferencesActivity extends Activity implements OnSharedPreferenceChangeListener{

	AlarmManager alarmManager;
	PendingIntent alarmIntent;
	
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
		int interval = Integer.parseInt(prefs.getString(getString(R.string.keyListaIntervalos), "0"));

		if (key.equals(getString(R.string.keyCheckbox))) {
			Log.d("PREFERENCIAS", "Autorefresh cambiado");
			Log.d("PREFERENCIAS", "" + autorefresh);
			if (autorefresh) {
				activarAlarma(interval);
			}
			else {
				if (alarmIntent != null) {
					Log.d("PREFERENCIAS", "alarm cancel()");
					alarmManager.cancel(alarmIntent);
				}
			}
		}
		else if (key.equals(getString(R.string.keyListaMagnitudes))) {
			Toast toast = Toast.makeText(getApplicationContext(), "Magnitud cambiada: " + mag, Toast.LENGTH_LONG);
			toast.show();
		}
		else if (key.equals(getString(R.string.keyListaIntervalos))) {
			Log.d("PREFERENCIAS", "Intervalo cambiado");
			Log.d("PREFERENCIAS", "" + interval);
			if (autorefresh) {
				activarAlarma(interval);
			}
		}
	}
	
	private	void activarAlarma(int interval) {
		alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		int	tipoAlarma = AlarmManager.RTC;	
		long tiempo = interval * 1000;
		Intent intent = new Intent(getResources().getString(R.string.action));
		alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		alarmManager.setInexactRepeating(tipoAlarma, 0, tiempo, alarmIntent);
	}

}