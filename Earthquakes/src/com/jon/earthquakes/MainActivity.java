package com.jon.earthquakes;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends Activity {

	DBOpenHelper dbOpenHelper;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbOpenHelper = new DBOpenHelper(this, DBOpenHelper.DATABASE_NAME, null, DBOpenHelper.DATABASE_VERSION);
		db = EarthquakeDB.open(dbOpenHelper);
		EarthquakeDB.insert(db, "50", "Errenteria", "12:00", "Menudo terremoto!", Float.valueOf("7.9"), Float.valueOf("43.312527"), Float.valueOf("-1.898613"), "http://es.wikipedia.org/wiki/Renter%C3%ADa");
	}
}
