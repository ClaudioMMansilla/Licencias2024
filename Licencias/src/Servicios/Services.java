package Servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import conector.Conexion;
import conector.MSSQLManager;
import hojaDeRuta.HojaDeRuta_Controller;
import modelos.HojaDeRuta;

public class Services {

	public static String dbProductos = "pruebas_2022.productos";
	public static String url_contadorGuiaTte = "pruebas_2022.contador_guiatransporte";
	public static String url_reg_guiastransporte = "pruebas_2022.reg_guiastransporte";
	public static String url_contadorEnvasadoPandulce = "pruebas_2022.contador_env_input_pdulces";
	public static String url_contadorEnvasadoBudines = "pruebas_2022.contador_env_input_budines";
	//testing.productos

	public String getDbProductos() {
		return dbProductos;
	}

	public void setDbProductos(String dbProductos) {
		this.dbProductos = dbProductos;
	}


	public Connection abrirConexion() {
		Conexion conexion = new Conexion();
		Connection cn;
		cn = conexion.Conectar();
		return cn;
	}

	public static Connection abrirConexionStatic() {
		Conexion conexion = new Conexion();
		Connection cn = conexion.Conectar();
		return cn;
	}


	public Statement newStatement(Connection cn) {
		Statement stm = null;
		try {
			stm = cn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error obteniendo conexión \n" + e);
		}
		return stm;
	}

	public static Statement newStatementStatic(Connection cn) {
		Statement stm = null;
		try {
			stm = cn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error obteniendo conexión \n" + e);
		}
		return stm;
	}

	public ResultSet newRsStm(Statement stm, String query) {
		ResultSet rs = null;
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta \n" + e);
		}
		return rs;
	}

	public static ResultSet newRsStmStatic(Statement stm, String query) {
		ResultSet rs = null;
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta \n" + e);
		}
		return rs;
	}

	public ResultSet getResulSet(String query) {
		Connection cn = abrirConexion();
		Statement stm = newStatement(cn);
		ResultSet rs = newRsStm(stm, query);
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta \n" + e);
		}
		return rs;
	}

	public static ResultSet getResulSetStatic(String query) {
		Connection cn = abrirConexionStatic();
		Statement stm = newStatementStatic(cn);
		ResultSet rs = newRsStmStatic(stm, query);
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta \n" + e);
		}
		return rs;
	}


	public static ResultSet getRsStaticWithOpenConn(Connection cn, String query) {
		Statement stm = newStatementStatic(cn);
		ResultSet rs = newRsStmStatic(stm, query);
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta \n" + e);
		}
		return rs;
	}


	public PreparedStatement preparedStUserLogin (Connection cn, String query) {
		//PreparedStatement prST = (PreparedStatement) cn.prepareStatement("Select name, password, user from pruebas_2022.usuarios where name=? and password=?");
		PreparedStatement prST = null;
		try {
			prST = cn.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta \n" + e);
		}
		return prST;
	}




	public boolean realizarInsertDB(String query) {

		boolean check = false;
		Connection cn = abrirConexion();
		Statement stm = newStatement(cn);

		try {
			stm = cn.createStatement();
			//rs = stm.executeQuery(query);
			stm.executeUpdate(query);
			check = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check;
	}



	public String getNumPalletIntoDB(String tableDB, String fechaIngreso){

		String query = ("SELECT numPallet FROM "+tableDB+" where fechaIngreso ='"+fechaIngreso+"' ");

		ResultSet rs = getResulSet(query);

		int numPallet = 0;
		String stringNumPallet = "null";

		try {
			while(rs.next()) {
				numPallet = rs.getInt(1);  //capturo idProductos de pruebas_2022.productos
				stringNumPallet = Integer.toString(numPallet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error obteniendo numero de pallet  \n\n "
					+ "SQLException : services: getNumPalletIntoDB() \n" + e);
		}

		return stringNumPallet;
	}


	public static ResultSet getResulSetMSSQL(String query) {
		Connection cn = MSSQLManager.ConectarMSSQL();
		Statement stm = Services.newStatementStatic(cn);
		ResultSet rs = Services.newRsStmStatic(stm, query);
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta \n" + e);
		}
		return rs;
	}

	public static ResultSet getRsMSSQLWithCnn(Connection cn, String query) {
		//Connection cn = MSSQLManager.ConectarMSSQL();
		Statement stm = Services.newStatementStatic(cn);
		ResultSet rs = Services.newRsStmStatic(stm, query);
		try {
			rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error ejecutando consulta \n" + e);
		}
		return rs;
	}


	public static boolean realizarInsertDBStatic(String query) {

		boolean check = false;
		Connection cn = Services.abrirConexionStatic();
		Statement stm = Services.newStatementStatic(cn);

		try {
			stm = cn.createStatement();
			//rs = stm.executeQuery(query);
			stm.executeUpdate(query);
			check = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check;
	}


	public String[] validarProducto2(int idProducto){

		String[] array = new String [4];

		String queryyyy = "SELECT "
				+ "idProducto, "
				+ "producto, "
				+ "detalle_env_inputhandler.tablaDestinoInput, "
				+ "detalle_env_inputhandler.tablaContador "
				+ "FROM pruebas_2022.productos "
				+ "INNER JOIN pruebas_2022.detalle_env_inputhandler "
				+ "ON productos.familiaProducto = detalle_env_inputhandler.familiaProducto "
				+ "WHERE idProducto= '"+idProducto+"' ;";

		//		String query = ("SELECT * FROM "+dbProductos+" where idProducto='"+idProducto+"'");
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("productos.idProducto, ");
		sb.append("productos.producto, ");
		sb.append("detalle_env_inputhandler.tablaDestinoInput, ");
		sb.append("detalle_env_inputhandler.tablaContador ");
		sb.append("FROM pruebas_2022.productos ");
		sb.append("INNER JOIN pruebas_2022.detalle_env_inputhandler ");
		sb.append("ON productos.familiaProducto = detalle_env_inputhandler.familiaProducto ");
		sb.append("WHERE idProducto='"+idProducto+"' ");

		try {
			//ResultSet rs = getResulSet(sb.toString());
			ResultSet rs = getResulSet(queryyyy);

			while(rs.next()) {
				array[0] = rs.getString("idProducto");  //capturo idProductos de pruebas_2022.productos
				array[1] = rs.getString("producto"); //capturo producto de pruebas_2022.productos
				array[2] = rs.getString("tablaDestinoInput");//capturo target table para el input
				array[3] = rs.getString("tablaContador");//capturo target table para el contador
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Código de producto inválido \n\n "
					+ "SQLException : services: validarProducto() \n" + e);
		}

		return array;
	}

	public static String[] validarProductoStatic(int idProducto){

		String[] array = new String [4];

		String queryyyy = "SELECT "
				+ "idProducto, "
				+ "producto, "
				+ "detalle_env_inputhandler.tablaDestinoInput, "
				+ "detalle_env_inputhandler.tablaContador "
				+ "FROM pruebas_2022.productos "
				+ "INNER JOIN pruebas_2022.detalle_env_inputhandler "
				+ "ON productos.familiaProducto = detalle_env_inputhandler.familiaProducto "
				+ "WHERE idProducto= '"+idProducto+"' ;";

		//		String query = ("SELECT * FROM "+dbProductos+" where idProducto='"+idProducto+"'");
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("productos.idProducto, ");
		sb.append("productos.producto, ");
		sb.append("detalle_env_inputhandler.tablaDestinoInput, ");
		sb.append("detalle_env_inputhandler.tablaContador ");
		sb.append("FROM pruebas_2022.productos ");
		sb.append("INNER JOIN pruebas_2022.detalle_env_inputhandler ");
		sb.append("ON productos.familiaProducto = detalle_env_inputhandler.familiaProducto ");
		sb.append("WHERE idProducto='"+idProducto+"' ");

		try {
			//ResultSet rs = getResulSet(sb.toString());
			ResultSet rs = getResulSetStatic(queryyyy);

			while(rs.next()) {
				array[0] = rs.getString("idProducto");  //capturo idProductos de pruebas_2022.productos
				array[1] = rs.getString("producto"); //capturo producto de pruebas_2022.productos
				array[2] = rs.getString("tablaDestinoInput");//capturo target table para el input
				array[3] = rs.getString("tablaContador");//capturo target table para el contador
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Código de producto inválido \n\n "
					+ "SQLException : services: validarProducto() \n" + e);
		}

		return array;
	}


}
