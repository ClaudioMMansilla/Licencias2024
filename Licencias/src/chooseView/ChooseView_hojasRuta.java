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
import logisticaNew.LogNew_DecrementarStock;
import logisticaNew.LogNew_IncrementarStock;


public class ChooseView_hojasRuta extends JFrame {

	private static String userSes;
	private JPanel contentPane;
	public JButton buttonA = new JButton("Ingreso al stock");
	public JButton buttonB = new JButton("Egreso del stock");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ChooseView_hojasRuta frame = new ChooseView_hojasRuta(userSes);
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
	public ChooseView_hojasRuta(String userSes) {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(450, 190, 500, 354);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel mensajeUsuario = new JLabel("Seleccionar tipo de movimiento");
        mensajeUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
        mensajeUsuario.setBounds(38, 59, 422, 51);
        contentPane.add(mensajeUsuario);

        buttonA.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonA.setBounds(38, 146, 196, 70);
        contentPane.add(buttonA);
        buttonA.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				LogNew_IncrementarStock frame = new LogNew_IncrementarStock(userSes);
				frame.setVisible(true);
            }
        });

        buttonB.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonB.setBounds(264, 146, 196, 70);
        contentPane.add(buttonB);
        buttonB.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				LogNew_DecrementarStock frame = new LogNew_DecrementarStock(userSes);
				frame.setVisible(true);
            }
        });

	}
}
