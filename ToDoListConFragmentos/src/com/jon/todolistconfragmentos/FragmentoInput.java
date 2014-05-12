package com.jon.todolistconfragmentos;

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

public class FragmentoInput extends Fragment {

	private Button boton;
	private TextView editor;
	private View v;
	
	public interface IActividad {
		public void nuevoElemento(String valor);
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
		v = inflater.inflate(R.layout.input_fragment, container, false);
		
		OnClickListener nuevo = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("LIST", "onClick()");
				
				editor = (TextView)FragmentoInput.this.v.findViewById(R.id.texto);
				String valor = editor.getText().toString();
		    	actividad.nuevoElemento(valor);
			}
		};
		
		boton = (Button)v.findViewById(R.id.boton);
		boton.setOnClickListener(nuevo);
		
		return v;
	}
	
}
