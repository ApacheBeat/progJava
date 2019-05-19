package calculoSuperficieVolumen;

//Importación de las clases necesarias para la programación de sockets
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class cliente {

	public static void main(String argv[]) {

		//Si no hay argumentos con los que trabajar, sale del programa
		if (argv.length == 0) {
			System.err.println("No se ha introducido la direccion del servidor");
			System.exit(1);
		}

		//Creación de objetos y variables
		Socket ladoCliente;
		DataOutputStream output;
		DataInputStream input;
		InetAddress address;
		int entrada=0, entrada2=0;

		int area=0, volumen=0;

		try {
			// Guardar en la variable dirección el nombre extraído de los argumentos
			address = InetAddress.getByName(argv[0]);
			// Creación del objeto socket con la dirección previamente guardada y el puerto 6001
			ladoCliente = new Socket(address, 6050);
			// Creación del objeto de salida de flujo 
			output = new DataOutputStream(ladoCliente.getOutputStream());
			//Para recibir respuesta del servidor
			input = new DataInputStream(new BufferedInputStream(ladoCliente.getInputStream()));
			Scanner teclado = new Scanner(System.in);
			//Presentacion
			System.out.println("==============================================================================================");
			System.out.println("BIENVENIDO AL PROGRAMA DE CALCULO DE LA SUPERFICIE Y VOLUMEN DE UN PRISMA CUADRANGULAR REGULAR");
			System.out.println("==============================================================================================");
			//Pedimos el lado y la altura y los enviamos al servidor
			System.out.print("Por favor, introduzca el valor del lado: ");
			entrada = (teclado.nextInt());
			output.writeInt(entrada);
			System.out.print(" y ahora introduce la altura: ");
			entrada2 = (teclado.nextInt());
			output.writeInt(entrada2);
			//Recibimos los datos del servidor
			area = input.readInt();	
			volumen = input.readInt();
			System.out.println("El area del prisma es " + area + " y el volumen es " + volumen + ".");
		}
		// Gestión de errores y salida del programa
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
