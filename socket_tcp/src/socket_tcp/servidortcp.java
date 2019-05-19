package socket_tcp;

//Importación de las clases necesarias para la programación de sockets

import java.net.*;
import java.io.*; 

public class servidortcp {

	public static void main(String argv[]) {

		/** Declaración de variables a usar en el programa
		*   Socket para usar en el servidor
		*   variable booleana para indicar el final de la conexión (inicialmente a valor falso)
		*/
		ServerSocket socket;
		boolean fin = false;

		try {
			// Creación mediante constructor de un nuevo socket en el lado del servidor en el puerto 6001
			socket = new ServerSocket(6001);
			// Creación de una variable tipo socket y puesta a la espera de nuevas conexiones
			Socket socket_cli = socket.accept();
			// Creación de un objeto de tipo DataInputStream donde se guardará el flujo de entrada por el socket
			DataInputStream in = new DataInputStream(socket_cli.getInputStream());
			// Bucle que lee la información que va entrando en in y lo va mostrando por pantalla
			do {
				String mensaje = "";
				mensaje = in.readUTF();
				System.out.println(mensaje);
			} while (1 > 0);
		}
		// Gestión de errores y salida del programa
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
