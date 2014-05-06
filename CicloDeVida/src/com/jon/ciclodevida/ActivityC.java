package com.jon.ciclodevida;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ActivityC extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c);
		Log.d("ActivityC", "SE HA EJECUTADO ONCREATE DE C");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_c, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d("ActivityC", "SE HA EJECUTADO ONSTART DE C");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d("ActivityC", "SE HA EJECUTADO ONRESUME DE C");
	}
	
	@Override
	public void onPause() {
		Log.d("ActivityC", "SE HA EJECUTADO ONPAUSE DE C");
		super.onPause();
	}
	
	@Override
	public void onStop() {
		Log.d("ActivityC", "SE HA EJECUTADO ONSTOP DE C");
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		Log.d("ActivityC", "SE HA EJECUTADO ONDESTROY DE C");
		super.onDestroy();
	}
	
	public void abrirA(View v) {
		Intent intent = new Intent(this, ActivityA.class);
		startActivity(intent);
	}
	
	public void abrirB(View v) {
		Intent intent = new Intent(this, ActivityB.class);
		startActivity(intent);
	}
	
	public void cerrar() {
		finish();
	}

}
