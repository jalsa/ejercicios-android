package com.jon.todolistconfragmentos;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements FragmentoInput.IActividad {

	FragmentManager fragmentManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		if (savedInstanceState == null) {
			fragmentTransaction.add(R.id.input, new FragmentoInput());
			fragmentTransaction.add(R.id.lista, new FragmentoLista(), "list");
			fragmentTransaction.commit();
		}

	}

	public void nuevoElemento(String valor) {
		((FragmentoLista)getFragmentManager().findFragmentByTag("list")).nuevoElemento(valor);
	}

}
