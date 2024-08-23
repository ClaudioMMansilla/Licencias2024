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
import javax.swing.table.DefaultTableModel;

//import reportes.HojaRuta;
//import EventosGUI.EventoDeTeclado;
//import envasadoUnificado.TextPrompt;
import com.toedter.calendar.JDateChooser;

import Atxy2k.CustomTextField.RestrictedTextField;
import EventosGUI.TextPrompt;


@SuppressWarnings("serial")
public class ListarHojaRuta_View extends JFrame {

	private JPanel contentPane;
	public JTable tablaLectura;
	public String [] datos;
//	public List<HojaRuta> listItems;
	public int rowsCounter;
	private JProgressBar progressBar;
	private static boolean isVisible = false;
	public JDateChooser calendarSince = new JDateChooser();
	public JDateChooser calendarTo = new JDateChooser();
	public JComboBox<String> cmbEstado;
	public JButton btnSelect;
	public JButton btnLoad;
	public JButton btnPrint;
	public JButton btnSearch;
	public TextPrompt placeholder;
	public JTextField txtId;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ListarHojaRuta_View frame = new ListarHojaRuta_View(isVisible);
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
	public ListarHojaRuta_View(boolean isVisible) {

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 10, 606, 538);
		setTitle("Listar hojas de ruta ");
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

		btnSelect = new JButton("Consultar");
		btnSelect.setBounds(104, 96, 90, 25);
		contentPane.add(btnSelect);


		JLabel lblDateSince = new JLabel("Fecha Desde:");
		lblDateSince.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDateSince.setBounds(22, 23, 72, 20);
		contentPane.add(lblDateSince);

		JLabel lblDateTo = new JLabel("Fecha Hasta:");
		lblDateTo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDateTo.setBounds(22, 42, 72, 20);
		contentPane.add(lblDateTo);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEstado.setBounds(51, 64, 43, 20);
		contentPane.add(lblEstado);

		calendarSince = new JDateChooser();
		calendarSince.setFont(new Font("Tahoma", Font.PLAIN, 11));
		calendarSince.setDateFormatString("dd/MM/yyyy");
		calendarSince.setBounds(104, 21, 90, 20);
		contentPane.add(calendarSince);

        calendarTo.setDateFormatString("dd/MM/yyyy");
        calendarTo.setFont(new Font("Tahoma", Font.PLAIN, 11));
        calendarTo.setBounds(104, 42, 90, 20);
        contentPane.add(calendarTo);

		cmbEstado = new JComboBox<>();
		cmbEstado.setBounds(104, 63, 192, 22);
		cmbEstado.setFont(new java.awt.Font("Tw Cen MT", 1, 14));
		contentPane.add(cmbEstado);


		txtId = new JTextField();
		txtId.setBounds(492, 23, 72, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(txtId);
		fieldIdRestringido.setOnlyNums(true);
	    placeholder = new TextPrompt("Nro hoja ruta", txtId);
	    placeholder.setFont(new Font("Tahoma", Font.PLAIN, 8));
	    placeholder.changeAlpha(0.75f);

		btnLoad = new JButton("");
		btnLoad.setIcon(new ImageIcon("C:\\MyReports\\images\\btnSearch.png"));
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnLoad.setBounds(492, 58, 29, 26);
		//btnCargar.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		//contentPane.add(btnCargar);


		btnPrint = new JButton("");
		btnPrint.setIcon(new ImageIcon("C:\\MyReports\\images\\btnPrint.png"));
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnPrint.setBounds(535, 45, 29, 26);
		//btnCargar.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		contentPane.add(btnPrint);

		btnSearch = new JButton("");
		btnSearch.setBounds(492, 47, 29, 26);
		btnSearch.setIcon(new ImageIcon("C:\\MyReports\\images\\btnSearch.png"));
		contentPane.add(btnSearch);

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
