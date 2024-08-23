package informeProduccion;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

public class InfoMovimPT_View extends JFrame {

	public JPanel contentPane;
	public String tituloContent = " INFORME CRUZADO: PRODUCCION - LOGISTICA ";

	public JDateChooser calendarFrom = new JDateChooser();
	public JDateChooser calendarTo = new JDateChooser();
	public JButton btnImprimir;
	public JButton btnConsultar;
	public JRadioButton rdbtnBudin = new JRadioButton();
	public JRadioButton rdbtnPDulce = new JRadioButton();
	public JRadioButton rdbtnTotal = new JRadioButton();
	public ButtonGroup rdbGroup = new ButtonGroup();

	public JTable tablaLectura;
	public JTextField txtProduccion;
	public JTextField txtLogistica;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					InfoMovimPT_View frame = new InfoMovimPT_View();
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
	public InfoMovimPT_View() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 30, 900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(tituloContent));
		contentPane.setLayout(null);
		setResizable(false);
		setContentPane(contentPane);

	    calendarFrom.setDateFormatString("dd/MM/yyyy");
	    calendarFrom.setFont(new Font("Tahoma", Font.BOLD, 12));
	    calendarFrom.setBounds(52, 50, 118, 25);
	    contentPane.add(calendarFrom);

	    calendarTo.setDateFormatString("dd/MM/yyyy");
	    calendarTo.setFont(new Font("Tahoma", Font.BOLD, 12));
	    calendarTo.setBounds(188, 50, 118, 25);
	    contentPane.add(calendarTo);

	    btnConsultar = new JButton("Consultar");
	    btnConsultar.setBounds(656, 50, 89, 25);
	    //contentPane.add(btnConsultar);

	    btnImprimir = new JButton("Imprimir");
	    btnImprimir.setBounds(755, 50, 89, 25);
	    contentPane.add(btnImprimir);

	    JLabel lblFijoProd = new JLabel("Total Producción:");
	    lblFijoProd.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblFijoProd.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblFijoProd.setBounds(52, 99, 133, 22);
	    contentPane.add(lblFijoProd);

	    JLabel lblFijoEgresos = new JLabel("Total Entregado:");
	    lblFijoEgresos.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblFijoEgresos.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblFijoEgresos.setBounds(331, 99, 133, 22);
	    contentPane.add(lblFijoEgresos);


		tablaLectura = new javax.swing.JTable();
		tablaLectura = new javax.swing.JTable();
		tablaLectura = new JTable();
		tablaLectura.setRowHeight(24);
		tablaLectura.setFont(new Font("Arial", 0, 12));
		tablaLectura.setForeground(Color.black);
		tablaLectura.setBackground(Color.white);
		tablaLectura.setShowVerticalLines(false);
		tablaLectura.setSurrendersFocusOnKeystroke(true);
		tablaLectura.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {},
				new Object [] {}
				));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 148, 864, 402);
		contentPane.add(scrollPane);
		scrollPane.add(tablaLectura);
		scrollPane.setViewportView(tablaLectura);

		rdbtnBudin.setFont(new Font("Tw Cen MT", 1, 14)); // NOI18N
        rdbtnBudin.setText("Budines");
		rdbtnBudin.setBounds(331, 50, 95, 25);
		contentPane.add(rdbtnBudin);
		rdbGroup.add(rdbtnBudin);


		rdbtnPDulce.setFont(new Font("Tw Cen MT", 1, 14)); // NOI18N
		rdbtnPDulce.setText("Pan Dulces");
		rdbtnPDulce.setBounds(428, 50, 95, 25);
		contentPane.add(rdbtnPDulce);
		rdbGroup.add(rdbtnPDulce);

		rdbtnTotal.setFont(new Font("Tw Cen MT", 1, 14)); // NOI18N
		rdbtnTotal.setText("Totales");
		rdbtnTotal.setBounds(525, 50, 95, 25);
		contentPane.add(rdbtnTotal);
		rdbGroup.add(rdbtnTotal);

		txtProduccion = new JTextField();
		txtProduccion.setBackground(new Color(255, 255, 224));
		txtProduccion.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtProduccion.setText("0");
		txtProduccion.setForeground(new Color(0, 0, 205));
		txtProduccion.setBounds(188, 99, 118, 22);
		txtProduccion.setColumns(10);
		contentPane.add(txtProduccion);

		txtLogistica = new JTextField();
		txtLogistica.setBackground(new Color(255, 255, 224));
		txtLogistica.setText("0");
		txtLogistica.setForeground(new Color(220, 20, 60));
		txtLogistica.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtLogistica.setColumns(10);
		txtLogistica.setBounds(466, 99, 118, 22);
		contentPane.add(txtLogistica);

	}
}
