package com.jon.earthquakes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	private ArrayList<Long> arrayIds;
	private String enlace = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
	private EarthquakeDB db;
	private Earthquake earthquake;
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
		
		new DownloadEarthquakes(getActivity()).execute(enlace);
		
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

//	@Override
//	public void onSaveInstanceState(Bundle savedInstanceState) {
//		savedInstanceState.putStringArrayList("listado", listado);
//		super.onSaveInstanceState(savedInstanceState);
//	}

//	public void nuevoElemento(String valor) {
//		listado.add(valor);
//		adaptador.notifyDataSetChanged();
//	}
	
	@Override
	public void onDestroy() {
		db.close();
		super.onDestroy();
	}

	@Override
	public void insertarTerremoto(Earthquake eq) {
		long id = db.insert(eq);
		arrayIds.add(id);
	}
	
}
