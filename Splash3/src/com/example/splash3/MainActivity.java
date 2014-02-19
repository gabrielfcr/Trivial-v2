package com.example.splash3;



import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {

	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
       
		final ImageView image = (ImageView) findViewById(R.id.imageView1);
		
		
	    Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanimation);//Llamamos a la animacion que hemos creado en el XML myanimation
	    Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);//Llamamos a la animacion que hemos creado en el XML alpha
	 
	    image.setAnimation(anim2); //Añadimos la animacion a nuestra imagen
	  

		final ImageView image2 = (ImageView) findViewById(R.id.imageView2);    
	    image2.setAnimation(anim);
	    

	anim.setAnimationListener(new AnimationListener(){

		@Override
		public void onAnimationEnd(Animation animation) {
			
			 Intent i = new Intent(MainActivity.this, Principal.class);
				
			 startActivity(i);
			 MainActivity.this.finish();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}});
	    
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Pedimos al sistema de ventanas de Android que nos quite el nombre de la aplicación y que además oculte la barra de notificaciones.
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}

	 public void lanzarAcercaDe(View view){	
		 Intent i = new Intent(this, Menu.class);
			
		 startActivity(i);
	   }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	


}
