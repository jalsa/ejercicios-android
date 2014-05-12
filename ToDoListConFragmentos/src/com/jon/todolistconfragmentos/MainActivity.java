package com.jon.todolistconfragmentos;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity implements FragmentoInput.IActividad {

	private ArrayList<String> listado;
	private ArrayAdapter<String> adaptador;
	private FragmentoLista fragmentoLista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listado = new ArrayList<String>();
		adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listado);

		fragmentoLista = new FragmentoLista();

//		if (savedInstanceState == null) {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(R.id.input, new FragmentoInput());
			fragmentTransaction.replace(R.id.lista, fragmentoLista);
			fragmentTransaction.commit();
//		}

		fragmentoLista.setListAdapter(adaptador);
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

	public void nuevoElemento(String valor) {
		listado.add(valor);
		adaptador.notifyDataSetChanged();
	}

}
