package chooseView;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import hojaDeRuta.BuscarHojaRuta;


@SuppressWarnings("serial")
public class ChooseView_ModificarHRuta extends JFrame {

	private JPanel contentPane;
	public JButton btnUpdate = new JButton("Actualizar");
	public JButton btnDelete = new JButton("Eliminar");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ChooseView_ModificarHRuta frame = new ChooseView_ModificarHRuta();
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
	public ChooseView_ModificarHRuta() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(450, 190, 500, 354);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel mensajeUsuario = new JLabel("Seleccione acción a realizar");
        mensajeUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
        mensajeUsuario.setBounds(38, 59, 422, 51);
        contentPane.add(mensajeUsuario);

        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnUpdate.setBounds(38, 146, 196, 70);
        contentPane.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	//new Stock_InputBudines(userSes).setVisible(true);
            	BuscarHojaRuta search = new BuscarHojaRuta(true);
            	search.setVisible(true);
            	dispose();
            }
        });

        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnDelete.setBounds(264, 146, 196, 70);
        contentPane.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	BuscarHojaRuta search = new BuscarHojaRuta(false);
            	search.setVisible(true);
            	dispose();
            }
        });

	}
}
