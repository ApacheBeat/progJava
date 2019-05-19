package httpclient;
//EJERCICIO 2B
import java.io.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class clientehttp2 {

	public static void main(String argv[]) {
		
		//Variable BufferedReader para leer la entrada del usuario
		BufferedReader br = null;
		//Crecion de una instancia del cliente http
		HttpClient cliente = new HttpClient();
		//Creacion del metodo GET 
		GetMethod getPage = null;

		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			//Pedimos al usuario el servidor al que conectarse y leemos la entrada
			System.out.print("Introduzca el servidor al cual realizar la conexión: ");
			String input = br.readLine();
			//Creacion del metodo GET y paso por parametro del servidor
			getPage = new GetMethod(input);
			
			//Ejecutar el metodo
			int codigoDeEstado = cliente.executeMethod(getPage);
			 //comprobar si hay un error de estado
			if(codigoDeEstado != HttpStatus.SC_OK) {
				System.err.println("Error de estado de conexion");
				System.exit(1);
			}
			
			//Mostramos por pantalla el comando antes de enviarlo
			System.out.println("Comando antes de enviar la peticion al servidor: " + getPage.getName());
			System.out.println("Conectando con el servidor...");
			System.out.println("Información descargada: ");
			
			//Recogemos la informacion en un array de bytes
			byte [] respuesta = getPage.getResponseBody();
			//Pintamos la respuesta por pantalla
			System.out.println(new String(respuesta));
			
		}
		// Gestión de errores y salida del programa
		catch (HttpException e) {
			System.err.println("error de protocolo" + e.getMessage());
			e.printStackTrace();
		}catch (IOException e) {
			System.err.println("Error de transporte " + e.getMessage());
			e.printStackTrace();
		} finally {
			//soltamos/cerramos la conexion
			getPage.releaseConnection();
		}
	}
}
