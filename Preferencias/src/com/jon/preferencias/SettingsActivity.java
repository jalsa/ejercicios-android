package com.jon.preferencias;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {

	ToggleButton boton;
	Spinner spinner;
	private SharedPreferences mySharedPreferences;
	boolean boolRefresh;
	int indiceIntervalo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		boton = (ToggleButton) findViewById(R.id.toggleBoton);
		spinner = (Spinner) findViewById(R.id.spinnerIntervalo);
		Spinner spinner = (Spinner) findViewById(R.id.spinnerIntervalo);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.intervalos_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);	
	}
	
	public void onResume() {
		super.onResume();
		mySharedPreferences = getSharedPreferences(MainActivity.MY_PREFS, Activity.MODE_PRIVATE);
		boolRefresh = mySharedPreferences.getBoolean(MainActivity.VALORREFRESH, true);
		indiceIntervalo = mySharedPreferences.getInt(MainActivity.VALORINTERVALO, 0);
		boton.setChecked(boolRefresh);
		spinner.setSelection(indiceIntervalo);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mySharedPreferences = getSharedPreferences(MainActivity.MY_PREFS, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putBoolean(MainActivity.VALORREFRESH,boton.isChecked());
		editor.putInt(MainActivity.VALORINTERVALO, spinner.getSelectedItemPosition());
		editor.apply();
	}
}