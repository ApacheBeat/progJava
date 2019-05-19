package com.example.multithreadapp;

import android.util.Log;

public class myMatrix{

	//Declaracion de variables para la creacion y uso de la matriz
	public Integer [][] matriz1;
	public Integer retornoget;
	public int NumCol;
	public int NumRow;
	public int MaxVal;
	public int MinVal;
	public String g ="";
	public Integer s;

	//Metodo constructor
	public myMatrix(int x, int y, int mxv, int mnv){
		NumRow = x;
		NumCol = y;
		MaxVal = mxv;
		MinVal = mnv;
		matriz1 = new Integer [NumRow][NumCol];
		for (int i = 0; i < NumRow; i++) {
			for (int j = 0; j < NumCol; j++) {
				matriz1[i][j] = MinVal + (int)(Math.random() * ((MaxVal - 
						MinVal) + 1));
			}
		}
	}

	//Metodos que devuelven el numero de columnas o de filas

	public int getNumCols(){
		return  NumCol;
	}

	public int getNumRows(){
		return NumRow;
	}

	//Metodo para imprimir la matriz
	public void imprimir(){
		g = "";
		for (int i = 0; i < NumRow; i++) {
			for (int j = 0; j < NumCol; j++) {	
				s = matriz1[i][j];
				g = g + Integer.toString(s) + "  " ;
			}
			g = g + "\n";
		}
		Log.d("mylogs", g);
	}

	//Metodos para obtener valores de la matriz y para introducirlos

	public Integer getData(int row, int col){
		retornoget = matriz1[row][col];
		return retornoget;
	}

	public void reescribir(int partialsum, int ordenx){
		int pos = 1;
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumCols(); j++) {
				if(pos == ordenx){
					matriz1[i][j] = partialsum;	 
				}
				pos++;			
			}
		}
	}

}
