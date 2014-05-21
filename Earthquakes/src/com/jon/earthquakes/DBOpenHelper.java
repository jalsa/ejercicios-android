package com.jon.earthquakes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	
	protected static final String DATABASE_NAME = "earthquakes.db";
	protected static final String DATABASE_TABLE = "earthquakes";
	protected static final int DATABASE_VERSION = 1;
	
	protected static final String ID_COLUMN = "_id";
	protected static final String ID_STR_COLUMN = "id_str";
	protected static final String PLACE_COLUMN = "place";
	protected static final String TIME_COLUMN = "time";
	protected static final String DETAIL_COLUMN = "detail";
	protected static final String MAGNITUDE_COLUMN = "magnitude";
	protected static final String LAT_COLUMN = "lat";
	protected static final String LONG_COLUMN = "long";
	protected static final String URL_COLUMN = "url";
	protected static final String CREATED_AT_COLUMN = "created_at";
	protected static final String UPDATED_AT_COLUMN = "updated_at";
	
	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ID_STR_COLUMN + " TEXT UNIQUE, " + PLACE_COLUMN + " TEXT, " + TIME_COLUMN + " DATETIME, " + DETAIL_COLUMN + " TEXT, " + MAGNITUDE_COLUMN + " REAL, " + LAT_COLUMN + " REAL, "
			+ LONG_COLUMN + " REAL, " + URL_COLUMN + " TEXT, " + CREATED_AT_COLUMN + " DATETIME, " + UPDATED_AT_COLUMN + " DATETIME);";

	public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}

}
