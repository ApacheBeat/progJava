package com.example.multithreadapp;

import android.util.Log;

public class myThread extends Thread{ 
	
	//Declaracion de variables para la creacion y uso de los threads
	public myInt _notifier;
	public int sumparcial;
	public int numerorepeticiones;
	public int valorthread;
	private int size = 0;
	public myMatrix matrix3;
	public myInt ordenthread;
	public myInt orden2;
	private int minombre;
	public int total;

	//Metodo constructor con todos sus atributos
	public myThread (myInt notifier, int valor, myMatrix matrix2, myInt orden, 
			int tamano){
		size = tamano;
		orden2 = orden;
		ordenthread = new myInt();
		matrix3 = matrix2;
		valorthread = valor;
		_notifier = notifier;
		int Myname = notifier.Value;
		minombre = Myname;
		notifier.Value = notifier.Value+1;
		setName("MyThread"+ Myname);
	}
	
	//Metodo principal de esa clase, se inicia con la instruccion start()
	public void run(){
		//Sleep que permite que todos los threads acaben de generarse
		//Al ser de 1s, les da tiempo a todos los threads a acabar
		try{
			sleep(1000);
		}catch(Exception e){}

		
		try{
			/*Synchronized que va dejando que cada thread escriba su suma 
			parcial y la muestre por pantalla, junto con el orden de
			llegada de cada uno, al usar la variable compartida _notifier
			se va haciendo que uno u otro esperen.*/ 
			synchronized(_notifier){	
				sumaparcial();
				orden2.Value++;
				ordenthread = orden2;
				Log.d("mylogs", "OThread" + minombre + " --> partialSum" + 
						minombre + ": " + valorthread + "*" + 
						numerorepeticiones + " = " + sumparcial + ", Ord" +
						minombre + ": "+ ordenthread.Value);
			}
			
			/*Synchronized que permite al úlitmo thread hacer las suma de las
			 sumas parciales e imprimir las matrices, tanto la intermedia
			 como la final, mientras los otros esperan.*/
			synchronized(orden2){
				if (ordenthread.Value<size){
					matrix3.reescribir(sumaparcial(), ordenthread.Value);
				}else if (ordenthread.Value == size){
					matrix3.reescribir(sumaparcial(), ordenthread.Value);
					Log.d("mylogs", "M (after making the partial sums):");
					matrix3.imprimir();
					Log.d("mylogs", "lastThread: OThread" + minombre);
					Log.d("mylogs", "M (after making the sum of partial sums):");
					matrix3.reescribir(getTotal(matrix3, ordenthread.Value), ordenthread.Value);
					matrix3.imprimir();
				}
			}			
		}catch(Exception e){}
	}	

	//Metodo que calcula la suma parcial de cada thread, entendiendo como esto
	//la multiplicacion del valor que representa el thread por el numero de 
	//veces que aparece en la matriz
	public int sumaparcial(){
		sumparcial = 0;
		for (int r = 0; r<matrix3.getNumRows(); r++){
			for (int c = 0; c<matrix3.getNumCols(); c++){
				if(valorthread == matrix3.getData(r, c)){
					numerorepeticiones++;
				}
			}
		}
		sumparcial = numerorepeticiones*valorthread;
		
		return sumparcial;
	}

	//Metodo que devuelve el orden del thread
	public int getOrder(){
		return ordenthread.Value;
	}	

	public int getTotal(myMatrix matrix4, int ord){
		total = 0;
		for (int k = 0; k<matrix4.getNumRows() && k < size; k++){
			for (int l = 0; l<matrix4.getNumCols() && l< size; l++){
				total = total + matrix4.getData(k, l);
			}
		}
		return total;
	}
}
