package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Servicios.Services;
import pedidos.Pedido_Controller;


public class HojaDeRuta_Controller_deprec {

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

				pedidosList.add(pedidoCtrl.getPedido(hojaRuta.getNroDeRemito()));
			}

			hojaRuta.setPedidosList(pedidosList);


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hojaRuta;
	}

	public static HojaDeRuta getHojaDeRutaFromDBWhereId(int hojaRutaID){

		HojaDeRuta hojaRuta = new HojaDeRuta();
		int rs;

		String query = "SELECT * FROM "+tableHojaRuta+" "
				+ "where idHojaRuta = '"+hojaRutaID+"'";

		hojaRuta = getHojaDeRuta(Servicios.Services.getResulSetStatic(query), hojaRutaID);
		return hojaRuta;
	}

	public static void insertIntoDBStatic(HojaDeRuta hojaRuta, Connection cn) {

		int rs;
		int idHojaRuta = 0;

		String querySelect = "SELECT proximoNro FROM "+tableContador+" ";
		String queryUpdate = "UPDATE "+tableContador+" SET proximoNro = proximoNro + 1";

		String queryInsert = "INSERT INTO "+tableHojaRuta+" "
				+ "(idHojaRuta, choferID, vehiculoID, nroDeRemito, estadoHojaID, fechaProgramada, fechaEmision, razonSocial, idCliente) "
				+ "values(?,?,?,?,?,?,now(),?,?)";

		ResultSet rsTwo = Servicios.Services.getRsStaticWithOpenConn(cn, querySelect);

		try {

			if(rsTwo.next()) {
				idHojaRuta = rsTwo.getInt("proximoNro");
				hojaRuta.setHojaRutaID(idHojaRuta);
			}

			PreparedStatement sendQuery = cn.prepareStatement(queryInsert); //pstConsulta por tipo y fecha.-

			for(Pedido elemento : hojaRuta.getPedidosList()) {
				sendQuery.setInt(1, idHojaRuta);
				sendQuery.setString(2, hojaRuta.getChoferID());
				sendQuery.setString(3, hojaRuta.getVehiculoID());
				sendQuery.setInt(4, elemento.getNroDeRemito());
				sendQuery.setInt(5, hojaRuta.getEstadoHojaID());
				sendQuery.setDate(6, hojaRuta.getFechaProgramadaSqlDate());
				sendQuery.setString(7, elemento.getRazonSocial());
				sendQuery.setString(8, elemento.getIdCliente());
				//sendQuery.setDate(6, fechaHoy);
				rs = sendQuery.executeUpdate();
				HojaDeRuta_Controller_deprec.insertArticulosIntoDB(elemento, elemento.getNroDeRemito(), cn);

			}
			Statement stm;
			stm = cn.createStatement();
			stm.executeUpdate(queryUpdate);
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


	public static void insertArticulosIntoDB(Pedido pedido, int nroRemito, Connection cn) {

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

	public static void insertRepartoAndArticulosIntoDB(HojaDeRuta hojaRuta) {
		Connection cn = Services.abrirConexionStatic();
		try {
			cn.setAutoCommit(false); // Deshabilitar auto commit para usar transacción
			HojaDeRuta_Controller_deprec.insertIntoDBStatic(hojaRuta, cn);
			cn.commit(); // Confirmar la transacción si todo ha sido exitoso
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
} // end of class
