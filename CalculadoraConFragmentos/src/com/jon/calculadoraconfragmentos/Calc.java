package com.jon.calculadoraconfragmentos;

import android.util.Log;

public class Calc {

	private static double primero, segundo;
	private static double resultado;
	private static String operacionGuardada;
	
	public static double guardarNumero(String c, String operacion) {
		if (operacion.equals("+") || operacion.equals("-") || operacion.equals("X") || operacion.equals("/")) {
			primero = Double.parseDouble(c);
			operacionGuardada = operacion;
			resultado = 0;
		}
		else if (operacion.equals("=")) {
			Log.d("Calculadora", " " + primero);
			segundo = Double.parseDouble(c);
			Log.d("Calculadora", " " + segundo);
			if (operacionGuardada.equals("+")) {
				resultado = primero + segundo;
			}
			else if (operacionGuardada.equals("-")) {
				resultado = primero - segundo;
			}
			else if (operacionGuardada.equals("X")) {
				resultado = primero * segundo;		
						}
			else if (operacionGuardada.equals("/")) {
				resultado = primero / segundo;
			}
		}
		Log.d("Calculadora", " " + resultado);
		return resultado;
	}
	
}
