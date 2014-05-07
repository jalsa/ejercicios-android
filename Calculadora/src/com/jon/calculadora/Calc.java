package com.jon.calculadora;

import android.view.View;
import android.widget.Button;

public class Calc {
	
	private double resultado;
	
	public void guardarNumero(String c, View v) {
		Button btn = (Button) v;
		int num = Integer.parseInt((String) btn.getText());
		resultado = Double.parseDouble(c);
	}
}
