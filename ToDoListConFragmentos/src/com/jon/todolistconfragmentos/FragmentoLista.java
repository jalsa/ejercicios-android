package com.jon.todolistconfragmentos;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FragmentoLista extends ListFragment {
	
	private ArrayList<String> listado;
	private ArrayAdapter<String> adaptador;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		listado = new ArrayList<String>();
		adaptador = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, listado);
		
		setListAdapter(adaptador);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			listado.addAll(savedInstanceState.getStringArrayList("listado"));
			adaptador.notifyDataSetChanged();
		}
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
