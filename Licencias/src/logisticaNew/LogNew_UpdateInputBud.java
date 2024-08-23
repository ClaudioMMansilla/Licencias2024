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
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Atxy2k.CustomTextField.RestrictedTextField;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import reportes.LeerEnvasado;

@SuppressWarnings("serial")
public class LogNew_UpdateInputBud extends JFrame {

//	String tablaInput = "desarrollo.env_input_budines";
//	String tablaProductos = "pruebas_2022.test_productos";

//	String tablaInput = "pruebas_2022.env_input_pdulces";
	String tablaInput = "pruebas_2022.env_input_budines";
	String tablaProductos = "pruebas_2022.productos";
	private Services servicio = new Services();
	private static String userSes;

	private String tituloFrame = " ***** PLANTA PAN DULCES ***** ";
	private String domicilio = "Planta: 4945";
//	private String tituloFrame = " ***** PLANTA BUDINES ***** ";
//	private String domicilio = "Planta: 4936";
	private String tituloContent = " MODIFICAR REGISTRO DE ENVASADO ";

	private JPanel contentPane;
	private JTable tablaLectura;
	private DefaultTableModel modelo;
	public String incognita1, incognita2;
	public String [] datos;
	public String [] datosAux;
	public List<LeerEnvasado> listItems;


	private Connection cn;
	private PreparedStatement pstguardarEnEnvasado;
	public int flag = 1;
	public int flagKeyIntro = 0;

	private final String queryBox =
			"SELECT numPallet FROM "+tablaInput+" WHERE flagDisponible = 1";

	private String pst = "SELECT numPallet, fechaIngreso, idProducto, producto, "
			+ "cajasInformadas, lote, vencimiento "
			+ "FROM "+tablaInput+" where numPallet >=? AND numPallet <=? AND flagDisponible = '"+flag+"' ";

	private JTextField textField;
	private JTextField txtId;
	private int numero;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LogNew_UpdateInputBud frame = new LogNew_UpdateInputBud(userSes);
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
	public LogNew_UpdateInputBud(String userSes) {

		addWindowListener(new Ventana()); //pongo la ventana en oyente
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 960, 200);
		setTitle(tituloFrame);
		contentPane = new JPanel();
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(tituloContent));
		contentPane.setLayout(null);
		setResizable(false);
		setContentPane(contentPane);

		EventoDeTeclado tecla=new EventoDeTeclado();
		addKeyListener(tecla);
		contentPane.addKeyListener(tecla);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		tablaLectura = new JTable();
		tablaLectura.setRowHeight(24);
		tablaLectura.setFont(new Font("Arial", 1, 14));
		tablaLectura.setForeground(Color.BLUE);
		tablaLectura.setBackground(Color.lightGray);
		tablaLectura.setShowVerticalLines(false);
		tablaLectura.setSurrendersFocusOnKeystroke(true);
		tablaLectura.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {

				}
				));


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 106, 934, 47);
		contentPane.add(scrollPane);
		scrollPane.add(tablaLectura);
		scrollPane.setViewportView(tablaLectura);


		JButton jb3 = new JButton("Guardar");
		jb3.setBounds(708, 58, 90, 25);
		contentPane.add(jb3);
		jb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JPanel panel = new JPanel();
				panel.setSize(new Dimension(250, 250));
				panel.setLayout(null);
				JLabel label2 = new JLabel("�Desea continuar? ");
				label2.setVerticalAlignment(SwingConstants.TOP);
				label2.setHorizontalAlignment(SwingConstants.LEFT);
				label2.setBounds(10, 20, 300, 100);
				panel.add(label2);
				UIManager.put("OptionPane.minimumSize", new Dimension(300, 100));
				int res = JOptionPane.showConfirmDialog(null, panel, "Confirm",
						JOptionPane.YES_NO_OPTION);
				if(res == 0)
				{
					try {
						guardarEnEnvasado(userSes, e);
						JOptionPane.showMessageDialog(null, "Registro actualizado correctamente ");
						//guardarEnStock(e);
					} catch (SQLException | UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1);
					}
					((DefaultTableModel)tablaLectura.getModel()).removeRow(0);
					textField.setText(null);
					txtId.setText(null);
//					System.out.println("Pressed YES");

				} else if (res == 1) {
//					System.out.println("Pressed NO");
					JOptionPane.showMessageDialog(null, "La acci�n ha sido cancelada");

				} else {
					System.out.println("Pressed CANCEL");
				}

				//				JOptionPane.showMessageDialog(null, "Opci�n restringida, consultar a logistica");
			}
		});


		JLabel lblHasta = new JLabel("Nro Pallet");
		lblHasta.setBounds(28, 42, 58, 14);
		contentPane.add(lblHasta);

		textField = new JTextField();
		textField.setBounds(28, 60, 65, 20);
		contentPane.add(textField);
		// libreria importada de internet - L132 to L135
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(textField);
		fieldIdRestringido.setLimit(4);
		fieldIdRestringido.setOnlyNums(true);
		// fin lib importada
		textField.addKeyListener(tecla);
		textField.addActionListener(new ActionListener()
		{
			public void actionPerformed(KeyEvent e)
			{
				numero = e.getKeyCode();
			}
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				if (numero == 10) {
					//KeyIntro();
					consultaTotal();
				}
				else
				{
					//System.out.println("saliendo del if en field.id"+ numero);
				}
			}
		});

		txtId = new JTextField();
		txtId.setText("id ?");
		txtId.setBounds(166, 60, 86, 20);
		contentPane.add(txtId);
		// libreria importada de internet - L132 to L135
		RestrictedTextField fieldIdRestringido2 = new RestrictedTextField(txtId);
		fieldIdRestringido2.setLimit(4);
		fieldIdRestringido2.setOnlyNums(true);
		// fin lib importada
		txtId.addKeyListener(tecla);
		txtId.addActionListener(new ActionListener()
		{
			public void actionPerformed(KeyEvent e)
			{
				numero = e.getKeyCode();
			}
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				if (numero == 10) {
					KeyIntro();
					//consultaTotal();
				}
				else
				{
					//System.out.println("saliendo del if en field.id"+ numero);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Cambiar Producto");
		lblNewLabel.setBounds(166, 42, 119, 14);
		contentPane.add(lblNewLabel);

		dispose();

	}

	//seteo el preparedStatement
	private void KeyIntro() {
		System.out.println("entrando a KeyIntro");
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

		try {
			cn = servicio.abrirConexion();
			stm = cn.createStatement();

			System.out.println("entrando a if");
			if(txtId.getText().equals("")) { // valido textfield id si esta vacio

				JOptionPane.showMessageDialog(null, "ERROR, c�digo ID no fue ingresado");
				((DefaultTableModel)tablaLectura.getModel()).removeRow(0);
			}

			if(flagKeyIntro == 0) {
				JOptionPane.showMessageDialog(null, "ERROR, primero debe buscar alg�n registro v�lido ");
				((DefaultTableModel)tablaLectura.getModel()).removeRow(0);
			}
			else
			{
				String texto_id = txtId.getText();
				int idLocal = Integer.parseInt(texto_id);
				System.out.println("idLocal :" +idLocal);
				rs = stm.executeQuery("SELECT idProducto, producto FROM "+tablaProductos+" where idProducto='"+idLocal+"'");
				datosAux  = new String [2];

				while(rs.next())
				{
					datosAux[0] = rs.getString(1);
					datosAux[1] = rs.getString(2);

					//datos[2] = datosAux[0];
					//datos[3] = datosAux[1];
					modelo.addRow(datos);

					//System.out.println("datosAux[0] = "+datosAux[0]);
					//System.out.println("datosAux[1] = "+datosAux[1]);
				}

				if (datosAux[0]==null) // valido textfield id sino hubo resultados en consulta
				{
					JOptionPane.showMessageDialog(null, "ERROR, c�digo ID ingresado no existe en registro");
					((DefaultTableModel)tablaLectura.getModel()).removeRow(0);
				}

				tablaLectura.setValueAt(datosAux[0], 0, 2);
				tablaLectura.setValueAt(datosAux[1], 0, 3);
				tablaLectura.setValueAt(datos[4], 0, 4);
				((DefaultTableModel)tablaLectura.getModel()).removeRow(1);

			}


		} catch (SQLException e){
			System.out.println(e);
			JOptionPane.showMessageDialog(null, e);
		}

	}


	public void consultaTotal() {

		Connection cn;
		Statement stm;
		ResultSet rs;

		int flag = 1;

		System.out.println("entrando a consultar");

		if(textField.getText().equals("")) { // valido textfield id si esta vacio
			JOptionPane.showMessageDialog(null, "ERROR, campo nro pallet esta vacio");
			((DefaultTableModel)tablaLectura.getModel()).removeRow(0);
		}
		else {

			String texto_id = textField.getText();
			int idLocal = Integer.parseInt(texto_id);
			System.out.println("idLocal :" +idLocal);

			modelo = new DefaultTableModel();
			modelo.addColumn("Orden N�");
			modelo.addColumn("Envasado");
			modelo.addColumn("id");
			modelo.addColumn("Descripci�n");
			modelo.addColumn("Cajas");
			modelo.addColumn("Lote");
			modelo.addColumn("Vencimiento");
			tablaLectura.setModel(modelo);

			//indico ancho de cada columna
			TableColumnModel columnModel = tablaLectura.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(5);
			columnModel.getColumn(1).setPreferredWidth(100);
			columnModel.getColumn(2).setPreferredWidth(10);
			columnModel.getColumn(3).setPreferredWidth(380);
			columnModel.getColumn(4).setPreferredWidth(5);
			columnModel.getColumn(5).setPreferredWidth(10);
			columnModel.getColumn(6).setPreferredWidth(60);

			//Instanciamos el TableRowSorter y lo a�adimos al JTable
			TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
			tablaLectura.setRowSorter(elQueOrdena);


			try {

				System.out.println("entrando en Try");
				cn = servicio.abrirConexion();
				stm = cn.createStatement();
				rs = stm.executeQuery(""
						+ "SELECT numPallet, fechaIngreso, idProducto, producto, "
						+ "cajasInformadas, lote, vencimiento, flagDisponible "
						+ "FROM "+tablaInput+" where numPallet = '"+idLocal+"' ");

				//			String [] datos  = new String [7];
				datos  = new String [8];

				// codigo 04.2022
				//rsDos = stm.executeQuery("SELECT numPallet FROM "+tablaInput+" where fechaIngreso ='"+fechaIngreso+"' ");
				System.out.println("linea post executeQuery");

				while(rs.next()) {
					//				System.out.println("datos[0] = rs.getString(1)");
					datos[0] = rs.getString(1);
					datos[1] = rs.getString(2);
					datos[2] = rs.getString(3);
					datos[3] = rs.getString(4);
					datos[4] = rs.getString(5);
					datos[5] = rs.getString(6);
					datos[6] = rs.getString(7);
					datos[7] = rs.getString(8);
					flag = Integer.parseInt(datos[7]);
					modelo.addRow(datos);
					System.out.println("modelo.addRow(datos)");
					flagKeyIntro = 1;
				}

				tablaLectura.setModel(modelo);

				//List <PrintEnvasado> listItems= new ArrayList<PrintEnvasado>();
				listItems= new ArrayList<>();

				for (int i = 0; i<tablaLectura.getRowCount(); i++) {
					LeerEnvasado listado = new LeerEnvasado(
							tablaLectura.getValueAt(i, 0).toString(),
							tablaLectura.getValueAt(i, 1).toString(),
							tablaLectura.getValueAt(i, 2).toString(),
							tablaLectura.getValueAt(i, 3).toString(),
							tablaLectura.getValueAt(i, 4).toString(),
							tablaLectura.getValueAt(i, 5).toString(),
							tablaLectura.getValueAt(i, 6).toString());
					listItems.add(listado);
				}


				if(datos [0] == null) {
					JOptionPane.showMessageDialog(null, "No hay datos disponibles");
					((DefaultTableModel)tablaLectura.getModel()).removeRow(0);
					flagKeyIntro = 0;
				}

				if(flag == 0) {
					JOptionPane.showMessageDialog(null, "El pallet seleccionado no esta disponible");
					((DefaultTableModel)tablaLectura.getModel()).removeRow(0);
					flagKeyIntro = 0;
				}

			}catch (Exception e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(null, e2);
				tablaLectura.setModel(null);
			}
		}
	}


	private void guardarEnEnvasado(String userSes, ActionEvent evt) throws SQLException, UnknownHostException {


		if(flagKeyIntro == 0)
		{
			JOptionPane.showMessageDialog(null, "ERROR, primero debe buscar alg�n registro v�lido ");
			((DefaultTableModel)tablaLectura.getModel()).removeRow(0);
		}
		else {
			Connection cn;
			Statement stm;

			try {

				cn = servicio.abrirConexion();
				stm = cn.createStatement();
				String sql = "UPDATE "+tablaInput+" "
						+ "set idProducto = '"+tablaLectura.getValueAt(0, 2)+"', "
						+ "producto = '"+tablaLectura.getValueAt(0, 3)+"', "
						+ "cajasInformadas ='"+tablaLectura.getValueAt(0, 4)+"', "
						+ "flagAjuste ='1'"
						+ "where numPallet = '"+tablaLectura.getValueAt(0, 0)+"' ";

				int rowsAffected = stm.executeUpdate(sql);
				System.out.println("Rows affected: " + rowsAffected);
			} catch (SQLException e){
				System.out.println(e);
				JOptionPane.showMessageDialog(null, e);
			}

			try {
				Reporte03(userSes);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e);
			}
		}

	}



	public  void Reporte03(String userSes) throws JRException {

    	//indico la ruta del objeto a cargar
    	JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\rotuloEnvasado.jasper");

    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
    	Map<String, Object> parametros = new HashMap<>();
    	parametros.put("descripcion", datosAux[1]);
    	parametros.put("lote", datos[5]);
    	parametros.put("planta", domicilio);
    	parametros.put("operador", userSes);
    	parametros.put("vencimiento", datos[6]); // fechaVencFormateada
    	parametros.put("pallet", datos[0]);
    	parametros.put("cajas", tablaLectura.getValueAt(0, 4));

    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
    	JasperPrint jasperPrint = JasperFillManager.fillReport(
				reporte, parametros,
				new JREmptyDataSource());

		// Exporto en pdf luego de disparar la impresion
		JRPdfExporter exp = new JRPdfExporter();
		exp.setExporterInput(new SimpleExporterInput(jasperPrint));
		exp.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\MyReports\\Pallets\\"+datos[0]+".pdf"));
		SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
		exp.setConfiguration(conf);
		exp.exportReport();
		System.out.println("creacion de pdf iterada");
    	// --------- cambio orden porque sino al cancelar impresion tampoco se genera pdf

		// primero genero pdf y luego proceso a disparar impresion en papel
		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // false = hide_on_close
		jasperViewer.setVisible(false);
		JasperPrintManager.printReport( jasperPrint, false); // false = dispara impresion a impresora por default
														    //  true = abre ventana dialogo

	}


	public class EventoDeTeclado implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) { // para escuchar tecla al presionar
			numero = e.getKeyCode();
			//System.out.println(numero);
		}

		@Override
		public void keyReleased(KeyEvent e) { // para escucha tecla al soltar

		}

		@Override
		public void keyTyped(KeyEvent e) { // para escuchar que tecla fue presionada y soltada
			char letra = e.getKeyChar();
			System.out.println(letra);
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

}


