package pedidos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelos.ArticuloPedido;
import modelos.Pedido;

public class Pedido_Controller {

	public Pedido_Controller() {

	}

	public String queryGetArticulosPendientes(int ventaID) {
		String query = "SELECT dbo.VentasDetalleProductos.VentasID, "
				+ "dbo.VentasDetalleProductos.ProductosID, "
				+ "dbo.Productos.Codigo, "
				+ "dbo.VentasDetalleProductos.Cantidad, "
				+ "dbo.VentasDetalleProductos.TextoEnFactura "
				+ "FROM dbo.VentasDetalleProductos "
				+ "JOIN dbo.Ventas ON dbo.VentasDetalleProductos.VentasID = dbo.Ventas.VentasID "
				+ "JOIN dbo.Productos ON dbo.VentasDetalleProductos.ProductosID = dbo.Productos.ProductosID "
				+ "WHERE dbo.Ventas.TiposDeDocumentosFiscalID = 5 AND dbo.Ventas.VentasID = '"+ventaID+"' "
				+ "ORDER BY dbo.VentasDetalleProductos.VentasID";

		return query;
	}

	public static String queryGetArticulosPendientesStatic(int ventaID) {
		String query = "SELECT dbo.VentasDetalleProductos.VentasID, "
				+ "dbo.VentasDetalleProductos.ProductosID, "
				+ "dbo.Productos.Codigo, "
				+ "dbo.VentasDetalleProductos.Cantidad, "
				+ "dbo.VentasDetalleProductos.TextoEnFactura "
				+ "FROM dbo.VentasDetalleProductos "
				+ "JOIN dbo.Ventas ON dbo.VentasDetalleProductos.VentasID = dbo.Ventas.VentasID "
				+ "JOIN dbo.Productos ON dbo.VentasDetalleProductos.ProductosID = dbo.Productos.ProductosID "
				+ "WHERE dbo.Ventas.TiposDeDocumentosFiscalID = 5 AND dbo.Ventas.VentasID = '"+ventaID+"' "
				+ "ORDER BY dbo.VentasDetalleProductos.VentasID";

		return query;
	}

	public String queryGetPedidoPendiente(int nroRemito) {

		String query = "SELECT dbo.Ventas.VentasID, "
				+ "dbo.Ventas.NroDeRemito, "
				+ "dbo.Ventas.Fecha, "
				+ "dbo.Ventas.[Razon Social], "
				+ "dbo.Ventas.TiposDeDocumentosFiscalID, "
				+ "dbo.Ventas.NetoNoGrabado, "
				+ "dbo.Ventas.EstadoVentasID, "
				+ "dbo.Clientes.Codigo "
				+ "FROM dbo.Ventas "
				+ "JOIN dbo.Clientes ON dbo.Ventas.ClientesID = dbo.Clientes.ClientesID "
				+ "WHERE dbo.Ventas.TiposDeDocumentosFiscalID = 5 AND dbo.Ventas.NroDeRemito = '"+nroRemito+"' "
				+ "ORDER BY dbo.Ventas.VentasID";

		return query;
	}


	public static String queryGetArticulosFromMySqlStatic(int nroRemito, int nroHojaRuta) {
		return "SELECT * FROM pruebas_2022.reg_articulospedidos "
				+ "WHERE FK_idHojaRuta = '"+nroHojaRuta+"'AND nroDeRemito = '"+nroRemito+"' ";
	}

	public String queryGetPedidoMySql(int nroHojaRuta, int nroRemito) {
		return "SELECT * FROM pruebas_2022.reg_hojaderuta "
				+ "WHERE idHojaRuta = '"+nroHojaRuta+"' AND nroDeRemito = '"+nroRemito+"'";
	}

	public static String queryGetPedidoPendienteStatic(int nroRemito) {

		String query = "SELECT dbo.Ventas.VentasID, "
				+ "dbo.Ventas.NroDeRemito, "
				+ "dbo.Ventas.Fecha, "
				+ "dbo.Ventas.[Razon Social], "
				+ "dbo.Ventas.TiposDeDocumentosFiscalID, "
				+ "dbo.Ventas.NetoNoGrabado, "
				+ "dbo.Ventas.EstadoVentasID, "
				+ "dbo.Clientes.Codigo "
				+ "FROM dbo.Ventas "
				+ "JOIN dbo.Clientes ON dbo.Ventas.ClientesID = dbo.Clientes.ClientesID "
				+ "WHERE dbo.Ventas.TiposDeDocumentosFiscalID = 5 AND dbo.Ventas.NroDeRemito = '"+nroRemito+"' "
				+ "ORDER BY dbo.Ventas.VentasID";

		return query;
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


	public static ArrayList<ArticuloPedido> addObjectToListStatic(ResultSet rs){

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


	public static ArrayList<ArticuloPedido> addArticuloToListFromMySqlStatic(ResultSet rs){

		ArrayList<ArticuloPedido> arrayProductos = new ArrayList<>();
		try {
			while(rs.next()) {
				//map.put( rs.getInt(1), rs.getInt(2) );
				arrayProductos.add( new ArticuloPedido(
						rs.getInt("id"),
						rs.getInt("productoID"),
						rs.getInt("codigo"),
						rs.getInt("cantidad"),
						rs.getString("textoEnFactura")
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
						rs.getInt("EstadoVentasID"),
						rs.getString("ClientesID")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arraylist;
	}

	public static ArrayList<Pedido> addListToListStatic(ResultSet rs){

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
						rs.getInt("EstadoVentasID"),
						rs.getString("ClientesID")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arraylist;
	}


	public Pedido getPedido(int nroDeRemito) {
		return (getNewPedido(
				Servicios.Services.getResulSetMSSQL(
						this.queryGetPedidoPendiente(nroDeRemito)
						)));
	}

	public Pedido getPedidoFromMySQL(int nroHojaRuta, int nroDeRemito) {
		return (getPedidoStaticFromMySQL(
				Servicios.Services.getResulSetStatic(
						this.queryGetPedidoMySql(nroHojaRuta, nroDeRemito)
						)));
	}

	public static Pedido getPedidoStatic(int nroDeRemito) {
		return (getNewPedidoStatic(
				Servicios.Services.getResulSetMSSQL(
						queryGetPedidoPendienteStatic(nroDeRemito)
						)));
	}

	public Pedido getPedidoWithCnn(Connection cn, int nroDeRemito) {
		return (getNewPedido(
				Servicios.Services.getRsMSSQLWithCnn(cn,
						this.queryGetPedidoPendiente(nroDeRemito)
						)));
	}


	private Pedido getNewPedido(ResultSet rs){

		Pedido pedido = new Pedido();

		try {
			while(rs.next()) {
				pedido = new Pedido(
						rs.getInt("VentasID"),
						rs.getInt("NroDeRemito"),
						rs.getDate("Fecha"),
						rs.getString("Razon Social"),
						rs.getInt("TiposDeDocumentosFiscalID"),
						rs.getBigDecimal("NetoNoGrabado"),
						rs.getInt("EstadoVentasID"),
						rs.getString("Codigo")
						);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


		pedido.setPedidoList(
				addObjectToList(
						Servicios.Services.getResulSetMSSQL(
								this.queryGetArticulosPendientes(
										pedido.getVentaID()
										))));
		return pedido;
	}


	private static Pedido getNewPedidoStatic(ResultSet rs){

		Pedido pedido = new Pedido();

		try {
			while(rs.next()) {
				pedido = new Pedido(
						rs.getInt("VentasID"),
						rs.getInt("NroDeRemito"),
						rs.getDate("Fecha"),
						rs.getString("Razon Social"),
						rs.getInt("TiposDeDocumentosFiscalID"),
						rs.getBigDecimal("NetoNoGrabado"),
						rs.getInt("EstadoVentasID"),
						rs.getString("ClientesID")
						);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


		pedido.setPedidoList(
				Pedido_Controller.addObjectToListStatic(
						Servicios.Services.getResulSetMSSQL(
								Pedido_Controller.queryGetArticulosPendientesStatic(
										pedido.getVentaID()
										))));
		return pedido;
	}

	public int PedidoCountElements(Pedido pedido) {
		int count = 0;

		for(ArticuloPedido elemento : pedido.getPedidoList())
		{
			count += elemento.getCantidad();
		}
		return count;
	}


	public static ArrayList<ArticuloPedido> addArticulosToListStaticFromMySql(ResultSet rs){

		ArrayList<ArticuloPedido> arrayProductos = new ArrayList<>();
		try {
			while(rs.next()) {
				//map.put( rs.getInt(1), rs.getInt(2) );
				arrayProductos.add( new ArticuloPedido(
						rs.getInt("id"),
						rs.getInt("productoID"),
						rs.getInt("codigo"),
						rs.getInt("cantidad"),
						rs.getString("textoEnFactura")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrayProductos;
	}


	private static Pedido getPedidoStaticFromMySQL(ResultSet rs){

		Pedido pedido = new Pedido();
		int TiposDeDocumentosFiscalID = 5; // HARDCODEADO!
		int EstadoVentasID = 1; // HARDCODEADO!

		try {
			while(rs.next()) {
				pedido = new Pedido(
						rs.getInt("idHojaRuta"),
						rs.getInt("nroDeRemito"),
						rs.getDate("fechaPedido"),
						rs.getString("razonSocial"),
						TiposDeDocumentosFiscalID,
						rs.getBigDecimal("netoNoGrabado"),
						EstadoVentasID,
						rs.getString("idCliente")
						);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


		pedido.setPedidoList(
				Pedido_Controller.addArticuloToListFromMySqlStatic(
						Servicios.Services.getResulSetStatic(
								Pedido_Controller.queryGetArticulosFromMySqlStatic(
										pedido.getNroDeRemito(), pedido.getVentaID()
										))));
		return pedido;
	}


}
