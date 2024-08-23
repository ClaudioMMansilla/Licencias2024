package productoTerminado;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Servicios.Services;


public class MovimientosStock {

	private Connection cn; //hago que el object cn sea accesible desde toda la clase.
	private Services servicio = new Services();
	private Statement stm;
	String tablaMovim = "pruebas_2022.reg_movim_productos";
//	String tablaMovim = "desarrollo.reg_movim_productos"; // testing

	private JTable tablaLectura;
	private JTextField TextFd_desde, TextFd_hasta;
	private String sentencia1, sentencia2, sentencia3, sentencia4;


	private final String pstConsulta1 = "SELECT fecha, hora, idProducto, producto, movimCantidad, movimUsuario, idCliente, razonSocial, comentario "
			+ "FROM "+tablaMovim+" WHERE movimTipo=? AND fecha =?"; // FINAL para que sea constante y no pueda ser modificada

	private String pstConsulta2 = "SELECT fecha, hora, idProducto, producto, movimCantidad, movimTipo, movimUsuario, idCliente, razonSocial, comentario "
			+ "FROM "+tablaMovim+" WHERE fecha >=? AND fecha <=?";

	public JFrame Ventana05;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		setDefaultLookAndFeelDecorated(true);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MovimientosStock window = new MovimientosStock();
					window.Ventana05.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}

	private static void setDefaultLookAndFeelDecorated(boolean b) {

	}

	/**
	 * Create the application.
	 */
	public MovimientosStock() {
		initComponents();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponents() {
		Ventana05 = new JFrame();
		Ventana05.setTitle("Consulta de movimientos");
		Ventana05.setBounds(100, 30, 1100, 600);
		Ventana05.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		Ventana05.setResizable(false);
		Ventana05.getContentPane().setLayout(null);
		setDefaultLookAndFeelDecorated(true);

		cn = null;
		Statement stm = null;
		ResultSet rs = null;

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		JLabel JLabel_TituloPanel = new JLabel("Consulta de movimientos: tienes que seleccionar tipo de consulta y per�odo a analizar");
		JLabel_TituloPanel.setBounds(25, 6, 490, 15);
		JLabel_TituloPanel.setFont(new java.awt.Font("Tw Cen MT", 0, 14));


		// Creacion del JComboBox y a�adir los items.
		JComboBox<String> comboBoxIZQ = new JComboBox<>();
		comboBoxIZQ.setBounds(119, 36, 125, 20);
		Ventana05.getContentPane().add(comboBoxIZQ);
		comboBoxIZQ.setFont(new java.awt.Font("Tw Cen MT", 0, 14));
		comboBoxIZQ.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box1 = (JComboBox)e.getSource();
				sentencia1 = (String) box1.getSelectedItem();
			}
		});

		// Creacion del JComboBox y a�adir los items.
		JComboBox<String> comboBoxDER = new JComboBox<>();
		comboBoxDER.setBounds(326, 36, 125, 20);
		Ventana05.getContentPane().add(comboBoxDER);
		comboBoxDER.setFont(new java.awt.Font("Tw Cen MT", 0, 14));
		comboBoxDER.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox box2 = (JComboBox)e.getSource();
				sentencia2 = (String) box2.getSelectedItem();
			}
		});

		try {
			//Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbase?serverTimezone=UTC", "root", "admin");
			//java.sql.Statement sentencia = miConexion.createStatement();

			cn = servicio.abrirConexion();
			stm = cn.createStatement();

			// -- CARGA DATOS EN COMBOX IZQUIERDO
			String consulta = "SELECT DISTINCTROW movimTipo FROM "+tablaMovim+" ";
			rs = stm.executeQuery(consulta);

			while(rs.next()) {
				comboBoxIZQ.addItem(rs.getString(1));
			}
			rs.close();

			// -- CARGA DATOS EN COMBOX DERECHO
			consulta = "SELECT DISTINCTROW fecha FROM "+tablaMovim+" WHERE fecha <= CURDATE() AND fecha >= DATE_ADD(CURDATE(), INTERVAL -30 DAY);";
			rs = stm.executeQuery(consulta);

			while(rs.next()) {
				comboBoxDER.addItem(rs.getString(1));
			}
			rs.close();

		}catch (Exception e2) {
			e2.printStackTrace();
		}

		JLabel JLabel1 = new JLabel("Movimiento");
		JLabel1.setBounds(35, 39, 74, 14);
		JLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 14));
		Ventana05.getContentPane().add(JLabel1);

		JLabel JLabel2 = new JLabel("Fecha");
		JLabel2.setBounds(273, 39, 43, 14);
		JLabel2.setFont(new java.awt.Font("Tw Cen MT", 1, 14));
		Ventana05.getContentPane().add(JLabel2);

		JLabel JLbl_informativo = new JLabel("Ingresa fecha en formato AAAA-MM-DD");
		JLbl_informativo.setBounds(34, 119, 230, 14);
		JLbl_informativo.setFont(new java.awt.Font("Tw Cen MT", 0, 14));
		Ventana05.getContentPane().add(JLbl_informativo);

		JLabel JLbl_desde = new JLabel("Desde");
		JLbl_desde.setBounds(298, 105, 50, 14);
		JLbl_desde.setFont(new java.awt.Font("Tw Cen MT", 1, 14));
		Ventana05.getContentPane().add(JLbl_desde);

		JLabel JLbl_hasta = new JLabel("Hasta");
		JLbl_hasta.setBounds(396, 105, 50, 14);
		JLbl_hasta.setFont(new java.awt.Font("Tw Cen MT", 1, 14));
		Ventana05.getContentPane().add(JLbl_hasta);

		//antes intente con JTextField TextFd_desde = new JTextField, pero de esta forma es una declaracion loca,
		//lo que retorna null al querer realizar getText desde otra parte del codigo.--
		TextFd_desde = new JTextField();
		TextFd_desde.setBounds(273, 118, 80, 15);
		TextFd_desde.setFont(new java.awt.Font("Tw Cen MT", 0, 12));
		Ventana05.getContentPane().add(TextFd_desde);
		TextFd_desde.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		//antes intente con JTextField TextFd_hasta = new JTextField, pero de esta forma es una declaracion loca,
		//lo que retorna null al querer realizar getText desde otra parte del codigo.--
		TextFd_hasta = new JTextField();
		TextFd_hasta.setBounds(371, 118, 80, 15);
		TextFd_hasta.setFont(new java.awt.Font("Tw Cen MT", 0, 12));
		Ventana05.getContentPane().add(TextFd_hasta);
		TextFd_hasta.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});


		JButton jb1 = new JButton();
		jb1.setBounds(470, 36, 114, 20);
		jb1.setText("Consulta rápida");
		Ventana05.getContentPane().add(jb1);
		jb1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				consultarBoxes();
			}
		});

		tablaLectura = new javax.swing.JTable();
		tablaLectura = new javax.swing.JTable();
		tablaLectura = new JTable();
		tablaLectura.setRowHeight(24);
		tablaLectura.setFont(new Font("Arial", 0, 12));
		tablaLectura.setForeground(Color.black);
		tablaLectura.setBackground(Color.white);
		tablaLectura.setShowVerticalLines(false);
		tablaLectura.setSurrendersFocusOnKeystroke(true);
		//        tablaLectura.setFont(new java.awt.Font("Tw Cen MT", 0, 12)); // NOI18N
		tablaLectura.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {

				},
				new String [] {

				}
				));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 159, 1050, 328);
		Ventana05.getContentPane().add(scrollPane);
		scrollPane.add(tablaLectura);
		scrollPane.setViewportView(tablaLectura);

		JButton jb2 = new JButton("Consultar");
		jb2.setBounds(471, 115, 109, 20);
		Ventana05.getContentPane().add(jb2);
		jb2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				consultarTextfield();
			}
		});

	}

	public void consultarBoxes() {

		ResultSet rs = null;

		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Fecha");
		modelo.addColumn("Hora");
		modelo.addColumn("Id");
		modelo.addColumn("Producto");
		modelo.addColumn("Cajas");
		modelo.addColumn("Usuario");
		modelo.addColumn("Id Cliente");
		modelo.addColumn("Razon Social");
		modelo.addColumn("Comentario");
		tablaLectura.setModel(modelo);

		String [] datos  = new String [9];

		//indico ancho de cada columna
		TableColumnModel columnModel = tablaLectura.getColumnModel();
		columnModel.setColumnMargin(10);
		columnModel.getColumn(0).setPreferredWidth(90);  //Fecha
		columnModel.getColumn(1).setPreferredWidth(70);  //Hora
		columnModel.getColumn(2).setPreferredWidth(50);  //Id
		columnModel.getColumn(3).setPreferredWidth(370); //Producto
		columnModel.getColumn(4).setPreferredWidth(70);  //Cajas
		columnModel.getColumn(5).setPreferredWidth(90); //Usuario
		columnModel.getColumn(6).setPreferredWidth(80);  //Id Cliente
		columnModel.getColumn(7).setPreferredWidth(280); //Razon Social
		columnModel.getColumn(8).setPreferredWidth(280); //Comentario

		//Instanciamos el TableRowSorter y lo a�adimos al JTable
		TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
		tablaLectura.setRowSorter(elQueOrdena);


		try {

			rs = null;

			PreparedStatement enviaConsulta = cn.prepareStatement(pstConsulta1); //pstConsulta por tipo y fecha.-
			enviaConsulta.setString(1, sentencia1);
			enviaConsulta.setString(2, sentencia2);

			rs = enviaConsulta.executeQuery();

			while(rs.next()) {
				System.out.println("entrando en while rs.next");
				datos[0] = rs.getString(1);
				datos[1] = rs.getString(2);
				datos[2] = rs.getString(3);
				datos[3] = rs.getString(4);
				datos[4] = rs.getString(5);
				datos[5] = rs.getString(6);
				datos[6] = rs.getString(7);
				datos[7] = rs.getString(8);
				datos[8] = rs.getString(9);
				modelo.addRow(datos);
				System.out.println("saliendo del while");
			}
			tablaLectura.setModel(modelo);

		}catch (Exception e2) {
			e2.printStackTrace();
		}
	}



	public void consultarTextfield() {

		ResultSet rs;

		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Fecha");
		modelo.addColumn("Hora");
		modelo.addColumn("Id");
		modelo.addColumn("Producto");
		modelo.addColumn("Cantidad");
		modelo.addColumn("Movimiento");
		modelo.addColumn("Usuario");
		tablaLectura.setModel(modelo);

		String [] datos  = new String [7];

		//indico ancho de cada columna
		TableColumnModel columnModel = tablaLectura.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(80);
		columnModel.getColumn(1).setPreferredWidth(60);
		columnModel.getColumn(2).setPreferredWidth(50);
		columnModel.getColumn(3).setPreferredWidth(350);
		columnModel.getColumn(4).setPreferredWidth(80);
		columnModel.getColumn(5).setPreferredWidth(70);
		columnModel.getColumn(6).setPreferredWidth(150);


		//Instanciamos el TableRowSorter y lo a�adimos al JTable
		TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
		tablaLectura.setRowSorter(elQueOrdena);


		try {

			rs = null;

			sentencia3 = TextFd_desde.getText();
			sentencia4 = TextFd_hasta.getText();

			if(sentencia3 != null && sentencia4 != null)
			{
				PreparedStatement enviaConsulta2 = cn.prepareStatement(pstConsulta2);
				enviaConsulta2.setString(1, sentencia3);//desde
				enviaConsulta2.setString(2, sentencia4);//hasta
				//primero se debe colocar el PST y lugeo abajo los setString

				rs = enviaConsulta2.executeQuery();
				System.out.println("linea post executeQuery");
				System.out.println(sentencia3);
				System.out.println(sentencia4);

				while(rs.next()) {
					System.out.println("entrando en while rs.next");
					datos[0] = rs.getString(1);
					datos[1] = rs.getString(2);
					datos[2] = rs.getString(3);
					datos[3] = rs.getString(4);
					datos[4] = rs.getString(5);
					datos[5] = rs.getString(6);
					datos[6] = rs.getString(7);
					modelo.addRow(datos);
					System.out.println("saliendo del while");
				}
				tablaLectura.setModel(modelo);
			}


		}catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "Se ha producido un error: \n"+e2);
		}

	}

}
