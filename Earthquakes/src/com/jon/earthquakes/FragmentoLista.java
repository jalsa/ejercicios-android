package com.jon.earthquakes;

import android.app.ListFragment;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.LoaderManager;

public class FragmentoLista extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter adaptador;
	public static final String enlace = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
	private static final int ID_EARTHQUAKES = 1;
	
	private String[] from = {MyContentProvider.MAGNITUDE_COLUMN, MyContentProvider.PLACE_COLUMN, MyContentProvider.TIME_COLUMN, MyContentProvider.ID_COLUMN};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		int to[] = {R.id.textoMagnitud, R.id.textoRegion, R.id.textoHora};
		adaptador = new SimpleCursorAdapter(getActivity(), R.layout.list_row, null, from, to, 0);
		adaptador.setViewBinder(new EarthquakeViewBinder());
		
		setListAdapter(adaptador);
		getLoaderManager().initLoader(ID_EARTHQUAKES, null, this);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		refrescarTerremotos();
	}
	
	public void refrescarTerremotos() {
		//new DownloadEarthquakes(getActivity(), this).execute(enlace);
		Intent intent = new Intent(getActivity(), MyService.class);
		intent.putExtra("Url", enlace);
		getActivity().startService(intent);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getLoaderManager().restartLoader(ID_EARTHQUAKES, null, this);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(getActivity(), DetalleActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
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
		adaptador.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adaptador.swapCursor(null);
	}
	
}