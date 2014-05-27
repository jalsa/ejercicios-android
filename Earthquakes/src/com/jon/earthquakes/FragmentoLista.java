package com.jon.earthquakes;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.LoaderManager;

public class FragmentoLista extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

//	private ArrayList<Earthquake> listado;
	//private ArrayAdapter<Earthquake> adaptador;
	private SimpleCursorAdapter adaptador;
	private String enlace = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
//	private EarthquakeDB db;
//	private static final String LISTA = "listado";
	private static final int DETALLE = 2;
	private int id = 1;
	
	private String[] from = {MyContentProvider.MAGNITUDE_COLUMN, MyContentProvider.PLACE_COLUMN, MyContentProvider.TIME_COLUMN, MyContentProvider.ID_COLUMN};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		int to[] = {R.id.textoMagnitud, R.id.textoRegion, R.id.textoHora};
		
//		listado = new ArrayList<Earthquake>();
//		adaptador = new ArrayAdapter<Earthquake>(inflater.getContext(), android.R.layout.simple_list_item_1, listado);
		adaptador = new SimpleCursorAdapter(getActivity(), R.layout.list_row, null, from, to, 0);
		adaptador.setViewBinder(new EarthquakeViewBinder());
		
		setListAdapter(adaptador);
		getLoaderManager().initLoader(id, null, this);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
//		db = EarthquakeDB.getDB(getActivity());
//		
//		if (savedInstanceState != null) {
//			listado.addAll((ArrayList<Earthquake>) savedInstanceState.getSerializable(LISTA));
//			adaptador.notifyDataSetChanged();
//		}
//		else {
//			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//			int mag = Integer.parseInt(prefs.getString(getString(R.string.keyListaMagnitudes), "0"));
//			Log.d("MAG", "" + mag);
//			listado.clear();
//			listado.addAll(db.filtrarPorMagnitud(mag));
//			adaptador.notifyDataSetChanged();
//		}	
		refrescarTerremotos();
	}
	
	public void refrescarTerremotos() {
		new DownloadEarthquakes(getActivity(), this).execute(enlace);
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//		int mag = Integer.parseInt(prefs.getString(getString(R.string.keyListaMagnitudes), "0"));
//		listado.clear();
//		listado.addAll(db.filtrarPorMagnitud(mag));
//		adaptador.notifyDataSetChanged();
		
		/*ContentResolver cr = getActivity().getContentResolver();
		String[] result_columns = new String[] {};
		String where = MyContentProvider.MAGNITUDE_COLUMN + " >= ?";
		String whereArgs[] = {String.valueOf(mag)};
		String order = MyContentProvider.TIME_COLUMN + " DESC";
		Cursor c = cr.query(MyContentProvider.CONTENT_URI, result_columns, where, whereArgs, order);
		
		adaptador.swapCursor(c);*/
	}
	
	@Override
	public void onDestroy() {
//		db.close();
		super.onDestroy();
	}

	public void mostrarLista(ArrayList<Earthquake> result) {
//		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//		int mag = Integer.parseInt(prefs.getString(getString(R.string.keyListaMagnitudes), "0"));
//		
//		for (Earthquake earthquake : result) {
//			if (earthquake.getMagnitude() >= mag) {
//				listado.add(0, earthquake);
//			}
//		}
//		adaptador.notifyDataSetChanged();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(getActivity(), DetalleActivity.class);
		intent.putExtra("id", id);
		startActivityForResult(intent, DETALLE);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int indice, Bundle args) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		int mag = Integer.parseInt(prefs.getString(getString(R.string.keyListaMagnitudes), "0"));
		
		ContentResolver cr = getActivity().getContentResolver();
		String[] result_columns = new String[] {};
		String where = MyContentProvider.MAGNITUDE_COLUMN + " >= ?";
		String whereArgs[] = {String.valueOf(mag)};
		String order = MyContentProvider.TIME_COLUMN + " DESC";
		//Cursor c = cr.query(MyContentProvider.CONTENT_URI, result_columns, where, whereArgs, order);
		CursorLoader loader = new CursorLoader(getActivity(), MyContentProvider.CONTENT_URI, result_columns, where, whereArgs, order);
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