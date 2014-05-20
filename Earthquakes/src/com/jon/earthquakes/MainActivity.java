package com.jon.earthquakes;

import java.util.ArrayList;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private DBOpenHelper dbOpenHelper;
	private SQLiteDatabase db;
	private ArrayList<Earthquake> arrayTerremotos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbOpenHelper = new DBOpenHelper(this, DBOpenHelper.DATABASE_NAME, null, DBOpenHelper.DATABASE_VERSION);
		db = EarthquakeDB.open(dbOpenHelper);
		Earthquake earthquakeErrenteria = new Earthquake("50", "Errenteria", "12:00", "Menudo terremoto!", Float.valueOf("7.9"), Float.valueOf("43.312527"), Float.valueOf("-1.898613"), "http://es.wikipedia.org/wiki/Renter%C3%ADa");
		Earthquake earthquakeBilbao = new Earthquake("60", "Bilbao", "13:00", "El terremoto m‡s grande del mundo!", Float.valueOf("10"), Float.valueOf("43.256944"), Float.valueOf("-2.923611"), "http://es.wikipedia.org/wiki/Bilbao");
		EarthquakeDB.insert(db, earthquakeErrenteria);
		EarthquakeDB.insert(db, earthquakeBilbao);
		EarthquakeDB.update(db, new String[]{DBOpenHelper.PLACE_COLUMN}, new String[]{"Donostia"}, DBOpenHelper.ID_COLUMN + " = ?", new String[]{"50"});
		arrayTerremotos = EarthquakeDB.filtrarPorMagnitud(db, (float) 8.0);
		Log.d("CONSULTA", "" + arrayTerremotos);
		EarthquakeDB.delete(db, DBOpenHelper.ID_COLUMN + " = ?", new String[]{"50"});
	}
}
