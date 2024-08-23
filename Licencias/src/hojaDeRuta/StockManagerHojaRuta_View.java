package hojaDeRuta;

//package LogisticaNew;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import Atxy2k.CustomTextField.RestrictedTextField;
import EventosGUI.TextPrompt;

@SuppressWarnings("serial")
public class StockManagerHojaRuta_View extends JFrame {

	private JPanel contentPane;
	public JTable tablaLectura;
	public String [] datos;
//	public List<HojaRuta> listItems;
	public int rowsCounter;
	private JProgressBar progressBar;
	public JTextField txtNroPedido;
	public JTextField txtFechaEmision;
	public JTextField txtChofer;
	public JTextField txtVehiculo;
	private static boolean isVisible = false;
	public JTextField txtFechaProgramada;
	public JTextField txtEstadoActual;
	public JTextField txtCantidadTotal;
	public JComboBox<String> cmbNuevoEstado;
	public JButton btnGuardar;
	public JButton btnCargar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					StockManagerHojaRuta_View frame = new StockManagerHojaRuta_View();
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
	public StockManagerHojaRuta_View() {

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 606, 538);
		setTitle("Visualizacion de hoja de ruta ");
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
		scrollPane.setBounds(22, 147, 542, 322);
		contentPane.add(scrollPane);
		scrollPane.add(tablaLectura);
		scrollPane.setViewportView(tablaLectura);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(492, 36, 72, 25);
		contentPane.add(btnGuardar);
		btnGuardar.addActionListener(new ActionListener(){
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
//					Controller_SQL objetoAuxiliar = new Controller_SQL();
//					try {
//						objetoAuxiliar.updateDB_tablaProductos(userSes);
//						report(pedido);
//
//					} catch (UnknownHostException | SQLException e1) {
//						e1.printStackTrace();
//						JOptionPane.showMessageDialog(null, e1);
//					}
					System.out.println("Pressed YES");

				} else if (res == 1) {
					System.out.println("Pressed NO");
					JOptionPane.showMessageDialog(null, "La acción ha sido cancelada");

				} else {
					System.out.println("Pressed CANCEL");
				}

			}
		});

		txtNroPedido = new JTextField();
		txtNroPedido.setBounds(22, 13, 96, 23);
		contentPane.add(txtNroPedido);
		txtNroPedido.setColumns(10);
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(txtNroPedido);
		fieldIdRestringido.setOnlyNums(true);
	    TextPrompt placeholder = new TextPrompt("Ingrese nro hoja de ruta", txtNroPedido);
	    placeholder.setFont(new Font("Tahoma", Font.PLAIN, 7));
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

		txtChofer = new JTextField();
		txtChofer.setEnabled(false);
		txtChofer.setEditable(false);
		txtChofer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtChofer.setColumns(10);
		txtChofer.setBounds(372, 95, 192, 20);
		contentPane.add(txtChofer);

		JLabel lblChofer = new JLabel("Chofer");
		lblChofer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblChofer.setBounds(320, 95, 43, 20);
		contentPane.add(lblChofer);

		JLabel lblVehiculo = new JLabel("Vehiculo");
//		lblVehiculo.setVisible(isVisible);
		lblVehiculo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVehiculo.setBounds(312, 116, 43, 20);
		contentPane.add(lblVehiculo);

		txtVehiculo = new JTextField();
//		txtVehiculo.setVisible(isVisible);
		txtVehiculo.setEnabled(false);
		txtVehiculo.setEditable(false);
		txtVehiculo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtVehiculo.setColumns(10);
		txtVehiculo.setBounds(372, 116, 192, 20);
		contentPane.add(txtVehiculo);

		JLabel lblFechaProgramada = new JLabel("Fecha Programada");
		lblFechaProgramada.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFechaProgramada.setBounds(22, 95, 96, 20);
		contentPane.add(lblFechaProgramada);

		txtFechaProgramada = new JTextField();
		txtFechaProgramada.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtFechaProgramada.setEnabled(false);
		txtFechaProgramada.setEditable(false);
		txtFechaProgramada.setColumns(10);
		txtFechaProgramada.setBounds(130, 95, 90, 20);
		contentPane.add(txtFechaProgramada);

		JLabel lblEstadoActual = new JLabel("Estado Actual ");
		lblEstadoActual.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEstadoActual.setBounds(22, 50, 78, 20);
		contentPane.add(lblEstadoActual);

		txtEstadoActual = new JTextField();
		txtEstadoActual.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtEstadoActual.setEnabled(false);
		txtEstadoActual.setEditable(false);
		txtEstadoActual.setColumns(10);
		txtEstadoActual.setBounds(130, 50, 192, 20);
		contentPane.add(txtEstadoActual);

		JLabel lblCantidad = new JLabel("Cantidad Total");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCantidad.setBounds(22, 116, 78, 20);
		contentPane.add(lblCantidad);

		txtCantidadTotal = new JTextField();
		txtCantidadTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtCantidadTotal.setEnabled(false);
		txtCantidadTotal.setEditable(false);
		txtCantidadTotal.setColumns(10);
		txtCantidadTotal.setBounds(130, 116, 90, 20);
		contentPane.add(txtCantidadTotal);

		JLabel lblNuevoEstado = new JLabel("Nuevo Estado");
		lblNuevoEstado.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNuevoEstado.setBounds(285, 15, 78, 20);
		contentPane.add(lblNuevoEstado);

		cmbNuevoEstado = new JComboBox();
		cmbNuevoEstado.setBounds(372, 12, 192, 22);
		contentPane.add(cmbNuevoEstado);
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
