package com.example.multithreadapp;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends Activity {

	//Declaracion de variables
	public int numRows, numCols, minValue, maxValue;
	public myInt orden;
	public myInt notifier;
	public boolean datoscorrectos = false;
	public myMatrix matriz;
	public HashSet<Integer> lista;
	public Integer [] listaArray;
	public Integer valor = 0;
	public String h = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}    

	//METODO PRINCIPAL
	public void principal(View view){
		//Leemos los datos introducidos por el usuario y comprovamos que estan
		//dentro de los margenes; creamos la matriz con ellos y la imprimimos
		readNumRows();
		readNumCols();
		readMinValue();
		readMaxValue();
		comprovacion();
		if(datoscorrectos){
			Log.d("mylogs", "numRows: " + numRows + "\n");
			Log.d("mylogs", "numCols: " + numCols + "\n");
			Log.d("mylogs", "minValue: " + minValue + "\n");
			Log.d("mylogs", "maxValue: " + maxValue + "\n");
			matriz = new myMatrix(numRows, numCols, maxValue, minValue);
			Log.d("mylogs", "M (after being created and initialized):");
			matriz.imprimir();

			//Creacion de un HashSet para meter la coleccion de los numeros de 
			//la matriz sin repetirse
			lista = new HashSet<Integer>();
			for (int i = 0; i < numCols; i++) {
				for (int j = 0; j < numRows; j++) {
					lista.add(matriz.getData(i, j));  
				}
			}

			//Convertimos el HashSet a array para facilitar las operaciones
			listaArray = new Integer[lista.size()];
			listaArray = lista.toArray(listaArray);
			Log.d("mylogs", "numThreads: " + lista.size());

			//Creamos tantos threads como valores diferentes tengamos en el 
			//HashSet (o array); le pasamos un parametro notifier para ir dando
			//distintos nombres a cada uno
			orden = new myInt();
			orden.Value = 0;
			notifier = new myInt();
			notifier.Value = 1;
			for(int it = 0; it < lista.size(); it++) {	
				valor = listaArray[it];
				myThread oThread = new myThread(notifier, valor, matriz, orden,
						lista.size());
				Log.d("mylogs", "OThread" + (it+1) + " --> Val" + (it+1) + ": "
						+ valor); 
				oThread.setPriority(Thread.NORM_PRIORITY);
				oThread.start();
			} 
		}else{
			Log.d("mylogs", "Los datos introducidos no cumplen las " +
					"especificaciones.");
		}

	}

	public void readNumRows(){
		EditText my_etNumRows;
		try{
			my_etNumRows = (EditText)findViewById(R.id.et_filas);
			numRows = Integer.parseInt(my_etNumRows.getText().toString());
		}catch(Exception e){
			Log.d("mylogs","Rows: No has introducido ningun valor");
		}
	}

	public void readNumCols(){
		EditText my_etNumCols;
		try{
			my_etNumCols = (EditText)findViewById(R.id.et_cols);
			numCols = Integer.parseInt(my_etNumCols.getText().toString());
		}catch(Exception e){
			Log.d("mylogs","Cols: No has introducido ningun valor");
		}	
	}

	public void readMinValue(){
		EditText my_etMinValue;
		try{
			my_etMinValue = (EditText)findViewById(R.id.et_minvalor);
			minValue = Integer.parseInt(my_etMinValue.getText().toString());
		}catch(Exception e){
			Log.d("mylogs","MinVal: No has introducido ningun valor");
		}
	}

	public void readMaxValue(){
		EditText my_etMaxValue;
		try{
			my_etMaxValue = (EditText)findViewById(R.id.et_maxvalor);
			maxValue = Integer.parseInt(my_etMaxValue.getText().toString());	
		}catch(Exception e){
			Log.d("mylogs","MaxVal: No has introducido ningun valor");
		}
	}

	//Comprovacion de que los valores introducidos cumplen los requisitos
	public boolean comprovacion(){
		if ((numRows > 0) && (numRows < 11) && (numCols > 0) &&(numCols < 11)){
			if((minValue>(-32768))&&(minValue<(32767)) && (maxValue>(-32768))&&
					(maxValue<(32767))){
				if(minValue<maxValue){
					datoscorrectos = true;
				}else {
					datoscorrectos = false;
				}
			}else{
				datoscorrectos = false;
			}
		}else{
			datoscorrectos = false;
		}
		return datoscorrectos;
	}

}
