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
	private ArrayList<Long> arrayIds;
	private long idErrenteria, idBilbao;
	private String enlace = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
	private Earthquake earthquake;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbOpenHelper = new DBOpenHelper(this, DBOpenHelper.DATABASE_NAME, null, DBOpenHelper.DATABASE_VERSION);
		db = EarthquakeDB.open(dbOpenHelper);
		Thread t = new Thread(new Runnable() {
			public void run() {
				descargarTerremotos();
			}
		});
		t.start();
		//arrayTerremotos = new ArrayList<Earthquake>();
		//Earthquake earthquakeErrenteria = new Earthquake("50", "Errenteria", "12:00", "Menudo terremoto!", Float.valueOf("7.9"), Float.valueOf("43.312527"), Float.valueOf("-1.898613"), "http://es.wikipedia.org/wiki/Renter%C3%ADa");
		//Earthquake earthquakeBilbao = new Earthquake("60", "Bilbao", "13:00", "El terremoto m‡s grande del mundo!", Float.valueOf("10"), Float.valueOf("43.256944"), Float.valueOf("-2.923611"), "http://es.wikipedia.org/wiki/Bilbao");
		//idErrenteria = EarthquakeDB.insert(db, earthquakeErrenteria);
		//idBilbao = EarthquakeDB.insert(db, earthquakeBilbao);
		//EarthquakeDB.update(db, new String[]{DBOpenHelper.PLACE_COLUMN}, new String[]{"Donostia"}, DBOpenHelper.ID_STR_COLUMN + " = ?", new String[]{"50"});
		//arrayTerremotos = EarthquakeDB.filtrarPorMagnitud(db, (float) 8.0);
		//Log.d("CONSULTA", "" + arrayTerremotos);
		//EarthquakeDB.delete(db, DBOpenHelper.ID_STR_COLUMN + " = ?", new String[]{"50"});
		//EarthquakeDB.close(dbOpenHelper);
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
				// Crear los terremotos
				earthquake = new Earthquake(idStr, place, time, detail, magnitude, latitude, longitude, url);
				// Insertarlos en la base de datos
				long id = EarthquakeDB.insert(db, earthquake);
				arrayIds.add(id);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
