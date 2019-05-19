package calculoSuperficieVolumen;

//Importación de las clases necesarias para la programación de sockets
import java.net.*;
import java.io.*; 

public class servidor {

	public static void main(String argv[]) {

		//Declaracion de variables
		Socket socketCliente;
		int lado = 0, altura = 0;
		int area = 0, volumen = 0;
		try {
			// Creación mediante constructor de un nuevo socket en el lado del servidor en el puerto 6050
			ServerSocket ladoServidor = new ServerSocket(6050);
			// Creación de una variable tipo socket para el cliente y puesta a la espera de nuevas conexiones
			socketCliente = ladoServidor.accept();
			// Creación de objetos de tipo DataInput/OutputStream para entrada y salida de datos del socket
			DataInputStream input = new DataInputStream(new BufferedInputStream(socketCliente.getInputStream()));
			DataOutputStream output = new DataOutputStream(socketCliente.getOutputStream());
			//Leemos los datos del cliente
			lado = input.readInt();
			System.out.print("Lado: " + String.valueOf(lado) + " --- ");
			altura = input.readInt();
			System.out.println("Altura " + String.valueOf(altura));
			//Realizamos operaciones para hallar area y volumen
			area = 2*lado*(lado + 2*altura);
			volumen = lado*lado*altura;
			System.out.println("El area del prisma es " + area + " y el volumen es " + volumen + ".");
			//Enviamos la informacion al cliente
			output.writeInt(area);
			output.writeInt(volumen);
			//Cerramos la conexion
			output.close();
			input.close();
		}
		// Gestión de errores y salida del programa
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	}


