package hojaDeRuta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import Servicios.Services;
import modelos.ArticuloPedido;
import modelos.HojaDeRuta;
import modelos.Pedido;
import pedidos.Pedido_Controller;


public class HojaDeRuta_Controller {

	public static String tableChofer = "pruebas_2022.detalle_chofer";
	public static String tableVehiculo = "pruebas_2022.detalle_vehiculo";
	public static String tableEstadoHojaRuta = "pruebas_2022.detalle_estadoshojaderuta";
	public static String tableContador = "pruebas_2022.contador_hojaderuta";
	public static String tableHojaRuta = "pruebas_2022.reg_hojaderuta";
	public static String tableArticulosPedidos = "pruebas_2022.reg_articulospedidos";
	public static Pedido_Controller pedidoCtrl= new Pedido_Controller();


	public static HojaDeRuta getHojaDeRuta(ResultSet rs, int hojaRutaID){

		HojaDeRuta hojaRuta = new HojaDeRuta();
		ArrayList<Pedido> pedidosList = new ArrayList<>();
		try {
			while(rs.next()) {
				hojaRuta = new HojaDeRuta(
						hojaRutaID,
						rs.getString("choferID"),
						rs.getString("vehiculoID"),
						rs.getInt("nroDeRemito"),
						rs.getInt("estadoHojaID"),
						rs.getDate("fechaEmision"),
						rs.getDate("fechaProgramada")
						);

				pedidosList.add(pedidoCtrl.getPedidoFromMySQL(hojaRutaID, hojaRuta.getNroDeRemito()));
			}

			hojaRuta.setPedidosList(pedidosList);


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hojaRuta;
	}

	public static HojaDeRuta getHojaDeRutaFromDBWhereId(int hojaRutaID){

		HojaDeRuta hojaRuta = new HojaDeRuta();

		String query = "SELECT * FROM "+hojaDeRuta.HojaDeRuta_Controller.tableHojaRuta+" "
				+ "WHERE idHojaRuta = '"+hojaRutaID+"' ";

		hojaRuta = getHojaDeRuta(Servicios.Services.getResulSetStatic(query), hojaRutaID);
		return hojaRuta;
	}


	public static void insertHojaRutaIntoDBStatic(HojaDeRuta hojaRuta, Connection cn, boolean newNumberId) {

		int rs;
		int idHojaRuta = 0;

		String querySelect = "SELECT proximoNro FROM "+hojaDeRuta.HojaDeRuta_Controller.tableContador+" ";
		String queryUpdate = "UPDATE "+hojaDeRuta.HojaDeRuta_Controller.tableContador+" SET proximoNro = proximoNro + 1";

		String queryInsert = "INSERT INTO "+hojaDeRuta.HojaDeRuta_Controller.tableHojaRuta+" "
				+ "(idHojaRuta, choferID, vehiculoID, nroDeRemito, estadoHojaID, fechaProgramada, fechaEmision, "
				+ "razonSocial, idCliente, fechaPedido, netoNoGrabado) "
				+ "values(?,?,?,?,?,?,now(),?,?,?,?)";

		try {
			if(newNumberId) {
				ResultSet rsTwo = Servicios.Services.getRsStaticWithOpenConn(cn, querySelect);
				if(rsTwo.next()) {
					idHojaRuta = rsTwo.getInt("proximoNro");
					hojaRuta.setHojaRutaID(idHojaRuta);
				}
				Statement stm;
				stm = cn.createStatement();
				stm.executeUpdate(queryUpdate);
			}


			PreparedStatement sendQuery = cn.prepareStatement(queryInsert); //pstConsulta por tipo y fecha.-

			for(Pedido elemento : hojaRuta.getPedidosList()) {
				sendQuery.setInt(1, hojaRuta.getHojaRutaID());
				sendQuery.setString(2, hojaRuta.getChoferID());
				sendQuery.setString(3, hojaRuta.getVehiculoID());
				sendQuery.setInt(4, elemento.getNroDeRemito());
				sendQuery.setInt(5, hojaRuta.getEstadoHojaID());
				sendQuery.setDate(6, hojaRuta.getFechaProgramadaSqlDate());
				sendQuery.setString(7, elemento.getRazonSocial());
				sendQuery.setString(8, elemento.getIdCliente());
				sendQuery.setDate(9, elemento.getFechaSqlDate());
				sendQuery.setBigDecimal(10, elemento.getNetoNoGrabado());
				//sendQuery.setString(10, elemento.getNetoNoGrabadoToString());
				//sendQuery.setDate(6, fechaHoy);
				rs = sendQuery.executeUpdate();
				HojaDeRuta_Controller.insertArticulosIntoDB(elemento, elemento.getNroDeRemito(),hojaRuta.getHojaRutaID(), cn);

			}

			//			PreparedStatement sendQuery2 = cn.prepareStatement(queryInsertA); //pstConsulta por tipo y fecha.-
			//			sendQuery2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int getCantidadTotalHojaRuta(HojaDeRuta hojaRuta) {
		int acumulador = 0;
		for(Pedido i : hojaRuta.getPedidosList()) {
			for(ArticuloPedido j : i.getPedidoList()) {
				acumulador += j.getCantidad();
			}
		}
		return acumulador;
	}


	public static void insertPedidoIntoDB(Pedido pedido, int nroRemito, Connection cn) {

		String queryInsert = "INSERT INTO "+tableArticulosPedidos+" "
				+ "(nroDeRemito, productoID, codigo, cantidad, textoEnFactura) "
				+ "values(?,?,?,?,?)";

		try {

			PreparedStatement sendQuery = cn.prepareStatement(queryInsert); //pstConsulta por tipo y fecha.-

			for(ArticuloPedido j : pedido.getPedidoList()) {
				sendQuery.setInt(1, nroRemito);
				sendQuery.setInt(2, j.getProductoID());
				sendQuery.setInt(3, j.getCodigo());
				sendQuery.setInt(4, j.getCantidad());
				sendQuery.setString(5, j.getTextoEnFactura());
				int rs = sendQuery.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertArticulosIntoDB(Pedido pedido, int nroRemito, int nroHojaRuta, Connection cn) {

		String queryInsert = "INSERT INTO "+tableArticulosPedidos+" "
				+ "(nroDeRemito, productoID, codigo, cantidad, textoEnFactura, FK_idHojaRuta) "
				+ "values(?,?,?,?,?,?)";

		try {

			PreparedStatement sendQuery = cn.prepareStatement(queryInsert); //pstConsulta por tipo y fecha.-

			for(ArticuloPedido j : pedido.getPedidoList()) {
				sendQuery.setInt(1, nroRemito);
				sendQuery.setInt(2, j.getProductoID());
				sendQuery.setInt(3, j.getCodigo());
				sendQuery.setInt(4, j.getCantidad());
				sendQuery.setString(5, j.getTextoEnFactura());
				sendQuery.setInt(6, nroHojaRuta);
				int rs = sendQuery.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertRepartoAndArticulosIntoDB(HojaDeRuta hojaRuta, boolean newNumberId) {

		Connection cn = Services.abrirConexionStatic();
		try {
			cn.setAutoCommit(false); // Deshabilitar auto commit para usar transacción
			HojaDeRuta_Controller.insertHojaRutaIntoDBStatic(hojaRuta, cn, newNumberId);
			cn.commit(); // Confirmar la transacción si todo ha sido exitoso
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteRepartoFromDBStatic(HojaDeRuta hojaRuta, String urlHojaRuta, Connection cn) {

		String queryInsert = "DELETE FROM "+urlHojaRuta+" WHERE idHojaRuta = "+hojaRuta.getHojaRutaID()+" ";

		String querySafeModeOFF = "SET SQL_SAFE_UPDATES = 0 ";
		String querySafeModeON = "SET SQL_SAFE_UPDATES = 1 ";

		try {

			Statement stm;
			stm = cn.createStatement();
			stm.executeUpdate(querySafeModeOFF);

			PreparedStatement sendQuery = cn.prepareStatement(queryInsert);
			sendQuery.executeUpdate();

			stm = cn.createStatement();
			stm.executeUpdate(querySafeModeON);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteArticulosPedidosFromDBStatic(HojaDeRuta hojaRuta, String urlArticulos, Connection cn) {

		String queryInsert = "DELETE FROM "+hojaDeRuta.HojaDeRuta_Controller.tableArticulosPedidos+" WHERE FK_idHojaRuta = "+hojaRuta.getHojaRutaID()+" ";

		String querySafeModeOFF = "SET SQL_SAFE_UPDATES = 0 ";
		String querySafeModeON = "SET SQL_SAFE_UPDATES = 1 ";

		try {

			Statement stm;
			stm = cn.createStatement();
			stm.executeUpdate(querySafeModeOFF);

			PreparedStatement sendQuery = cn.prepareStatement(queryInsert);
			sendQuery.executeUpdate();

			stm = cn.createStatement();
			stm.executeUpdate(querySafeModeON);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteRepartoAndArticulosFromDB(HojaDeRuta hojaRuta, String urlHojaRuta, String urlArticulos) {
		Connection cn = Services.abrirConexionStatic();
		try {
			cn.setAutoCommit(false); // Deshabilitar auto commit para usar transacción
			HojaDeRuta_Controller.deleteRepartoFromDBStatic(hojaRuta, hojaDeRuta.HojaDeRuta_Controller.tableHojaRuta, cn);
			HojaDeRuta_Controller.deleteArticulosPedidosFromDBStatic(hojaRuta, hojaDeRuta.HojaDeRuta_Controller.tableArticulosPedidos, cn);
			cn.commit(); // Confirmar la transacción si todo ha sido exitoso
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void deleteHojaRuta(boolean runDelete, HojaDeRuta hojaRuta) {
		if(runDelete) {
			hojaDeRuta.HojaDeRuta_Controller.deleteRepartoAndArticulosFromDB(
					hojaRuta,
					hojaDeRuta.HojaDeRuta_Controller.tableHojaRuta,
					hojaDeRuta.HojaDeRuta_Controller.tableArticulosPedidos);
		}
	}


	public static ArrayList<HojaDeRuta> selectFromDBWhereDateIs(ResultSet rs) {

		ArrayList<HojaDeRuta> arrayHoja = new ArrayList<>();
		if(rs != null) {
			try {
				while(rs.next()) {
					arrayHoja.add( new HojaDeRuta(
							rs.getInt("idHojaRuta"),
							rs.getString("choferID"),
							rs.getString("vehiculoID"),
							0,
							rs.getInt("estadohojaID"),
							rs.getDate("fechaEmision"),
							rs.getDate("fechaProgramada")
							));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayHoja;
	}



	/**
	 * get data from DB where date and status conditions
	 * @param since: date to init search
	 * @param to: date to finish search
	 * @param status: status id on object HojaDeRuta
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<HojaDeRuta> getArrayHojaRutaFromDBWhereDateAndStatus(Date since, Date to, int status, Connection cn){

		ArrayList<HojaDeRuta> list = new ArrayList<>();

        String dateSinceStr = fecha.Fecha.formatterDateBD(since);
        String dateToStr = fecha.Fecha.formatterDateBD(to);

		if(to.equals(since) || to.after(since)) {

//			String querySelect = "SELECT * FROM "+hojaDeRuta.HojaDeRuta_Controller.tableHojaRuta+" "
//					+ "WHERE fechaProgramada >=? AND fechaProgramada <=? AND estadohojaID=? ";

			String querySelect = "SELECT DISTINCTROW idHojaRuta, choferID, vehiculoID, estadohojaID, fechaEmision, fechaProgramada "
					+ "FROM "+hojaDeRuta.HojaDeRuta_Controller.tableHojaRuta+" "
					+ "WHERE fechaProgramada >=? AND fechaProgramada <=? AND estadohojaID=? ";

			try {
				PreparedStatement enviaConsulta;
				enviaConsulta = cn.prepareStatement(querySelect);
				enviaConsulta.setString(1, dateSinceStr);
				enviaConsulta.setString(2, dateToStr);
				enviaConsulta.setInt(3, status);
				list = selectFromDBWhereDateIs(enviaConsulta.executeQuery());

			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error, getHojaDeRutaFromDBWhereDate: "+ e);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Rango de fecha incorrecto, intente nuevamente ");
		}

		return list;

	}


	/***
	 * Method receives an already open connection
	 * @param hojaRuta
	 * @param cn
	 */
	public static boolean UpdateStatusHojaRutaIntoDBStatic(HojaDeRuta hojaRuta, Connection cn) {
		boolean outCome = false;
		if(hojaRuta != null) {
			Statement stm;
			try
			{
				stm = cn.createStatement();

				String query = "UPDATE "+hojaDeRuta.HojaDeRuta_Controller.tableHojaRuta+" "
						+ "SET estadoHojaID = '"+hojaRuta.getEstadoHojaID()+"' "
						+ "WHERE idHojaRuta ='"+hojaRuta.getHojaRutaID()+"' ";
				stm.executeUpdate(query);

			} catch (SQLException e){
				System.out.println(e);
			}
			outCome = true;
		}
		return outCome;
	}

	public static boolean IsEmpty(HojaDeRuta hojaRuta) {
		return hojaRuta.getPedidosList().isEmpty();
	}
	

} // end of class
