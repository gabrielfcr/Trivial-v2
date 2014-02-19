package com.example.splash3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Credits extends Activity {
	private String line;
	private TextView tv0,tv1,tv2,tv3;
	private int numero = 0;

	@Override
	
	protected void onCreate(Bundle savedInstanceState) {

		//Pedimos al sistema de ventanas de Android que nos quite el nombre de la aplicación y que además oculte la barra de notificaciones.
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		tv0 = (TextView)findViewById(R.id.textView5);
		tv1 = (TextView)findViewById(R.id.textView6);
		tv2 = (TextView)findViewById(R.id.textView7);
		tv3 = (TextView)findViewById(R.id.textView8);
		InputStream inputStream = getResources().openRawResource(R.raw.autores);
		DataInputStream dataStream = new DataInputStream(inputStream);
		try {
			Scanner sc = new Scanner(dataStream,"UTF-8");
			while(sc.hasNext()){
				line = sc.nextLine();
				switch(numero){
				case 0: tv0.setText(line);break;
				case 1: tv1.setText(line);break;
				case 2: tv2.setText(line);break;
				case 3: tv3.setText(line);break;				
				}
				Log.d("Devuelve", line);
				numero++;
			}
			sc.close();
			dataStream.close();
			inputStream.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		numero = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.credits, menu);
		return true;
	}

}
