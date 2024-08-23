package envasadoUnificado2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import EventosGUI.EventoDeTeclado;



@SuppressWarnings("serial")
public class Vencimiento_View extends JFrame {

	private JPanel contentPane;

	public JTextField field_ingresoVto;
	public JLabel lbl_detallePlanta;
	public JLabel lbl_fechaCalculada;
	public JButton btn_volver;
	public JButton btn_avanzar;

	private EventoDeTeclado tecla=new EventoDeTeclado();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Vencimiento_View frame = new Vencimiento_View();
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
	public Vencimiento_View() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(450, 190, 500, 354);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

		addKeyListener(tecla);
        contentPane.addKeyListener(tecla);

        JLabel lbl_mensajePpal = new JLabel("Indicar fecha de vencimiento del producto");
        lbl_mensajePpal.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbl_mensajePpal.setBounds(103, 84, 304, 27);
        contentPane.add(lbl_mensajePpal);

        JLabel lbl_meses = new JLabel("Cantidad Meses: ");
        lbl_meses.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbl_meses.setBounds(32, 122, 100, 30);
        contentPane.add(lbl_meses);

        field_ingresoVto = new JTextField();
        field_ingresoVto.setBounds(135,122,50,30);
        field_ingresoVto.setFont(new java.awt.Font("Tw Cen MT", 0, 24));
		contentPane.add(field_ingresoVto);
		// libreria importada de internet - L132 to L135
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(field_ingresoVto);
		fieldIdRestringido.setLimit(2);
		fieldIdRestringido.setOnlyNums(true);
	    TextPrompt placeholder = new TextPrompt("99", field_ingresoVto);
	    placeholder.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    placeholder.changeAlpha(0.75f);
	    //placeholder.changeStyle(Font.ITALIC);
	    field_ingresoVto.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
//				if (codigo == 10) {
//					JBTCargarActionPerformed(e);
//				}
//				else
//				{
//					System.out.println("saliendo del if en field.id"+ codigo);
//				}
			}
		});


		JLabel lbl_vtoCalculado = new JLabel("Vencimiento:");
		lbl_vtoCalculado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_vtoCalculado.setBounds(239, 122, 78, 30);
		contentPane.add(lbl_vtoCalculado);

		JLabel lbl_planta = new JLabel("Planta: ");
		lbl_planta.setBounds(103, 24, 50, 27);
		contentPane.add(lbl_planta);

		lbl_detallePlanta = new JLabel("(sin info)");
		lbl_detallePlanta.setForeground(new Color(255, 128, 64));
		lbl_detallePlanta.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_detallePlanta.setBounds(163, 18, 189, 33);
		contentPane.add(lbl_detallePlanta);

		lbl_fechaCalculada = new JLabel("01/01/0000");
		lbl_fechaCalculada.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_fechaCalculada.setForeground(new Color(255, 128, 64));
		lbl_fechaCalculada.setBounds(327, 116, 146, 37);
		contentPane.add(lbl_fechaCalculada);

		btn_avanzar = new JButton("Avanzar");
		btn_avanzar.setBounds(374, 256, 89, 30);
		contentPane.add(btn_avanzar);

		btn_volver = new JButton("Atrás");
		btn_volver.setBounds(26, 256, 89, 30);
		contentPane.add(btn_volver);

	}

}
