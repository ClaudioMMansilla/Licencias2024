package hojaDeRuta;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import Servicios.Services;
import modelos.ArticuloPedido;
import modelos.HojaDeRuta;
import modelos.Pedido;
import pedidos.Pedido_Controller;


public class EmitirHojaRuta_Controller {

	public String tableChofer = "pruebas_2022.detalle_chofer";
	public String tableVehiculo = "pruebas_2022.detalle_vehiculo";
	public String tableEstadoHojaRuta = "pruebas_2022.detalle_estadoshojaderuta";
	public String tableContador = "pruebas_2022.contador_hojaderuta";
	public String tableHojaRuta = "pruebas_2022.reg_hojaderuta";
	final String tablaProductos = "pruebas_2022.productos";

	//public static Connection cn = MSSQLManager.ConectarMSSQL();
	public static Connection cn;
	public static Pedido_Controller pedidoCtrl= new Pedido_Controller();
	public static ArrayList<Pedido> pedidosList = new ArrayList<>();
	private HojaDeRuta hojaRuta = new HojaDeRuta();
	private Date fechaHoy = new Date(System.currentTimeMillis());
	private HojaRuta_View view = new HojaRuta_View(true);

	private String selectedCmbChofer;
	private String selectedCmbVehiculo;
	private String retiraFab = "RETIRA DE FABRICA";
	private int cantidadTotal = 0;



	public EmitirHojaRuta_Controller() {

		view.txtNroPedido.addActionListener(new ActionListener(){
	        @Override
			public void actionPerformed(ActionEvent e) {
	        	addPedidoToList(getPedido(getNroPedidoFromTextfield()));
	        	sendDataToTable();
	        	setTotalView();
	        }
	    });

		view.btnCargar.addActionListener(new ActionListener(){
	        @Override
			public void actionPerformed(ActionEvent e) {
	        	addPedidoToList(getPedido(getNroPedidoFromTextfield()));
	        	sendDataToTable();
	        	setTotalView();
	        }
	    });

		view.btnQuitar.addActionListener(new ActionListener(){
	        @Override
			public void actionPerformed(ActionEvent e) {
	        	deletePedidoFromList(getPedido(getNroPedidoFromTextfield()));
	        	sendDataToTable();
	        	setTotalView();
	        }
	    });

		view.btnGuardar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				panel.setSize(new Dimension(250, 250));
				panel.setLayout(null);
				JLabel label2 = new JLabel("¿Desea continuar? ");
				label2.setVerticalAlignment(SwingConstants.TOP);
				label2.setHorizontalAlignment(SwingConstants.LEFT);
				label2.setBounds(10, 20, 300, 100);
				panel.add(label2);
				UIManager.put("OptionPane.minimumSize", new Dimension(300, 100));
				int res = JOptionPane.showConfirmDialog(null, panel, "Confirm",
						JOptionPane.YES_NO_OPTION);
				if(res == 0)
				{
					setDataToModel();
					if(validarAtributosAntesInsert(hojaRuta))
					{
						hojaDeRuta.HojaDeRuta_Controller.insertRepartoAndArticulosIntoDB(hojaRuta, true);
						reportes.PrintHojaRuta.reportHojaDeRuta(hojaRuta);
						view.dispose();
					}
					else {JOptionPane.showMessageDialog(null, "ERROR, alguno de los campos obligatorios está vacio");}

				} else if (res == 1) {
					System.out.println("Pressed NO");
					JOptionPane.showMessageDialog(null, "La acción ha sido cancelada");

				} else {
					System.out.println("Pressed CANCEL");
				}

			}
		});


		view.cmbChofer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box1 = (JComboBox)e.getSource();
				selectedCmbChofer = (String) box1.getSelectedItem();
			}
		});

		view.cmbVehiculo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box1 = (JComboBox)e.getSource();
				selectedCmbVehiculo = (String) box1.getSelectedItem();
			}
		});
		
		// code implemented since: 2024-04-09
		view.chbxOwnTruck.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                // Verificamos si el checkbox está seleccionado
                if (view.chbxOwnTruck.isSelected()) {
            		if(HojaDeRuta_Controller.IsEmpty(hojaRuta)) {
            			JOptionPane.showMessageDialog(null, "ERROR, no hay pedidos seleccionados");
            		} 
            		else {
            			// set customers name as driver and truck as own
            			ReloadCmbChofer(hojaRuta);
            			ReloadCmbVehiculo();
            			hojaRuta.setVehiculoID(selectedCmbVehiculo);
            		}
                } else {
                    // reload comboboxes from DB
            		injectionCmbChofer();
            		injectionCmbVehiculo();
                }
            }
        });
		
		this.iniciar();


	} // end constructor

	private int getNroPedidoFromTextfield() {
		return Integer.parseInt(view.txtNroPedido.getText());
	}

	public Pedido getPedido(int nroPedido) {
		return pedidoCtrl.getPedido(nroPedido);
	}

	public void addPedidoToList(Pedido pedido) {
		int index = -1;
		if(pedido != null && pedido.getNroDeRemito() > 0)
		{
			for(Pedido elemento : this.hojaRuta.getPedidosList())
			{
				if(elemento.getNroDeRemito() == pedido.getNroDeRemito() )
				{
					index = this.hojaRuta.getPedidosList().indexOf(elemento);
				}
			}

			if(index == -1)
			{
				this.hojaRuta.getPedidosList().add(pedido);
				for(ArticuloPedido elemento : pedido.getPedidoList())
				{
					cantidadTotal += elemento.getCantidad();
				}
			}
		}
		else {System.out.println("Pedido IS NULL");}
	}

	public void deletePedidoFromList(Pedido pedido) {
		int index = -1;
		if(pedido != null && pedido.getNroDeRemito() > 0)
		{
			for(Pedido elemento : this.hojaRuta.getPedidosList())
			{
				if(elemento.getNroDeRemito() == pedido.getNroDeRemito() )
				{
					index = this.hojaRuta.getPedidosList().indexOf(elemento);
				}
			}

			if(index > -1)
			{
				this.hojaRuta.getPedidosList().remove(index);
				for(ArticuloPedido elemento : pedido.getPedidoList())
				{
					cantidadTotal -= elemento.getCantidad();
				}
			}
		}
		else {System.out.println("Pedido IS NULL");}
	}



	public void iniciar() {
		//view.setTitle("Ingreso de vencimiento y lote manual ");
		view.setVisible(true);
		view.setLocationRelativeTo(null);
		view.txtFechaEmision.setText(fechaHoy.toLocaleString());
		view.btnDelete.setVisible(false);
		view.lblNewStatus.setVisible(false);
		view.cmbNewStatus.setVisible(false);

		injectionCmbChofer();
		injectionCmbVehiculo();
		injectionCmbEstado();

	}

	public void sendDataToTable() {

		view.tablaLectura.removeAll();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Pedido Nº");
		modelo.addColumn("Fecha alta");
		modelo.addColumn("Razon Social");
		modelo.addColumn("Importe");
		modelo.addColumn("Cantidad");

		view.tablaLectura.setModel(modelo);
		view.tablaLectura.setRowHeight(24);
		view.tablaLectura.setFont(new Font("Arial", 1, 11));
		String [] datos = new String[5];

		//Instanciamos el TableRowSorter y lo añadimos al JTable
		TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
		view.tablaLectura.setRowSorter(elQueOrdena);

		//indico ancho de cada columna
		TableColumnModel columnModel = view.tablaLectura.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(5);
		columnModel.getColumn(1).setPreferredWidth(5);
		columnModel.getColumn(2).setPreferredWidth(180);
		columnModel.getColumn(3).setPreferredWidth(20);
		columnModel.getColumn(4).setPreferredWidth(2);


		view.tablaLectura.setModel(modelo);

		for(Pedido elemento : this.hojaRuta.getPedidosList())
		{
			datos[0] = String.valueOf(elemento.getNroDeRemito());
			datos[1] = String.valueOf(elemento.getFecha());
			datos[2] = String.valueOf(elemento.getRazonSocial());
			try {datos[3] = elemento.getNetoNoGrabadoToString();}
			catch(NumberFormatException e) {JOptionPane.showMessageDialog(null, e);}
			datos[4] = String.valueOf(pedidoCtrl.PedidoCountElements(elemento));
			modelo.addRow(datos);
		}
	}


	private void injectionCmbChofer(){

		// -- CARGA DATOS EN COMBOX
		ResultSet rs = null;
		String query = "SELECT nombreCompleto, dni FROM "+tableChofer+" ";

		String rsOne = null;
		String rsTwo = null;
		view.cmbChofer.removeAllItems();

		try {
			rs = Servicios.Services.getResulSetStatic(query);
			while(rs.next()) {
				rsOne = rs.getString(1);
				rsTwo = rs.getString(2);
				view.cmbChofer.addItem(rsOne+" - "+rsTwo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void injectionCmbVehiculo(){

		// -- CARGA DATOS EN COMBOX
		ResultSet rs = null;
		String query = "SELECT marcaModelo, dominio FROM "+tableVehiculo+" ";

		String rsOne = null;
		String rsTwo = null;
		view.cmbVehiculo.removeAllItems();

		try {
			rs = Servicios.Services.getResulSetStatic(query);
			while(rs.next()) {
				rsOne = rs.getString(1);
				rsTwo = rs.getString(2);
				view.cmbVehiculo.addItem(rsOne+" - "+rsTwo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void injectionCmbEstado(){

		// -- CARGA DATOS EN COMBOX
		ResultSet rs = null;
		String query = "SELECT detalle FROM "+tableEstadoHojaRuta+" where idEstado = 1";
		String rsOne = null;

		try {
			rs = Servicios.Services.getResulSetStatic(query);
			while(rs.next()) {
				rsOne = rs.getString(1);
				view.cmbEstado.addItem(rsOne);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void setDataToModel() {

		this.hojaRuta.setChoferID(this.selectedCmbChofer);
		this.hojaRuta.setVehiculoID(this.selectedCmbVehiculo);
		this.hojaRuta.setEstadoHojaID(1);
		this.hojaRuta.setFechaEmision(fechaHoy);
		this.hojaRuta.setFechaProgramada(view.calendar.getDate());
		view.txtCantidadTotal.setText(String.valueOf(cantidadTotal));
	}


	private void insertIntoDB(HojaDeRuta hojaRuta) {

		Connection cn = Services.abrirConexionStatic();
		int rs;
		int idHojaRuta = 0;

		String querySelect = "SELECT proximoNro FROM "+tableContador+" ";
		String queryUpdate = "UPDATE "+tableContador+" SET proximoNro = proximoNro + 1";

		String queryInsert = "INSERT INTO "+tableHojaRuta+" "
				+ "(idHojaRuta, choferID, vehiculoID, nroDeRemito, estadoHojaID, fechaProgramada, fechaEmision) "
				+ "values(?,?,?,?,?,?,now())";

		ResultSet rsTwo = Services.getRsStaticWithOpenConn(cn, querySelect);

		try {

			if(rsTwo.next()) {
				idHojaRuta = rsTwo.getInt("proximoNro");
				this.hojaRuta.setHojaRutaID(idHojaRuta);
			}

			PreparedStatement sendQuery = cn.prepareStatement(queryInsert); //pstConsulta por tipo y fecha.-

			for(Pedido elemento : hojaRuta.getPedidosList()) {
				sendQuery.setInt(1, idHojaRuta);
				sendQuery.setString(2, hojaRuta.getChoferID());
				sendQuery.setString(3, hojaRuta.getVehiculoID());
				sendQuery.setInt(4, elemento.getNroDeRemito());
				sendQuery.setInt(5, hojaRuta.getEstadoHojaID());
				sendQuery.setDate(6, hojaRuta.getFechaProgramadaSqlDate());
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

	private void setTotalView() {
		view.txtCantidadTotal.setText(String.valueOf(cantidadTotal));
	}

	private boolean validarAtributosAntesInsert(HojaDeRuta hojaRuta) {
		boolean flag = false;
		if(hojaRuta != null
			&& hojaRuta.getFechaProgramada() != null
			&& hojaRuta.getChoferID() != null
			&& hojaRuta.getVehiculoID() != null
			&& !hojaRuta.getPedidosList().isEmpty())
		{
			flag = true;
		}
		return flag;
	}


	public Map<Integer, String> injectDataToMap(String query) throws SQLException
	{
		Map<Integer, String> bufferMap = new HashMap<>();
		Connection cn = Services.abrirConexionStatic();
		ResultSet rs = null;
		PreparedStatement consultaIngreso = cn.prepareStatement(query);
		rs = consultaIngreso.executeQuery();

		while(rs.next()) {
			bufferMap.put( rs.getInt(1), rs.getString(2) );
		}
		cn.close();
		return bufferMap;
	}


	// code: 2024-04-09
	private void ReloadCmbChofer(HojaDeRuta roadmap){
		// -- CARGA DATOS EN COMBOX
		view.cmbChofer.removeAllItems();
		roadmap.getPedidosList().forEach(Pedido -> view.cmbChofer.addItem(Pedido.getRazonSocial()));
	}
	private void ReloadCmbVehiculo(){
		// -- CARGA DATOS EN COMBOX
		view.cmbVehiculo.removeAllItems();
		view.cmbVehiculo.addItem(retiraFab);
	}
	
	

}
