package com.jon.calculadora;

import android.view.View;
import android.widget.Button;

public class Calc {
	
	private double primero, segundo;
	private double resultado;
	
	public void guardarNumero(String c, View v) {
		Button btn = (Button) v;
		String operacion = String.valueOf(btn.getText());
		if (operacion == "+" || operacion == "-" || operacion == "*" || operacion == "/") {
			primero = Double.parseDouble(c);
		}
		else if (operacion == "=") {
			segundo = Double.parseDouble(c);
			if(operacion == "+") {
				resultado = primero + segundo;
			}
			else if (operacion == "-") {
				resultado = primero - segundo;
			}
			else if (operacion == "*") {
				resultado = primero * segundo;		
						}
			else if (operacion == "/") {
				resultado = primero / segundo;
			}
		}
	}
}
