package envasadoUnificado2024;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

//import javax.swing.border.TitledBorder;
import Atxy2k.CustomTextField.RestrictedTextField;
import EventosGUI.EventoDeTeclado;


@SuppressWarnings("serial")
public class Envasado_View extends JFrame {

	public JTextField field_id,
					  field_cantidad;

	public JButton JBT_guardar;
	public JButton JBT_LtoVtoView;

	// label del parte inferior, donde quedan en pantalla los datos ingresados
	public JLabel info_cantidad,
				  info_descripcion,
				  info_pallets;

	public JLabel label6, // imprime el error "campo id vacio"
				  label12, // opuesto a label6, aca se muestra la descripcion del producto
				  lbl_setearLote, // imprime el lote seteado
				  lbl_setearVenc; // imprime vencimiento seteado


	private EventoDeTeclado tecla=new EventoDeTeclado();
	private JPanel contentPane;

	//cierro declaracion de variables


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Envasado_View frame = new Envasado_View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return
	 */
	public Envasado_View() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" REGISTRO "));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setDefaultLookAndFeelDecorated(true);
        setBounds(30, 20, 900, 630);
        setResizable(false);
        contentPane.setBackground(Color.DARK_GRAY);


		addKeyListener(tecla);
        contentPane.addKeyListener(tecla);

		//Inicio creaci�n de componentes
		JLabel label1 = new JLabel("CARGA DE MERCADERIA A INFORME DE ENVASADO");
		label1.setBounds(25,26,617,23);
		label1.setFont(new Font ("Tw Cen MT", 1, 20));
		label1.setForeground(Color.white);
		contentPane.add(label1);

		JLabel label2 = new JLabel ("Id: ");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setBounds(25,60,63,50);
		label2.setFont(new Font ("Tw Cen MT",0,30));
		label2.setForeground(Color.white);
		contentPane.add(label2);

		JLabel label3 = new JLabel ("Producto: ");
		label3.setBounds(25,151,109,33);
		label3.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		label3.setForeground(Color.white);
		contentPane.add(label3);

		JLabel label4 = new JLabel ("Lote: ");
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		label4.setBounds(304,60,80,50);
		label4.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		label4.setForeground(Color.white);
		contentPane.add(label4);

		JLabel label5 = new JLabel ("Vto: ");
		label5.setHorizontalAlignment(SwingConstants.RIGHT);
		label5.setBounds(517,60,75,50);
		label5.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		label5.setForeground(Color.white);
		contentPane.add(label5);

		label6 = new JLabel (); //label que debe mostrar la descrip del producto
		label6.setBounds(25,184,857,49);
		label6.setFont(new Font("Arial", Font.BOLD, 26));
		label6.setForeground(Color.yellow);
		contentPane.add(label6);

		JLabel label7 = new JLabel ("CAJAS A INFORMAR: ");
		label7.setBounds(151,330,255,72);
		label7.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
		label7.setForeground(Color.white);
		contentPane.add(label7);

		field_id = new JTextField();
		field_id.setBounds(98,60,135,50);
		field_id.setFont(new java.awt.Font("Tw Cen MT", 0, 40));
		contentPane.add(field_id);
		// libreria importada de internet - L132 to L135
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(field_id);
		fieldIdRestringido.setLimit(4);
		fieldIdRestringido.setOnlyNums(true);
		// fin lib importada
		field_id.addKeyListener(tecla);


		lbl_setearLote = new JLabel (); // imprime lote seteado
		lbl_setearLote.setBounds(398,61,80,50);
		lbl_setearLote.setFont(new Font("Tw Cen MT", Font.PLAIN, 35));
		lbl_setearLote.setForeground(new Color(255, 128, 64));
		contentPane.add(lbl_setearLote);


		lbl_setearVenc = new JLabel (); // imprime lote seteado
		lbl_setearVenc.setBounds(611,60,224,50);
		lbl_setearVenc.setFont(new Font("Tw Cen MT", Font.PLAIN, 35));
		lbl_setearVenc.setForeground(new Color(255, 128, 64));
		contentPane.add(lbl_setearVenc);


		field_cantidad = new JTextField();
		field_cantidad.setBounds(441,330,150,72);
		field_cantidad.setFont(new java.awt.Font("Tw Cen MT", 0, 70));
		field_cantidad.setBackground(Color.white);
		contentPane.add(field_cantidad);
		// libreria importada de internet - L229 to L231
		RestrictedTextField fieldCantidadRestringido = new RestrictedTextField(field_cantidad);
		fieldCantidadRestringido.setLimit(3);
		fieldCantidadRestringido.setOnlyNums(true);
		// fin lib importada

		JBT_guardar = new JButton("Guardar");
		JBT_guardar.setBounds(710,330,141,72);
		JBT_guardar.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
		contentPane.add(JBT_guardar);

		JBT_LtoVtoView = new JButton("Ingreso manual");
		//JBT_LtoVtoView.setForeground(Color.BLUE);
		JBT_LtoVtoView.setBounds(710, 11, 141, 23);
		JBT_LtoVtoView.setFont(new Font("Tw Cen MT", Font.BOLD, 14));
		contentPane.add(JBT_LtoVtoView);

		JPanel panelINF =new JPanel();
		panelINF.setBounds(25, 435, 836, 145);
		contentPane.add(panelINF);
		panelINF.setBorder(javax.swing.BorderFactory.createTitledBorder(" ULTIMO INGRESO "));
		panelINF.setLayout(null);
		panelINF.setBackground(Color.BLACK);

		JLabel label11 = new JLabel ("Cantidad: ");
		label11.setBounds(549,20,118,41); //(138,239,70,15);
		label11.setFont(new Font("Tw Cen MT", 1, 24));
		label11.setForeground(Color.WHITE);
		panelINF.add(label11);

		JLabel label19 = new JLabel("Ultimo Pallet Liberado: ");
		label19.setHorizontalAlignment(SwingConstants.CENTER);
		label19.setForeground(Color.WHITE);
		label19.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
		label19.setBounds(23, 20, 275, 46);
		panelINF.add(label19);

		label12 = new JLabel (); //label que debe mostrar e
		label12.setBounds(15,164,820,49);
		label12.setFont(new Font("Arial",1,26));
		label12.setForeground(Color.red);
		contentPane.add(label12);

		info_cantidad = new JLabel ();
		info_cantidad.setBounds(692,20,118,41);
		info_cantidad.setFont(new Font("ARIAL",1,30));
		info_cantidad.setForeground(Color.CYAN);
		panelINF.add(info_cantidad);

		info_descripcion = new JLabel();
		info_descripcion.setForeground(Color.CYAN);
		info_descripcion.setFont(new Font("Arial", Font.BOLD, 26));
		info_descripcion.setBounds(23, 93, 803, 41);
		panelINF.add(info_descripcion);

		info_pallets = new JLabel();
		info_pallets.setForeground(Color.CYAN);
		info_pallets.setFont(new Font("Arial", Font.BOLD, 30));
		info_pallets.setBounds(323, 20, 102, 41);
		panelINF.add(info_pallets);

		//Cierro creacion de componentes

	}
}


