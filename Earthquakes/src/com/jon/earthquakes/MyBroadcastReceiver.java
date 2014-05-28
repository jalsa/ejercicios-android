package com.jon.earthquakes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

	public static final String ACTION = "com.jon.alarmaRefrescarTerremotos";
	
	@Override
	public void onReceive(Context context, Intent intent) { 
		Intent intentAlarma = new Intent(context, MyService.class);
		intentAlarma.putExtra("Url", FragmentoLista.enlace);
		context.startService(intentAlarma);
	}

}