package com.jon.mapas;

import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textoLatitud, textoLongitud, textoAltitud;
	private LocationManager locationManager;
	private int t = 5000;
	private int distance = 5;
	private static final String provider = LocationManager.GPS_PROVIDER;
	private HashMap <Marker, String> hashMap;
	private Marker myMarkerLondres, myMarkerRenteria;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setContentView(R.layout.map_layout);
		
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		
		hashMap = new HashMap <Marker, String>();
		
		MarkerOptions markerRenteria = new MarkerOptions().position(new LatLng(43.312527, -1.898613)).title("Renteria");
		myMarkerRenteria = map.addMarker(markerRenteria);
		hashMap.put(myMarkerRenteria, "holaRenteria");
		MarkerOptions markerLondres = new MarkerOptions().position(new LatLng(51.507222, -0.1275)).title("Londres");
		myMarkerLondres = map.addMarker(markerLondres);
		hashMap.put(myMarkerLondres, "holaLondres");
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Log.d("DATOS", ""+hashMap.get(marker));
				Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
				intent.putExtra("nombre", marker.getTitle());
				startActivity(intent);
				return false;
			}

        });
		
//		textoLatitud = (TextView) findViewById(R.id.textoLatitud);
//		textoLongitud = (TextView) findViewById(R.id.textoLongitud);
//		textoAltitud = (TextView) findViewById(R.id.textoAltitud);
//		
//		String serviceString = LOCATION_SERVICE;
//		locationManager = (LocationManager)getSystemService (serviceString);
//		
//		Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//		criteria.setPowerRequirement(Criteria.POWER_LOW);
//		criteria.setAltitudeRequired(false);
//		criteria.setSpeedRequired(false);
		//String bestProvider = locationManager.getBestProvider(criteria, true);	
		//LocationProvider provider = locationManager.getProvider(bestProvider);
		
		//String provider = LocationManager.GPS_PROVIDER;
		//Location location = locationManager.getLastKnownLocation(provider);
		
		//obtenerUltimaLocalizacion();
		
		//obtenerPosicionActual();
		
	}
	
	public void obtenerUltimaLocalizacion() {
		LocationListener locListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				
			}
			
			@Override
			public void onLocationChanged(Location loc) {
				Log.d("LOCALIZACION", "" + loc.getLongitude());
				Log.d("LOCALIZACION", "" + loc.getLatitude());
				Log.d("LOCALIZACION", "" + loc.getAltitude());
				textoLatitud.setText(String.valueOf(loc.getLatitude()));
				textoLongitud.setText(String.valueOf(loc.getLongitude()));
				textoAltitud.setText(String.valueOf(loc.getAltitude()));
			}
		};
		locationManager.requestLocationUpdates(provider, t, distance, locListener);
	}
	
	public void obtenerPosicionActual() {
		final int locationUpdateRC = 0;
		int flags = PendingIntent.FLAG_UPDATE_CURRENT;
		Intent intent = new Intent(this, MyLocationUpdateReceiver.class); 
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this,locationUpdateRC, intent, flags);
		locationManager.requestLocationUpdates(provider, t, distance, pendingIntent);
	}
	
}