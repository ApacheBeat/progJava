package httpclient;
//EJERCICIO 2A
import java.io.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class clientehttp {

	public static void main(String argv[]) {
		
		//Variable BufferedReader para leer la entrada del usuario
		BufferedReader br = null;
		//Crecion de una instancia del cliente http
		HttpClient cliente = new HttpClient();
		//Creacion del HeadMethod de la peticion HEAD
		HeadMethod head = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			//Pedimos al usuario el servidor al que conectarse y leemos la entrada
			System.out.print("Introduzca el servidor al cual realizar la conexión: ");
			String input = br.readLine();
			//Creacion del metodo HEAD y paso por parametro del servidor
			head = new HeadMethod(input);
			head.getParams().setParameter(HttpMethodParams.USER_AGENT, "valonsoga@uoc.edu");
			
			//Ejecutar el metodo
			int codigoDeEstado = cliente.executeMethod(head);
			//comprobar si hay un error de estado de la conexion
			if(codigoDeEstado != HttpStatus.SC_OK) {
				System.err.println("Error de estado de conexion");
				System.exit(1);
			}
			
			//Mostramos por pantalla el comando antes de enviarlo
			System.out.println("Comando antes de enviar la peticion al servidor: " + head.getName());
			System.out.println("Conectando con el servidor...");
			//Si no ha habido error en la conexion, recogemos la informacion
			Header [] respuesta = head.getResponseHeaders();
			//Iteramos el array "respuesta" donde hemos metidos los distintos campos del header
			// y los mostramos por pantalla
			System.out.println("La respuesta del cliente: ");
			for(int i=0; i < respuesta.length; i++){      
			    System.out.println(respuesta[i].getName()
			        + ": " + respuesta[i].getValue());
			}	
		}
		// Gestión de posibles errores en http y posterior salida del programa
		catch (HttpException e) {
			System.err.println("Error de protocolo" + e.getMessage());
			e.printStackTrace();
		}catch (IOException e) {
			System.err.println("Error de transporte " + e.getMessage());
			e.printStackTrace();
		} finally {
			//soltamos/cerramos la conexion
			head.releaseConnection();
		}
	}
}
