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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FragmentoLista extends ListFragment {

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
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				descargarTerremotos();
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (savedInstanceState != null) {
			listado.addAll((ArrayList<Earthquake>) savedInstanceState.getSerializable(LISTA));
			adaptador.notifyDataSetChanged();
		} else {
			listado.addAll(db.filtrarPorMagnitud(0));
			adaptador.notifyDataSetChanged();
		}
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
	
	public void descargarTerremotos() {
		try {
			URL url = new URL(enlace);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			int	responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK)	{
				InputStream	in = httpConnection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				StringBuilder builder = new StringBuilder();
				String input = br.readLine();
				while (input != null) {
					builder.append(input);
					input = br.readLine();
				}
				guardarTerremotos(builder);
			}
		}
		catch(MalformedURLException	e) {
			Log.d("ERROR",	"Malformed	URL	Exception.", e);
		}
		catch(IOException e) {
			Log.d("ERROR", "IO	Exception.", e);
		}
	}
	
	public void guardarTerremotos(StringBuilder builder) {
		arrayIds = new ArrayList<Long>();
		try {
			JSONObject json = new JSONObject(builder.toString());
			JSONArray array = json.getJSONArray("features");
			for (int i=0; i<array.length(); i++) {
				// Obtener todos los datos
				JSONObject terremoto = array.getJSONObject(i);
				String idStr = terremoto.getString("id");
				String place = terremoto.getJSONObject("properties").getString("place");
				String time = String.valueOf(terremoto.getJSONObject("properties").getLong("time"));
				String detail = terremoto.getJSONObject("properties").getString("detail");
				float magnitude = (float) terremoto.getJSONObject("properties").getDouble("mag");
				float latitude = (float) terremoto.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1);
				float longitude = (float) terremoto.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0);
				String url = terremoto.getJSONObject("properties").getString("url");
				// Crear los terremotos y a–adirlos al array
				earthquake = new Earthquake(idStr, place, time, detail, magnitude, latitude, longitude, url);
				// Insertarlos en la base de datos
				long id = db.insert(earthquake);
				arrayIds.add(id);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDestroy() {
		db.close();
		super.onDestroy();
	}
	
}
