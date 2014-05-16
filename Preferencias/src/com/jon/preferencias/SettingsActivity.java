package com.jon.preferencias;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {

	public static final String MY_PREFS = "PREFERENCIAS";
	ToggleButton boton;
	Spinner spinner;
	private SharedPreferences mySharedPreferences;
	
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
	@Override
	public void onPause() {
		mySharedPreferences = getSharedPreferences(MY_PREFS, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("valorRefresh", (String) boton.getText());
		editor.putString("valorIntervalo", (String) spinner.getSelectedItem());
		editor.apply();
	}
}
