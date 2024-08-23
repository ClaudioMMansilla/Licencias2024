package testCode;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Servicios.Services;
import conector.MSSQLManager;
import modelos.ArticuloPedido;
import modelos.HojaDeRuta;
import modelos.Pedido;
import pedidos.Pedido_Controller;

public class TestCrearHojaDeRuta {

	public static Connection cn = MSSQLManager.ConectarMSSQL();
	public static Pedido_Controller pedidoCtrl= new Pedido_Controller();
	public static ArrayList<Pedido> pedidosList = new ArrayList<>();


	public static void main(String[] args) {

		HojaDeRuta hojaRuta = new HojaDeRuta();
		HojaDeRuta read = new HojaDeRuta();
        Date fechaHoy = new Date(System.currentTimeMillis());

//		pedidosList.add(pedidoCtrl.getPedidoWithCnn(cn, 60517));
//		//pedidosList.add(pedidoCtrl.getPedidoWithCnn(cn, 60553));
//		//pedidosList.add(pedidoCtrl.getPedidoWithCnn(cn, 60554));
//
//		hojaRuta.setPedidosList(pedidosList);
//		hojaRuta.setChoferID("Lucho");
//		hojaRuta.setVehiculoID("Sexor");
//		hojaRuta.setEstadoHojaID(1);
////		hojaRuta.setFechaEmision("now()");
//		hojaRuta.setFechaProgramada(fechaHoy);

//		for(Pedido elemento : hojaRuta.getPedidosList()) {
//			System.out.println(elemento.getRazonSocial());
//		}

		//insertIntoDB(hojaRuta);

		read = getHojaDeRutaFromDBWhereId(15);
		System.out.println(read.getFechaEmision());
		System.out.println(read.getFechaProgramada());
		System.out.println("Chofer "+read.getChoferID());
		System.out.println("Vehiculo "+read.getVehiculoID());

		for(Pedido i : read.getPedidosList()) {
			System.out.println(i.getRazonSocial());
			System.out.println(i.getNetoNoGrabado());
			System.out.println("Estado Venta "+i.getEstadoVenta());

			for(ArticuloPedido j : i.getPedidoList()) {
				System.out.println("Codigo "+j.getCodigo());
				System.out.println("Cantidad "+j.getCantidad());
				System.out.println(j.getTextoEnFactura());
			}
			System.out.println("-------------------------------------------");
		}


	}//main


	public static void insertIntoDB(HojaDeRuta hojaRuta) {
		Connection cn = Services.abrirConexionStatic();
		int rs;
		int idHojaRuta = 0;

		String querySelect = "SELECT proximoNro FROM pruebas_2022.contador_hojaderuta";
		String queryUpdate = "UPDATE pruebas_2022.contador_hojaderuta SET proximoNro = proximoNro + 1";

		String queryInsert = "INSERT INTO pruebas_2022.reg_hojaderuta "
				+ "(idHojaRuta, choferID, vehiculoID, nroDeRemito, estadoHojaID, fechaProgramada, fechaEmision) "
				+ "values(?,?,?,?,?,?,now())";

		ResultSet rsTwo = Services.getRsStaticWithOpenConn(cn, querySelect);
        Date fechaHoy = new Date(System.currentTimeMillis());

		try {

			if(rsTwo.next()) {
				idHojaRuta = rsTwo.getInt("proximoNro");
			}

			PreparedStatement sendQuery = cn.prepareStatement(queryInsert); //pstConsulta por tipo y fecha.-

			for(Pedido elemento : hojaRuta.getPedidosList()) {
				sendQuery.setInt(1, idHojaRuta);
				sendQuery.setString(2, hojaRuta.getChoferID());
				sendQuery.setString(3, hojaRuta.getVehiculoID());
				sendQuery.setInt(4, elemento.getNroDeRemito());
				sendQuery.setInt(5, hojaRuta.getEstadoHojaID());
				sendQuery.setDate(6, fechaHoy);
				//sendQuery.setDate(6, fechaHoy);
				rs = sendQuery.executeUpdate();
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

	public static HojaDeRuta getHojaDeRutaFromDBWhereId(int hojaRutaID){

		HojaDeRuta hojaRuta = new HojaDeRuta();
		int rs;

		String query = "SELECT * FROM pruebas_2022.reg_hojaderuta "
				+ "where idHojaRuta = '"+hojaRutaID+"'";

		hojaRuta = getHojaDeRuta(Services.getResulSetStatic(query), hojaRutaID);
		return hojaRuta;
	}

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


}
