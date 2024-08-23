package actualizarPallet;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;


public class CambiarPallet_View extends JFrame {

	private JPanel contentPane;
	public JButton buttonA = new JButton("Tipo: Pan dulce");
	public JButton buttonB = new JButton("Tipo: Budines");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CambiarPallet_View frame = new CambiarPallet_View();
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
	public CambiarPallet_View() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(450, 190, 500, 354);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel mensajeUsuario = new JLabel("Seleccione tipo de producto");
        mensajeUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
        mensajeUsuario.setBounds(38, 59, 422, 51);
        contentPane.add(mensajeUsuario);

        //JButton buttonA = new JButton("Planta:   Pan dulce");
        buttonA.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonA.setBounds(38, 146, 196, 70);
        contentPane.add(buttonA);

        //JButton buttonB = new JButton("Planta:   Budines");
        buttonB.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonB.setBounds(264, 146, 196, 70);
        contentPane.add(buttonB);

	}
}
