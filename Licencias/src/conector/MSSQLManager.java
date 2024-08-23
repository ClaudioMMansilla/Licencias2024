package conector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class MSSQLManager {

	//private static String url = "jdbc:sqlserver:\\DESKTOP-B79CH15\\SQLEXPRESS:1433;databaseName=Licencias2021";
	//private static String url = "jdbc:sqlserver://DESKTOP-B79CH15\\SQLEXPRESS;encrypt=true;integratedSecurity=true;databaseName=Licencias2021";
	//private static String url = "jdbc:sqlserver://DESKTOP-B79CH15/SQLEXPRESS;databaseName=Licencias2021;loginTimeout=10;";
	private static String usuario = "UsuarioSSTLi";
	private static String contraseña = "GiSA85***Io2522020*as*";


	private static String url = "jdbc:sqlserver://DESKTOP-B79CH15\\SQLEXPRESS;databaseName=Licencias2021;encrypt=true;trustServerCertificate=true";


	static String connectionUrl =
            "jdbc:sqlserver://DESKTOP-B79CH15/SQLEXPRESS;"
                    + "databaseName=Licencias2021;"
                    + "user=UsuarioSSTLi;"
                    + "password=GiSA85***Io2522020*as*"
                    + "loginTimeout=30;";

	//connectionString="Data Source=DESKTOP-B79CH15\SQLEXPRESS;Initial Catalog=Licencias2021;User Id=UsuarioSSTLi;Password=GiSA85***Io2522020*as*; Connect Timeout=30" providerName="System.Data.SqlClient";

	static
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Driver OK"); } catch (ClassNotFoundException e)
		{
				System.out.println(e);
		}
	}

	public static Connection ConectarMSSQL()
	{
		Connection conexion = null;

		try {
			conexion = DriverManager.getConnection(url, usuario, contraseña);
			//conexion = DriverManager.getConnection(connectionUrl);
			System.out.println("Conexion establecida ConectarMSSQL ");

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error obteniendo conexión, verifique conexión pc actual o del servidor");
		}
		return conexion;
	}
}
