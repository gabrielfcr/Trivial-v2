package com.example.trivialgabriel;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Play extends Activity {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.helpPlay:
			algo = true;
			ayuda();
			item.setEnabled(false);
			help = false;
			break;
		case R.id.comodin:
			new SegundoHilos().execute();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (help)
			menu.findItem(R.id.helpPlay).setEnabled(true);
		return super.onPrepareOptionsMenu(menu);
	}

	private TextView tvpregunta, tvscore, tvtiempo;
	private Button bt1, bt2, bt3, bt4;
	private ProgressBar pgb;
	private AlertDialog.Builder dialog;
	public ArrayList<Question> preguntas = new ArrayList<Question>();;
	private int posicion;
	private int puntosGenerales, puntos;
	private int progress;
	private UpdateProgress barra;
	private Boolean help, algo = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		tvpregunta = (TextView) findViewById(R.id.miTextoPregunta);
		tvscore = (TextView) findViewById(R.id.miTextoScore);
		tvtiempo = (TextView)findViewById(R.id.miTiempo);
		bt1 = (Button) findViewById(R.id.miBoton1);
		bt2 = (Button) findViewById(R.id.miBoton2);
		bt3 = (Button) findViewById(R.id.miBoton3);
		bt4 = (Button) findViewById(R.id.miBoton4);
		pgb = (ProgressBar) findViewById(R.id.miProgressbar);
		pgb = (ProgressBar) findViewById(R.id.miProgressbar);
		cargarPreguntas();
		cargaSiguientePregunta();
		posicion = 0;
		puntosGenerales = 0;
		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				barra.cancel(true);
				if (preguntas.get(posicion).getRightAnswer() == 0) {
					bt1.getBackground().setColorFilter(0xFF00FF00,
							PorterDuff.Mode.MULTIPLY);
					Mensaje("Has acertado");
					puntos = 100 - progress;
					puntosGenerales += puntos;
				} else {
					bt1.getBackground().setColorFilter(0xFFFF0000,
							PorterDuff.Mode.MULTIPLY);
					Mensaje("Has fallado");
				}

			}
		});
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				barra.cancel(true);
				if (preguntas.get(posicion).getRightAnswer() == 1) {
					bt2.getBackground().setColorFilter(0xFF00FF00,
							PorterDuff.Mode.MULTIPLY);
					Mensaje("Has acertado");
					puntos = 100 - progress;
					puntosGenerales += puntos;
				} else {
					bt2.getBackground().setColorFilter(0xFFFF0000,
							PorterDuff.Mode.MULTIPLY);
					Mensaje("Has fallado");
				}

			}
		});
		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				barra.cancel(true);
				if (preguntas.get(posicion).getRightAnswer() == 2) {
					bt3.getBackground().setColorFilter(0xFF00FF00,
							PorterDuff.Mode.MULTIPLY);
					Mensaje("Has acertado");
					puntos = 100 - progress;
					puntosGenerales += puntos;
				} else {
					bt3.getBackground().setColorFilter(0xFFFF0000,
							PorterDuff.Mode.MULTIPLY);
					Mensaje("Has fallado");
				}

			}
		});
		bt4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				barra.cancel(true);
				if (preguntas.get(posicion).getRightAnswer() == 3) {
					bt4.getBackground().setColorFilter(0xFF00FF00,
							PorterDuff.Mode.MULTIPLY);
					Mensaje("Has acertado");
					puntosGenerales += puntos;
				} else {
					bt4.getBackground().setColorFilter(0xFFFF0000,
							PorterDuff.Mode.MULTIPLY);
					Mensaje("Has fallado");
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

	public void cargarPreguntas() {
		preguntas.add(new Question("Historia",
				"¿En qué año finalizó la Guerra Civil Española?", new String[] {
						"1929", "1932", "1939", "1937" }, 2, 0));
		preguntas
				.add(new Question(
						"Deportes",
						"¿Cuál fue el tenista que lideró más tiempo el titulo de número uno?",
						new String[] { "Pete Sampras", "André Agassi",
								"Rafael Nadal", "Roger Federer" }, 3, 1));

		preguntas.add(new Question("Literatura",
				"¿Quién fue el autor del Lazarillo de Tormes?", new String[] {
						"Miguel de Cervantes", "Anónimo", "Pio Baroja",
						"Federico García Lrca" }, 1, 3));
		preguntas
				.add(new Question(
						"Informatica",
						"¿Qué nuevo Sistema Operativo va a lanzar para moviles en 2013?",
						new String[] { "Ubuntu Phone", "Android Cake",
								"Neo Samsung ", "Xtream" }, 0, 2));
		preguntas
				.add(new Question(
						"Geografia",
						"¿Cuanto tiempo tarda la Tierra en dar una vuelta sobre sí misma?",
						new String[] { "Un dia", "Un año", "28 dias",
								"Una semana" }, 0, 3));
	}

	public void cargaSiguientePregunta() {
		help = true;
		bt1.setVisibility(View.VISIBLE);
		bt2.setVisibility(View.VISIBLE);
		bt3.setVisibility(View.VISIBLE);
		bt4.setVisibility(View.VISIBLE);
		barra = new UpdateProgress();
		barra.execute();
		String[] listaRespuestas;
		Question question;
		question = preguntas.get(posicion);
		listaRespuestas = question.getAnswers();
		tvscore.setText(Integer.toString(puntosGenerales));// //PUEDE DAR ERROR
		tvpregunta.setText(question.getQuestionText());
		bt1.setText(listaRespuestas[0]);
		bt2.setText(listaRespuestas[1]);
		bt3.setText(listaRespuestas[2]);
		bt4.setText(listaRespuestas[3]);
		bt1.getBackground()
				.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		bt2.getBackground()
				.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		bt3.getBackground()
				.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		bt4.getBackground()
				.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);

	}

	public void Mensaje(String palabra) {
		posicion++;
		String mensaje;
		dialog = new AlertDialog.Builder(Play.this);
		if (palabra.equals("Se ha terminado el Tiempo")) {
			mensaje = palabra + " !!! Siguiente pregunta, ¿Quieres continuar?";
		}
		if (posicion != preguntas.size()) {
			mensaje = palabra + " !!! Siguiente pregunta, ¿Quieres continuar?";
		} else {
			mensaje = palabra + "!!! Se ha finalizado el juego";
		}
		dialog.setMessage(mensaje);
		dialog.setCancelable(false);
		dialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (posicion != (preguntas.size())) {
					cargaSiguientePregunta();
				} else {
					Intent data = new Intent();
					setResult(RESULT_OK, data);
					finish();
				}

			}
		});
		if (posicion != preguntas.size()) {
			dialog.setNegativeButton("NO",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							setResult(RESULT_CANCELED);
							Play.this.finish();

						}
					});
		}
		dialog.show();

	}

	public class UpdateProgress extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Void result) {
			// Una vez que el proceso haya terminado, el botón podrá
			// ser presionado nuevamente después de cambiar su atributo
			// clickable.
			barra.cancel(true);
			Mensaje("Se ha terminado el Tiempo");

		}

		@Override
		protected void onPreExecute() {
			// Definimos el progreso de la barra en 0 para empezar.
			progress = 0;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// Actualiza la ProgressBar
			// Se invoca una vez después de una llamada a
			// publishProgress(progress)
			pgb.setProgress(values[0]);
		}

		@Override
		protected Void doInBackground(Void... params) {
			/*
			 * En este método definimos la forma en la que estará avanzando la
			 * ProgressBar, para lo cual nos auxiliamos de la clase SystemClock,
			 * y para cada vez que nuestra variable progress aumente se
			 * actualice su avance en el UI thread.
			 */
			while (progress < 100) {
				if (algo) {
					progress++;
					publishProgress(progress);
					// ahora pasa a realizar el onProgressUpdate
					SystemClock.sleep(100); // duerme el proceso 100
											// milisegundos
					if (isCancelled()) {
						break;
					}
				}
			}
			return null;
		}

	}
	public class SegundoHilos extends AsyncTask<Void, Integer, Void> {
		int i = 6;

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Void result) {
			// Una vez que el proceso haya terminado, el botón podrá
			// ser presionado nuevamente después de cambiar su atributo
			// clickable.
			algo = true;
			tvtiempo.setVisibility(View.INVISIBLE);

		}

		@Override
		protected void onPreExecute() {
			// Definimos el progreso de la barra en 0 para empezar.
			algo = false;
			tvtiempo.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// Actualiza la ProgressBar
			// Se invoca una vez después de una llamada a
			// publishProgress(progress)
			tvtiempo.setText(String.valueOf(i));
		}

		@Override
		protected Void doInBackground(Void... params) {
			/*
			 * En este método definimos la forma en la que estará avanzando la
			 * ProgressBar, para lo cual nos auxiliamos de la clase SystemClock,
			 * y para cada vez que nuestra variable progress aumente se
			 * actualice su avance en el UI thread.
			 */
			
			while (i > 0) {
				i--;
				publishProgress(i);
				System.out.println(i);
				SystemClock.sleep(1000);
			}
			return null;
		}

	}
	

	public void ayuda() {
		int pre = preguntas.get(posicion).getHelp();
		switch (pre) {
		case 0:
			bt1.setVisibility(View.INVISIBLE);
			break;
		case 1:
			bt2.setVisibility(View.INVISIBLE);
			break;
		case 2:
			bt3.setVisibility(View.INVISIBLE);
			break;
		case 3:
			bt4.setVisibility(View.INVISIBLE);
			break;
		}
	}
}
