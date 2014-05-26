package com.jon.earthquakes;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class EarthquakeViewBinder implements ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor c, int idx) {
		int time_idx = c.getColumnIndex(MyContentProvider.TIME_COLUMN);
		if (time_idx == idx) {
			SimpleDateFormat s = new SimpleDateFormat("EEE, d MM yyyy HH:mm::ss aaa", Locale.ENGLISH);
			String dateStr = s.format(c.getLong(time_idx));
			
			((TextView)view.findViewById(R.id.textoHora)).setText(dateStr);
			
			return true;
		}
		return false;
	}

}
