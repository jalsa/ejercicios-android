package com.jon.todolistconfragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
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
				editor = (TextView)FragmentoInput.this.v.findViewById(R.id.texto);
				String valor = editor.getText().toString();
		    	actividad.nuevoElemento(valor);
		    	editor.setText("");
			}
		};
		
		boton = (Button)v.findViewById(R.id.boton);
		boton.setOnClickListener(nuevo);
		
		editor = (TextView)v.findViewById(R.id.texto);
		editor.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
						String valor = editor.getText().toString();
						actividad.nuevoElemento(valor);
						editor.setText("");
						return true;
					}
				}
				return false;
			}
		});
		
		return v;
	}
	
}
