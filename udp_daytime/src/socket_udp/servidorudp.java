package socket_udp;

import java.net.*;
import java.io.*;
import java.util.*;

public class servidorudp {

	public static void main(String argv[]) throws Exception{
		// Declaracion del objeto de tipo Datagramsocket y variable booleana
		DatagramSocket socket;
		boolean fin = false;

		// Bloque try-catch para inicializar el servidor
		try {
			do {
				socket = new DatagramSocket(13);
				//socket.setSoTimeout(1000);
				//Peticion recibida
				byte[] mensaje_recibido = new byte[256];
				//Creacion respuesta a enviar
				byte[] respuesta = new byte[256];
				DatagramPacket paquete = new DatagramPacket(mensaje_recibido, 256);
				socket.receive(paquete);
				System.out.println("Recibido de " + paquete.getAddress());
				//Creamos el objeto de la clase Date para obtener la fecha
				Date fecha = new Date();
				String daytime = fecha.toString() + "";
				//Lo convertimos a bytes para enviar
				respuesta = daytime.getBytes();
				DatagramPacket envio = new DatagramPacket (respuesta, respuesta.length, paquete.getAddress(), 13);
				socket.send(envio);
				socket.close();
				System.out.println("final");
			} while (!fin);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
