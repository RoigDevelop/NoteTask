package com.example.actionbarsample;


import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fragment1 extends Fragment {
	Button btonSave;
	private EditText nom, desc;
	private String nomTasca, descTasca;
	private TitularsSQLiteHelper titHelper;
	private TitularsConversor titularsConv;
	private SQLiteDatabase db;
	

	public EditText getNom() {
		return nom;
	}

	public String getNomTasca() {
		return nomTasca;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		titHelper = new  TitularsSQLiteHelper(getActivity(), "Tasques.db", null, 2);
		 db = titHelper.getWritableDatabase();
        titularsConv = new TitularsConversor(titHelper);
		View rootView = inflater.inflate(R.layout.fragment1,container,false);
		btonSave = (Button) rootView.findViewById(R.id.button3);
		nom = (EditText) rootView.findViewById(R.id.editText1);
		desc = (EditText) rootView.findViewById(R.id.editText2);
		btonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				nomTasca = nom.getText().toString();
				descTasca = desc.getText().toString();
		
				if(db != null) {
		 			
		 			titularsConv.save(new Tasca(0, nomTasca.toString(), descTasca.toString()));
		 			getActivity().setResult(getActivity().RESULT_OK);
		 		}
				
			    nom.setText("");
			    desc.setText("");
			    Toast.makeText(getActivity(), "Tasca Guardada", Toast.LENGTH_SHORT).show();
			}
		});
		return rootView;
	}

}
