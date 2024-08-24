package hojaDeRuta;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import modelos.HojaDeRuta;
import net.sf.jasperreports.engine.JRException;

public class ListarHojaRuta_Controller {

	private ListarHojaRuta_View view = new ListarHojaRuta_View(true);
	private ArrayList<HojaDeRuta> list = new ArrayList<>();
	private String selectedCmbEstado;
	private HashMap<Integer, String> mapEstados = new HashMap<>();


	public ListarHojaRuta_Controller() {

		this.initView();

		view.btnPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(view.txtId.getText() != null) {
					String idStr = view.txtId.getText();
					try {
						reportes.PrintHojaRuta.Print(idStr);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "El campo 'Nro de hoja' está vacío, por favor ingrese un ID correcto");
				}

			}
		});

		view.btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				int id = Integer.parseInt(view.txtId.getText());
//				RecuperarHojaRuta_Controller ctrl = new RecuperarHojaRuta_Controller(id, false);

			}
		});

		view.btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection cn = Servicios.Services.abrirConexionStatic();

				list = HojaDeRuta_Controller.getArrayHojaRutaFromDBWhereDateAndStatus(
						view.calendarSince.getDate(),
						view.calendarTo.getDate(),
						mapManager.MapManager.returnDetailEstadoHojaSelectedWhereId(mapEstados, selectedCmbEstado),
						cn);

				sendDataToTable();
				System.out.println("List Size "+list.size());
				try {
					cn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		view.btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
//				pedido = pedidoCtrl.getPedido(
//						Fecha.parserStringToInt(txtNroPedido.getText())
//						);

//				sendDataToTable();
//				setDataToTxtRazonSocial(pedido);
				int id = Integer.parseInt(view.txtId.getText());
				hojaDeRuta.RecuperarHojaRuta_Controller ctrl = new hojaDeRuta.RecuperarHojaRuta_Controller(id, "isInventory");
			}
		});

		view.cmbEstado.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box1 = (JComboBox)e.getSource();
				selectedCmbEstado = (String) box1.getSelectedItem();
			}
		});

	} // end declaration


	private void injectionCmbAndMapEstado(){

		// -- CARGA DATOS EN COMBOX
		ResultSet rs = null;
		String query = "SELECT idEstado, detalle FROM "+hojaDeRuta.HojaDeRuta_Controller.tableEstadoHojaRuta+" ";
		String rsOne = null;
		try {
			rs = Servicios.Services.getResulSetStatic(query);
			while(rs.next()) {
				rsOne = rs.getString("detalle");
				view.cmbEstado.addItem(rsOne);
				mapEstados.put(rs.getInt("idEstado"), rsOne);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void initView() {
		view.setVisible(true);
		view.setLocationRelativeTo(null);
		view.calendarSince.setForeground(Color.BLACK);
		view.calendarTo.setForeground(Color.BLACK);
		this.injectionCmbAndMapEstado();
	}

	public void sendDataToTable() {

		view.tablaLectura.removeAll();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("H.Ruta Nº");
		modelo.addColumn("Fecha Prog");
		modelo.addColumn("Chofer");
		modelo.addColumn("Vehiculo");
		modelo.addColumn("Fecha Alta");

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
		columnModel.getColumn(1).setPreferredWidth(10);
		columnModel.getColumn(2).setPreferredWidth(100);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(4).setPreferredWidth(10);


		view.tablaLectura.setModel(modelo);

		for(HojaDeRuta elemento : this.list)
		{
			datos[0] = String.valueOf(elemento.getHojaRutaID());
			datos[1] = fecha.Fecha.formatterCalendarDate(elemento.getFechaProgramada());
			datos[2] = String.valueOf(elemento.getChoferID());
			datos[3] = String.valueOf(elemento.getVehiculoID());
			datos[4] = fecha.Fecha.formatterCalendarDate(elemento.getFechaEmision());
			modelo.addRow(datos);
		}
	}

}
