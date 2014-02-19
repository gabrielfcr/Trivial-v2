package com.example.trivialgabriel;



	import android.os.Bundle;
	import android.app.Activity;
	import android.content.Intent;
	import android.view.Menu;
	import android.view.animation.Animation;
	import android.view.animation.Animation.AnimationListener;
	import android.view.animation.AnimationUtils;
	import android.widget.ImageView;

	public class Splash extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_splash);
			
			 final ImageView imageView = (ImageView) findViewById(R.id.titulo);
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.animadotitulo);
			imageView.setAnimation(anim);
			
			final ImageView imageView2 = (ImageView) findViewById(R.id.interrogante1);
			Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.animadointerrog1);
			imageView2.setAnimation(anim2);
			
			final ImageView imageView3 = (ImageView) findViewById(R.id.interrogante2);
			Animation anim3 = AnimationUtils.loadAnimation(this, R.anim.animadointerrog2);
			imageView3.setAnimation(anim3);
			
			final ImageView imageView4 = (ImageView) findViewById(R.id.interrogante3);
			Animation anim4 = AnimationUtils.loadAnimation(this, R.anim.animadointerrog3);
			imageView4.setAnimation(anim4);
			
			final ImageView imageView5 = (ImageView) findViewById(R.id.logo);
			Animation anim5 = AnimationUtils.loadAnimation(this, R.anim.animadologo);
			imageView5.setAnimation(anim5);
			
			anim5.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
				
					
						Intent intent = new Intent(Splash.this,Main.class);
						startActivity(intent);
						Splash.this.finish();
					
					
				}
			});
			
			
		}
		
		
		
		

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.splash, menu);
			return true;
		}

	}
