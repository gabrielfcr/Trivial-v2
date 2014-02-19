package com.example.splash3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.ls.LSInput;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Play extends Activity {

	final Handler handler = new Handler();
	Timer t = new Timer();

	final static int DIALOGO_CORRECTO = 1;

	Button boton1;
	Button boton2;
	Button boton3;
	Button boton4;
	ArrayList<Question> listapreg = new ArrayList<Question>();
	ProgressBar barraProgreso;
	TextView preguntas;
	TextView puntos;
	int numeroP;
	Ejecucion ejec;
	int puntuacion = 0;
	int total;
	

	private MediaPlayer mpCronometro, mpAplausos, mpRisas;
	private int progress;

	SharedPreferences prefs, prefsProgreso;
	SharedPreferences.Editor editor;
	Boolean pruebaEvaluar = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Pedimos al sistema de ventanas de Android que nos quite el nombre de
		// la aplicación y que además oculte la barra de notificaciones.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
		prefsProgreso = getSharedPreferences("PrferenciasProgreso",
				Context.MODE_PRIVATE);
		numeroP = prefsProgreso.getInt("numeroPregunta", 0);
		total = 0;
		iniciarReproduccionPrincipal();
		if (!comprobarBD()) {
			copiarBD();
		}
		barraProgreso = (ProgressBar) findViewById(R.id.progressBar1);
		barraProgreso.setProgress(0);

		boton1 = (Button) findViewById(R.id.button1);
		boton2 = (Button) findViewById(R.id.button2);
		boton3 = (Button) findViewById(R.id.button3);
		boton4 = (Button) findViewById(R.id.button4);

		// Question uno = new Question(
		// "Movies",
		// "¿Qué ocurrió con el satélite espacial europeo Olympus?",
		// new String[] {
		// "Nunga logró despegar de la Tierra",
		// "Debería estar en orbita alrededor de Venus pero esta en Marte",
		// "Fue destruido por un meteorito",
		// "Fue reposeido por un transbordador espacial ruso" },
		// 3, 1);
		// Question dos = new Question("Movies",
		// "¿En qué planeta está el volcán más grande del universo?",
		// new String[] { "En Mercurio", "En Marte", "En la Tierra",
		// "En Saturno" }, 2, 3);
		// Question tres = new Question(
		// "Movies",
		// "¿Qué planeta rota en sentido horario, haciendo que el sol aparezca siempre por el oesta?",
		// new String[] { "Venus", "Martes", "Júpiter", "Mercurio" }, 1, 4);
		// Question cuatro = new Question(
		// "Movies",
		// "¿Hacia dónde apunta la cola de los cometas, sin importar su dirección y sentido?",
		// new String[] { "Hacia el Sol", "Hacia el norte magnético",
		// "En contra del Sol", "Hacia el planeta más cercano" },
		// 1, 2);
		//
		// listapreg.add(uno);
		// listapreg.add(dos);
		// listapreg.add(tres);
		// listapreg.add(cuatro);

		listapreg = leerBD();

		puntos = (TextView) findViewById(R.id.textView2);
		preguntas = (TextView) findViewById(R.id.textView4);

		insertarPregunta();

		ejec = new Ejecucion();
		ejec.execute();

		boton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				puntuacion = ejec.puntuacion();
				ejec.continuar(false);
				respuesta(1);

			}
		});
		boton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				puntuacion = ejec.puntuacion();
				ejec.continuar(false);
				respuesta(2);

			}
		});
		boton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				puntuacion = ejec.puntuacion();
				ejec.continuar(false);
				respuesta(3);
			}
		});
		boton4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				puntuacion = ejec.puntuacion();
				ejec.continuar(false);
				respuesta(4);
			}
		});

	}

	public void sumarPuntos() {
		total = total + puntuacion;
		puntos.setText(total + "");
	}

	public void respuesta(int res) {

		Question pregunta = (Question) listapreg.get(numeroP);
		int correcta = pregunta.getRightAnswer();
		if (res == correcta) {
			switch (res) {

			case 1:
				boton1.setTextColor(Color.GREEN);
				break;
			case 2:
				boton2.setTextColor(Color.GREEN);
				break;
			case 3:
				boton3.setTextColor(Color.GREEN);
				break;
			case 4:
				boton4.setTextColor(Color.GREEN);
				break;
			}
			sumarPuntos();
			onCreateDialog(1).show();
		} else {
			switch (correcta) {

			case 1:
				boton1.setTextColor(Color.GREEN);
				break;
			case 2:
				boton2.setTextColor(Color.GREEN);
				break;
			case 3:
				boton3.setTextColor(Color.GREEN);
				break;
			case 4:
				boton4.setTextColor(Color.GREEN);
				break;
			}

			switch (res) {

			case 1:
				boton1.setTextColor(Color.RED);
				break;
			case 2:
				boton2.setTextColor(Color.RED);
				break;
			case 3:
				boton3.setTextColor(Color.RED);
				break;
			case 4:
				boton4.setTextColor(Color.RED);
				break;
			}

			onCreateDialog(2).show();

		}
	}

	@Override
	protected void onStop() {
		ejec.cancel(true);
		super.onStop();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

	public Dialog onCreateDialog(int id) {

		AlertDialog dialogo;
		AlertDialog.Builder builder = new AlertDialog.Builder(Play.this);

		switch (id) {

		case DIALOGO_CORRECTO:
			iniciarReproduccionAplausos();
			builder.setTitle("Acertaste");
			builder.setMessage("¿Continuar?");
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							numeroP++;

							t.schedule(new TimerTask() { // Prepara la ejecución
															// de una tarea
															// después de un
															// tiempo
															// determinado (ms).
										// Es una clase que representa una tarea
										// a ejecutar en un tiempo especificado.
										public void run() { // Dentro de este
															// método definimos
															// las operaciones
															// de la tarea a
															// realizar.
											handler.post(new Runnable() { // Gracias
																			// al
																			// método
																			// post,
																			// podemos
																			// acceder
																			// desde
																			// el
																			// hilo
																			// secundario
																			// al
																			// hilo
																			// principal.
												public void run() { // Realizo
																	// la tarea
																	// que
																	// quiero
																	// realizar
																	// al acabar
																	// el tiempo
																	// del
																	// schedule
																	// (1000ms).
													recorrerLista();
												}
											});
										}
									}, 1000);
							mpAplausos.stop();
							iniciarReproduccionPrincipal();
						}
					});
			break;

		case 2:
			iniciarReproduccionRisas();
			builder.setTitle("Fallastes");
			builder.setMessage("¿Continuar?");
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							numeroP++;
							t.schedule(new TimerTask() { // Prepara la ejecución
															// de una tarea
															// después de un
															// tiempo
															// determinado (ms).
										// Es una clase que representa una tarea
										// a ejecutar en un tiempo especificado.
										public void run() { // Dentro de este
															// método definimos
															// las operaciones
															// de la tarea a
															// realizar.
											handler.post(new Runnable() { // Gracias
																			// al
																			// método
																			// post,
																			// podemos
																			// acceder
																			// desde
																			// el
																			// hilo
																			// secundario
																			// al
																			// hilo
																			// principal.
												public void run() { // Realizo
																	// la tarea
																	// que
																	// quiero
																	// realizar
																	// al acabar
																	// el tiempo
																	// del
																	// schedule
																	// (1000ms).
													recorrerLista();
												}
											});
										}
									}, 1000);
							mpRisas.stop();
							iniciarReproduccionPrincipal();
						}

					});
			break;

		case 3:
			iniciarReproduccionRisas();
			builder.setTitle("Tiempo agotado");
			builder.setMessage("¿Continuar?");
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							numeroP++;
							recorrerLista();
							mpRisas.stop();
							iniciarReproduccionPrincipal();
						}

					});
			break;

		case 4:
			iniciarReproduccionAplausos();
			builder.setTitle("Fin");
			builder.setMessage("Tu puntuación es: " + total);
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// RECOGEMOS LOS DATOS DEL XML
							ArrayList<Puntuacion> miLista = ordenarPuntuacion(compruebaPuntuacion(leerXML()));
							for (int i = 0; i < miLista.size(); i++) {
								Log.d("Nombre: ", miLista.get(i).getNombre()
										.toString());
								Log.d("Puntuacion: ", String.valueOf(miLista
										.get(i).getPuntuacion()));
							}
							String nombre = prefs.getString("nombre", "");
							Intent i;
							if(!nombre.equals("")){
								Log.d("Estas dentro", "DENTRO");
								escribirXML(miLista);
								i = new Intent(getApplicationContext(),
										Principal.class);
							}else{
								Log.d("Estas fura", "FUERA");
								i = new Intent(getApplicationContext(),
										Settings.class);
								Toast.makeText(getApplicationContext(), "INTRODUCE UN USER NAME PARA PODER PUNTUAR", Toast.LENGTH_LONG).show();
							}
							mpAplausos.stop();
							pruebaEvaluar = false;
							finish();
							startActivity(i);
						}
					});
			break;

		}
		return dialogo = builder.create();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_help:

			int help = listapreg.get(numeroP).getHelp();
			switch (help) {
			case 1:
				boton1.setEnabled(false);
				break;
			case 2:
				boton2.setEnabled(false);
				break;
			case 3:
				boton3.setEnabled(false);
				break;
			case 4:
				boton4.setEnabled(false);
				break;

			}

			break;
		case R.id.menu_volvermenu:

			Intent i = new Intent(Play.this, Principal.class);
			startActivity(i);
			Play.this.finish();

		}
		return super.onOptionsItemSelected(item);
	}

	public void insertarPregunta() {
		Question pregunta = (Question) listapreg.get(numeroP);
		preguntas.setText(pregunta.getQuestionText());
		boton1.setText(pregunta.answers[0]);
		boton2.setText(pregunta.answers[1]);
		boton3.setText(pregunta.answers[2]);
		boton4.setText(pregunta.answers[3]);
		boton1.setEnabled(true);
		boton2.setEnabled(true);
		boton3.setEnabled(true);
		boton4.setEnabled(true);
		boton1.setTextColor(Color.BLACK);
		boton2.setTextColor(Color.BLACK);
		boton3.setTextColor(Color.BLACK);
		boton4.setTextColor(Color.BLACK);
	}

	public void recorrerLista() {
		ejec.cancel(true);
		if (numeroP < listapreg.size()) {

			insertarPregunta();
			listapreg.get(numeroP);
			ejec = new Ejecucion();
			ejec.execute();
		} else {

			onCreateDialog(4).show();
		}
	}

	public class Ejecucion extends AsyncTask<Void, Integer, Void> {
		boolean continuar = true;

		public void continuar(boolean cont) {

			this.continuar = cont;
		}

		public int puntuacion() {
			int punt = 100 - progress;
			return punt;
		}

		@Override
		protected void onPreExecute() {
			progress = 0;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			while (progress < 100 && continuar && !isCancelled()) {

				progress++;
				publishProgress(progress);

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		// Sirve para mostrar progreso visual en la interfaz de usuario mientra
		// se realiza tareas en segundo plano
		// Para recuperar los valores dentro del onProgressUpdate, habrá que
		// saber que este parámetro es un array estático, conlo cual habrá que
		// tratarlo como tal.
		@Override
		protected void onProgressUpdate(Integer... values) {

			barraProgreso.setProgress(values[0]);
		}

		protected void onPostExecute(Void result) {

			if (continuar) {

				onCreateDialog(3).show();

			}
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		pruebaEvaluar = false;
		Intent i = new Intent(Play.this, Principal.class);
		startActivity(i);
		Play.this.finish();
		// super.onBackPressed();
	}

	// Comprobar si la puntuacion obtenida es mayor que la anterior y si es un
	// nuevo usuario lo añade a la lista
	public ArrayList<Puntuacion> compruebaPuntuacion(
			ArrayList<Puntuacion> listaPuntuacion) {
		String nombre = prefs.getString("nombre", "");
		Boolean nuevoUsurio = true;
		for (int i = 0; i < listaPuntuacion.size(); i++) {
			if (listaPuntuacion.get(i).getNombre().equals(nombre)) {
				nuevoUsurio = false;
				if (listaPuntuacion.get(i).getPuntuacion() < total) {
					listaPuntuacion.get(i).setPuntuacion(total);
				}
			}
		}
		if (nuevoUsurio) {
			listaPuntuacion.add(new Puntuacion(nombre, total));
		}
		return listaPuntuacion;
	}

	/*
	 * 
	 * XML PUNTUACIONES
	 */

	// Metodo para comprobar si hay xml
	public void comprobarXML() {

	}

	// Metodo para leer de XML
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

	// Metodo que ordena puntuaciones
	public ArrayList<Puntuacion> ordenarPuntuacion(
			ArrayList<Puntuacion> listaPuntuacion) {
		Collections.sort(listaPuntuacion, new Comparator<Puntuacion>() {
			@Override
			public int compare(Puntuacion pt1, Puntuacion pt2) {
				return new Integer(pt2.getPuntuacion()).compareTo(new Integer(
						pt1.getPuntuacion()));
			}
		});
		return listaPuntuacion;
	}

	// Metodo que escribe puntuaciones en el XML
	public void escribirXML(ArrayList<Puntuacion> listaPuntuacion) {
		XmlSerializer serialiser = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serialiser.setOutput(writer);
			serialiser.startDocument(null, null);
			serialiser.startTag(null, "ListaPuntuacion");
			for (int i = 0; i < listaPuntuacion.size(); i++) {
				serialiser.startTag(null, "Jugador");
				serialiser.attribute(null, "ranking", String.valueOf(i + 1));
				serialiser.attribute(null, "nombre", listaPuntuacion.get(i)
						.getNombre());
				serialiser.attribute(null, "puntuacion",
						String.valueOf(listaPuntuacion.get(i).getPuntuacion()));
				serialiser.endTag(null, "Jugador");
			}
			serialiser.endTag(null, "ListaPuntuacion");
			serialiser.endDocument();
			FileOutputStream fos = openFileOutput("Score.xml", MODE_PRIVATE);
			fos.write(writer.toString().getBytes());
			fos.flush();
			fos.close();
		} catch (Exception ex) {
			Log.d("Error", "No se puede escribir en el XML");
		}

	}

	/*
	 * 
	 * BASE DE DATOS
	 */

	// Metodo para comprobar si esta la base de datos "supertrivialgame.db"
	public boolean comprobarBD() {
		File fichero = getDatabasePath("supertrivialgame.db");
		if (fichero.isFile())
			return true;
		else
			return false;
	}

	// Metodo para copiar la base de datos de raw al dispositivo interno
	public void copiarBD() {
		SQLiteDatabase db = this.openOrCreateDatabase("supertrivialgame.db",
				MODE_PRIVATE, null);
		InputStream in = getResources().openRawResource(R.raw.supertrivialgame);
		try {
			FileOutputStream out = new FileOutputStream(db.getPath());
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			Toast.makeText(
					getApplicationContext(),
					"No se puede copiar la base de datos al dispositivo interno",
					Toast.LENGTH_LONG);
		}
		db.close();

	}

	// Metodo para leer de la base de datos y guradar el reultado en un
	// arraylist
	public ArrayList<Question> leerBD() {
		ArrayList<Question> lista = new ArrayList<Question>();
		SQLiteDatabase db = this.openOrCreateDatabase("supertrivialgame.db",
				MODE_PRIVATE, null);
		Cursor c1 = db
				.rawQuery(
						"select subject, questionText, answer1, answer2, answer3, answer4, rightanswer, help from questions",
						null);
		while (c1.moveToNext()) {
			lista.add(new Question(c1.getString(0), c1.getString(1),
					new String[] { c1.getString(2), c1.getString(3),
							c1.getString(4), c1.getString(5) }, c1.getInt(6),
					c1.getInt(7)));
		}
		c1.close();
		db.close();
		return lista;
	}

	// Metodos para iniciar Reproduccion
	public void iniciarReproduccionPrincipal() {
		mpCronometro = MediaPlayer.create(this, R.raw.cronometro);
		mpCronometro.start();
		mpCronometro.setLooping(true);
	}

	public void iniciarReproduccionRisas() {
		mpCronometro.stop();
		mpRisas = MediaPlayer.create(this, R.raw.risas);
		mpRisas.start();
	}

	public void iniciarReproduccionAplausos() {
		mpCronometro.stop();
		mpAplausos = MediaPlayer.create(this, R.raw.aplausos);
		mpAplausos.start();
	}

	@Override
	protected void onPause() {
		mpCronometro.stop();
		editor = prefsProgreso.edit();
		if (pruebaEvaluar) {	
			editor.putInt("progreso", progress);
			editor.putInt("numeroPregunta", numeroP);
			editor.putInt("puntos", total);
			
		}else{
			editor.putInt("progreso", 0);
			editor.putInt("numeroPregunta", 0);
			editor.putInt("puntos", 0);
		}
		editor.commit();
		

		Log.d("onPause PROGRESS", String.valueOf(progress));
		Log.d("onPause Numero de la pregunta", String.valueOf(numeroP));
		super.onPause();
	}

	@Override
	protected void onResume() {
		progress = prefsProgreso.getInt("progreso", 0);
		numeroP = prefsProgreso.getInt("numeroPregunta", 0);
		total = prefsProgreso.getInt("puntos", 0);
		puntos.setText(String.valueOf(total));
		Log.d("onResume PROGRESS", String.valueOf(progress));
		Log.d("onResume Numero de la pregunta", String.valueOf(numeroP));
		super.onResume();

	}
	

}
