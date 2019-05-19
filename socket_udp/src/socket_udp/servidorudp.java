package socket_udp;

import java.net.*;
import java.io.*;

public class servidorudp {

	public static void main(String argv[]) {
		// Declaracion del objeto de tipo Datagramsocket y variable booleana
		DatagramSocket socket;
		boolean fin = false;

		// Bloque try-catch para inicializar el servidor
		try {
			// Creacion del objeto DatagramSocket			
			socket = new DatagramSocket(6000);
			do {
				byte[] mensaje_bytes = new byte[256];
				DatagramPacket paquete = new DatagramPacket(mensaje_bytes, 256);
				socket.receive(paquete);
				String mensaje = "";
				mensaje = new String(mensaje_bytes);
				System.out.println(mensaje);
				if (mensaje.startsWith("end"))
					fin = true;
			} while (!fin);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
