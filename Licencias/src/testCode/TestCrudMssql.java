package testCode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Servicios.Services;
import conector.MSSQLManager;
import modelos.ArticuloPedido;
import modelos.Pedido;


public class TestCrudMssql {

	public static ArrayList<ArticuloPedido> articulos;
	public static Pedido pedido;
	public static ArrayList<Pedido> pedidos;

	public static void main(String[] args) {

		TestCrudMssql.getPedidosPendientes();




//        for (ArticuloPedido elemento : articulos) {
//
//
//
//            System.out.println(elemento.getNroDeRemito()+
//            		" - "+elemento.getCodigo()+
//            		" - "+elemento.getRazonSocial()+
//            		" - "+elemento.getNetoNoGrabado()
//            		);
//        }


      for (Pedido elemento : pedidos) {

          System.out.println(elemento.getNroDeRemito()+
            	" - "+elemento.getVentaID()+
          		" - "+elemento.getFecha()+
          		" - "+elemento.getRazonSocial()+
          		" - "+elemento.getTiposDeDocumentosFiscalID()+
          		" - "+elemento.getNetoNoGrabado()+
          		" - "+elemento.getEstadoVenta()+
          		" - "+elemento.getPedidoList()
          		);

      }

	}


	public static void getPedidosPendientes() {
		String query = "SELECT dbo.VentasDetalleProductos.VentasID, "
				+ "dbo.VentasDetalleProductos.ProductosID, "
				+ "dbo.Productos.Codigo, "
				+ "dbo.VentasDetalleProductos.Cantidad, "
				+ "dbo.VentasDetalleProductos.TextoEnFactura "
				+ "FROM dbo.VentasDetalleProductos "
				+ "JOIN dbo.Ventas ON dbo.VentasDetalleProductos.VentasID = dbo.Ventas.VentasID "
				+ "JOIN dbo.Productos ON dbo.VentasDetalleProductos.ProductosID = dbo.Productos.ProductosID "
				+ "WHERE dbo.Ventas.TiposDeDocumentosFiscalID = 5 AND dbo.Ventas.EstadoVentasID = 1 "
				+ "ORDER BY dbo.VentasDetalleProductos.VentasID";


		String queryNewPedido = "SELECT dbo.Ventas.VentasID, "
				+ "dbo.Ventas.NroDeRemito, "
				+ "dbo.Ventas.Fecha, "
				+ "dbo.Ventas.[Razon Social], "
				+ "dbo.Ventas.TiposDeDocumentosFiscalID, "
				+ "dbo.Ventas.NetoNoGrabado, "
				+ "dbo.Ventas.EstadoVentasID "
				+ "FROM dbo.Ventas "
				+ "WHERE dbo.Ventas.TiposDeDocumentosFiscalID = 5 AND dbo.Ventas.EstadoVentasID = 1 "
				+ "ORDER BY dbo.Ventas.VentasID";

		TestCrudMssql crud = new TestCrudMssql();
		try {
			articulos = crud.addObjectToList(TestCrudMssql.getResulSetMSSQL(query));
			pedidos = crud.addListToList(TestCrudMssql.getResulSetMSSQL(queryNewPedido));



			for (ArticuloPedido i : articulos) {

				for (Pedido j : pedidos) {
					if(i.getVentaID() == j.getVentaID()) {
						ArrayList<ArticuloPedido> arrayProductos = new ArrayList<>();
						arrayProductos.add(i);
						j.setPedidoList(arrayProductos);
//
//				        // Obtener el iterador
//				        Iterator<ArticuloPedido> iterator = j.getPedidoList().iterator();
//
//				        // Recorrer la lista utilizando el iterador
//				        while (iterator.hasNext()) {
//				        	ArticuloPedido elemento = iterator.next();
//				            System.out.println(elemento.getTextoEnFactura());
//				        }
					}
				}
			}



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public ArrayList<ArticuloPedido> addObjectToList(ResultSet rs){

		ArrayList<ArticuloPedido> arrayProductos = new ArrayList<>();
		try {
			while(rs.next()) {
				//map.put( rs.getInt(1), rs.getInt(2) );
				arrayProductos.add( new ArticuloPedido(
						rs.getInt("VentasID"),
						rs.getInt("ProductosID"),
						rs.getInt("Codigo"),
						rs.getInt("Cantidad"),
						rs.getString("TextoEnFactura")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrayProductos;
	}

	public ArrayList<Pedido> addListToList(ResultSet rs){

		ArrayList<Pedido> arraylist = new ArrayList<>();
		try {
			while(rs.next()) {
				//map.put( rs.getInt(1), rs.getInt(2) );
				arraylist.add( new Pedido(
						rs.getInt("VentasID"),
						rs.getInt("NroDeRemito"),
						rs.getDate("Fecha"),
						rs.getString("Razon Social"),
						rs.getInt("TiposDeDocumentosFiscalID"),
						rs.getBigDecimal("NetoNoGrabado"),
						rs.getInt("EstadoVentasID")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arraylist;
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
}
