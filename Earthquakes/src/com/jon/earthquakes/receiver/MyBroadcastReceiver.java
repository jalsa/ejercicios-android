package com.jon.earthquakes.receiver;

import com.jon.earthquakes.fragment.FragmentoLista;
import com.jon.earthquakes.service.MyService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) { 
		Intent intentAlarma = new Intent(context, MyService.class);
		intentAlarma.putExtra("Url", FragmentoLista.enlace);
		context.startService(intentAlarma);
	}

}