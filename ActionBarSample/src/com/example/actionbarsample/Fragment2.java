package com.example.actionbarsample;


import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment2 extends Fragment implements OnItemClickListener {
	
	private Cursor titulars;
	ListView llista;
	private TascaAdapter adapter;
	private TitularsSQLiteHelper titHelper;
	private TitularsConversor titularsConv; 
	
	@Override
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		titHelper = new  TitularsSQLiteHelper(getActivity(), "Tasques.db", null, 2);
        // obtenir l'objecte BD 
        SQLiteDatabase db = titHelper.getWritableDatabase();
        titularsConv = new TitularsConversor(titHelper);
        // Si s'ha obert correctament la BD    
		View rootView = inflater.inflate(R.layout.fragment2,container,false);
		llista = ((ListView)rootView.findViewById(R.id.listView1));
		if(db != null) {
			// actualitzar la llista
			refreshData();
	    	// Tancar la base de dades
			db.close();
		} 
		llista.setOnItemClickListener(this);
		
        return rootView;
	}

	private void refreshData() {
		titulars = titularsConv.getAll();
        adapter = new TascaAdapter(getActivity(), titulars);        
        llista.setAdapter(adapter);  
        if(titulars.getCount() == 0) {
        	llista.setVisibility(llista.INVISIBLE);
        }
        else {
        	llista.setVisibility(llista.VISIBLE);
        }
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		registerForContextMenu(getView());
    	final AdapterContextMenuInfo info = new AdapterContextMenuInfo(arg1, arg2, arg3);

	    //es prepara la alerta al crear la isntancia
        AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());
        alertbox.setMessage("Vols esborrar la tasca?");
        //cas afirmatiu
        alertbox.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            //Funcion llamada cuando se pulsa el boton Si
            public void onClick(DialogInterface arg0, int arg1) {
            	//Eliminem l'item seleccionat
        		titularsConv.remove(adapter.getItem(info.position));
        			// actualitzar la llista
        			refreshData();
                    Toast.makeText(getActivity(), "Tasca Eliminada", Toast.LENGTH_SHORT).show();	  
        }});
 
        //cas negatiu
        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
       
                Toast.makeText(getActivity(), "Tasca NO Eliminada", Toast.LENGTH_SHORT).show();	
                	
            }
        });
 
        //mostrem alertbox
        alertbox.show();
		
	}
}
