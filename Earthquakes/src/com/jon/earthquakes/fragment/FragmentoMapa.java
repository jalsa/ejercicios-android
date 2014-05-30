package com.jon.earthquakes.fragment;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.jon.earthquakes.R;
import com.jon.earthquakes.provider.MyContentProvider;

public class FragmentoMapa extends MapFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final int ID_EARTHQUAKES = 1;
	private String[] from = {MyContentProvider.MAGNITUDE_COLUMN, MyContentProvider.PLACE_COLUMN, MyContentProvider.TIME_COLUMN, MyContentProvider.ID_COLUMN};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapa)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		
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
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		
	}
}
