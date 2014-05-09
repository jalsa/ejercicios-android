package com.jon.calculadoraconfragmentos;

import com.jon.calculadoraconfragmentos.FragmentoBotonesSimples.IActividad;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FragmentoBotonesAvanzados extends Fragment {

	private int[] botonesOperacion = {R.id.porciento, R.id.cuadrado, R.id.raiz, R.id.modulo};
	private Button boton;
	TextView textView;
	
	
	public interface IActividad {
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
		View v = inflater.inflate(R.layout.botones_avanzados_fragment, container, false);
		
		OnClickListener operacion = new OnClickListener() {
			@Override
			public void onClick(View v) {
				String valor = ((Button)v).getText().toString();
		    	Log.d("Boton", ""+valor);
		    	actividad.pulsarOperacion(valor);
			}
		};
		
		for (int i=0; i<botonesOperacion.length; i++) {
			boton = (Button)v.findViewById(botonesOperacion[i]);
			boton.setOnClickListener(operacion);
		}
		return v;
	}
	
}
