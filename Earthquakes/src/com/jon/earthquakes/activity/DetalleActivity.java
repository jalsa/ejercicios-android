package com.jon.earthquakes.activity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jon.earthquakes.R;
import com.jon.earthquakes.fragment.FragmentoMapa;
import com.jon.earthquakes.provider.MyContentProvider;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static long LOADER_ID;
	private static final int ID_EARTHQUAKES = 1;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

		Intent intent = getIntent();
		LOADER_ID = intent.getLongExtra("id", 0);
		
		getLoaderManager().initLoader(ID_EARTHQUAKES, null, this);

	}

	@Override
	public Loader<Cursor> onCreateLoader(int iden, Bundle args) {
		String[] result_columns = MyContentProvider.KEYS_ALL;
		Uri rowURI = ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, LOADER_ID);
		CursorLoader loader = new CursorLoader(this, rowURI, result_columns, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		int PLACE_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.PLACE_COLUMN);
		int TIME_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.TIME_COLUMN);
		int MAGNITUDE_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.MAGNITUDE_COLUMN);
//		int ID_STR_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.ID_STR_COLUMN);
//		int DETAIL_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.DETAIL_COLUMN);
		int LATITUDE_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.LAT_COLUMN);
		int LONGITUDE_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.LONG_COLUMN);
//		int URL_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.URL_COLUMN);
//		int CREATED_AT_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.CREATED_AT_COLUMN);
//		int UPDATED_AT_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.UPDATED_AT_COLUMN);
		
		if (map == null) {
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		}
		
		if (cursor.moveToFirst()) {
			String lugar = cursor.getString(PLACE_COLUMN_INDEX);
			long hora = cursor.getLong(TIME_COLUMN_INDEX);
			String magnitud = String.valueOf(cursor.getFloat(MAGNITUDE_COLUMN_INDEX));
//			String idStr = cursor.getString(ID_STR_COLUMN_INDEX);
//			String detail = cursor.getString(DETAIL_COLUMN_INDEX);
			float latitude = cursor.getFloat(LATITUDE_COLUMN_INDEX);
			float longitude = cursor.getFloat(LONGITUDE_COLUMN_INDEX);
//			String url = cursor.getString(URL_COLUMN_INDEX);
//			long createdAt = cursor.getLong(CREATED_AT_COLUMN_INDEX);
//			long updatedAt = cursor.getLong(UPDATED_AT_COLUMN_INDEX);
			
			SimpleDateFormat s = new SimpleDateFormat("EEE, d MM yyyy HH:mm:ss aaa", Locale.ENGLISH);
			String time = s.format(hora);
			((TextView) findViewById(R.id.textoLugar)).setText(lugar);
			((TextView) findViewById(R.id.textoMomento)).setText(time);
			((TextView) findViewById(R.id.textoGrados)).setText(magnitud);
			
			MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(latitude, longitude)).title(lugar);
			map.addMarker(markerOptions);
			CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
	        map.moveCamera(center);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}