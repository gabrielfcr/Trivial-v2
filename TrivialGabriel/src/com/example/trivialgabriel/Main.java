package com.example.trivialgabriel;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Main extends Activity {
	private String[] datos;
	private ArrayAdapter<String> adaptador;
	private ListView lista;
	private Intent intent;
	private Builder ventana;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		datos = new String[] { "Play", "Score", "Credits" };
		adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, datos);
		lista = (ListView) findViewById(R.id.miListView);
		lista.setAdapter(adaptador);
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				switch (position) {
				case 0:
					intent = new Intent(Main.this, Play.class);
					break;
				case 1:
					intent = new Intent(Main.this, Score.class);
					break;
				case 2:
					intent = new Intent(Main.this, Credits.class);
					break;
				}
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			intent = new Intent(Main.this, Settings.class);
			startActivity(intent);
			return true;
		case R.id.help:
			ventana = new AlertDialog.Builder(this);
			ventana.setTitle("Ayuda");
			ventana.setMessage("Pulsa PLAY para empezar la diversiòn, pulsa atras para volver al menu");
			ventana.setIcon(R.drawable.help);
			ventana.show();
			return true;
		default:	
			return super.onOptionsItemSelected(item);
		}
	}

}
