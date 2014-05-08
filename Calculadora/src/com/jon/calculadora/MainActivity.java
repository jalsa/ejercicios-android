package com.jon.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	//Calc calc;
	TextView textView;
	String cadena, operacion;
	Double resultado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//calc = new Calc();
		textView = (TextView)findViewById(R.id.entrada);
		cadena = "";
		textView.setText(cadena);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	     super.onRestoreInstanceState(savedInstanceState);
		 cadena = savedInstanceState.getString("cadena");
		 operacion = savedInstanceState.getString("operacion");
		 resultado = savedInstanceState.getDouble("resultado");
		 textView.setText(cadena);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("cadena", cadena);
		savedInstanceState.putString("operacion", operacion);
		savedInstanceState.putDouble("resultado", resultado);
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	public void pulsarNumero(View v) {
		//calc.guardarNumero(v);
		Button btn = (Button) v;
		cadena += (String) btn.getText();
		textView.setText(cadena);
	}
	
	public void pulsarOperacion(View v) {
		Button btn = (Button) v;
		operacion = (String) btn.getText();
		resultado = Calc.guardarNumero(cadena, operacion);
		cadena = "";
		if (operacion.equals("=")) {
			textView.setText(String.valueOf(resultado));
			cadena = String.valueOf(resultado);
		}
		else {
			textView.setText(cadena);
		}
	}
	
	public void pulsarBorrar(View v) {
		cadena = "";
		textView.setText(cadena);
	}
	
}
