package hojaDeRuta;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import envasadoUnificado2024.TextPrompt;

public class BuscarHojaRuta extends JFrame {

	private JPanel contentPane;
	private static boolean isUpdate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					BuscarHojaRuta frame = new BuscarHojaRuta(BuscarHojaRuta.isUpdate);
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
	public BuscarHojaRuta(boolean isUpdate) {
		setTitle("Buscar ");
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 194, 132);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);


		JTextField txtId = new JTextField();
		txtId.setBounds(32, 40, 68, 26);
		contentPane.add(txtId);
		txtId.setColumns(10);
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(txtId);
		//fieldIdRestringido.setLimit(4);
		fieldIdRestringido.setOnlyNums(true);
		// fin lib importada
	    TextPrompt placeholder = new TextPrompt("Ingrese nro", txtId);
	    placeholder.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    placeholder.changeAlpha(0.75f);


		txtId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(txtId.getText());
				hojaDeRuta.RecuperarHojaRuta_Controller ctrl = new hojaDeRuta.RecuperarHojaRuta_Controller(id, isUpdate);
				dispose();
			}
		});

		JButton btnCargar = new JButton("");
		btnCargar.setIcon(new ImageIcon("C:\\MyReports\\images\\btnSearch.png"));
		btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnCargar.setBounds(116, 40, 29, 26);
		//btnCargar.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		contentPane.add(btnCargar);
		btnCargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(txtId.getText());
				hojaDeRuta.RecuperarHojaRuta_Controller ctrl = new hojaDeRuta.RecuperarHojaRuta_Controller(id, isUpdate);
				dispose();
			}
		});

	}
}
