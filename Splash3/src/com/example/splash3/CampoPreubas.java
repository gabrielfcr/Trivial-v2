package com.example.splash3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class CampoPreubas extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_campo_preubas);
//		if(comprobarBD()){
//			String pregunta = leerBD().get(0).getSubject();
//			Log.d("RESULTADO", pregunta);
//		}else{
//			
//			copiarBD();
//		}
		comprobarXML();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.campo_preubas, menu);
		return true;
	}
	//Metodo para comprobar si esta la base de datos "supertrivialgame.db"
	public boolean comprobarBD(){
		File fichero = getDatabasePath("supertrivialgame.db");
		if(fichero.isFile())
			return true;
		else
			return false;
	}
	//Metodo para copiar la base de datos de raw al dispositivo interno
	public void copiarBD(){
		SQLiteDatabase db = this.openOrCreateDatabase("supertrivialgame.db", MODE_PRIVATE, null);
		InputStream in = getResources().openRawResource(R.raw.supertrivialgame);
		try {
			FileOutputStream out = new FileOutputStream(db.getPath());
			int c;
			while( (c = in.read() ) != -1){
				out.write(c);				
			}				
			in.close();
			out.close();		
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "No se puede copiar la base de datos al dispositivo interno", Toast.LENGTH_LONG);
		}
		db.close();
		
	}
	//Metodo para leer de la base de datos y guradar el reultado en un arraylist
	public ArrayList<Question> leerBD(){
		ArrayList<Question> lista = new ArrayList<Question>();
		SQLiteDatabase db = this.openOrCreateDatabase("supertrivialgame.db", MODE_PRIVATE, null);
		Cursor c1 = db.rawQuery("select subject, questionText, answer1, answer2, answer3, answer4, rightanswer, help from questions", null);
		while(c1.moveToNext()){
			lista.add(new Question(c1.getString(0), c1.getString(1), new String[]{
				c1.getString(2),
				c1.getString(3),
				c1.getString(4),
				c1.getString(5)
			}, c1.getInt(6),  c1.getInt(7)));
		}
		c1.close();
		db.close();
		return lista;
	}
	//Metodo para comprobar XML
	public void comprobarXML(){
		try {
			FileInputStream inputStream = openFileInput("Score.xml");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
