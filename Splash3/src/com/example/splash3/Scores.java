package com.example.splash3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Scores extends Activity {
	private TableLayout tabla;
	private ArrayList<Puntuacion> listaJugadores = new ArrayList<Puntuacion>();
	int nColunas = 3, nFilas = 40;
	private Principal principal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Pedimos al sistema de ventanas de Android que nos quite el nombre de
		// la aplicación y que además oculte la barra de notificaciones.
//		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		principal = new Principal();
//		
		
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scores);
		tabla = (TableLayout) findViewById(R.id.miTabla);
//		listaJugadores = leerXML();
//		for (int i = 0; i < listaJugadores.size(); i++) {
//			TableRow tr = new TableRow(this);
//			for (int j = 0; j < nColunas; j++) {
//				TextView tv = new TextView(this);
//				switch (j) {
//				case 0:
//					tv.setText(listaJugadores.get(i).getNombre());
//					break;
//				case 1:
//					tv.setText(String.valueOf(listaJugadores.get(i)
//							.getPuntuacion()));
//					break;
//				case 2:
//					tv.setText(String.valueOf(i + 1));
//					break;
//				}
//				tv.setPadding(0, 0, 80, 20);
//				tr.addView(tv);
//			}
//			tabla.addView(tr);
//		}
			
		 new Hilo().execute();
	}
	//Hilo para cargar los jugadores desde el XML
	public class Hilo extends AsyncTask<Void, TableRow, Void> {
		TableRow tr;
		TextView tv;

		@Override
		protected void onPreExecute() {
			Scores.this.setProgressBarIndeterminate(true);
			Scores.this.setProgressBarIndeterminateVisibility(true);
			Toast.makeText(getApplicationContext(),
					"A empezado la carga de jugadores", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			listaJugadores = leerXML();
			for (int i = 0; i < listaJugadores.size(); i++) {
				tr = new TableRow(getApplicationContext());
				for (int j = 0; j < nColunas; j++) {
					TextView tv = new TextView(getApplicationContext());
					
					
					switch (j) {
					case 0:
						tv.setText(listaJugadores.get(i).getNombre());
						tv.setPadding(0, 0, 80, 20);
						break;
					case 1:
						tv.setText(String.valueOf(listaJugadores.get(i)
								.getPuntuacion()));
						tv.setPadding(0, 0, 140, 20);
						break;
					case 2:
						tv.setText(String.valueOf(i + 1));
						break;
					}
					tv.setTextColor(Color.GREEN);
					tv.setTextColor(Color.rgb(224, 191, 56));
					tv.setTextSize(30);
					
					tr.addView(tv);
				}
				publishProgress(tr);
				SystemClock.sleep(100);
			}
			return null;

		}

		@Override
		protected void onProgressUpdate(TableRow... values) {
			tabla.addView(tr);
		}

		@Override
		protected void onPostExecute(Void result) {
			Scores.this.setProgressBarIndeterminate(false);
			Scores.this.setProgressBarIndeterminateVisibility(false);
			Toast.makeText(getApplicationContext(), "Ha finalizado",
					Toast.LENGTH_LONG).show();
		}

	}	
	//Metodo para leer desde el XML
	public ArrayList<Puntuacion> leerXML() {
		ArrayList<Puntuacion> listaPuntuacion = new ArrayList<Puntuacion>();
		try {
			FileInputStream inputStream = openFileInput("Score.xml");
			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(inputStream, null);
			int evenType = XmlPullParser.START_DOCUMENT;
			while (evenType != XmlPullParser.END_DOCUMENT) {
				if (evenType == XmlPullParser.START_TAG) {
					if (parser.getAttributeValue(null, "nombre") != null) {
						String datoNombre = parser.getAttributeValue(null,
								"nombre");
						String datoPuntuacion = parser.getAttributeValue(null,
								"puntuacion");
						Puntuacion puntuacion = new Puntuacion(datoNombre,
								Integer.valueOf(datoPuntuacion));
						listaPuntuacion.add(puntuacion);
					}
				}
				evenType = parser.next();
			}
			inputStream.close();
		} catch (Exception e) {
			Log.d("ERROR",
					"No se puede leer desde el XML numero de error:"
							+ e.getMessage());
		}
		return listaPuntuacion;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scores, menu);
		return true;
	}
	@Override
	protected void onPause() {
		principal.finish();
		super.onPause();
	}
	@Override
	protected void onStop() {
		principal.finish();
		super.onStop();
	}
	

}
