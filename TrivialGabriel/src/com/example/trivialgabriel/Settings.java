package com.example.trivialgabriel;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings extends Activity {
	private String[] data = new String[]{"None","dakkon.disca.upv.es","test server(no funciona)"};
	private	Spinner spiner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
		spiner = (Spinner)findViewById(R.id.spinner1);
		adapter.setDropDownViewResource 
		(android.R.layout.simple_spinner_dropdown_item);
		// Establecemos el adaptador en el Spinner
		spiner.setAdapter(adapter); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
