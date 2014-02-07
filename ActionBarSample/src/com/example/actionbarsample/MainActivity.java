package com.example.actionbarsample;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements
		OnItemClickListener {

	private ArrayList<String> opcionsMenuLateral;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private String titolApp;
	private ActionBarDrawerToggle drawerToggle;
	protected CharSequence titolSeccio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		crearMenuLateral();
		configurarIconaActionBar();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setHomeButtonEnabled(true);
	}

	/**
	 * Crear les opcions que es mostraran en el menú lateral
	 */
	private void crearMenuLateral() {
		opcionsMenuLateral = new ArrayList<String>();
		opcionsMenuLateral.add("Opció 1");
		opcionsMenuLateral.add("Opció 2");
		opcionsMenuLateral.add("Opció 3");

		// mapejar l'objecte DrawerLayout
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// mapejar l'objecte ListView
		drawerList = (ListView) findViewById(R.id.left_drawer);
		// assignar l'adapter, indicant que el seu tema s'adapti a l'actual
		drawerList.setAdapter(new ArrayAdapter<String>(getSupportActionBar()
				.getThemedContext(), android.R.layout.simple_list_item_1,
				opcionsMenuLateral));
		// indicar quin és el listener
		drawerList.setOnItemClickListener(this);

	}

	/**
	 * Configurar la icona de l'action bar: una icona per quan el menú lateral 
	 * és visible i una altra per quan no és visible
	 */
	private void configurarIconaActionBar() {
		titolApp = getTitle().toString();

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_navigation_drawer, R.string.menu_obert,
				R.string.menu_tancat) {

			/**
			 * Canviar el títol quan es tanca el menú lateral
			 */
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(titolSeccio);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}

			/**
			 * Canviar el títol quan s'obre el menú lateral
			 */
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(titolApp);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }
		switch (item.getItemId()) {
		case R.id.action_add:
			obrirSegonaActivitat();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void obrirSegonaActivitat() {
		startActivity(new Intent(this, SecondActivity.class));

	}

	@Override
	/**
	 * Clic d'un element del menú lateral
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment fragment = null;

		// quina opció s'ha seleccionat?
		switch (position) {
		case 0:
			fragment = new Fragment1();
			break;
		case 1:
			fragment = new Fragment2();
			break;
		case 2:
			fragment = new Fragment3();
			break;
		}

		// obtenir el text que es mostrarà en el títol de l'action bar
		titolSeccio = opcionsMenuLateral.get(position);
		// substituir i posar el nou fragment
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// seleccionar l'element de la llista seleccionat
		drawerList.setItemChecked(position, true);
		// modificar el títol
		getActionBar().setTitle(opcionsMenuLateral.get(position));
		// tancar el menú lateral
		drawerLayout.closeDrawer(drawerList);
	}

	@Override
	/**
	 * Abans de mostrar el menú, indicar que volem ocultar l'action button
	 */
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (drawerLayout.isDrawerOpen(drawerList))
			menu.findItem(R.id.action_add).setVisible(false);
		else
			menu.findItem(R.id.action_add).setVisible(true);

		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	    drawerToggle.syncState();
	}
	 
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    drawerToggle.onConfigurationChanged(newConfig);
	}
}
