package com.jon.ejerciciointents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActividadSecundaria extends Activity {

	private String valor;
	private TextView label, editor;
	private Button botonOk, botonBack;
	private static final String CADENA = "cadena1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secundario);
		
		Intent intent = getIntent();
		valor = intent.getStringExtra("cadena");
		label = (TextView)findViewById(R.id.labelSecundario);
		label.setText(valor);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		OnClickListener okey = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editor = (TextView)findViewById(R.id.editorSecundario);
				String valor = editor.getText().toString();
				Intent intent = new Intent(ActividadSecundaria.this, MainActivity.class);
				intent.putExtra(CADENA, valor);
				startActivity(intent);
			}
		};
		
		botonOk = (Button)findViewById(R.id.botonOk);
		botonOk.setOnClickListener(okey);
		
		OnClickListener volver = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActividadSecundaria.this, MainActivity.class);
				startActivity(intent);
			}
		};
		
		botonBack = (Button)findViewById(R.id.botonBack);
		botonBack.setOnClickListener(volver);
		
	}
	
}
