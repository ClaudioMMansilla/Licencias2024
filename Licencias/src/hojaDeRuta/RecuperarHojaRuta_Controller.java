package hojaDeRuta;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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

import guiaTransporte.Guiatransporte_Controller;
import modelos.ArticuloPedido;
import modelos.HojaDeRuta;
import modelos.Pedido;
import pedidos.Pedido_Controller;

public class RecuperarHojaRuta_Controller {

	public Pedido_Controller pedidoCtrl= new Pedido_Controller();
	public ArrayList<Pedido> pedidosList = new ArrayList<>();
	private HojaDeRuta hojaRuta = new HojaDeRuta();
	private Date fechaHoy = new Date(System.currentTimeMillis());
	public HojaRuta_View view = new HojaRuta_View(true);
	public int nroPedido = 0;
	public int nroHojaRuta = 0;
	private String selectedCmbChofer;
	private String selectedCmbVehiculo;
	private String selectedCmbNewStatus;
	private int cantidadTotal = 0;
	private HashMap<Integer, String> mapEstados = new HashMap<>();
	private boolean isUpdate;
	private boolean isRemove;
	private boolean isInventory;
	private String retiraFab = "RETIRA DE FABRICA";

	/***
	 * Constructor of a "Update" instance
	 * @param nroId
	 * @param isUpdate: is the reason by create the instance
	 */
	public RecuperarHojaRuta_Controller(int nroId, boolean isUpdate) {
		this.nroHojaRuta = nroId;

		if(isUpdate == true) {
			this.isUpdate = true;
			this.isRemove = false;
			this.isInventory = false;
		}
		else if(isUpdate == false) {
			this.isUpdate = false;
			this.isRemove = true;
			this.isInventory = false;
		}

		if (this.isInventory) {
			this.InitViewInventory();
		}
		else {
			this.iniciar();
		}

		view.txtNroPedido.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				getNroPedidoFromTextfield();
				addPedidoToList(pedidoCtrl.getPedido(nroPedido));
				sendDataToTable();
				setDataToView();
				injectionCmbEstado();
			}
		});

		view.btnCargar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				getNroPedidoFromTextfield();
				addPedidoToList(pedidoCtrl.getPedido(nroPedido));
				sendDataToTable();
				setDataToView();
				injectionCmbEstado();
			}
		});

		view.btnQuitar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				getNroPedidoFromTextfield();
				deletePedidoFromList(getPedido(nroPedido));
				sendDataToTable();
				setDataToView();
				injectionCmbEstado();
			}
		});

		view.btnGuiatte.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Guiatransporte_Controller guiatte = new Guiatransporte_Controller(hojaRuta);
				view.dispose();
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
				int res = JOptionPane.showConfirmDialog(null, panel, "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if(res == 0)
				{
					System.out.println("Pressed YES");
					setDataToModel();
					if(validarAtributosAntesInsert(hojaRuta))
					{
						hojaDeRuta.HojaDeRuta_Controller.deleteHojaRuta(true, hojaRuta);
						hojaDeRuta.HojaDeRuta_Controller.insertRepartoAndArticulosIntoDB(hojaRuta, false);
						reportes.PrintHojaRuta.reportHojaDeRuta(hojaRuta);
						//JOptionPane.showMessageDialog(null, "Registro guardado exitosamente ");
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

		view.btnDelete.addActionListener(new ActionListener(){
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
				int res = JOptionPane.showConfirmDialog(null, panel, "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if(res == 0)
				{
					System.out.println("Pressed YES");
					hojaDeRuta.HojaDeRuta_Controller.deleteHojaRuta(true, hojaRuta);
					JOptionPane.showMessageDialog(null, "Registro borrado exitosamente ");
					view.dispose();

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
	} // end constructor


	/**
	 * Constructor Overloaded for new Inventory manager
	 * @param nroId: is the object to inject from DB
	 * @param isInventory: is a flag to set isVisible into some control in the view
	 */
	public RecuperarHojaRuta_Controller(int nroId, String isInventory) {
		this.nroHojaRuta = nroId;
		this.isUpdate = false;
		this.isRemove = false;
		this.isInventory = true;
		this.InitViewInventory();

		view.cmbNewStatus.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box1 = (JComboBox)e.getSource();
				selectedCmbNewStatus = (String) box1.getSelectedItem();
			}
		});

		view.btnGuardar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				panel.setSize(new Dimension(250, 250));
				panel.setLayout(null);
				JLabel label2 = new JLabel("Se ejecutarán cambios en el stock. ¿Desea continuar? ");
				label2.setVerticalAlignment(SwingConstants.TOP);
				label2.setHorizontalAlignment(SwingConstants.LEFT);
				label2.setBounds(5, 20, 400, 100);
				panel.add(label2);
				UIManager.put("OptionPane.minimumSize", new Dimension(400, 100));
				int res = JOptionPane.showConfirmDialog(null, panel, "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if(res == 0)
				{
					System.out.println("Pressed YES");
					setDataToModel();
					if(validarAtributosAntesUpdate(hojaRuta))
					{
						Connection cn = Servicios.Services.abrirConexionStatic();
						if(inventory.InventoryManager_Controller.UpdateInventory("Sin datos", "Substract", "Egreso", hojaRuta, cn)) {
							hojaRuta.setEstadoHojaID(
									mapManager.MapManager.returnDetailEstadoHojaSelectedWhereId(
											mapEstados, selectedCmbNewStatus) );
							if(hojaDeRuta.HojaDeRuta_Controller.UpdateStatusHojaRutaIntoDBStatic(hojaRuta, cn)) {
								JOptionPane.showMessageDialog(null, "Stock actualizado correctamente");
							}
						}
						try {
							cn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
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
	}


	private void getNroPedidoFromTextfield() {
		nroPedido = Integer.parseInt(view.txtNroPedido.getText());
	}

	private Pedido getPedido(int nroPedido) {
		return pedidoCtrl.getPedido(nroPedido);
	}

	private void addPedidoToList(Pedido pedido) {
		boolean flag = false;
		if(pedido != null && pedido.getNroDeRemito() > 0)
		{

			for(Pedido elemento : this.hojaRuta.getPedidosList())
			{
				if(elemento.getNroDeRemito() == pedido.getNroDeRemito() )
				{
					flag = true;
				}
			}

			if(!flag)
			{
				this.hojaRuta.addPedidoIntoPedidosList(pedido);
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

		view.setVisible(true);
		view.setLocationRelativeTo(null);
		view.calendar.setForeground(Color.BLACK);
		view.txtFechaEmision.setBackground(Color.WHITE);
		view.txtCantidadTotal.setBackground(Color.WHITE);

		view.lblNewStatus.setVisible(false);
		view.cmbNewStatus.setVisible(false);

		initInformation(nroHojaRuta);

		if(this.isUpdate && !this.isRemove && !this.isInventory) {
			view.setTitle("Actualizar registro nro: "+nroHojaRuta+" ");
			view.btnGuardar.setVisible(true);
			view.btnQuitar.setEnabled(true);
			view.btnCargar.setEnabled(true);
			view.btnDelete.setVisible(false);
			view.txtNroPedido.setVisible(true);
			view.cmbEstado.setEnabled(true);
		}

		if(!this.isUpdate && this.isRemove && !this.isInventory) {
			view.setTitle("ELIMINAR registro nro: "+nroHojaRuta+" ");
			view.btnGuardar.setVisible(false);
			view.btnQuitar.setVisible(false);
			view.btnCargar.setVisible(false);
			view.btnDelete.setVisible(true);
			view.txtNroPedido.setVisible(false);
			view.cmbEstado.setEnabled(false);
		}
	}


	/***
	 * Init view to show Inventory Control Mode
	 */
	public void InitViewInventory() {

		view.setVisible(true);
		view.setLocationRelativeTo(null);
		view.calendar.setForeground(Color.BLACK);
		view.txtFechaEmision.setBackground(Color.WHITE);
		view.txtCantidadTotal.setBackground(Color.WHITE);

		//		initInformation(nroHojaRuta);

		view.calendar.setForeground(Color.RED);
		view.txtFechaEmision.setBackground(Color.RED);
		view.txtCantidadTotal.setBackground(Color.RED);
		view.cmbChofer.setBackground(Color.RED);
		view.cmbChofer.setForeground(Color.WHITE);
		view.cmbVehiculo.setBackground(Color.RED);
		view.cmbVehiculo.setForeground(Color.WHITE);
		view.lblNewStatus.setVisible(true);
		view.cmbNewStatus.setVisible(true);

		if(!this.isUpdate && !this.isRemove && this.isInventory) {
			view.setTitle("Administración de STOCK - registro nro: "+nroHojaRuta+" ");
			view.btnGuardar.setVisible(true);
			view.btnQuitar.setVisible(false);
			view.btnCargar.setVisible(false);
			view.btnDelete.setVisible(false);
			view.txtNroPedido.setVisible(false);
			view.cmbEstado.setEnabled(false);
		}


		initInformation(nroHojaRuta);

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
			//try {datos[3] = elemento.getNetoNoGrabadoToString();}
			try {datos[3] = String.valueOf(elemento.getNetoNoGrabado());}
			catch(NumberFormatException e) {JOptionPane.showMessageDialog(null, e);}
			datos[4] = String.valueOf(pedidoCtrl.PedidoCountElements(elemento));
			modelo.addRow(datos);
		}
	}


	private void injectionCmbChofer(){

		// -- CARGA DATOS EN COMBOX
		ResultSet rs = null;
		String query = "SELECT nombreCompleto, dni FROM "+hojaDeRuta.HojaDeRuta_Controller.tableChofer+" ";

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
		String query = "SELECT marcaModelo, dominio FROM "+hojaDeRuta.HojaDeRuta_Controller.tableVehiculo+" ";

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
		view.cmbEstado.removeAllItems();
		// -- CARGA DATOS EN COMBOX
		ResultSet rs = null;
		//		String query = "SELECT DISTINCTROW detalle FROM "+tableEstadoHojaRuta+" ";
		String query = "SELECT detalle FROM "+hojaDeRuta.HojaDeRuta_Controller.tableEstadoHojaRuta+" where idEstado = "+this.hojaRuta.getEstadoHojaID()+" ";
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

	private void injectionCmbAndMapEstado(){
		view.cmbNewStatus.removeAllItems();
		// -- CARGA DATOS EN COMBOX
		ResultSet rs = null;
		String query = "SELECT idEstado, detalle FROM "+hojaDeRuta.HojaDeRuta_Controller.tableEstadoHojaRuta+" ";
		String rsOne = null;
		try {
			rs = Servicios.Services.getResulSetStatic(query);
			while(rs.next()) {
				rsOne = rs.getString("detalle");
				view.cmbNewStatus.addItem(rsOne);
				mapEstados.put(rs.getInt("idEstado"), rsOne);
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


	private void setDataToView() {
		view.txtCantidadTotal.setText(String.valueOf(cantidadTotal));
		view.txtFechaEmision.setText(this.hojaRuta.getFechaEmision().toString());
		view.calendar.setDate(this.hojaRuta.getFechaProgramadaSqlDate());
		//		view.cmbChofer.removeAllItems();
		//		view.cmbChofer.addItem(this.hojaRuta.getChoferID());
		//		view.cmbVehiculo.removeAllItems();
		//		view.cmbVehiculo.addItem(this.hojaRuta.getVehiculoID());
		injectionCmbChofer();
		injectionCmbVehiculo();
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

	private boolean validarAtributosAntesUpdate(HojaDeRuta hojaRuta) {
		boolean flag = false;
		if(hojaRuta != null && this.selectedCmbNewStatus != null)
		{
			flag = true;
		}
		return flag;
	}

	private void initInformation(int nroId) {
		hojaRuta = hojaDeRuta.HojaDeRuta_Controller.getHojaDeRutaFromDBWhereId(nroId);
		cantidadTotal = hojaDeRuta.HojaDeRuta_Controller.getCantidadTotalHojaRuta(hojaRuta);
		sendDataToTable();
		setDataToView();
		injectionCmbEstado();
		injectionCmbAndMapEstado();

		if(hojaRuta.getEstadoHojaID() != 1) {
			JOptionPane.showMessageDialog(null, "No es posible modificar la H.R. indicada, su estado ha sido cambiado");
			view.btnGuardar.setEnabled(false);
			view.btnDelete.setEnabled(false);
		}
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
