package socket_tcp;

//Importación de las clases necesarias para la programación de sockets
import java.net.*;
import java.io.*;

public class clientetcp {

	public static void main(String argv[]) {
		
		//Si no hay argumentos con los que trabajar, sale del programa
		if (argv.length == 0) {
			System.err.println("java clientetcp servidor");
			System.exit(1);
		}

		//Creación de objetos y variables
		// buffer in para ir leyendo el flujo de entrada
		// variable socket
		// variable de tipo dirección de internet
		// array de 256 bytes
		// string mensaje
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Socket socket;
		InetAddress address;
		byte[] mensaje_bytes = new byte[256];
		String mensaje = "";

		try {
			// Guardar en la variable dirección el nombre extraído de los argumentos
			address = InetAddress.getByName(argv[0]);
			// Creación del objeto socket con la dirección previamente guardada y el puerto 6001
			socket = new Socket(address, 6001);
			// Creación del objeto de salida de flujo 
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			// Bucle que va cogiendo lo que hay en in y lo va pasando/escribiendo en out, hasta que lo que haya en in no sea "end"
			do {
				mensaje = in.readLine();
				out.writeUTF(mensaje);
			} while (!mensaje.startsWith("end"));
		}
		// Gestión de errores y salida del programa
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
