package hojaDeRuta;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

import conector.MSSQLManager;
import modelos.ArticuloPedido;
import modelos.HojaDeRuta;
import modelos.Pedido;
import pedidos.Pedido_Controller;

public class ModificarHojaRuta_Controller {

	public String tableChofer = "pruebas_2022.detalle_chofer";
	public String tableVehiculo = "pruebas_2022.detalle_vehiculo";
	public String tableEstadoHojaRuta = "pruebas_2022.detalle_estadoshojaderuta";
	public String tableContador = "pruebas_2022.contador_hojaderuta";
	public String tableHojaRuta = "pruebas_2022.reg_hojaderuta";

	public static Connection cn = MSSQLManager.ConectarMSSQL();
	public Pedido_Controller pedidoCtrl= new Pedido_Controller();
	public ArrayList<Pedido> pedidosList = new ArrayList<>();
	private HojaDeRuta hojaRuta = new HojaDeRuta();
	private Date fechaHoy = new Date(System.currentTimeMillis());
	private HojaRuta_View view = new HojaRuta_View(true);
	public int nroPedido = 0;

	private String selectedCmbChofer;
	private String selectedCmbVehiculo;
	private int cantidadTotal = 0;



	public ModificarHojaRuta_Controller() {

		this.iniciar();

		view.placeholder.setText("Nro pedido");

		view.txtNroPedido.addActionListener(new ActionListener(){
	        @Override
			public void actionPerformed(ActionEvent e) {
	        	getNroPedidoFromTextfield();
	        	hojaRuta = modelos.HojaDeRuta_Controller_deprec.getHojaDeRutaFromDBWhereId(nroPedido);
	        	cantidadTotal = hojaDeRuta.HojaDeRuta_Controller.getCantidadTotalHojaRuta(hojaRuta);
	        	sendDataToTable();
	        	setDataToView();
	        	injectionCmbEstado();
	        }
	    });

		view.btnCargar.setEnabled(false);
		view.btnCargar.addActionListener(new ActionListener(){
	        @Override
			public void actionPerformed(ActionEvent e) {
	        	getNroPedidoFromTextfield();
	        	hojaRuta = hojaDeRuta.HojaDeRuta_Controller.getHojaDeRutaFromDBWhereId(nroPedido);
	        	cantidadTotal = hojaDeRuta.HojaDeRuta_Controller.getCantidadTotalHojaRuta(hojaRuta);
	        	sendDataToTable();
	        	setDataToView();
	        	injectionCmbEstado();
	        }
	    });

		view.btnGuardar.setEnabled(false);
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
					System.out.println("Pressed YES");
					setDataToModel();
					if(validarAtributosAntesInsert(hojaRuta))
					{

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

		view.btnQuitar.setEnabled(false);

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


	} // end constructor

	private void getNroPedidoFromTextfield() {
		nroPedido = Integer.parseInt(view.txtNroPedido.getText());
	}

	private Pedido getPedido(int nroPedido) {
		return pedidoCtrl.getPedidoWithCnn(cn, nroPedido);
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


	public void iniciar() {
		view.setTitle("Modificar hoja de ruta ");
		view.setVisible(true);
		view.setLocationRelativeTo(null);
		view.calendar.setForeground(Color.BLACK);
		view.txtFechaEmision.setBackground(Color.WHITE);
		view.txtCantidadTotal.setBackground(Color.WHITE);
		view.cmbEstado.setEnabled(true);
		view.btnGuardar.setVisible(false);

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
		String query = "SELECT detalle FROM "+tableEstadoHojaRuta+" where idEstado = "+this.hojaRuta.getEstadoHojaID()+" ";
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


	private void setDataToView() {
		view.txtCantidadTotal.setText(String.valueOf(cantidadTotal));
		view.txtFechaEmision.setText(this.hojaRuta.getFechaEmision().toString());
		view.calendar.setDate(this.hojaRuta.getFechaProgramadaSqlDate());
		view.cmbChofer.removeAllItems();
		view.cmbChofer.addItem(this.hojaRuta.getChoferID());
		view.cmbVehiculo.removeAllItems();
		view.cmbVehiculo.addItem(this.hojaRuta.getVehiculoID());
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




}
