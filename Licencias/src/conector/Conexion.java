	package conector;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexion {

	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
    // Nombre del host (nombre de red) de la PC
	private static final String nombreHost = "SERVIDOR_SQL"; // Cambia esto por el nombre real del host
	private static String ipHost = "";
	
	//private static final String url = "jdbc:mysql://192.168.100.69:3306/pruebas_2022?serverTimezone=UTC"; //cambio "dbase?"
	//private String url = "jdbc:mysql://"+ipHost+":3306/pruebas_2022?serverTimezone=America/Buenos_Aires";
	private static final String usuario = "remoto";
	private static final String password = "remoto";


	static
	{
		try
		{
			Class.forName(CONTROLADOR);

		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public Connection Conectar() {
		Connection conexion = null;

	try {
		getIP();
		String url = "jdbc:mysql://"+ipHost+":3306/pruebas_2022?serverTimezone=America/Buenos_Aires";
		//System.out.println(url);
		conexion = DriverManager.getConnection(url, usuario, password);
		System.out.println("Conexion establecida MySQL - host: "+ ipHost);

		} catch (SQLException e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error obteniendo conexión, verifique conexión pc actual o del servidor");
		}
	return conexion;
	}
	
    public static void getIP() {

        
        try {
            // Obtener la dirección IP del nombre del host
            InetAddress direccion = InetAddress.getByName(nombreHost);
            // Imprimir la dirección IP
            //System.out.println("La dirección IP de " + nombreHost + " es: " + direccion.getHostAddress());
            ipHost = direccion.getHostAddress();
        } catch (UnknownHostException e) {
            // Manejo de excepciones si el nombre del host no se puede resolver
            System.out.println("No se pudo resolver el nombre del host: " + e.getMessage());
        }
    }
}


