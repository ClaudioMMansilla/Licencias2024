package envasadoUnificado2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import actualizarPallet.CambiarPallet_MVC;

public class UserHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static String userSes;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                try {
                    UserHome frame = new UserHome(userSes);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
//
//    public UserHome() {
//
//    }

    /**
     * Create the frame.
     */
    public UserHome(String userSes) {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(450, 190, 500, 354);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton(" Ingresar Pallet ");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton.setBounds(26, 68, 205, 50);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	dispose();
            	Domicilio_MVC nextMVC = new Domicilio_MVC(userSes);
            }
        });


        JButton btnNewButton5 = new JButton("Modificar pallet");
        btnNewButton5.setForeground(new Color(0, 0, 0));
        btnNewButton5.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton5.setBounds(256, 68, 205, 50);
        contentPane.add(btnNewButton5);
        btnNewButton5.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	CambiarPallet_MVC nextMVC = new CambiarPallet_MVC(userSes);

            }
        });


        JButton button = new JButton("Cambiar Contraseña\r\n");
        button.setBackground(UIManager.getColor("Button.disabledForeground"));
        button.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button.setBounds(26, 178, 205, 50);
        contentPane.add(button);
        button.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                ChangePassword bo = new ChangePassword(userSes);
                bo.setTitle("Change Password");
                bo.setVisible(true);

            }
        });

        JButton button_1 = new JButton("Cerrar Sesión");
        button_1.setForeground(Color.BLACK);
        button_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button_1.setBackground(SystemColor.textInactiveText);
        button_1.setBounds(256, 178, 205, 50);
        contentPane.add(button_1);
        button_1.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Desea continuar?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                }
                dispose();
                UserLogin_EnvUnificado obj = new UserLogin_EnvUnificado();

                obj.setTitle("Login");
                obj.setVisible(true);
        	}
        });



    }
}