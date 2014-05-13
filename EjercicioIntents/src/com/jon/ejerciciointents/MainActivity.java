package com.jon.ejerciciointents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button botonForm;
	private TextView editor, label;
	private String valorDeVuelta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		valorDeVuelta = intent.getStringExtra("cadena1");
		label = (TextView)findViewById(R.id.label);
		label.setText(valorDeVuelta);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		OnClickListener formulario = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editor = (TextView)findViewById(R.id.editor);
				String valor = editor.getText().toString();
				Intent intent = new Intent(MainActivity.this, ActividadSecundaria.class);
				intent.putExtra("cadena", valor);
				startActivity(intent);
			}
		};
		
		botonForm = (Button)findViewById(R.id.formulario);
		botonForm.setOnClickListener(formulario);
	}
	
}
