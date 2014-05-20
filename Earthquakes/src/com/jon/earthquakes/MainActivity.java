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

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private DBOpenHelper dbOpenHelper;
	private SQLiteDatabase db;
	private ArrayList<Earthquake> arrayTerremotos;
	long idErrenteria, idBilbao;
	private String enlace = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbOpenHelper = new DBOpenHelper(this, DBOpenHelper.DATABASE_NAME, null, DBOpenHelper.DATABASE_VERSION);
		db = EarthquakeDB.open(dbOpenHelper);
		descargarTerremotos();
		//Earthquake earthquakeErrenteria = new Earthquake("50", "Errenteria", "12:00", "Menudo terremoto!", Float.valueOf("7.9"), Float.valueOf("43.312527"), Float.valueOf("-1.898613"), "http://es.wikipedia.org/wiki/Renter%C3%ADa");
		//Earthquake earthquakeBilbao = new Earthquake("60", "Bilbao", "13:00", "El terremoto m‡s grande del mundo!", Float.valueOf("10"), Float.valueOf("43.256944"), Float.valueOf("-2.923611"), "http://es.wikipedia.org/wiki/Bilbao");
		//idErrenteria = EarthquakeDB.insert(db, earthquakeErrenteria);
		//idBilbao = EarthquakeDB.insert(db, earthquakeBilbao);
		//EarthquakeDB.update(db, new String[]{DBOpenHelper.PLACE_COLUMN}, new String[]{"Donostia"}, DBOpenHelper.ID_STR_COLUMN + " = ?", new String[]{"50"});
		//arrayTerremotos = EarthquakeDB.filtrarPorMagnitud(db, (float) 8.0);
		//Log.d("CONSULTA", "" + arrayTerremotos);
		//EarthquakeDB.delete(db, DBOpenHelper.ID_STR_COLUMN + " = ?", new String[]{"50"});
	}
	
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
				mostrarDatos(builder);
			}
		}
		catch(MalformedURLException	e) {
			Log.d("ERROR",	"Malformed	URL	Exception.", e);
		}
		catch(IOException e) {
			Log.d("ERROR", "IO	Exception.", e);
		}
	}
	
	public void mostrarDatos(StringBuilder builder) {
		try {
			JSONObject json = new JSONObject(builder.toString());
			JSONArray arrayTerremotos = json.getJSONArray("features");
			for (int i=0; i<arrayTerremotos.length(); i++) {
				JSONObject terremoto = arrayTerremotos.getJSONObject(i);
				String id = terremoto.getString("id");
				String place = terremoto.getJSONObject("properties").getString("place");
				// Obtener todos los datos
				
				// Crear los terremotos
				
				// Insertarlos en la base de datos
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
