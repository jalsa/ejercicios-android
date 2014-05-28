package com.jon.earthquakes;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetalleActivity extends Activity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private long id;
	private static final int ID_EARTHQUAKES = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);

		Intent intent = getIntent();
		id = intent.getLongExtra("id", 0);
		
		getLoaderManager().initLoader(ID_EARTHQUAKES, null, this);

	}

	@Override
	public Loader<Cursor> onCreateLoader(int iden, Bundle args) {
		String[] result_columns = { MyContentProvider.MAGNITUDE_COLUMN, MyContentProvider.PLACE_COLUMN, MyContentProvider.TIME_COLUMN };

		Uri rowURI = ContentUris.withAppendedId(
				MyContentProvider.CONTENT_URI, id);
		CursorLoader loader = new CursorLoader(this,
				rowURI, result_columns, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		int PLACE_COLUMN_INDEX = cursor
				.getColumnIndexOrThrow(MyContentProvider.PLACE_COLUMN);
		int TIME_COLUMN_INDEX = cursor
				.getColumnIndexOrThrow(MyContentProvider.TIME_COLUMN);
		int MAGNITUDE_COLUMN_INDEX = cursor
				.getColumnIndexOrThrow(MyContentProvider.MAGNITUDE_COLUMN);
		
		Log.d("EARTHQUAKE", String.valueOf(cursor.getCount()));
		
		if (cursor.moveToFirst()) {
			String lugar = cursor.getString(PLACE_COLUMN_INDEX);
			long hora = cursor.getLong(TIME_COLUMN_INDEX);
			String magnitud = String.valueOf(cursor
					.getFloat(MAGNITUDE_COLUMN_INDEX));
			SimpleDateFormat s = new SimpleDateFormat("EEE, d MM yyyy HH:mm:ss aaa", Locale.ENGLISH);
			String time = s.format(hora);
			((TextView) findViewById(R.id.textoLugar)).setText(lugar);
			((TextView) findViewById(R.id.textoMomento)).setText(time);
			((TextView) findViewById(R.id.textoGrados)).setText(magnitud);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}