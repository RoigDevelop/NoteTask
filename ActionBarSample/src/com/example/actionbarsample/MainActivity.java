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
		crearGestorDrawer();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setHomeButtonEnabled(true);
	}

	private void crearMenuLateral() {
		opcionsMenuLateral = new ArrayList<String>();
		opcionsMenuLateral.add("Opció 1");
		opcionsMenuLateral.add("Opció 2");
		opcionsMenuLateral.add("Opció 3");

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		drawerList.setAdapter(new ArrayAdapter<String>(getSupportActionBar()
				.getThemedContext(), android.R.layout.simple_list_item_1,
				opcionsMenuLateral));
		drawerList.setOnItemClickListener(this);

	}

	private void crearGestorDrawer() {
		titolApp = getTitle().toString();

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_navigation_drawer, R.string.menu_obert,
				R.string.menu_tancat) {

	

			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(titolSeccio);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}

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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment fragment = null;

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

		titolSeccio = opcionsMenuLateral.get(position);
		FragmentManager fragmentManager = getFragmentManager();

		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		drawerList.setItemChecked(position, true);
		getActionBar().setTitle(opcionsMenuLateral.get(position));
		drawerLayout.closeDrawer(drawerList);
	}

	@Override
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
