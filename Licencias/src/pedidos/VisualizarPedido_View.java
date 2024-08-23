package pedidos;

//package LogisticaNew;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

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

import Atxy2k.CustomTextField.RestrictedTextField;
import EventosGUI.TextPrompt;


@SuppressWarnings("serial")
public class VisualizarPedido_View extends JFrame {

	private JPanel contentPane;
	private JTable tablaLectura;
	public String [] datos;
//	public List<HojaRuta> listItems;
	public int rowsCounter;
	private JProgressBar progressBar;
	public JTextField txtNroPedido;
	private JTextField txtRazonSocial;
	private JTextField txtCantidad;
	private JTextField txtImporte;
	private static boolean isVisible = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VisualizarPedido_View frame = new VisualizarPedido_View(isVisible);
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
	public VisualizarPedido_View(boolean isVisible) {

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 606, 538);
		setTitle("Visualizacion de pedido ");
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

		JButton jbGuardar = new JButton("Guardar");
		jbGuardar.setBounds(474, 27, 90, 25);
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

		JButton jbCargar = new JButton("Cargar");
		jbCargar.setBounds(118, 27, 90, 25);
		contentPane.add(jbCargar);
		jbCargar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
//				pedido = pedidoCtrl.getPedido(
//						Fecha.parserStringToInt(txtNroPedido.getText())
//						);

//				sendDataToTable();
//				setDataToTxtRazonSocial(pedido);
			}
		});

		txtNroPedido = new JTextField();
		txtNroPedido.setBounds(22, 29, 86, 23);
		contentPane.add(txtNroPedido);
		txtNroPedido.setColumns(10);
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(txtNroPedido);
		fieldIdRestringido.setOnlyNums(true);
	    TextPrompt placeholder = new TextPrompt("Ingrese nro del pedido", txtNroPedido);
	    placeholder.setFont(new Font("Tahoma", Font.PLAIN, 7));
	    placeholder.changeAlpha(0.75f);


		JLabel lblRazonSocial = new JLabel("Razon Social");
		lblRazonSocial.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRazonSocial.setBounds(22, 78, 86, 20);
		contentPane.add(lblRazonSocial);

		txtRazonSocial = new JTextField();
		txtRazonSocial.setEnabled(false);
		txtRazonSocial.setEditable(false);
		txtRazonSocial.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtRazonSocial.setBounds(118, 79, 446, 20);
		contentPane.add(txtRazonSocial);
		txtRazonSocial.setColumns(10);

		txtCantidad = new JTextField();
		txtCantidad.setEnabled(false);
		txtCantidad.setEditable(false);
		txtCantidad.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtCantidad.setColumns(10);
		txtCantidad.setBounds(118, 116, 90, 20);
		contentPane.add(txtCantidad);

		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantidad.setBounds(46, 116, 62, 20);
		contentPane.add(lblCantidad);

		JLabel lblImporte = new JLabel("Importe");
		lblImporte.setVisible(isVisible);
		lblImporte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblImporte.setBounds(351, 116, 62, 20);
		contentPane.add(lblImporte);

		txtImporte = new JTextField();
		txtImporte.setVisible(isVisible);
		txtImporte.setEnabled(false);
		txtImporte.setEditable(false);
		txtImporte.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtImporte.setColumns(10);
		txtImporte.setBounds(423, 116, 141, 20);
		contentPane.add(txtImporte);

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
