package org.pmm.pantalladepreferencias;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button  bt1, bt2;
	private TextView nombre, edad, sexo, sonido;
	private String nom, se;
	private boolean ed, so;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bt1 = (Button)findViewById(R.id.button1);
		bt2 = (Button)findViewById(R.id.button2);
		nombre = (TextView)findViewById(R.id.nombre);
		edad = (TextView)findViewById(R.id.edad);
		sexo = (TextView)findViewById(R.id.sexo);
		sonido = (TextView)findViewById(R.id.sonido);
		consultaDatos();
		
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, MisPreferncias.class));	
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				consultaDatos();
				
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
		startActivity(new Intent(MainActivity.this,MisPreferncias.class));
		return super.onOptionsItemSelected(item);
	}
	public void consultaDatos(){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		nom = pref.getString("nombre", "");
		se = pref.getString("sexo", "");
		ed = pref.getBoolean("edad", false);
		so = pref.getBoolean("sonido", false);
		nombre.setText("Nombre :"+nom);
		sexo.setText("Sexo: "+se);
		edad.setText("Edad: "+String.valueOf(ed));
		sonido.setText("Sonido: "+String.valueOf(so));
	}
	

}
