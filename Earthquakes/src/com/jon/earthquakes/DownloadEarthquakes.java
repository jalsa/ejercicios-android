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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadEarthquakes extends AsyncTask<String, Void, ArrayList<Earthquake>> {

	private Earthquake earthquake;
	private ArrayList<Long> arrayIds;
	private ArrayList<Earthquake> arrayTerremotos;
	private Context contexto;
	private EarthquakeDB db;
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
				arrayTerremotos.add(earthquake);
				// Insertarlos en la base de datos
				db = EarthquakeDB.getDB(contexto);
				long id = db.insert(earthquake);
				arrayIds.add(id);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arrayTerremotos;
	}

}
