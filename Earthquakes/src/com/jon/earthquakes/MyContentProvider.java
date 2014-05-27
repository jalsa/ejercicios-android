package com.jon.earthquakes;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {

	public static final Uri CONTENT_URI = Uri.parse("content://com.jon.provider.earthquakes/earthquakes");
	
	public static final String ID_COLUMN = "_id";
	public static final String ID_STR_COLUMN = "id_str";
	public static final String PLACE_COLUMN = "place";
	public static final String TIME_COLUMN = "time";
	public static final String DETAIL_COLUMN = "detail";
	public static final String MAGNITUDE_COLUMN = "magnitude";
	public static final String LAT_COLUMN = "lat";
	public static final String LONG_COLUMN = "long";
	public static final String URL_COLUMN = "url";
	public static final String CREATED_AT_COLUMN = "created_at";
	public static final String UPDATED_AT_COLUMN = "updated_at";
	
	private DBOpenHelper myOpenHelper;
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	private static final UriMatcher uriMatcher;
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.jon.provider.earthquakes", "earthquakes", ALLROWS);
		uriMatcher.addURI("com.jon.provider.earthquakes", "earthquakes/#", SINGLE_ROW);
	}
	
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
	    switch (uriMatcher.match(uri)) {
	    	case SINGLE_ROW :
	    		String rowID = uri.getPathSegments().get(1);
	    		where = ID_COLUMN + "=" + rowID + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : "");
	    	default: break;
	    }
	    if (where == null) {
	    	where = "1";
	    }
	    int deleteCount = db.delete(DBOpenHelper.DATABASE_TABLE, where, whereArgs);
	    getContext().getContentResolver().notifyChange(uri, null);
	    return deleteCount;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
			case ALLROWS:
				return "vnd.android.cursor.dir/vnd.jon.provider.earthquakes";
			case SINGLE_ROW:
				return "vnd.android.cursor.item/vnd.jon.provider.earthquakes";
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
	    String nullColumnHack = null;
	    long now = System.currentTimeMillis();
	    values.put(MyContentProvider.CREATED_AT_COLUMN, now);
	    values.put(MyContentProvider.UPDATED_AT_COLUMN, now);
	    long id = db.insert(DBOpenHelper.DATABASE_TABLE, nullColumnHack, values);
	    if (id > -1) {
	      Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
	      getContext().getContentResolver().notifyChange(insertedId, null);
	      return insertedId;
	    }
	    else
	      return null;
	}

	@Override
	public boolean onCreate() {
		myOpenHelper = new DBOpenHelper(getContext(), DBOpenHelper.DATABASE_NAME, null, DBOpenHelper.DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] columns, String where, String[] whereArgs, String order) {
		String groupBy = null;
		String having = null;
		
		SQLiteDatabase db;
		try {
			db = myOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = myOpenHelper.getReadableDatabase();
		}
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		switch (uriMatcher.match(uri)) {
			case SINGLE_ROW :
				String rowID = uri.getPathSegments().get(1);
			    queryBuilder.appendWhere(ID_COLUMN + "=" + rowID);
			  	default: break;
		}
		
		queryBuilder.setTables(DBOpenHelper.DATABASE_TABLE);

		Cursor cursor = queryBuilder.query(db, columns, where, whereArgs, groupBy, having, order);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
	    switch (uriMatcher.match(uri)) {
	      case SINGLE_ROW :
	        String rowID = uri.getPathSegments().get(1);
	        where = ID_COLUMN + "=" + rowID + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : "");
	      default: break;
	    }
		int updateCount = db.update(DBOpenHelper.DATABASE_TABLE, values, where, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
	    return updateCount;
	}
	
	private class DBOpenHelper extends SQLiteOpenHelper {
		
		public static final String DATABASE_NAME = "earthquakes.db";
		public static final String DATABASE_TABLE = "earthquakes";
		public static final int DATABASE_VERSION = 1;
		
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

}