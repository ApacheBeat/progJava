package socket_udp;

import java.net.*;
import java.io.*;

public class clienteudp {

	public static void main(String argv[]) throws Exception{
		if (argv.length == 0) {
			System.err.println("java cliente udp servidor");
			System.exit(1);
		}
		//Declaracion de Variables
		DatagramSocket socket;
		InetAddress address;
		byte[] mensaje_bytes = new byte[256];
		byte [] respuesta2 = new byte [256];
		String mensaje = "";
		//Datagramas para enviar y recibir respuesta
		DatagramPacket paquete;
		DatagramPacket respuesta;

		mensaje_bytes = mensaje.getBytes();

		try {
			socket = new DatagramSocket();
			address = InetAddress.getByName(argv[0]);

			do {
				//Creacuib del mensaje de envio y de respuesta
				mensaje_bytes = mensaje.getBytes();
				paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 13);
				respuesta = new DatagramPacket(respuesta2, 256);
				//Recibimos el paquete
				socket.receive(respuesta);
				//Imprimimos la respuesta
				String daytime = new String(respuesta.getData());
				System.out.println("La respuesta es: " + daytime);
				//Cerramos el socket
				socket.close();
			} while (true);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
