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
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadEarthquakes extends AsyncTask<String, Void, ArrayList<Earthquake>> {

	private Earthquake earthquake;
	private ArrayList<Earthquake> arrayTerremotos;
	private Context contexto;
	//private EarthquakeDB db;
	private FragmentoLista fragmento;
	
	public DownloadEarthquakes(Context contexto, FragmentoLista fragmento) {
		this.contexto = contexto;
		this.fragmento = fragmento;
	}
	
	@Override
	protected ArrayList<Earthquake> doInBackground(String... strings) {
		ArrayList<Earthquake> result = new ArrayList<Earthquake>();
		
		result = descargarTerremotos(strings[0]);
		
		return result;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Earthquake> result) {
		super.onPostExecute(result);
		((InterfazAdaptador) fragmento).mostrarLista(result);
	}
	
	public interface InterfazAdaptador {
		public void mostrarLista(ArrayList<Earthquake> result);
	}
	
	public ArrayList<Earthquake> descargarTerremotos(String enlace) {
		arrayTerremotos = new ArrayList<Earthquake>();
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
				arrayTerremotos = guardarTerremotos(builder);
			}
		}
		catch(MalformedURLException	e) {
			Log.d("ERROR",	"Malformed	URL	Exception.", e);
		}
		catch(IOException e) {
			Log.d("ERROR", "IO	Exception.", e);
		}
		return arrayTerremotos;
	}
	
	public ArrayList<Earthquake> guardarTerremotos(StringBuilder builder) {
		try {
			JSONObject json = new JSONObject(builder.toString());
			JSONArray array = json.getJSONArray("features");
			for (int i=0; i<array.length(); i++) {
				// Obtener todos los datos
				JSONObject terremoto = array.getJSONObject(i);
				String idStr = terremoto.getString("id");
				String place = terremoto.getJSONObject("properties").getString("place");
				long time = terremoto.getJSONObject("properties").getLong("time");
				String detail = terremoto.getJSONObject("properties").getString("detail");
				float magnitude = (float) terremoto.getJSONObject("properties").getDouble("mag");
				float latitude = (float) terremoto.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1);
				float longitude = (float) terremoto.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0);
				String url = terremoto.getJSONObject("properties").getString("url");
				// Crear los terremotos
				earthquake = new Earthquake(idStr, place, time, detail, magnitude, latitude, longitude, url);
				// Insertarlos en la base de datos
				insertEarthQuake(earthquake);
				//db = EarthquakeDB.getDB(contexto);
				//long id = db.insert(earthquake);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arrayTerremotos;
	}
	
	private void insertEarthQuake(Earthquake eq) {
		Date date = new Date();
		ContentValues newValues = new ContentValues();
	    newValues.put(MyContentProvider.ID_STR_COLUMN, eq.getIdStr());
	    newValues.put(MyContentProvider.PLACE_COLUMN, eq.getPlace());
	    newValues.put(MyContentProvider.TIME_COLUMN, eq.getTime().getTime());
	    newValues.put(MyContentProvider.DETAIL_COLUMN, eq.getDetail());
	    newValues.put(MyContentProvider.MAGNITUDE_COLUMN, eq.getMagnitude());
	    newValues.put(MyContentProvider.LAT_COLUMN, eq.getLatitude());
	    newValues.put(MyContentProvider.LONG_COLUMN, eq.getLongitude());
	    newValues.put(MyContentProvider.URL_COLUMN, eq.getUrl());
	    newValues.put(MyContentProvider.CREATED_AT_COLUMN, String.valueOf(date.getTime()));
	    newValues.put(MyContentProvider.UPDATED_AT_COLUMN, String.valueOf(date.getTime())); 
	    
		ContentResolver cr = contexto.getContentResolver();
		cr.insert(MyContentProvider.CONTENT_URI, newValues);
		
		// A–adirlos al array si no estaban en la base de datos
		//if (id >= 0) {
			arrayTerremotos.add(0, earthquake);
		//}
	}

}