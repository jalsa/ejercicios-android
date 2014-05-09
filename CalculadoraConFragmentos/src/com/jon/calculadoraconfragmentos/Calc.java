package com.jon.calculadoraconfragmentos;

import android.util.Log;

public class Calc {

	private static double primero, segundo;
	private static double resultado;
	private static String operacionGuardada;
	
	public static double guardarNumero(String c, String operacion) {
		if (operacion.equals("+") || operacion.equals("-") || operacion.equals("X") || operacion.equals("/") || operacion.equals("%") || operacion.equals("χ²")) {
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
			else if (operacionGuardada.equals("%")) {
				resultado = (primero / 100) * segundo;
			}
			else if (operacion.equals("χ²")) {
				resultado = primero * primero;
			}
		}
		Log.d("Calculadora", " " + resultado);
		return resultado;
	}
	
}
