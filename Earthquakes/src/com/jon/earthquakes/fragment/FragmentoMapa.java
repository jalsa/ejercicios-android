package com.jon.earthquakes.fragment;

import java.util.HashMap;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jon.earthquakes.R;
import com.jon.earthquakes.activity.DetalleActivity;
import com.jon.earthquakes.provider.MyContentProvider;

public class FragmentoMapa extends MapFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private long id;
	private String place;
	private float latitude, longitude;
	private static final int ID_EARTHQUAKES = 1;
	private GoogleMap map;
	private HashMap <Marker, Long> hashMap;
	private String[] from = {MyContentProvider.LAT_COLUMN, MyContentProvider.PLACE_COLUMN, MyContentProvider.LONG_COLUMN, MyContentProvider.ID_COLUMN};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.lista)).getMap();
		hashMap = new HashMap <Marker, Long>();
		
		getLoaderManager().initLoader(ID_EARTHQUAKES, null, this);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int indice, Bundle args) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		int mag = Integer.parseInt(prefs.getString(getString(R.string.keyListaMagnitudes), "0"));
		
		String where = MyContentProvider.MAGNITUDE_COLUMN + " >= ?";
		String whereArgs[] = {String.valueOf(mag)};
		String order = MyContentProvider.TIME_COLUMN + " DESC";
		CursorLoader loader = new CursorLoader(getActivity(), MyContentProvider.CONTENT_URI, from, where, whereArgs, order);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		int ID_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.ID_COLUMN);
		int PLACE_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.PLACE_COLUMN);
		int LAT_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.LAT_COLUMN);
		int LONG_COLUMN_INDEX = cursor.getColumnIndexOrThrow(MyContentProvider.LONG_COLUMN);
		
		if (map == null) {
			map = this.getMap();
		}
		
		while (cursor.moveToNext()) {
			id = cursor.getLong(ID_COLUMN_INDEX);
			place = cursor.getString(PLACE_COLUMN_INDEX);
			latitude = cursor.getFloat(LAT_COLUMN_INDEX);
			longitude = cursor.getFloat(LONG_COLUMN_INDEX);
			
			MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(latitude, longitude)).title(place);
			Marker marker = map.addMarker(markerOptions);
			hashMap.put(marker, id);
		}
		cursor.close();
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Intent intent = new Intent(getActivity(), DetalleActivity.class);
				intent.putExtra("id", hashMap.get(marker));
				startActivity(intent);
				return false;
			}

        });
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		
	}
}
