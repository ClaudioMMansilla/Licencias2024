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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import conector.Conexion;
import envasadoUnificado2024.Services;
import reportes.HojaRuta;


@SuppressWarnings("serial")
public class LogNew_LoadFromCsv_old extends JFrame {

	String tablaProductos = "pruebas_2022.productos";
//	String tablaProductos = "desarrollo.productos";

	private Services servicio = new Services();

    private static String userSes;
	private JPanel contentPane;
	private JTable tablaLectura;
	private DefaultTableModel modelo;
	private int numero;
	private char letra;
	public String [] datos;
	public List<HojaRuta> listItems;
	private File archivoSeleccionado;
	public int rowsCounter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LogNew_LoadFromCsv_old frame = new LogNew_LoadFromCsv_old(userSes);
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
	public LogNew_LoadFromCsv_old(String userSes) {

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 970, 660);
		setTitle("07 - Logistica: importar archivo.csv ");
		contentPane = new JPanel();
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" CARGA HOJAS DE RUTA - EGRESO DE STOCK "));
		contentPane.setLayout(null);
		setResizable(false);
		setContentPane(contentPane);

    	addWindowListener(new Ventana()); //pongo la ventana en oyente
		EventoDeTeclado tecla=new EventoDeTeclado();
		addKeyListener(tecla);
		contentPane.addKeyListener(tecla);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		scrollPane.setBounds(10, 120, 934, 402);
		contentPane.add(scrollPane);
		scrollPane.add(tablaLectura);
		scrollPane.setViewportView(tablaLectura);

		JButton jbGuardar = new JButton("Guardar");
		jbGuardar.setBounds(800, 58, 90, 25);
		contentPane.add(jbGuardar);
		jbGuardar.addActionListener(new ActionListener(){
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
			    	  Controller_SQL objetoAuxiliar = new Controller_SQL();
			    	  try {
						objetoAuxiliar.updateDB_tablaProductos(userSes);
					} catch (UnknownHostException | SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1);
					}
			    	  System.out.println("Pressed YES");

			      } else if (res == 1) {
			         System.out.println("Pressed NO");
			         JOptionPane.showMessageDialog(null, "La acci�n ha sido cancelada");

			      } else {
			         System.out.println("Pressed CANCEL");
			      }

			}
		});

		JButton jbCargar = new JButton("Cargar");
		jbCargar.setBounds(43, 58, 90, 25);
		contentPane.add(jbCargar);
		jbCargar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				archivoSeleccionado = chooseFile();
				readFile(archivoSeleccionado);
			}
		});


	}

	public static File chooseFile() {

		String path = null;
		File archivoSeleccionado;
		JFileChooser seleccionar = new JFileChooser();
		seleccionar.showOpenDialog(null);
		archivoSeleccionado = seleccionar.getSelectedFile();
		System.out.println("Path: "+seleccionar.getCurrentDirectory());
		path = seleccionar.getCurrentDirectory().toString();
		return archivoSeleccionado;
	}

	public void readFile(File pFile) {

		//id,name,lastname,price,flycode,typePassenger,statusFlight
		//idProd,producto,cantidad,idCliente,razonSocial
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Idº");
		modelo.addColumn("Producto");
		modelo.addColumn("Cajas");
		modelo.addColumn("Id Cliente");
		modelo.addColumn("Razon Social");
		modelo.addColumn("Comentarios");
		tablaLectura.setModel(modelo);

		//indico ancho de cada columna
		TableColumnModel columnModel = tablaLectura.getColumnModel();
		columnModel.setColumnMargin(10);
		columnModel.getColumn(0).setPreferredWidth(60);
		columnModel.getColumn(1).setPreferredWidth(380);
		columnModel.getColumn(2).setPreferredWidth(60);
		columnModel.getColumn(3).setPreferredWidth(60);
		columnModel.getColumn(4).setPreferredWidth(350);
		columnModel.getColumn(5).setPreferredWidth(350);

		//Instanciamos el TableRowSorter y lo a�adimos al JTable
		TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
		tablaLectura.setRowSorter(elQueOrdena);

//		String csvFile = "C:\\Users\\Matias\\Desktop\\Ficheros\\HR_1.csv";
		//String csvFile = directory;
		BufferedReader br = null;
		String line = "";
		String header = "";

		//Se define separador ","
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(pFile));
			header = br.readLine();

			while ((line = br.readLine()) != null)
			{
				rowsCounter++;
				datos = line.split(cvsSplitBy);
				modelo.addRow(datos);
			}

			tablaLectura.setModel(modelo);

			//List <PrintEnvasado> listItems= new ArrayList<PrintEnvasado>();
			listItems= new ArrayList<HojaRuta>();

			for (int i = 0; i<tablaLectura.getRowCount(); i++) {
				HojaRuta listado = new HojaRuta(
						tablaLectura.getValueAt(i, 0).toString(),
						tablaLectura.getValueAt(i, 1).toString(),
						tablaLectura.getValueAt(i, 2).toString(),
						tablaLectura.getValueAt(i, 3).toString(),
						tablaLectura.getValueAt(i, 4).toString(),
						tablaLectura.getValueAt(i, 5).toString());
				listItems.add(listado);
			}

			if(datos [0] == null) {
				JOptionPane.showMessageDialog(null, "No hay datos disponibles");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


	}


	public class Controller_SQL{

	    private void updateDB_tablaProductos(String userSes) throws SQLException, UnknownHostException {


	    	Conexion conexion = new Conexion();
	    	Connection cn = null;
	    	Statement stm;
	    	ResultSet rsLeer;

	    	int stockDBase;
	    	int cantidadTable;
	    	int stockFinal;

	    	if(tablaLectura.getRowCount() > 0) {

	    		for(int i = 0; i < tablaLectura.getRowCount(); i++) {

	    			try {

	    				cn = servicio.abrirConexion();
	    				stm = cn.createStatement();
	    				rsLeer = stm.executeQuery("SELECT stock FROM "+tablaProductos+" "
	    						+ "where idProducto= '"+tablaLectura.getValueAt(i, 0)+"' ");

	    				while(rsLeer.next()) {
	    					stockDBase = rsLeer.getInt("stock");
	    					cantidadTable = Integer.parseInt((String) tablaLectura.getValueAt(i, 2));
	    					stockFinal = stockDBase - cantidadTable;

	    					try {
//	    						cn = Conexion.Conectar();
	    						stm = cn.createStatement();
	    						String sql = "UPDATE "+tablaProductos+" "
	    								+ "set stock ='"+stockFinal+"', "
	    								+ "movimUsuario ='"+userSes+"', "
	    								+ "movimTipo ='Egreso', "
	    								+ "movimCantidad ='"+cantidadTable+"', "
	    								+ "idCliente ='"+tablaLectura.getValueAt(i, 3)+"', "
	    								+ "razonSocial ='"+tablaLectura.getValueAt(i, 4)+"', "
	    								+ "comentario ='"+tablaLectura.getValueAt(i, 5)+"' "
	    								+ "where idProducto ='"+tablaLectura.getValueAt(i, 0)+"' ";

	    						int rowsAffected = stm.executeUpdate(sql);
	    						System.out.println("Rows affected: " + rowsAffected);

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

	    	dispose();
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

}
