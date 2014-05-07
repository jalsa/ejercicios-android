package com.jon.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Calc calc;
	TextView textView;
	String cadena;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		calc = new Calc();
		textView = (TextView)findViewById(R.id.entrada);
		cadena = "";
	}
	
	public void pulsarNumero(View v) {
		//calc.guardarNumero(v);
		Button btn = (Button) v;
		cadena += btn.getText();
		textView.setText(cadena);
	}
	
	public void pulsarOperacion(View v) {
		calc.guardarNumero(cadena, v);
	}
	
}
