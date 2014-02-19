package com.example.splash3;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Settings extends Activity {

	AlertDialog.Builder builder;
	private Button preferences;
	private Spinner spiner1;
	private EditText nameUsuario, email, addEmail;
	private String nombre, emilio , add;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	static private String[] listpreferences = {"Sports","Literature","Science","Movies","History"};
	static final boolean seleccionados[] = new  boolean[listpreferences.length];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		nameUsuario = (EditText)findViewById(R.id.editText1);
		email = (EditText)findViewById(R.id.editText2);
		addEmail = (EditText)findViewById(R.id.editText3);
		prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
		spiner1 = (Spinner)findViewById(R.id.spinner1);
		preferences = (Button)findViewById(R.id.button1);
		
		
		String[] spiner = new String[]{"None","dakkon.disca.upv.es","test server(not working)"};
		
	    ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item,spiner);
		adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		spiner1.setAdapter(adapter);
		
		
		    
		
		preferences.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				onCreateDialog(1).show();
						
			}
			
						
		});
	}
	
	
	
	 @Override
	protected void onPause() {
		nombre = nameUsuario.getText().toString();
		emilio = email.getText().toString();
		add = addEmail.getText().toString();
		editor = prefs.edit();
        editor.putString("email", emilio);
        editor.putString("nombre", nombre);
        editor.putString("addAmigo", add);
        editor.putInt("posicion", spiner1.getSelectedItemPosition());
        for (int i = 0; i < seleccionados.length; i++) {
			switch (i) {
			case 0:editor.putBoolean("Sports", seleccionados[i]);break;
			case 1:editor.putBoolean("Literature", seleccionados[i]);break;
			case 2:editor.putBoolean("Science", seleccionados[i]);break;
			case 3:editor.putBoolean("Movies", seleccionados[i]);break;
			case 4:editor.putBoolean("History", seleccionados[i]);break;
			}
		}
        for (int i = 0; i < seleccionados.length; i++) {
			Log.d("booleanoOnresumen", String.valueOf(seleccionados[i]));
		}
        editor.commit();
		super.onPause();
	}



	@Override
	protected void onResume() {
		nameUsuario.setText(prefs.getString("nombre", ""));
		email.setText(prefs.getString("email", ""));
		addEmail.setText(prefs.getString("addAmigo", ""));
		spiner1.setSelection(prefs.getInt("posicion", 0));
		for (int i = 0; i < seleccionados.length; i++) {
			switch (i) {
			case 0: seleccionados[i] = prefs.getBoolean("Sports", true);break;
			case 1: seleccionados[i] = prefs.getBoolean("Literature", true);break;
			case 2: seleccionados[i] = prefs.getBoolean("Science", true);break;
			case 3: seleccionados[i] = prefs.getBoolean("Movies", true);break;
			case 4: seleccionados[i] = prefs.getBoolean("History", true);break;
			}
			
		}
		super.onResume();
	}



	protected Dialog onCreateDialog(int id){
		 
		for (int i = 0; i < seleccionados.length; i++) {
			Log.d("booleanoODialogo", String.valueOf(seleccionados[i]));
		}
	 	  builder = new AlertDialog.Builder(Settings.this);
	 	  
	 	    switch(id){
	 	         
	 	    case 1:
	 	    	
	 	    	
	 	  builder.setMultiChoiceItems(listpreferences, seleccionados,new DialogInterface.OnMultiChoiceClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				
				if(isChecked){			
					seleccionados[which]=true;
				}else{
					seleccionados[which]=false;
				}
			}
	 		  
	 		  
	 	  });
	 	    	
	 	   builder.setTitle("Questions preferences");
	 	   
	 	   
	 	   builder.setCancelable(true);
	 	   
	 	   builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				arg0.cancel();
				
			}
	 		   	 		   
	 	   });
	 	   
	 	  builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
					arg0.cancel();
					
				}
		 		   	 		   
		 	   });
	 	    break;
	 	    
	 	    }
	 	    
			return  builder.create();
		 
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
