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
import logisticaNew.LogNew_UpdateInputBud;
import logisticaNew.LogNew_UpdateInputPD;


public class ChooseView_modifPallet extends JFrame {

	private static String userSes;
	private JPanel contentPane;
	public JButton buttonA = new JButton("Budines");
	public JButton buttonB = new JButton("Pan dulces");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ChooseView_modifPallet frame = new ChooseView_modifPallet(userSes);
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
	public ChooseView_modifPallet(String userSes) {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(450, 190, 500, 354);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel mensajeUsuario = new JLabel("Seleccionar tipo de producto");
        mensajeUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
        mensajeUsuario.setBounds(38, 59, 422, 51);
        contentPane.add(mensajeUsuario);

        buttonA.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonA.setBounds(38, 146, 196, 70);
        contentPane.add(buttonA);
        buttonA.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	dispose();
				LogNew_UpdateInputBud UpdateInputBud = new LogNew_UpdateInputBud(userSes);
				UpdateInputBud.setTitle("Modificacion de pallet ingresados Budines ");
				UpdateInputBud.setVisible(true);
            }
        });

        buttonB.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonB.setBounds(264, 146, 196, 70);
        contentPane.add(buttonB);
        buttonB.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	dispose();
            	LogNew_UpdateInputPD UpdateInputPD = new LogNew_UpdateInputPD(userSes);
            	UpdateInputPD.setTitle("Modificacion de pallet ingresados: Pan Dulces ");
            	UpdateInputPD.setVisible(true);
            }
        });

	}
}
