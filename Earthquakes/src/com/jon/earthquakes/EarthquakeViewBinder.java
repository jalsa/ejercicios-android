package com.jon.earthquakes;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class EarthquakeViewBinder implements ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor c, int idx) {
		int time_idx = c.getColumnIndex(MyContentProvider.TIME_COLUMN);
		int mag_idx = c.getColumnIndex(MyContentProvider.MAGNITUDE_COLUMN);
		int place_idx = c.getColumnIndex(MyContentProvider.PLACE_COLUMN);
		if (time_idx == idx) {
			SimpleDateFormat s = new SimpleDateFormat("EEE, d MM yyyy HH:mm:ss aaa", Locale.ENGLISH);
			String dateStr = s.format(c.getLong(time_idx));
			
			((TextView)view).setText(dateStr);
			
			return true;
		}
		else if (mag_idx == idx) {
			float mag = c.getFloat(mag_idx);
			if (mag < 1.0) {
				((TextView)view).setTextColor(Color.GREEN);
			}
			else if (mag >= 1.0 && mag < 4.0) {
				((TextView)view).setTextColor(Color.BLUE);
			}
			else if (mag >= 4.0) {
				((TextView)view).setTextColor(Color.RED);
			}
		}
		else if (place_idx == idx) {
			((TextView)view).setTypeface(null, Typeface.BOLD);
		}
		return false;
	}

}