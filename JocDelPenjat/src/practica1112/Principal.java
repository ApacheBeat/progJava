/**
 * @author Víctor Alonso Garrigós
 * Pràctica final de INFORMÀTICA I - Programació
 * Joc del Penjat 
 * Aquesta classe conté la presentació, la declaració de les variables de les 
 * classes Diccionari i Penjat i el llançament del métode jugar(), que 
 * inicialitza el joc. 
 */
package practica1112;
public class Principal {

    static final DiccionariAmbFitxer p = new DiccionariAmbFitxer();
    static Penjat a = new Penjat(p.obtenirParaula());

    public static void main(String[] args) {
        //Presentacio
        System.out.println("JOC DEL PENJAT");
        System.out.println();
        //Iniciacioa del joc
        a.jugar();
    }
}
