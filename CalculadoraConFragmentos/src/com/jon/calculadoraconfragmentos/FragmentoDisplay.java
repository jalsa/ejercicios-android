package com.jon.calculadoraconfragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentoDisplay extends Fragment {
	
	Button boton;
	TextView textView;

	public interface IActividad {
		public void pulsarBorrar();
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
		View view = inflater.inflate(R.layout.display_fragment, container, false);
		textView = (TextView)view.findViewById(R.id.entrada);
		boton = (Button)view.findViewById(R.id.borrar);
		boton.setOnClickListener(new Button.OnClickListener() {
		      public void onClick(View v) {
		    	  actividad.pulsarBorrar();
		}});
		return view;
	}
	
	public void mostrarValor(String valor) {
		textView.setText(valor);
	}
	
}
