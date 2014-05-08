package com.jon.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ArrayList<String> listado;
	private ArrayAdapter<String> adaptador;
	
	private EditText textoEditable;
	private ListView vistaListado;
	Button btn;
	String cadena;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listado = new ArrayList<String>();
		adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listado);
		
		textoEditable = (EditText) findViewById(R.id.texto);
		vistaListado = (ListView) findViewById(R.id.lista);
		
		vistaListado.setAdapter(adaptador);
		
		textoEditable.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
						listado.add(0, textoEditable.getText().toString());
						adaptador.notifyDataSetChanged();
						textoEditable.setText("");
						return true;
					}
				}
				return false;
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		adaptador.notifyDataSetChanged();
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	     super.onRestoreInstanceState(savedInstanceState);
		 listado.addAll(savedInstanceState.getStringArrayList("listado"));
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putStringArrayList("listado", listado);
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	public void nuevoElemento(View v) {
		cadena = textoEditable.getText().toString();
		listado.add(cadena);
		adaptador.notifyDataSetChanged();
	}
	
}
