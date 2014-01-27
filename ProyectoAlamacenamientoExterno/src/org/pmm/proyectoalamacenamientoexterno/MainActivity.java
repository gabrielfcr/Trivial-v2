package org.pmm.proyectoalamacenamientoexterno;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Printer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText et1, et2;
	private Button bt1, bt2;
	private String nombreArchivo, contenidoArchivo, ruta;
	private String[] lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		bt1 = (Button) findViewById(R.id.button1);
		bt2 = (Button) findViewById(R.id.button2);

		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				nombreArchivo = et1.getText().toString();
				contenidoArchivo = et2.getText().toString();
				if (nombreArchivo.equals("") || contenidoArchivo.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"Tienes que poner un nombre de archivo y algo de contenido",
							Toast.LENGTH_SHORT).show();
				} else {
					String estado = Environment.getExternalStorageState();
					if (Environment.MEDIA_MOUNTED.equals(estado)) {
						try {
							File archivo = new File(getExternalFilesDir(null),
									nombreArchivo.trim());
							PrintWriter printer = new PrintWriter(archivo);
							printer.write(contenidoArchivo);
							printer.close();
							Toast.makeText(
									getApplicationContext(),
									"El archivo se guardo correctamente con el contenido",
									Toast.LENGTH_SHORT).show();
						} catch (FileNotFoundException e) {
							Toast.makeText(
									getApplicationContext(),
									"ERROR no se ha podido crear ni guardar el contenido",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					}else {
						Toast.makeText(getApplicationContext(),
								"NO se puede leer ni escribir en almacenamietno Interno",
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				et2.setText("");
				contenidoArchivo = "";
				nombreArchivo = et1.getText().toString();
				String estado = Environment.getExternalStorageState();
				if (Environment.MEDIA_MOUNTED.equals(estado)) {
					ruta = getExternalFilesDir(null).getPath() + "/"
							+ nombreArchivo.trim();
					File archivo = new File(ruta);
					if (archivo.exists()) {
						try {
							Scanner sc = new Scanner(archivo);
							while (sc.hasNext()) {
								contenidoArchivo += sc.nextLine();
							}
							et2.setText(contenidoArchivo);
						} catch (FileNotFoundException e) {
							Toast.makeText(getApplicationContext(),
									"ERROR leer del archivo",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
						Toast.makeText(getApplicationContext(), ruta,
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(),
								"El fichero indicado no existe",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"NO se puede leer ni escribir en almacenamietno Interno",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
