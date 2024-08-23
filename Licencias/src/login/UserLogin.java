package login;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import conector.Conexion;
import envasadoAPP.EnvasadoInputBudines;

public class UserLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                try {
                    UserLogin frame = new UserLogin();
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
    public UserLogin() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(450, 190, 500, 354);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Bienvenido, para operar deber� iniciar sesi�n");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblNewLabel.setBounds(121, 22, 343, 50);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField.setBounds(177, 97, 250, 50);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        passwordField.setBounds(177, 172, 250, 50);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("Usuario");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsername.setBounds(80, 95, 87, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Contrase�a");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPassword.setBounds(47, 172, 119, 52);
        contentPane.add(lblPassword);

        btnNewButton = new JButton("Iniciar");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(232, 233, 150, 50);
        btnNewButton.addActionListener(new ActionListener() {

            @Override
			public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                String password = passwordField.getText();
                try {
            		Conexion conexion = new Conexion();
            		Connection cn = conexion.Conectar();
//                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo",
//                        "root", "root");

                    PreparedStatement st = cn.prepareStatement("Select name, password from desarrollo.usuarios where name=? and password=?");

                    st.setString(1, userName);
                    st.setString(2, password);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        dispose();
//                        UserHome ah = new UserHome(userName);
//                        ah.setTitle("Welcome");
//                        ah.setVisible(true);
//                        JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");

    					EnvasadoInputBudines frame = new EnvasadoInputBudines();
    					frame.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });

        contentPane.add(btnNewButton);
    }

}