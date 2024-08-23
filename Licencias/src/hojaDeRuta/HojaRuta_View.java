package hojaDeRuta;

//package LogisticaNew;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

//import reportes.HojaRuta;
//import EventosGUI.EventoDeTeclado;
//import envasadoUnificado.TextPrompt;
import com.toedter.calendar.JDateChooser;

import Atxy2k.CustomTextField.RestrictedTextField;
import EventosGUI.TextPrompt;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;


@SuppressWarnings("serial")
public class HojaRuta_View extends JFrame {

	private JPanel contentPane;
	public JTable tablaLectura;
	public String [] datos;
//	public List<HojaRuta> listItems;
	public int rowsCounter;
	private JProgressBar progressBar;
	public JTextField txtNroPedido;
	public JTextField txtFechaEmision;
	public JTextField txtCantidadTotal;
	private static boolean isVisible = false;
	public JDateChooser calendar = new JDateChooser();
	public JComboBox<String> cmbChofer;
	public JComboBox<String> cmbVehiculo;
	public JComboBox<String> cmbEstado;
	public JComboBox<String> cmbNewStatus;
	public JButton btnGuardar;
	public JButton btnCargar;
	public JButton btnQuitar;
	public JButton btnDelete;
	public JButton btnGuiatte;
	public TextPrompt placeholder;
	public JLabel lblNewStatus;
	public JCheckBox chbxOwnTruck;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					HojaRuta_View frame = new HojaRuta_View(isVisible);
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
	public HojaRuta_View(boolean isVisible) {

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 606, 580);
		setTitle("Emisión de hoja de ruta ");
		contentPane = new JPanel();
		//contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" DETALLE DEL PEDIDO "));
		contentPane.setLayout(null);
		setResizable(false);
		setContentPane(contentPane);

		addWindowListener(new Ventana()); //pongo la ventana en oyente
//		EventoDeTeclado tecla = new EventoDeTeclado();
//		this.addKeyListener(tecla);
//		contentPane.addKeyListener(tecla);

		progressBar = new JProgressBar();
		progressBar.setBounds(264, 11, 100, 25);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		//contentPane.add(progressBar);


		tablaLectura = new JTable();
		tablaLectura.setRowHeight(24);
		tablaLectura.setFont(new Font("Calibri", 0, 12));
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
		scrollPane.setBounds(22, 147, 542, 322);
		contentPane.add(scrollPane);
		scrollPane.add(tablaLectura);
		scrollPane.setViewportView(tablaLectura);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(474, 486, 90, 25);
		contentPane.add(btnGuardar);

		btnCargar = new JButton("");
		btnCargar.setForeground(new Color(255, 255, 255));
		btnCargar.setIcon(new ImageIcon("C:\\MyReports\\images\\btnAdd.png"));
		btnCargar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCargar.setBounds(130, 11, 28, 28);
		contentPane.add(btnCargar);

		btnQuitar = new JButton("");
		btnQuitar.setForeground(new Color(255, 255, 255));
		btnQuitar.setIcon(new ImageIcon("C:\\MyReports\\images\\btnDelete.png"));
		btnQuitar.setBounds(168, 11, 28, 28);
		contentPane.add(btnQuitar);

	    btnDelete = new JButton("Eliminar");
		btnDelete.setBounds(474, 486, 90, 25);
		contentPane.add(btnDelete);
		
		btnGuiatte = new JButton("Guias Tte");
		btnGuiatte.setBounds(356, 486, 90, 25);
		contentPane.add(btnGuiatte);

		txtNroPedido = new JTextField();
		txtNroPedido.setBounds(22, 13, 86, 23);
		contentPane.add(txtNroPedido);
		txtNroPedido.setColumns(10);
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(txtNroPedido);
		fieldIdRestringido.setOnlyNums(true);
	    placeholder = new TextPrompt("", txtNroPedido);
	    placeholder.setText("Nro Pedido...");
	    placeholder.setFont(new Font("Tahoma", Font.PLAIN, 9));
	    placeholder.changeAlpha(0.75f);

		JLabel lblFechaEmision = new JLabel("Fecha Emisión");
		lblFechaEmision.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFechaEmision.setBounds(22, 73, 72, 20);
		contentPane.add(lblFechaEmision);

		txtFechaEmision = new JTextField();
		txtFechaEmision.setEnabled(false);
		txtFechaEmision.setEditable(false);
		txtFechaEmision.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtFechaEmision.setBounds(130, 73, 90, 20);
		contentPane.add(txtFechaEmision);
		txtFechaEmision.setColumns(10);

		JLabel lblChofer = new JLabel("Chofer");
		lblChofer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblChofer.setBounds(319, 95, 43, 20);
		contentPane.add(lblChofer);

		JLabel lblVehiculo = new JLabel("Vehiculo");
//		lblVehiculo.setVisible(isVisible);
		lblVehiculo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVehiculo.setBounds(319, 116, 43, 20);
		contentPane.add(lblVehiculo);

		JLabel lblFechaProgramada = new JLabel("Fecha Programada");
		lblFechaProgramada.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFechaProgramada.setBounds(22, 95, 96, 20);
		contentPane.add(lblFechaProgramada);

		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEstado.setBounds(319, 73, 43, 20);
		contentPane.add(lblEstado);

		JLabel lblCantidad = new JLabel("Cantidad Total");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCantidad.setBounds(22, 116, 78, 20);
		contentPane.add(lblCantidad);

		txtCantidadTotal = new JTextField();
		txtCantidadTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtCantidadTotal.setEnabled(false);
		txtCantidadTotal.setColumns(10);
		txtCantidadTotal.setBounds(130, 116, 90, 20);
		contentPane.add(txtCantidadTotal);

        calendar.setDateFormatString("dd/MM/yyyy");
        calendar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        calendar.setBounds(130, 95, 90, 20);
        contentPane.add(calendar);

		cmbChofer = new JComboBox<>();
		cmbChofer.setBounds(372, 92, 192, 22);
		cmbChofer.setFont(new java.awt.Font("Tw Cen MT", 0, 14));
		contentPane.add(cmbChofer);

		cmbVehiculo = new JComboBox<>();
		cmbVehiculo.setBounds(372, 114, 192, 22);
		cmbVehiculo.setFont(new java.awt.Font("Tw Cen MT", 0, 14));
		contentPane.add(cmbVehiculo);

		cmbEstado = new JComboBox<>();
		cmbEstado.setEnabled(false);
		cmbEstado.setBounds(372, 70, 192, 22);
		cmbEstado.setFont(new java.awt.Font("Tw Cen MT", 1, 14));
		contentPane.add(cmbEstado);

		lblNewStatus = new JLabel("Nuevo Estado");
		lblNewStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewStatus.setBounds(284, 13, 78, 20);
		contentPane.add(lblNewStatus);

		cmbNewStatus = new JComboBox<>();
		cmbNewStatus.setFont(new Font("Tw Cen MT", Font.PLAIN, 14));
		cmbNewStatus.setBounds(372, 12, 192, 22);
		contentPane.add(cmbNewStatus);
		
		chbxOwnTruck = new JCheckBox("Retira de fábrica");
		chbxOwnTruck.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		chbxOwnTruck.setForeground(new Color(0, 128, 192));
		chbxOwnTruck.setBounds(372, 41, 192, 23);
		contentPane.add(chbxOwnTruck);
		

		//Code: 2024-04-09
        // Create a custom DocumentListener
        DocumentListener myListener = new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                // Handle changes (e.g., text pasted)
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                // Handle insertion (e.g., text pasted)
                // You can access the pasted text using documentEvent.getDocument().getText()
            	//System.out.println(txtNroPedido.getText());
            	//txtNroPedido = new JTextField();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                // Handle removal (e.g., text deleted)
            }
        };
        
        // Add the listener to the JTextField
        txtNroPedido.getDocument().addDocumentListener(myListener);
		
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
