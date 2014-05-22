package com.jon.earthquakes;

import java.util.ArrayList;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FragmentoLista extends ListFragment implements DownloadEarthquakes.InterfazAdaptador {

	private ArrayList<Earthquake> listado;
	private ArrayAdapter<Earthquake> adaptador;
	private String enlace = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
	private EarthquakeDB db;
	private static final String LISTA = "listado";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		listado = new ArrayList<Earthquake>();
		adaptador = new ArrayAdapter<Earthquake>(inflater.getContext(), android.R.layout.simple_list_item_1, listado);
		
		setListAdapter(adaptador);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		db = EarthquakeDB.getDB(getActivity());
		
		if (savedInstanceState != null) {
			listado.addAll((ArrayList<Earthquake>) savedInstanceState.getSerializable(LISTA));
			adaptador.notifyDataSetChanged();
		}
		else {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
			int mag = Integer.parseInt(prefs.getString(getString(R.string.keyListaMagnitudes), "0"));
			Log.d("MAG", "" + mag);
			listado.clear();
			listado.addAll(db.filtrarPorMagnitud(mag));
			adaptador.notifyDataSetChanged();
		}	
		new DownloadEarthquakes(getActivity(), this).execute(enlace);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		int mag = Integer.parseInt(prefs.getString(getString(R.string.keyListaMagnitudes), "0"));
		Log.d("MAG", "" + mag);
		listado.clear();
		listado.addAll(db.filtrarPorMagnitud(mag));
		adaptador.notifyDataSetChanged();
	}
	
	@Override
	public void onDestroy() {
		db.close();
		super.onDestroy();
	}

	public void mostrarLista(ArrayList<Earthquake> result) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		int mag = Integer.parseInt(prefs.getString(getString(R.string.keyListaMagnitudes), "0"));
		Log.d("MAG", "" + mag);
		
		for (Earthquake earthquake : result) {
			if (earthquake.getMagnitude() >= mag) {
				listado.add(0, earthquake);
			}
		}
		
//		listado.clear();
//		listado.addAll(db.filtrarPorMagnitud(mag));
		adaptador.notifyDataSetChanged();
	}
	
}