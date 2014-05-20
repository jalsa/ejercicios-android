package com.jon.earthquakes;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EarthquakeDB {

	DBOpenHelper dbOpenHelper;
	ArrayList<Earthquake> arrayTerremotos;
	Earthquake earthquake;
	
	public static SQLiteDatabase open(DBOpenHelper dbOpenHelper) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		return db;
	}
	
	public static void close(DBOpenHelper dbOpenHelper) {
		dbOpenHelper.close();
	}
	
	public ArrayList<Earthquake> filtrarPorMagnitud(SQLiteDatabase db, float magnitud) {
		String id, place, time, detail, url, created_at, updated_at;
		float magnitude, latitude, longitude;
		
		String[] result_columns = new String[]{DBOpenHelper.ID_COLUMN, DBOpenHelper.PLACE_COLUMN, DBOpenHelper.TIME_COLUMN, DBOpenHelper.DETAIL_COLUMN, 
				DBOpenHelper.MAGNITUDE_COLUMN, DBOpenHelper.LAT_COLUMN, DBOpenHelper.LONG_COLUMN, DBOpenHelper.URL_COLUMN, DBOpenHelper.CREATED_AT_COLUMN, 
				DBOpenHelper.UPDATED_AT_COLUMN};
		String where = DBOpenHelper.MAGNITUDE_COLUMN + ">= ?";
		String[] whereArgs = new String[]{String.valueOf(magnitud)};
		String groupBy = null;
		String having = null;
		String order= null;
		Cursor cursor = query(db, result_columns, where, whereArgs, groupBy, having, order);
		int ID_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.ID_COLUMN);
		int PLACE_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.PLACE_COLUMN);
		int TIME_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.TIME_COLUMN);
		int DETAIL_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.DETAIL_COLUMN);
		int MAGNITUDE_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.MAGNITUDE_COLUMN);
		int LAT_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.LAT_COLUMN);
		int LONG_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.LONG_COLUMN);
		int URL_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.URL_COLUMN);
		int CREATED_AT_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.CREATED_AT_COLUMN);
		int UPDATED_AT_COLUMN_INDEX = cursor.getColumnIndexOrThrow(DBOpenHelper.UPDATED_AT_COLUMN);
		while (cursor.moveToNext()) {
			id = cursor.getString(ID_COLUMN_INDEX);
			place = cursor.getString(PLACE_COLUMN_INDEX);
			time = cursor.getString(TIME_COLUMN_INDEX);
			detail = cursor.getString(DETAIL_COLUMN_INDEX);
			magnitude = cursor.getFloat(MAGNITUDE_COLUMN_INDEX);
			latitude = cursor.getFloat(LAT_COLUMN_INDEX);
			longitude = cursor.getFloat(LONG_COLUMN_INDEX);
			url = cursor.getString(URL_COLUMN_INDEX);
			created_at = cursor.getString(CREATED_AT_COLUMN_INDEX);
			updated_at = cursor.getString(UPDATED_AT_COLUMN_INDEX);
			earthquake = new Earthquake(id, place, time, detail, magnitude, latitude, longitude, url, created_at, updated_at);
			arrayTerremotos.add(earthquake);
		}
		cursor.close();
		return arrayTerremotos;
	}

	public static Cursor query(SQLiteDatabase db, String[] result_columns, String where, String[] whereArgs, String groupBy, String having, String order) {
		Cursor cursor = db.query(DBOpenHelper.DATABASE_TABLE, result_columns, where, whereArgs, groupBy, having, order);
		return cursor;
	}
	
	public static void insert(SQLiteDatabase db, String id, String place, String time, String detail, float magnitude, float latitude, float longitude, String url) {
		Date date = new Date();
		ContentValues newValues = new ContentValues();
	    newValues.put(DBOpenHelper.ID_COLUMN, id);
	    newValues.put(DBOpenHelper.PLACE_COLUMN, place);
	    newValues.put(DBOpenHelper.TIME_COLUMN, time);
	    newValues.put(DBOpenHelper.DETAIL_COLUMN, detail);
	    newValues.put(DBOpenHelper.MAGNITUDE_COLUMN, magnitude);
	    newValues.put(DBOpenHelper.LAT_COLUMN, latitude);
	    newValues.put(DBOpenHelper.LONG_COLUMN, longitude);
	    newValues.put(DBOpenHelper.URL_COLUMN, url);
	    newValues.put(DBOpenHelper.CREATED_AT_COLUMN, String.valueOf(date));
	    newValues.put(DBOpenHelper.UPDATED_AT_COLUMN, String.valueOf(date)); 
		db.insert(DBOpenHelper.DATABASE_TABLE, null, newValues);
	}

	public static void update(SQLiteDatabase db, String nombreColumna, String valor, String clausulaWhere, String[] argsWhere) {
		ContentValues updatedValues = new ContentValues();
	    updatedValues.put(nombreColumna, valor);
		String where = clausulaWhere;
		String whereArgs[] = argsWhere;
		db.update(DBOpenHelper.DATABASE_TABLE, updatedValues, where, whereArgs);
	}
	
	public static void delete(SQLiteDatabase db, String clausulaWhere, String[] argsWhere) {
		String where = clausulaWhere;
	    String whereArgs[] = argsWhere;
	    db.delete(DBOpenHelper.DATABASE_TABLE, where, whereArgs);
	}
	
}
