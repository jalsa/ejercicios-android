package com.jon.earthquakes;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	FragmentManager fragmentManager;
	private static final int PREFERENCES = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*if (savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.lista, new FragmentoLista(), "list");
			fragmentTransaction.commit();
		}*/
		
		ContentResolver cr = getContentResolver();
		String[] result_columns = new String[] {};
		Uri rowAddress = ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, 1);
		String where = null;
		String whereArgs[] = null;
		String order = null;
		Cursor resultCursor = cr.query(rowAddress, result_columns, where, whereArgs, order);
		
		int ID_COLUMN_INDEX = resultCursor.getColumnIndexOrThrow(DBOpenHelper.ID_COLUMN);
		int ID_STR_COLUMN_INDEX = resultCursor.getColumnIndexOrThrow(DBOpenHelper.ID_STR_COLUMN);
		int PLACE_COLUMN_INDEX = resultCursor.getColumnIndexOrThrow(DBOpenHelper.PLACE_COLUMN);
		int TIME_COLUMN_INDEX = resultCursor.getColumnIndexOrThrow(DBOpenHelper.TIME_COLUMN);
		int DETAIL_COLUMN_INDEX = resultCursor.getColumnIndexOrThrow(DBOpenHelper.DETAIL_COLUMN);
		int MAGNITUDE_COLUMN_INDEX = resultCursor.getColumnIndexOrThrow(DBOpenHelper.MAGNITUDE_COLUMN);
		int LAT_COLUMN_INDEX = resultCursor.getColumnIndexOrThrow(DBOpenHelper.LAT_COLUMN);
		int LONG_COLUMN_INDEX = resultCursor.getColumnIndexOrThrow(DBOpenHelper.LONG_COLUMN);
		int URL_COLUMN_INDEX = resultCursor.getColumnIndexOrThrow(DBOpenHelper.URL_COLUMN);
		
		while (resultCursor.moveToNext()) {
			long id = resultCursor.getLong(ID_COLUMN_INDEX);
			String idStr = resultCursor.getString(ID_STR_COLUMN_INDEX);
			String place = resultCursor.getString(PLACE_COLUMN_INDEX);
			long time = resultCursor.getLong(TIME_COLUMN_INDEX);
			String detail = resultCursor.getString(DETAIL_COLUMN_INDEX);
			float magnitude = resultCursor.getFloat(MAGNITUDE_COLUMN_INDEX);
			float latitude = resultCursor.getFloat(LAT_COLUMN_INDEX);
			float longitude = resultCursor.getFloat(LONG_COLUMN_INDEX);
			String url = resultCursor.getString(URL_COLUMN_INDEX);
			Log.d("EARTHQUAKE", "Id: " + id + " IdStr: " + idStr + " Place: " + place + " Time: " + time + " Detail: " +  detail + " Magnitude: " + magnitude + " Latitude: " + latitude + " Longitude: " + longitude + " Url: " + url);
		}
		resultCursor.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_preferences) {
			Intent intent = new Intent(this, PreferencesActivity.class);
			startActivityForResult(intent, PREFERENCES);
			return true;
		}
		else if (id == R.id.action_refresh) {
			((FragmentoLista)getFragmentManager().findFragmentByTag("list")).refrescarTerremotos();
		}
		return super.onOptionsItemSelected(item);
	}
	
}