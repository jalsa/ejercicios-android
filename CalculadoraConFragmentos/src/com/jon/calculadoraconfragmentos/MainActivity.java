package com.jon.calculadoraconfragmentos;

import com.jon.calculadoraconfragmentos.Calc;
import com.jon.calculadoraconfragmentos.R;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity implements FragmentoBotonesSimples.IActividad, FragmentoDisplay.IActividad, FragmentoBotonesAvanzados.IActividad {

	String cadena, operacion;
	Double resultado;
	boolean igual = false;
	FragmentoDisplay display;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cadena = "";
		display = (FragmentoDisplay)getFragmentManager().findFragmentById(R.id.displayFragmento);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	     super.onRestoreInstanceState(savedInstanceState);
		 cadena = savedInstanceState.getString("cadena");
		 operacion = savedInstanceState.getString("operacion");
		 resultado = savedInstanceState.getDouble("resultado");
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("cadena", cadena);
		savedInstanceState.putString("operacion", operacion);
		savedInstanceState.putDouble("resultado", resultado);
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		display.mostrarValor(cadena);
	}
	
	public void pulsarNumero(String num) {
		if (igual) {
			cadena = num;
			igual = false;
		}
		else {
			cadena += num;
		}	
		display.mostrarValor(cadena);
	}
	
	public void pulsarOperacion(String op) {
		resultado = Calc.guardarNumero(cadena, op);
		cadena = "";
		if (op.equals("=")) {
			igual = true;
			display.mostrarValor(String.valueOf(resultado));
			cadena = String.valueOf(resultado);
		}
		else {
			display.mostrarValor(cadena);
		}
	}
	
	public void pulsarBorrar() {
		cadena = "";
		display.mostrarValor(cadena);
	}

}
