package guiaTransporte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import Servicios.Services;
import conector.MSSQLManager;
import fecha.Fecha;
import hojaDeRuta.HojaDeRuta_Controller;
import mapManager.MapManager;
import modelos.ArticuloPedido;
import modelos.FleteExpreso;
import modelos.GuiaTransporte;
import modelos.HojaDeRuta;
import modelos.Pedido;

public class Guiatransporte_Controller{

	private HojaDeRuta roadmap;
	private HashMap<Integer, Integer> mapPedidos;
	private HashMap<Integer, Integer> mapGuiasReport;
	private ArrayList<GuiaTransporte> guias;
	private Guiatransporte_View view;
	private static String[] flete = {"A CARGO DEL CLIENTE", "FLETE PAGO EN ORIGEN"};


	public Guiatransporte_Controller(HojaDeRuta roadmapParam) {
		this.roadmap = roadmapParam;
		this.mapPedidos = new HashMap<Integer, Integer>();
		this.mapGuiasReport = new HashMap<Integer, Integer>();
		this.guias = new ArrayList<GuiaTransporte>();

		//Object[][] data = new Object[roadmap.getPedidosList().size()][4];

		//view = new Guiatransporte_View(data);
		view = new Guiatransporte_View(GuiaTransporte.ConvertArraylistToMatrix(roadmapParam));
		view.button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				FilterListByCheckbox(roadmapParam);
//				Guiatransporte_Controller.insertIntoDBWithCommit(guias);
				mapGuiasReport.forEach((key, value) -> reportes.PrintGuiatte.PrintGuia(value.toString()));
				view.disposeView();
			}
		});
	}


	public static GuiaTransporte selectFromDBWhereIdguia(ResultSet rs) {
		GuiaTransporte guia = new GuiaTransporte();
		if(rs != null) {
			System.out.println("hola");
			try {
				System.out.println("Yendo");
				while(rs.next()) {
					guia.setEmtpy(false);
					System.out.println("Estoy: "+ rs.getInt("idGuia"));
					guia.setIdGuia(rs.getInt("idGuia"));
//					guia.setIdGuia(rs.getInt("FK_idHojaRuta"));
//					guia.setTotalCantidad(rs.getInt("totalCantidad"));
//					guia.setTotalNeto(rs.getBigDecimal("totalNeto"));
//					guia.setNroDeRemito(rs.getInt("FK_nroDeRemito"));
//					guia.setNroDeRemitoBuffer(rs.getInt("FK_nroDeRemito"));
//					guia.setTotalCantidadBuffer(rs.getInt("totalCantidad"));
//					guia.setTotalNetoBuffer(rs.getBigDecimal("totalNeto"));
					break;

				}
				System.out.println("Chau");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return guia;
	}


	public static GuiaTransporte getNextNumGuiaFromDB(GuiaTransporte guia, HojaDeRuta hojaRuta, int idCliente, Connection cn) {
//		GuiaTransporte guia = new GuiaTransporte();

//		String querySelect = " SELECT idGuia "
//				+ "FROM "+Services.url_reg_guiastransporte+" "
//				+ "WHERE idCliente= "+idCliente+" AND FK_idHojaRuta = "+hojaRuta.getHojaRutaID()+" ";
//				+ "WHERE idCliente= "+169001+" AND FK_idHojaRuta = "+1802+" ";
		
		String querySelect = "SELECT * FROM "+Services.url_reg_guiastransporte+" WHERE idCliente ='"+idCliente+"' AND FK_idHojaRuta ='"+hojaRuta.getHojaRutaID()+"' ";

		String queryUpdate = "UPDATE "+Services.url_contadorGuiaTte+" SET proximoNro = proximoNro + 1";

		try {
			cn.setAutoCommit(false); // Deshabilitar auto commit para usar transacción
			PreparedStatement enviaConsulta;
			enviaConsulta = cn.prepareStatement(querySelect);
			guia = selectFromDBWhereIdguia(enviaConsulta.executeQuery());


			if(guia.isEmtpy()) {
				guia = Guiatransporte_Controller.generateNumGuiaFromDB(cn);
				Statement stm;
				stm = cn.createStatement();
				stm.executeUpdate(queryUpdate);
				//guia.setEmtpy(false);
			}

			cn.commit(); 
			cn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error, selectNumGuiaFromDB: "+ e);
		}

		return guia;
	}


	public static GuiaTransporte getProxNum(ResultSet rs) {
		GuiaTransporte guia = new GuiaTransporte();
		if(rs != null) {
			try {
				while(rs.next()) {
					guia.setIdGuia(rs.getInt("proximoNro"));
					guia.setTotalNeto(BigDecimal.ZERO);
					guia.setTotalCantidad(0);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return guia;
	}


	public static GuiaTransporte generateNumGuiaFromDB(Connection cn) {
		GuiaTransporte guia = new GuiaTransporte();
		String querySelect = "SELECT proximoNro FROM "+Services.url_contadorGuiaTte+" ";

		try {
			PreparedStatement enviaConsulta;
			enviaConsulta = cn.prepareStatement(querySelect);
			guia = getProxNum(enviaConsulta.executeQuery());
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error, getProxNumGuiaFromDB: "+ e);
		}
		return guia;
	}


	private void FilterListByCheckbox(HojaDeRuta hojaRuta) {    	
		for (int row = 0; row < view.table.getRowCount(); row++) {
			Boolean emitirGuia = (Boolean) view.table.getValueAt(row, 1);
			Boolean sinFlete = (Boolean) view.table.getValueAt(row, 2);

			if (emitirGuia != null && emitirGuia) 
			{
				String razonSocial = view.table.getValueAt(row, 0).toString();
				GuiaTransporte guiaLocal = new GuiaTransporte(0, hojaRuta.getHojaRutaID());
				for (Pedido pedido : hojaRuta.getPedidosList()) 
				{	
					if (pedido.getRazonSocial().equalsIgnoreCase(razonSocial)) 
					{
						if(!mapPedidos.containsKey(pedido.getNroDeRemito() )) 
						{
							int idCliente = Fecha.parserStringToInt(pedido.getIdCliente());
							
							if(mapGuiasReport.containsKey(guiaLocal.getIdCliente())) {
								guiaLocal.setIdGuia(mapGuiasReport.get(guiaLocal.getIdCliente()));
							} else {
								guiaLocal = Guiatransporte_Controller.getNextNumGuiaFromDB(
										guiaLocal, roadmap, idCliente, Servicios.Services.abrirConexionStatic() 
										);
							}

							guiaLocal = Guiatransporte_Controller.getCostumerInformationFromDB(idCliente, guiaLocal);
							guiaLocal.setNroDeRemito(pedido.getNroDeRemito());
							guiaLocal.getExpreso().setFleteCondicion(flete[0]);
							guiaLocal.setIdCliente(idCliente);
							guiaLocal.setIdHojaRuta(hojaRuta.getHojaRutaID());
							guiaLocal.setCantidad(this.getCantidadFromPedido(pedido));
							guiaLocal.setNetoNoGrabado(pedido.getNetoNoGrabado());
							//	guiaLocal.setTotalCantidad(guiaLocal.getTotalCantidad() + guiaLocal.getCantidad());
							//	guiaLocal.setTotalNeto(guiaLocal.getTotalNeto().add(guiaLocal.getNetoNoGrabado()));
							guiaLocal.setTotalCantidad(guiaLocal.getCantidad());
							guiaLocal.setTotalNeto(guiaLocal.getNetoNoGrabado());

							if (sinFlete != null && sinFlete) {
								guiaLocal.getExpreso().setFleteCondicion(flete[1]);
							}

							if(!mapGuiasReport.containsKey(guiaLocal.getIdCliente())) {
								mapGuiasReport = MapManager.putGuiaClienteIntoMap(mapGuiasReport, guiaLocal.getIdCliente(), guiaLocal.getIdGuia());
							}

							mapPedidos = MapManager.putGuiaClienteIntoMap(mapPedidos, pedido.getNroDeRemito(),guiaLocal.getIdGuia()); 
							guias.add(guiaLocal);
							
							if(guiaLocal.isEmtpy()) {
								Guiatransporte_Controller.ejecutarInsertGuiatte(guiaLocal, Services.abrirConexionStatic());
							} else {
								Guiatransporte_Controller.ejecutarUpdateGuiatte(guiaLocal, Services.abrirConexionStatic());
							}
							

							break;
						}
					}
				}
			}    
		} 
	}


	private static String querySelectMSSQL(int idCostumer) {
		String query = "SELECT "
				+ "dbo.Clientes.[Razon Social], "
				+ "dbo.Clientes.[DomicilioNotaDeEnvio], "
				+ "dbo.Clientes.[LocalidadEntrega], "
				+ "dbo.Clientes.[Provincia], "
				+ "dbo.Clientes.[Cuit], "
				+ "dbo.Clientes.[TipoIva], "
				+ "dbo.Clientes.[Telefono], "
				+ "dbo.Clientes.[TransporteProveedoresID] "
				+ "FROM [Licencias2021].[dbo].[Clientes]  "
				+ "WHERE [Licencias2021].[dbo].[Clientes].[Codigo]="+idCostumer+" ";
		return query;
	}

	public static GuiaTransporte getCostumerInformationFromDB(int idCostumer, GuiaTransporte guia) 
	{
		Connection cn = MSSQLManager.ConectarMSSQL();
		try {
			guia = Guiatransporte_Controller.setCostumerInformation(
					cn,
					Services.getRsMSSQLWithCnn(cn,Guiatransporte_Controller.querySelectMSSQL(idCostumer)), 
					guia);

			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guia;
	}

	public static GuiaTransporte setCostumerInformation(Connection cn, ResultSet rs, GuiaTransporte guia) 
	{
		if(rs != null) {
			try {
				while(rs.next()) {
					guia.setRazonSocial(rs.getString("Razon Social"));
					guia.setDireccion(rs.getString("DomicilioNotaDeEnvio"));
					guia.setLocalidad(rs.getString("LocalidadEntrega"));
					guia.setProvincia(rs.getString("Provincia"));
					guia.setCuit(rs.getString("Cuit"));
					guia.setIva(rs.getString("TipoIva"));
					guia.setTelefono(rs.getString("Telefono"));
					guia.setExpreso(
							Guiatransporte_Controller.getShipInformationFromDB(
									cn, rs.getInt("TransporteProveedoresID")
									));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return guia;
	}


	public static void ejecutarInsertGuiatte(GuiaTransporte guia, Connection cn) {
		String query = queryInsertGuiatte(guia);
		try ( PreparedStatement statement = cn.prepareStatement(query)) {
			statement.setInt(1, guia.getIdGuia());
			statement.setInt(2, guia.getIdHojaRuta());
			statement.setInt(3, guia.getNroDeRemito());
			statement.setInt(4, guia.getIdCliente());
			statement.setString(5, guia.getDireccion());
			statement.setString(6, guia.getLocalidad());
			statement.setString(7, guia.getProvincia());
			statement.setString(8, guia.getCuit());
			statement.setString(9, guia.getIva());
			statement.setString(10, guia.getTelefono());
			statement.setInt(11, guia.getCantidad());
			statement.setString(12, guia.getExpreso().getFleteCondicion());
			statement.setString(13, guia.getExpreso().getFleteExpreso());
			statement.setString(14, guia.getExpreso().getFleteDireccion());
			statement.setBigDecimal(15, guia.getNetoNoGrabado());
			statement.setInt(16, guia.getTotalCantidad());
			statement.setBigDecimal(17, guia.getTotalNeto());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted < 1) { 
				JOptionPane.showMessageDialog(null, "ERROR, fallo inserción de Guia de Transporte en base de datos");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void ejecutarUpdateGuiatte(GuiaTransporte guia, Connection cn) {
		String query = queryUpdateGuiatte(guia);
		try ( PreparedStatement statement = cn.prepareStatement(query)) {
			statement.setInt(1, guia.getIdGuia());
			statement.setInt(2, guia.getIdHojaRuta());
			statement.setInt(3, guia.getNroDeRemito());
			statement.setInt(4, guia.getIdCliente());
			statement.setString(5, guia.getDireccion());
			statement.setString(6, guia.getLocalidad());
			statement.setString(7, guia.getProvincia());
			statement.setString(8, guia.getCuit());
			statement.setString(9, guia.getIva());
			statement.setString(10, guia.getTelefono());
			statement.setInt(11, guia.getCantidad());
			statement.setString(12, guia.getExpreso().getFleteCondicion());
			statement.setString(13, guia.getExpreso().getFleteExpreso());
			statement.setString(14, guia.getExpreso().getFleteDireccion());
			statement.setBigDecimal(15, guia.getNetoNoGrabado());
			statement.setInt(16, guia.getTotalCantidad());
			statement.setBigDecimal(17, guia.getTotalNeto());
			statement.setInt(18, guia.getIdGuia());
			statement.setInt(19, guia.getIdHojaRuta());
			statement.setInt(20, guia.getNroDeRemito());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted < 1) { 
				JOptionPane.showMessageDialog(null, "ERROR, fallo actualización de Guia de Transporte en base de datos");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String queryInsertGuiatte(GuiaTransporte guia) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(Services.url_reg_guiastransporte).append(" ");
		sb.append("(idGuia, FK_idHojaRuta, FK_nroDeRemito, idCliente, direccion, localidad, ");
		sb.append("provincia, cuit, iva, telefono, cantidad, fleteCondicion, fleteExpreso, fleteDireccion, ");
		sb.append("netoNoGrabado, totalCantidad, totalNeto) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ;");
		return sb.toString();
	}

	private static String queryUpdateGuiatte(GuiaTransporte guia) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(Services.url_reg_guiastransporte).append(" ");
		sb.append("SET ");
		sb.append("idGuia=? ,");
		sb.append("FK_idHojaRuta=? ,");
		sb.append("FK_nroDeRemito=? ,");
		sb.append("idCliente=? ,");
		sb.append("direccion=? ,");
		sb.append("localidad=? ,");
		sb.append("provincia=? ,");
		sb.append("cuit=? ,");
		sb.append("iva=? ,");
		sb.append("telefono=? ,");
		sb.append("cantidad=? ,");
		sb.append("fleteCondicion=? ,");
		sb.append("fleteExpreso=? ,");
		sb.append("fleteDireccion=? ,");
		sb.append("netoNoGrabado=? ,");
		sb.append("totalCantidad=? ,");
		sb.append("totalNeto=? ");
		sb.append("WHERE idGuia=? ");
		sb.append("AND FK_idHojaRuta=? ");
		sb.append("AND FK_nroDeRemito=? ;");
		return sb.toString();
	}

	private static void deleteGuiaFromDB(GuiaTransporte guia, Connection cn) {

		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM "+Services.url_reg_guiastransporte+" ");
		sb.append("WHERE idGuia = "+guia.getIdGuia()+" ");
		sb.append("AND FK_nroDeRemito = "+guia.getNroDeRemito()+" ");
		sb.append("AND FK_idHojaRuta = "+guia.getIdHojaRuta()+" ");

		String querySafeModeOFF = "SET SQL_SAFE_UPDATES = 0 ";
		String querySafeModeON = "SET SQL_SAFE_UPDATES = 1 ";

		try {

			Statement stm;
			stm = cn.createStatement();
			stm.executeUpdate(querySafeModeOFF);

			PreparedStatement sendQuery = cn.prepareStatement(sb.toString());
			sendQuery.executeUpdate();

			stm = cn.createStatement();
			stm.executeUpdate(querySafeModeON);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getCantidadFromPedido(Pedido pedido) {
		int cantidad = 0;
		for(Pedido elemento : roadmap.getPedidosList())
		{
			if(elemento.getNroDeRemito() == pedido.getNroDeRemito() )
			{
				for(ArticuloPedido articulo : pedido.getPedidoList())
				{
					cantidad += articulo.getCantidad();
				}
			}
		}
		return cantidad;
	}

	public static FleteExpreso getShipInformationFromDB(Connection cn, int TransporteProveedoresID) 
	{
		return  Guiatransporte_Controller.setShipInformation(
				Services.getRsMSSQLWithCnn(cn,Guiatransporte_Controller.queryShipInformationMSSQL(TransporteProveedoresID)));

	}

	public static FleteExpreso setShipInformation(ResultSet rs) 
	{
		FleteExpreso expreso = new FleteExpreso();
		if(rs != null) {
			try {
				while(rs.next()) {
					expreso.setFleteExpreso(rs.getString("Razon Social"));
					expreso.setFleteDireccion(rs.getString("Domicilio"));
					expreso.setFleteLocalidad(rs.getString("Localidad"));
					expreso.setFleteCuit(rs.getString("CUIT"));
					expreso.setFleteTelefono(rs.getString("Tel. Laboral"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return expreso;
	}

	private static String queryShipInformationMSSQL(int TransporteProveedoresID) {
		String query = "SELECT "
				+ "dbo.Proveedores.[Razon Social], "
				+ "dbo.Proveedores.[Domicilio], "
				+ "dbo.Proveedores.[Localidad], "
				+ "dbo.Proveedores.[CUIT], "
				+ "dbo.Proveedores.[Tel. Laboral] "
				+ "FROM [Licencias2021].[dbo].[Proveedores]  "
				+ "WHERE [Licencias2021].[dbo].[Proveedores].[ProveedoresID]="+TransporteProveedoresID+" ";
		return query;
	}


	private static void updateDBWithCommit(ArrayList<GuiaTransporte> list) {

		Connection cn = Services.abrirConexionStatic();
		try {
			cn.setAutoCommit(false); // Deshabilitar auto commit para usar transacción
			for(GuiaTransporte element : list) {
				//				if(element.getNroDeRemito() == element.getNroDeRemitoBuffer()) {
				if(!element.isEmtpy()) {
					//					element.setTotalCantidad(element.getTotalCantidad() - element.getTotalCantidadBuffer());
					//					element.setTotalNeto(element.getTotalNeto().subtract(element.getTotalNetoBuffer()));
					Guiatransporte_Controller.ejecutarUpdateGuiatte(element, cn);
				} else {
					Guiatransporte_Controller.ejecutarInsertGuiatte(element, cn);
				}
			}
			cn.commit(); // Confirmar la transacción si todo ha sido exitoso
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


