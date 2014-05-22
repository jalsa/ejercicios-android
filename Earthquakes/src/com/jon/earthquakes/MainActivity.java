package com.jon.earthquakes;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	FragmentManager fragmentManager;
	private static final int PREFERENCES = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.lista, new FragmentoLista(), "list");
			fragmentTransaction.commit();
		}
		
	}
	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		
//		arrayTerremotos = new ArrayList<Earthquake>();
//		arrayTerremotos = db.filtrarPorMagnitud(0);
//		for (int i=0; i<arrayTerremotos.size(); i++) {
//			// Insertarlos en la lista
//			Earthquake eq = arrayTerremotos.get(i);
//			((FragmentoLista)getFragmentManager().findFragmentByTag("list")).nuevoElemento(eq.toString());
//		}
//	}
	
//	@Override
//	protected void onDestroy() {
//		db.close(dbOpenHelper);
//		super.onDestroy();
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_preferences) {
			Intent i = new Intent(this, PreferencesActivity.class);
			startActivityForResult(i, PREFERENCES);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}