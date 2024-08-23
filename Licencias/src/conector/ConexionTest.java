package conector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionTest {

	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://192.168.100.69:3306/dbase?serverTimezone=UTC"; //cambio "dbase?"
	private static final String usuario = "remoto";
	private static final String password = "remoto";

	static {
		try {
			Class.forName(CONTROLADOR);

		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
		}
	}

	public static Connection Conectar() {
		Connection conexion = null;

	try {
		conexion = DriverManager.getConnection(url, usuario, password);
		System.out.println("Conexi�n establecida");

		} catch (SQLException e) {
		System.out.println("Error en la conexi�n");
		e.printStackTrace();
		}
	return conexion;
	}
}


