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
	}
}
