package com.jon.earthquakes;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class _EarthquakeDB {

	private _DBOpenHelper dbOpenHelper;
	private ArrayList<Earthquake> arrayTerremotos;
	private Earthquake earthquake;
	private Context mContext;
	private static _EarthquakeDB INSTANCE = null;
	private SQLiteDatabase db;
	
	private _EarthquakeDB(Context context) {
		mContext = context;
	}
	
	public static _EarthquakeDB getDB(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new _EarthquakeDB(context);
			INSTANCE.open();
		}
		return INSTANCE;
	}
	
	public void open() {
		dbOpenHelper = new _DBOpenHelper(mContext, _DBOpenHelper.DATABASE_NAME, null, _DBOpenHelper.DATABASE_VERSION);
		db = dbOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		dbOpenHelper.close();
	}
	
	public ArrayList<Earthquake> filtrarPorMagnitud(float magnitud) {
		long id, time;
		String idStr, place, detail, url;
		float magnitude, latitude, longitude;
		arrayTerremotos = new ArrayList<Earthquake>();
		
		String[] result_columns = new String[]{_DBOpenHelper.ID_COLUMN, _DBOpenHelper.ID_STR_COLUMN, _DBOpenHelper.PLACE_COLUMN, _DBOpenHelper.TIME_COLUMN, _DBOpenHelper.DETAIL_COLUMN, 
				_DBOpenHelper.MAGNITUDE_COLUMN, _DBOpenHelper.LAT_COLUMN, _DBOpenHelper.LONG_COLUMN, _DBOpenHelper.URL_COLUMN, _DBOpenHelper.CREATED_AT_COLUMN, 
				_DBOpenHelper.UPDATED_AT_COLUMN};
		String where = _DBOpenHelper.MAGNITUDE_COLUMN + ">= ?";
		String[] whereArgs = new String[]{String.valueOf(magnitud)};
		String groupBy = null;
		String having = null;
		String order = _DBOpenHelper.TIME_COLUMN + " DESC";
		Cursor cursor = query(result_columns, where, whereArgs, groupBy, having, order);
		
		int ID_COLUMN_INDEX = cursor.getColumnIndexOrThrow(_DBOpenHelper.ID_COLUMN);
		int ID_STR_COLUMN_INDEX = cursor.getColumnIndexOrThrow(_DBOpenHelper.ID_STR_COLUMN);
		int PLACE_COLUMN_INDEX = cursor.getColumnIndexOrThrow(_DBOpenHelper.PLACE_COLUMN);
		int TIME_COLUMN_INDEX = cursor.getColumnIndexOrThrow(_DBOpenHelper.TIME_COLUMN);
		int DETAIL_COLUMN_INDEX = cursor.getColumnIndexOrThrow(_DBOpenHelper.DETAIL_COLUMN);
		int MAGNITUDE_COLUMN_INDEX = cursor.getColumnIndexOrThrow(_DBOpenHelper.MAGNITUDE_COLUMN);
		int LAT_COLUMN_INDEX = cursor.getColumnIndexOrThrow(_DBOpenHelper.LAT_COLUMN);
		int LONG_COLUMN_INDEX = cursor.getColumnIndexOrThrow(_DBOpenHelper.LONG_COLUMN);
		int URL_COLUMN_INDEX = cursor.getColumnIndexOrThrow(_DBOpenHelper.URL_COLUMN);
		
		while (cursor.moveToNext()) {
			id = cursor.getLong(ID_COLUMN_INDEX);
			idStr = cursor.getString(ID_STR_COLUMN_INDEX);
			place = cursor.getString(PLACE_COLUMN_INDEX);
			time = cursor.getLong(TIME_COLUMN_INDEX);
			detail = cursor.getString(DETAIL_COLUMN_INDEX);
			magnitude = cursor.getFloat(MAGNITUDE_COLUMN_INDEX);
			latitude = cursor.getFloat(LAT_COLUMN_INDEX);
			longitude = cursor.getFloat(LONG_COLUMN_INDEX);
			url = cursor.getString(URL_COLUMN_INDEX);
			earthquake = new Earthquake(id, idStr, place, time, detail, magnitude, latitude, longitude, url);
			arrayTerremotos.add(earthquake);
		}
		cursor.close();
		
		return arrayTerremotos;
	}

	public Cursor query(String[] result_columns, String where, String[] whereArgs, String groupBy, String having, String order) {
		Cursor cursor = db.query(_DBOpenHelper.DATABASE_TABLE, result_columns, where, whereArgs, groupBy, having, order);
		return cursor;
	}
	
	public long insert(Earthquake earthquake) {
		Date date = new Date();
		ContentValues newValues = new ContentValues();
	    newValues.put(_DBOpenHelper.ID_STR_COLUMN, earthquake.getIdStr());
	    newValues.put(_DBOpenHelper.PLACE_COLUMN, earthquake.getPlace());
	    newValues.put(_DBOpenHelper.TIME_COLUMN, earthquake.getTime().getTime());
	    newValues.put(_DBOpenHelper.DETAIL_COLUMN, earthquake.getDetail());
	    newValues.put(_DBOpenHelper.MAGNITUDE_COLUMN, earthquake.getMagnitude());
	    newValues.put(_DBOpenHelper.LAT_COLUMN, earthquake.getLatitude());
	    newValues.put(_DBOpenHelper.LONG_COLUMN, earthquake.getLongitude());
	    newValues.put(_DBOpenHelper.URL_COLUMN, earthquake.getUrl());
	    newValues.put(_DBOpenHelper.CREATED_AT_COLUMN, String.valueOf(date.getTime()));
	    newValues.put(_DBOpenHelper.UPDATED_AT_COLUMN, String.valueOf(date.getTime())); 
		return db.insert(_DBOpenHelper.DATABASE_TABLE, null, newValues);
	}

	public void update(String[] nombreColumna, String[] valor, String clausulaWhere, String[] argsWhere) {
		Date date = new Date();
		ContentValues updatedValues = new ContentValues();
		for (int i=0; i<nombreColumna.length; i++) {
			updatedValues.put(nombreColumna[i], valor[i]);
		}
		updatedValues.put(_DBOpenHelper.UPDATED_AT_COLUMN, String.valueOf(date.getTime()));
		String where = clausulaWhere;
		String whereArgs[] = argsWhere;
		db.update(_DBOpenHelper.DATABASE_TABLE, updatedValues, where, whereArgs);
	}
	
	public void delete(String clausulaWhere, String[] argsWhere) {
		String where = clausulaWhere;
	    String whereArgs[] = argsWhere;
	    db.delete(_DBOpenHelper.DATABASE_TABLE, where, whereArgs);
	}
	
}