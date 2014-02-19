package com.example.splash3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;

public class Principal extends Activity {

	private Button btplay;
	private Button btcredits;
	private Button btscores;
	private MediaPlayer mpPrincipal;
	private Boolean evaluar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		evaluar = false;
		setContentView(R.layout.activity_principal);
		mpPrincipal = MediaPlayer.create(this, R.raw.audio);
		mpPrincipal.setLooping(true);
		mpPrincipal.start();
		
		btplay = (Button)findViewById(R.id.button1);
		btscores = (Button)findViewById(R.id.button2);
		btcredits = (Button)findViewById(R.id.button3);
		
		btplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(Principal.this,Play.class);
				
				 startActivity(i);
				
				 Principal.this.finish();
				
			}
		});
		
        btscores.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				evaluar = true;
				Intent i = new Intent(Principal.this, Scores.class);
				
				 startActivity(i);
				
			}
		});

         btcredits.setOnClickListener(new OnClickListener() {
	
	         @Override
	         public void onClick(View v) {
	        	 evaluar = true;
	            	Intent i = new Intent(Principal.this, Credits.class);
	  	
		     startActivity(i);
		
	}
});
         
		
	
	}

	@Override
	protected void onResume() {
		mpPrincipal.start();
		evaluar = false;
		super.onResume();
	}

	@Override
	protected void onPause() {
		if(!evaluar){
			mpPrincipal.pause();
		}
		super.onPause();
	}
	

	@Override
	protected void onStop() {
		if(!evaluar){
			mpPrincipal.pause();
		}
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
  @Override
public boolean onOptionsItemSelected(MenuItem item){
	
	  switch(item.getItemId()){	  
	  case R.id.menu_help:
		AlertDialog.Builder builder;
	    	
  	    builder = new AlertDialog.Builder(this);
  	    builder.setMessage("Este es el dialogo de ayuda");
  	    builder.show();
	   break;
	   
	  case R.id.menu_settings:
		  evaluar = true;
		  Intent i  = new Intent(Principal.this, Settings.class);
		  startActivity(i);
			  
	   break;
	  }
	return super.onOptionsItemSelected(item);
  }
 	
  
     }

