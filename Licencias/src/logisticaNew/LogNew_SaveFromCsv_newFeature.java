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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Servicios.Services;
import reportes.HojaRuta;


@SuppressWarnings("serial")
public class LogNew_SaveFromCsv_newFeature extends JFrame {

//	String tablaProductos = "pruebas_2022.productos";
	String tablaProductos = "testing.productos_prueba";
	String tablaRegistros = "testing.reg_movim_prueba";

	private Services servicio = new Services();

    private static String userSes;
	private JPanel contentPane;
	private JTable tablaLectura;
	private int numero;
	private char letra;
	public String [] datos;
	public List<HojaRuta> listItems;
	private File archivoSeleccionado;
	public int rowsCounter;
	private static String pathOrigen = null;
	private int listSize = 0;
	private JLabel lblCount = new JLabel();
	private JLabel lblSize = new JLabel();
	private JProgressBar progressBar;
	private int itProgressBar;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LogNew_SaveFromCsv_newFeature frame = new LogNew_SaveFromCsv_newFeature("Admin");
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
	public LogNew_SaveFromCsv_newFeature(String userSes) {

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 970, 660);
		setTitle("INGRESAR HOJAS DE RUTA, REINTEGRAR AL STOCK ");
		contentPane = new JPanel();
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
		contentPane.setLayout(null);
		setResizable(false);
		setContentPane(contentPane);

    	addWindowListener(new Ventana()); //pongo la ventana en oyente
		EventoDeTeclado tecla=new EventoDeTeclado();
		addKeyListener(tecla);
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
		scrollPane.setBounds(10, 120, 934, 402);
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
						try {
							makeEmptyFile(archivoSeleccionado, archivoSeleccionado);
						} catch (IOException e1) {
							e1.printStackTrace();
						}


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
		jbCargar.setBounds(43, 58, 90, 25);
		contentPane.add(jbCargar);
		jbCargar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				archivoSeleccionado = chooseFile();
				//readFile(archivoSeleccionado);
				try {
					validateFileNotNull(archivoSeleccionado);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JLabel lblProgress = new JLabel("Subido: ");
		lblProgress.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProgress.setBounds(667, 58, 51, 30);
		contentPane.add(lblProgress);



	}

	public static File chooseFile() {

		File archivoSeleccionado;
		JFileChooser seleccionar = new JFileChooser();
		seleccionar.showOpenDialog(null);
		archivoSeleccionado = seleccionar.getSelectedFile();
		System.out.println("Path: "+seleccionar.getCurrentDirectory());
		pathOrigen = seleccionar.getCurrentDirectory().toString();
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

		BufferedReader br = null;
		String line = "";
		//Se define separador ","
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(pFile));
			br.readLine();

			while ((line = br.readLine()) != null)
			{
				rowsCounter++;
				datos = line.split(cvsSplitBy);
				modelo.addRow(datos);
			}

			if((line = br.readLine()) == null) {
				//JOptionPane.showMessageDialog(null, "El archivo está vacio, se eliminará automáticamente");
				br.close();
				deleteFile(archivoSeleccionado);
			}

			tablaLectura.setModel(modelo);

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

			listSize = listItems.size();


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

	    	Connection cn = Services.abrirConexionStatic();
	    	Statement stm;
	    	ResultSet rsLeer;

	    	if(tablaLectura.getRowCount() > 0) {

	    		for(int i = 0; i < tablaLectura.getRowCount(); i++) {

	    			try {

	    				cn = servicio.abrirConexion();
	    				stm = cn.createStatement();
	    				rsLeer = stm.executeQuery("SELECT idProducto FROM "+tablaProductos+" "
	    						+ "where idProducto= '"+tablaLectura.getValueAt(i, 0)+"' ");

	    				while(rsLeer.next())
	    				{

	    					try
	    					{

//	    						cn = Conexion.Conectar();
//	    						stm = cn.createStatement();
//	    						String sql = "INSERT INTO "+tablaRegistros+" "
//	    								+"(fecha, hora, idProducto, producto, movimCantidad, movimTipo) "
//	    								+"VALUES("
//	    								+ "now(), "
//	    								+ "now(), "
//	    								+ "'"+tablaLectura.getValueAt(i, 0)+"', "
//	    								+ "'"+tablaLectura.getValueAt(i, 1)+"', "
//	    								+ "'"+tablaLectura.getValueAt(i, 2)+"', "
//	    								+ "'Ingreso'"
//	    								+ ")";

	    						String sql = "INSERT INTO "+tablaRegistros+" "
	    								+"(fecha, hora, idProducto, producto, movimCantidad, movimTipo) "
	    								+"VALUES(now(), now(), ?, ?, ?, ?)";



	    						try {
	    							PreparedStatement prST = cn.prepareStatement(sql);
	    							prST.setString(1, (String) tablaLectura.getValueAt(i, 0));
	    							prST.setString(2, (String) tablaLectura.getValueAt(i, 1));
	    							prST.setString(3, (String) tablaLectura.getValueAt(i, 2));
	    							prST.setString(4, "Ingreso");

	    							prST.executeUpdate();
	    						} catch (SQLException e) {
	    							e.printStackTrace();
	    							JOptionPane.showMessageDialog(null, "Error ejecutando consulta \n" + e);
	    						}

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

	public void moveFile(String pathOrigen, String pathDestino) {

		//String path = "\\Documents\\Pedidos\\Hojas Subidas al stock";
		//File archivoSeleccionado;
		//JFileChooser seleccionar = new JFileChooser();
		//seleccionar.showOpenDialog(null);
		//archivoSeleccionado = seleccionar.getSelectedFile();
		//System.out.println("Path: "+seleccionar.getCurrentDirectory());
		//path = seleccionar.getCurrentDirectory().toString();

        Path origenPath = FileSystems.getDefault().getPath(pathOrigen);
        Path destinoPath = origenPath.getFileName();

        try {
            //Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println(e);
        }
	}

	public void makeEmptyFile(File sourceFile, File destFile) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(sourceFile);
            out = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            in.close();
            out.close();
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


	public void deleteFile(File file) {
	    File myObj = file;
	    if (myObj.delete()) {
	      System.out.println("Deleted the file: " + myObj.getName());
	    } else {
	      System.out.println("Failed to delete the file.");
	    }
	}

	public void validateFileNotNull(File pFile) throws IOException {

		BufferedReader br = null;
		br = new BufferedReader(new FileReader(pFile));

		if((br.readLine()) == null) {
			JOptionPane.showMessageDialog(null, "El archivo está vacio, se eliminará automáticamente");
			br.close();
			deleteFile(archivoSeleccionado);
			dispose();
		}
		else {
			readFile(pFile);
		}

	}
}
