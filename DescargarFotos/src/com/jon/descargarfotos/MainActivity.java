package com.jon.descargarfotos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button botonFotos, botonZip;
	private String enlace = "http://www.arkaitzgarro.com/android/photos.json";
	private long reference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		OnClickListener descargar = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread t = new Thread(new Runnable() {
					public void run() {
						descargarFotos();
					}
				});
				t.start();
			}
		};
		
		botonFotos = (Button) findViewById(R.id.botonDescarga);
		botonFotos.setOnClickListener(descargar);
		
		OnClickListener descargarZip = new OnClickListener() {
			@Override
			public void onClick(View v) {
				descargarArchivoZip();
			}
		};
		
		botonZip = (Button) findViewById(R.id.botonZip);
		botonZip.setOnClickListener(descargarZip);
		
		IntentFilter filter	= new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void	onReceive(Context context, Intent intent) {
				long thisReference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				if (reference == thisReference){
					Query downloadQuery = new Query();
					downloadQuery.setFilterById(thisReference);					
					DownloadManager	downloadManager;
					String serviceString = Context.DOWNLOAD_SERVICE;
					downloadManager	= (DownloadManager)getSystemService(serviceString);
					Cursor downloads = downloadManager.query(downloadQuery);
					int	filenameIdx = downloads.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
					int	sizeIdx = downloads.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
					int	statusIdx = downloads.getColumnIndex(DownloadManager.COLUMN_STATUS);					
					while (downloads.moveToNext()) {
						String filename = downloads.getString(filenameIdx);
						long size = downloads.getLong(sizeIdx);
						int	status = downloads.getInt(statusIdx);
						switch (status)	{
							case DownloadManager.STATUS_SUCCESSFUL:
								TextView labelNombre = (TextView) findViewById(R.id.labelNombre);
								TextView labelTamano = (TextView) findViewById(R.id.labelTamano);
								labelNombre.setText(filename);
								labelTamano.setText(String.valueOf(size));
								break;
							default	:
								break;
						}
					}
					downloads.close();
				}
			}
		};
		registerReceiver(receiver, filter);
	}
	
	public void descargarArchivoZip() {
		String	serviceString =	Context.DOWNLOAD_SERVICE;
		DownloadManager	downloadManager;
		downloadManager	= (DownloadManager)getSystemService(serviceString);
		Uri	uri	= Uri.parse("http://developer.android.com/shareables/icon_templates-v4.0.zip");
		DownloadManager.Request	request	= new Request(uri);
		request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "icon_templates.zip");
		reference = downloadManager.enqueue(request);
	}
	
	public void descargarFotos() {
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
			JSONArray arrayPhotos = json.getJSONArray("photos");
			for (int i=0; i<arrayPhotos.length(); i++) {
				JSONObject photo = arrayPhotos.getJSONObject(i);
				String urlPhoto = photo.getString("image_url");
				String id = photo.getString("id");
				String name = photo.getString("name");
				Log.d("FOTO" + i + ": ", "Id: " + id + "\nNombre: " + name + "\nURL: " + urlPhoto + "\n");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}