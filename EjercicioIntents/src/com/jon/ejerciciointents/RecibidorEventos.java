package com.jon.ejerciciointents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class RecibidorEventos extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
				String state = extras.getString(TelephonyManager.EXTRA_STATE);
				if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
					String number = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
					Toast t = Toast.makeText(context, number, Toast.LENGTH_LONG);
				    t.show();
				}
			}
			else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
				Object[] pdus = (Object[]) extras.get("pdus");
			    SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);
			    Toast t = Toast.makeText(context, messages.getDisplayMessageBody(), Toast.LENGTH_LONG);
			    t.show();	   
			}
			else if (intent.getAction().equals("android.intent.action.AIRPLANE_MODE")) {
				if (Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0) {
					Toast t = Toast.makeText(context, "Est‡s en modo avi—n", Toast.LENGTH_LONG);
				    t.show();
				}
				else {
					Toast t = Toast.makeText(context, "Ya no est‡s en modo avi—n", Toast.LENGTH_LONG);
				    t.show();
				}
			}
			else if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
				if (ConnectivityManager.EXTRA_NO_CONNECTIVITY != null) {
					Toast t = Toast.makeText(context, "Est‡s sin conexi—n", Toast.LENGTH_LONG);
				    t.show();
				}
			}
		}
	}
}