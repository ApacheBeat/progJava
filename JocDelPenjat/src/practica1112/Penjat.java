/**
 * @author VÃ­ctor Alonso GarrigÃ³s 
 * Classe Penjat: aquesta classe contÃ© tots els mÃ©todes necesaris per jugar.
 */
package practica1112;

public class Penjat {

    //NÃºmero d'errors que es pot cometre
    public static int errors = 7;
    //Quantitat de lletres encertades
    public static int contador = 0;
    //Lletra per llegir des de teclat
    public static char lletra = ' ';
    //Paraula que s'ha d'adivinar
    public static String objectiu;
    //Array de guions
    public static char[] guions;
    //String en blanc per imprimir a la plantilla
    public static final String BLANC = " ";

    //MÃ©tode constructor
    public Penjat(String str) {
        objectiu = str;
        guions = new char[objectiu.length()];
        for (int idx = 0; idx < objectiu.length(); idx++) {
            guions[idx] = '_';
        }
    }

    //MÃ‰TODE D'INTERFÃ�CIE
    public void jugar() {
        System.out.println("El maxim d'errors permesos es: " + errors);
        System.out.println();
        mostrarPlantilla(guions);
        do {
            lletra = llegirLletra();
            System.out.println();
            if (!lletraEnParaula(lletra)) {
                errors--;
                dibuixar(obtenirDibuixError(errors));
            }
            mostrarPlantilla(guions);
        } while ((errors > 0) && (contador != objectiu.length()));
        if (contador == objectiu.length()) {
            System.out.println("Enhorabona!!!");
            System.out.println("La paraula secreta era: " + objectiu);
        } else if (errors == 0) {
            System.out.println("Has perdut!");
            System.out.println("La paraula secreta era: " + objectiu);
        }
    }

    /*
     * MÃ©tode que demana una lletra a l'usuari, comproba si el carÃ cter
     * introduÃ¯t Ã©s una lletra i retorna aquesta lletra. Si s'introdueixen mÃ©s
     * d'una lletra, nomÃ©s s'agafa la primera.
     */
    private static char llegirLletra() {
        try {
            do {
                System.out.print("Introdueix una lletra: ");
                lletra = (char) System.in.read();
                botarLletres();
                if (Character.isLetter(lletra)) {
                    lletra = Character.toLowerCase(lletra);
                } else if (Character.isDigit(lletra)) {
                    System.out.println("Aixo es un numero!");
                } else {
                    System.out.println("Aixo no es una lletra!");
                }
            } while (!Character.isLetter(lletra));
        } catch (Exception e) {
        }
        return lletra;
    }

    /*
     * MÃ©tode que comproba si la paraula que li pasam per parÃ metre es troba a
     * la paraula que s'ha d'encertar. Si hi Ã©s, retorna vertader, la
     * substitueix a la plantilla i augmenta el contador de lletres encertades.
     */
    private boolean lletraEnParaula(char c) {
        int i;
        boolean trobada = false;
        do {
            for (i = 0; i < objectiu.length(); i++) {
                if ((c == (objectiu.charAt(i))) && (c != guions[i])) {
                    guions[i] = objectiu.charAt(i);
                    contador++;
                    trobada = true;
                }
            }
        } while (i < objectiu.length());
        return trobada;
    }

    //MÃ©tode que mostra la plantilla amb guions i les lletres encertades
    private void mostrarPlantilla(char[] guionets) {
        System.out.print("Paraula: ");
        for (int j = 0; j < objectiu.length(); j++) {
            System.out.print(guionets[j] + BLANC);
        }
        System.out.println();
        System.out.println();
    }

    /*
     * MÃ©tode que reb un sencer per parÃ metre i segons aquest, indica quin
     * dibuix s'ha d'imprimir.
     */
    private static String obtenirDibuixError(int error) {
        String dibuix = "";
        switch (error) {
            case 6:
                dibuix = ("+----¬\n" + "|/   |\n" + "|     \n" + "|\n" + "|\n" + "|\n" + "+-----\n");
                break;
            case 5:
                dibuix = ("+----¬\n" + "|/   |\n" + "|    O\n" + "|    \n" + "|\n" + "|\n" + "+-----\n");
                break;
            case 4:
                dibuix = ("+----¬\n" + "|/   |\n" + "|    O\n" + "|    |\n" + "|\n" + "|\n" + "+-----\n");
                break;
            case 3:
                dibuix = ("+----¬\n" + "|/   |\n" + "|    O\n" + "|   /|\n" + "|\n" + "|\n" + "+-----\n");
                break;
            case 2:
                dibuix = ("+----¬\n" + "|/   |\n" + "|    O\n" + "|   /|\\\n" + "|\n" + "|\n" + "+-----\n");
                break;
            case 1:
                dibuix = ("+----¬\n" + "|/   |\n" + "|    O\n" + "|   /|\\\n" + "|   /  \n" + "|\n" + "+-----\n");
                break;
            case 0:
                dibuix = ("+----¬\n" + "|/   |\n" + "|    O\n" + "|   /|\\\n" + "|   / \\\n" + "|\n" + "+-----\n");
                break;
        }
        return dibuix;
    }

    //MÃ©tode que dibuixa la sortida que s'ha obtingut al mÃ©tode obtenirDibuixError()
    private static void dibuixar(String dibuix2) {
        System.out.println(dibuix2);
    }

    //MÃ©tode necessari per botar lletres quan es vol que l'usuari introdueixi una lletra
    private static void botarLletres() {
        char lletra2 = ' ';
        try {
            while (lletra2 != '\n') {
                lletra2 = (char) System.in.read();
            }
        } catch (Exception e) {
        }
    }
}
