package inventory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import modelos.ArticuloPedido;
import modelos.HojaDeRuta;
import modelos.Pedido;

public class InventoryManager_Controller {

	private static String tableInventoryQty = "pruebas_2022.productos";

	/**
	 * Method update product inventory in database
	 * @param userSes: who is updating DB
	 * @param mathOperator: indicates the operation to make (Add, Substract, Multiply or Divide)
	 * @param hojaRuta: object that have inside a product collection
	 * @return boolean if the method ran okay
	 */
	public static boolean UpdateInventory(String userSes, String mathOperator, String inOrOut, HojaDeRuta hojaRuta, Connection cn)  {

		if(hojaRuta != null) {
			Statement stm;
			String operator = null;
			int rowsAffected = 0;

			switch(fecha.Fecha.CapitalizeFirstLetter(mathOperator)) {
			case "Add":
				operator = "+";
				break;
			case "Substract":
				operator = "-";
				break;
			case "Multiply":
				operator = "*";
				break;
			case "Divide":
				operator = "/";
				break;
			}

			for(Pedido i : hojaRuta.getPedidosList()) {
				for(ArticuloPedido j : i.getPedidoList()) {
					try
					{
						stm = cn.createStatement();

						String query = "UPDATE "+inventory.InventoryManager_Controller.tableInventoryQty+" "
								+ "SET stock = stock "+operator+" "+j.getCantidad()+", "
								+ "movimUsuario ='"+userSes+"', "
								+ "movimTipo ='"+fecha.Fecha.CapitalizeFirstLetter(inOrOut)+"', "
								+ "movimCantidad ='"+j.getCantidad()+"', "
								+ "idCliente ='"+i.getIdCliente()+"', "
								+ "razonSocial ='"+i.getRazonSocial()+"', "
								+ "comentario = 'H.Ruta: "+hojaRuta.getHojaRutaID()+" - Pedido: "+i.getNroDeRemito()+" '"
								+ "WHERE idProducto ='"+j.getCodigo()+"' ";
						rowsAffected += stm.executeUpdate(query);

					} catch (SQLException e){
						System.out.println(e);
					}
					System.out.println("Rows affected: " + rowsAffected);
				}
			}
			return true;
		}
		return false;
	}

}
