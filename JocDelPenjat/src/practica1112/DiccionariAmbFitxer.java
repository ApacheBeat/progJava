/**
 * @author Víctor Alonso Garrigós
 * Classe que conté les paraules possibles per al joc; aquestes están dins un 
 * array de vint posicions, que s'han anat omplint amb el contingut d'un archiu
 * de text.
 */
package practica1112;

import java.io.*;
//És necessari importar això per poder crear un objecte de la classe Random per 
//elegir una paraula aleatoria.
import java.util.*;

public class DiccionariAmbFitxer {

    //Array on ficam totes les paraules de l'arxiu
    public static String DiccionariAmbFitxer[] = new String[20];
    public static FileReader fin;
    public static BufferedReader entrada;
    //Sencer per recórrer tot l'arxiu del diccionari
    public static int i = 1;
    //Objecte de la clase Random per obtenir la posició de la paraula
    public static Random aleatori = new Random();

    //CONSTRUCTOR (Diccionari on consten les 20 paraules)
    public DiccionariAmbFitxer() {
        try {
            obrirDiccionari();
            String linia;
            linia = entrada.readLine();
            while (linia != null) {
                for (i = 1; i < 21; i++) {
                    linia = entrada.readLine();
                    DiccionariAmbFitxer[i] = linia;
                }
            }
            tancarDiccionari();
        } catch (Exception e) {
        }
    }

    /*Métode per crear els objectes necessaris per llegir l'arxiu amb les 
    paraules.*/
    public static void obrirDiccionari() {
        try {
            fin = new FileReader("diccionari.txt");
            entrada = new BufferedReader(fin);
        } catch (FileNotFoundException fnfex) {
            System.out.println("Fitxer no trobat.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    //Métode que tanca el diccionari de manera controlada
    public static void tancarDiccionari(){
        try{
            fin.close();
        }catch (Exception e){}
    }

    //Métode que retorna una paraula aleatoria del diccionari
    public String obtenirParaula() {
        String paraula;
        /*Asignam el valor a la variable sencera amb el métode nextInt, que 
        retorna un nombre aleatori entre 1 i 20.*/
        int nombre = aleatori.nextInt(20);
        paraula = DiccionariAmbFitxer[nombre];
        return paraula;
    }
}
