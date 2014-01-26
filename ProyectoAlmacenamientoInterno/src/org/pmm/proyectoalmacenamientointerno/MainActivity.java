package org.pmm.proyectoalmacenamientointerno;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv1;
	private EditText et;
	private Button b1;
	private String datos = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et = (EditText) findViewById(R.id.editText1);
		b1 = (Button) findViewById(R.id.button1);
		tv1 = (TextView) findViewById(R.id.textView1);
		final String[] lista = fileList();
		if (busqueda(lista, "datos.txt")) {
			try {
				FileInputStream fos = openFileInput("datos.txt");
				Scanner sc = new Scanner(fos);
				while (sc.hasNext()) {
					datos += sc.nextLine();
				}
				if (datos.equals("")) {
					et.setText("NO HAY DATOS");
				} else {
					et.setText(datos);
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			et.setText("NO Existe fichero");
		}

		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				leerArchivo();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		String[] lista = fileList();
		if(busqueda(lista, "datos.txt")){
			leerArchivo();
		}
		super.onResume();
	}

	public boolean busqueda(String[] lista, String nombreArchivo) {
		boolean existe = false;
		for (int i = 0; i < lista.length; i++) {
			if (nombreArchivo.equals(lista[i])) {
				existe = true;
			}
		}
		return existe;
	}
	
	public void leerArchivo(){
		try {
			FileOutputStream fos = openFileOutput("datos.txt", MODE_PRIVATE);
			PrintWriter printer = new PrintWriter(fos,true);
			printer.write(et.getText().toString());
			printer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
