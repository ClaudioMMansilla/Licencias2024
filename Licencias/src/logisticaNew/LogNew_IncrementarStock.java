package logisticaNew;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import Atxy2k.CustomTextField.RestrictedTextField;
import Servicios.Services;
import envasadoUnificado2024.TextPrompt;
import fecha.Fecha;
import modelos.ArticuloPedido;
import modelos.HojaDeRuta;
import modelos.Pedido;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import pedidos.Pedido_Controller;


@SuppressWarnings("serial")
public class LogNew_IncrementarStock extends JFrame {

	public Pedido pedido = new Pedido();
	public Pedido_Controller pedidoCtrl= new Pedido_Controller();

	String tablaProductos = "pruebas_2022.productos";
	//	String tablaProductos = "testing.productos";

	private Services servicio = new Services();

	private static String userSes;
	private JPanel contentPane;
	private JTable tablaLectura;
	private DefaultTableModel modelo;
	private int numero;
	private char letra;
	public String [] datos;
	public List<HojaDeRuta> listItems;
	private File archivoSeleccionado;
	public int rowsCounter;
	private static String pathOrigen = null;
	private int listSize = 0;
	private JLabel lblCount = new JLabel();
	private JLabel lblSize = new JLabel();
	private int sizeAux = listSize;
	private JProgressBar progressBar;
	private int itProgressBar;
	public JTextField txtNroPedido;
	private JTextField txtRazonSocial;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LogNew_IncrementarStock frame = new LogNew_IncrementarStock(userSes);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogNew_IncrementarStock(String userSes) {

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 780, 660);
		setTitle("07 - Logistica: administrar pedidos en stock ");
		contentPane = new JPanel();
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" LECTURA DE PEDIDO - EGRESO DE STOCK "));
		contentPane.setLayout(null);
		setResizable(false);
		setContentPane(contentPane);

		addWindowListener(new Ventana()); //pongo la ventana en oyente
		EventoDeTeclado tecla=new EventoDeTeclado();
		this.addKeyListener(tecla);
		contentPane.addKeyListener(tecla);

		progressBar = new JProgressBar();
		//contentPane.add(progressBar);
		progressBar.setBounds(718, 58, 100, 25);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);


		tablaLectura = new JTable();
		tablaLectura.setRowHeight(24);
		tablaLectura.setFont(new Font("Arial", 0, 12));
		tablaLectura.setForeground(Color.black);
		tablaLectura.setBackground(Color.white);
		tablaLectura.setShowVerticalLines(false);
		tablaLectura.setSurrendersFocusOnKeystroke(true);
		tablaLectura.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {

				}
				));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 195, 677, 402);
		contentPane.add(scrollPane);
		scrollPane.add(tablaLectura);
		scrollPane.setViewportView(tablaLectura);

		JButton jbGuardar = new JButton("Guardar");
		jbGuardar.setBounds(527, 58, 90, 25);
		contentPane.add(jbGuardar);
		jbGuardar.addActionListener(new ActionListener(){
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
					Controller_SQL objetoAuxiliar = new Controller_SQL();
					try {
						objetoAuxiliar.updateDB_tablaProductos(userSes);
						report(pedido);

					} catch (UnknownHostException | SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1);
					}
					System.out.println("Pressed YES");

				} else if (res == 1) {
					System.out.println("Pressed NO");
					JOptionPane.showMessageDialog(null, "La acción ha sido cancelada");

				} else {
					System.out.println("Pressed CANCEL");
				}

			}
		});

		JButton jbCargar = new JButton("Cargar");
		jbCargar.setBounds(137, 58, 90, 25);
		contentPane.add(jbCargar);
		jbCargar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				pedido = pedidoCtrl.getPedido(
						Fecha.parserStringToInt(txtNroPedido.getText())
						);

				sendDataToTable();
				setDataToTxtRazonSocial(pedido);
			}
		});

		txtNroPedido = new JTextField();
		txtNroPedido.setBounds(41, 60, 86, 23);
		contentPane.add(txtNroPedido);
		txtNroPedido.setColumns(10);
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(txtNroPedido);
		fieldIdRestringido.setOnlyNums(true);
	    TextPrompt placeholder = new TextPrompt("Ingrese nro del pedido", txtNroPedido);
	    placeholder.setFont(new Font("Tahoma", Font.PLAIN, 7));
	    placeholder.changeAlpha(0.75f);

		JLabel lblNewLabel = new JLabel("Razon Social");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(41, 138, 86, 25);
		contentPane.add(lblNewLabel);

		txtRazonSocial = new JTextField();
		txtRazonSocial.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtRazonSocial.setBounds(137, 140, 480, 25);
		contentPane.add(txtRazonSocial);
		txtRazonSocial.setColumns(10);

	}

	public void setDataToTxtRazonSocial(Pedido pedido){
		this.txtRazonSocial.setText(pedido.getRazonSocial());
	}

	public class Controller_SQL{

		private void updateDB_tablaProductos(String userSes) throws SQLException, UnknownHostException {

			Connection cn = null;
			Statement stm;
			ResultSet rsLeer;

			int stockDBase;
			int stockFinal;
			int rowsAffected = 0;

			if(pedido != null) {
				ArrayList<ArticuloPedido> list = pedido.getPedidoList();
				for (ArticuloPedido elemento : list) {
					try {
						cn = servicio.abrirConexion();
						stm = cn.createStatement();
						rsLeer = stm.executeQuery("SELECT stock FROM "+tablaProductos+" "
								+ "where idProducto= '"+elemento.getCodigo()+"' ");

						while(rsLeer.next())
						{
							stockDBase = rsLeer.getInt("stock");
							stockFinal = stockDBase + elemento.getCantidad();

							try
							{
								stm = cn.createStatement();
								String sql = "UPDATE "+tablaProductos+" "
										+ "set stock ='"+stockFinal+"', "
										+ "movimUsuario ='"+userSes+"', "
										+ "movimTipo ='Ingreso', "
										+ "movimCantidad ='"+elemento.getCantidad()+"', "
										+ "idCliente ='"+pedido.getNroDeRemito()+"', "
										+ "razonSocial ='"+pedido.getRazonSocial()+"', "
										+ "comentario ='Pedido reintegrado: "+pedido.getNroDeRemito()+"' "
										+ "where idProducto ='"+elemento.getCodigo()+"' ";

								rowsAffected += stm.executeUpdate(sql);
								//itProgressBar++;
								//progressBar.setValue(itProgressBar);

							} catch (SQLException e){
								System.out.println(e);
							}
						}

					} catch (SQLException e){
						System.out.println(e);
					}
				}

				cn.close();
				JOptionPane.showMessageDialog(null, "\nProceso realizado correctamente\n");

			}
			else
			{
				JOptionPane.showMessageDialog(null, "No hay datos disponibles");
			}

			System.out.println("Rows affected: " + rowsAffected);
			dispose();
		}
	}


	public void sendDataToTable() {

		/**
		 *
		 *  private int ventaID;
			private int productoID;
			private int codigo;
			private int cantidad;
			private String textoEnFactura;
		 */

		this.tablaLectura.removeAll();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Id");
		modelo.addColumn("Producto");
		modelo.addColumn("Cantidad");

		this.tablaLectura.setModel(modelo);
		String [] datos = new String[3];

		//Instanciamos el TableRowSorter y lo añadimos al JTable
		TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
		this.tablaLectura.setRowSorter(elQueOrdena);

		//indico ancho de cada columna
		TableColumnModel columnModel = this.tablaLectura.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(5);
		columnModel.getColumn(1).setPreferredWidth(350);
		columnModel.getColumn(2).setPreferredWidth(15);
		this.tablaLectura.setModel(modelo);

		ArrayList<ArticuloPedido> list = pedido.getPedidoList();
		for (ArticuloPedido elemento : list) {

			datos[0] = String.valueOf(elemento.getCodigo());
			datos[1] = elemento.getTextoEnFactura();
			datos[2] = String.valueOf(elemento.getCantidad());
			modelo.addRow(datos);
			System.out.println(elemento.getTextoEnFactura() + String.valueOf(elemento.getCantidad()));

		}
	}


	public void report(Pedido pedido) {

		if(pedido != null) {
			ArrayList<ArticuloPedido> list = pedido.getPedidoList();

//			for(Iterator<ArticuloPedido> iterador = list.iterator(); iterador.hasNext();) {
//				ArticuloPedido value = iterador.next();
//				if(value.getProductoID() > 0) {
//
//				}
//			}

	    	//indico la ruta del objeto a cargar
	    	JasperReport reporte;
			try {
				reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\InformePedido.jasper");

				/* Convert List to JRBeanCollectionDataSource */
				JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(pedido.getPedidoList());

		    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
		    	Map<String, Object> parametros = new HashMap<>();
		    	parametros.put("NroDeRemito", pedido.getNroDeRemito());
		    	parametros.put("RazonSocial", pedido.getRazonSocial());
		    	parametros.put("CollectionBeanParam", itemsJRBean);


		    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
		    	JasperPrint jasperPrint = JasperFillManager.fillReport(
						reporte, parametros,
						new JREmptyDataSource());

				// primero genero pdf y luego proceso a disparar impresion en papel
				JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // false = hide_on_close
				jasperViewer.setVisible(true);
				JasperPrintManager.printReport( jasperPrint, true); // false = dispara impresion a impresora por default
																    //  true = abre ventana dialogo

				// Exporto en pdf luego de disparar la impresion
//		    	JRPdfExporter exp = new JRPdfExporter();
//				exp.setExporterInput(new SimpleExporterInput(jasperPrint));
//				exp.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\MyReports\\Pedidos\\"+pedido.getNroDeRemito()+"_Pedido_"+pedido.getRazonSocial()+".pdf"));
//				SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
//				exp.setConfiguration(conf);
//				exp.exportReport();
		    	// --------- cambio orden porque sino al cancelar impresion tampoco se genera pdf

			} catch (JRException e) {
				e.printStackTrace();
	   			JOptionPane.showMessageDialog(null,"Error generando reporte  \n\n "
	   					+ "SQLException : reports: reportEnvasado() \n" + e);
			}
		}
	}



	public class EventoDeTeclado implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) { // para escuchar tecla al presionar
			numero = e.getKeyCode();
			System.out.println(numero);
		}

		@Override
		public void keyReleased(KeyEvent e) { // para escucha tecla al soltar

		}

		@Override
		public void keyTyped(KeyEvent e) { // para escuchar que tecla fue presionada y soltada
			letra = e.getKeyChar();
			System.out.println(letra);
		}
	}

	class CambiaEstado implements WindowStateListener {
		@Override
		public void windowStateChanged(java.awt.event.WindowEvent e) {
			System.out.println("el estado de la ventana ha cambiado");
			System.out.println(e.getNewState());//recibo el c�digo que identifica el tipo de evento o�do
		}
	}

	class Ventana extends WindowAdapter { //extends refiere que hereda los atributos de clase WindowAdapter

		@Override
		public void windowIconified(java.awt.event.WindowEvent e) {
			System.out.println("windowIconified: cambio a estado minimizado");
		}
		@Override
		public void windowClosing(java.awt.event.WindowEvent e) {
			System.out.println("dispose ejecutado");
			dispose();

		}
		@Override
		public void windowDeiconified(java.awt.event.WindowEvent e) {
			System.out.println("windowDeiconified: cambio a estado normal");
		}
	}


	public void showProgressLblCount(int count){
		this.lblCount.setText(String.valueOf(count));
	}

	public void showProgressLblSize(String size){
		this.lblSize.setText(size);
	}

	public void iterate() {
		while(itProgressBar <= listSize) {
			progressBar.setValue(itProgressBar);
			itProgressBar ++;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
