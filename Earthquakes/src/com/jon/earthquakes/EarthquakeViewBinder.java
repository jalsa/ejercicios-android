package com.jon.earthquakes;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class EarthquakeViewBinder implements ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor c, int idx) {
		int time_idx = c.getColumnIndex(MyContentProvider.TIME_COLUMN);
		int mag_idx = c.getColumnIndex(MyContentProvider.MAGNITUDE_COLUMN);
		if (time_idx == idx) {
			SimpleDateFormat s = new SimpleDateFormat("EEE, d MM yyyy HH:mm::ss aaa", Locale.ENGLISH);
			String dateStr = s.format(c.getLong(time_idx));
			
			((TextView)view.findViewById(R.id.textoHora)).setText(dateStr);
			
			return true;
		}
		if (mag_idx == idx) {
			float mag = c.getFloat(mag_idx);
			if (mag < 1.0) {
				((TextView)view.findViewById(R.id.textoMagnitud)).setTextColor(Color.GREEN);
			}
			else if (mag >= 1.0 && mag < 4.0) {
				((TextView)view.findViewById(R.id.textoMagnitud)).setTextColor(Color.BLUE);
			}
			else if (mag >= 4.0) {
				((TextView)view.findViewById(R.id.textoMagnitud)).setTextColor(Color.RED);
			}
		}
		return false;
	}

}
