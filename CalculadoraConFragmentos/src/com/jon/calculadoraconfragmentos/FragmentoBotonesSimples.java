package com.jon.calculadoraconfragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentoBotonesSimples extends Fragment {
	
	private int[] botonesNumero = {R.id.cero, R.id.uno, R.id.dos, R.id.tres, R.id.cuatro, R.id.cinco, R.id.seis, 
	         R.id.siete, R.id.ocho, R.id.nueve, R.id.punto}; 
	private int[] botonesOperacion = {R.id.mas, R.id.por, R.id.entre, R.id.menos, R.id.igual};
	private Button boton;
	TextView textView;
	
	
	public interface IActividad {
		public void pulsarNumero(String num);
		public void pulsarOperacion(String op);
	}
	
	private IActividad actividad;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
		    actividad = (IActividad)activity;
		  } catch (ClassCastException e) {
		    throw new ClassCastException(activity.toString() + " must implement IActividad");
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.botones_simples_fragment, container, false);
		
		OnClickListener numero = new OnClickListener() {
			@Override
			public void onClick(View v) {
				String valor = ((Button)v).getText().toString();
		    	actividad.pulsarNumero(valor);
				
			}
		};
		
		OnClickListener operacion = new OnClickListener() {
			@Override
			public void onClick(View v) {
				String valor = ((Button)v).getText().toString();
		    	actividad.pulsarOperacion(valor);
			}
		};
		
		for (int i=0; i<botonesNumero.length; i++) {
			boton = (Button)v.findViewById(botonesNumero[i]);
			boton.setOnClickListener(numero);
		}
		
		for (int i=0; i<botonesOperacion.length; i++) {
			boton = (Button)v.findViewById(botonesOperacion[i]);
			boton.setOnClickListener(operacion);
		}
		return v;	
	}
	
}
