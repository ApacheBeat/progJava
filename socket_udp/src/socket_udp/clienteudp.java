package socket_udp;

import java.net.*;
import java.io.*;

public class clienteudp {

	public static void main(String argv[]) {
		if (argv.length == 0) {
			System.err.println("java clienteudp servidor");
			System.exit(1);
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket socket;
		InetAddress address;
		byte[] mensaje_bytes = new byte[256];
		String mensaje = "";
		DatagramPacket paquete;

		mensaje_bytes = mensaje.getBytes();

		try {
			socket = new DatagramSocket();
			address = InetAddress.getByName(argv[0]);

			do {
				mensaje = in.readLine();
				mensaje_bytes = mensaje.getBytes();
				paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 6000);
				socket.send(paquete);
			} while (!mensaje.startsWith("end"));
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
