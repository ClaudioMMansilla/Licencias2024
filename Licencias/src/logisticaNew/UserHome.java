package logisticaNew;

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
import chooseView.ChooseView_hojasRuta;
import chooseView.ChooseView_ingrEgrManual;
import chooseView.ChooseView_ingresoEgreso;
import informeProduccion.InfoMovimPT_MVC;
import productoTerminado.ConsultarStock;
import productoTerminado.MovimientosStock;

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
        setBounds(450, 190, 649, 450);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnConsStock = new JButton("Consultar Stock");
        btnConsStock.setForeground(new Color(0, 0, 0));
        btnConsStock.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnConsStock.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnConsStock.setBounds(26, 33, 290, 50);
        contentPane.add(btnConsStock);
        btnConsStock.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	new ConsultarStock().setVisible(true);
            }
        });


        JButton btnMovimLogis = new JButton("Movimientos Logistica");
        btnMovimLogis.setForeground(new Color(0, 0, 0));
        btnMovimLogis.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnMovimLogis.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnMovimLogis.setBounds(328, 33, 290, 50);
        contentPane.add(btnMovimLogis);
        btnMovimLogis.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	new MovimientosStock().Ventana05.setVisible(true);
            }
        });


        JButton btnIngBud = new JButton("Ingresar Producción");
        btnIngBud.setForeground(new Color(0, 0, 0));
        btnIngBud.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnIngBud.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnIngBud.setBounds(26, 94, 290, 50);
        contentPane.add(btnIngBud);
        btnIngBud.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	new ChooseView_ingresoEgreso(userSes).setVisible(true);
            }
        });


        JButton btnIngPdul = new JButton("Ingreso/Egreso Manual");
        btnIngPdul.setForeground(new Color(0, 0, 0));
        btnIngPdul.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnIngPdul.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnIngPdul.setBounds(328, 94, 290, 50);
        contentPane.add(btnIngPdul);
        btnIngPdul.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				new ChooseView_ingrEgrManual(userSes).setVisible(true);
            }
        });

        JButton btnModifPallBud = new JButton("Modificar Pallet");
        btnModifPallBud.setForeground(new Color(0, 0, 0));
        btnModifPallBud.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnModifPallBud.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnModifPallBud.setBounds(26, 155, 290, 50);
        contentPane.add(btnModifPallBud);
        btnModifPallBud.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	new CambiarPallet_MVC(userSes);
            }
        });


        JButton btnEgrHRuta = new JButton("Administrar Hojas de Ruta");
        btnEgrHRuta.setForeground(new Color(0, 0, 0));
        btnEgrHRuta.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnEgrHRuta.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnEgrHRuta.setBounds(328, 155, 290, 50);
        contentPane.add(btnEgrHRuta);
        btnEgrHRuta.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				//new ChooseView_hojasRuta(userSes).setVisible(true);
				//chooseView.ChooseView_ModificarHRuta.main(null);
				hojaDeRuta.ListarHojaRuta_Controller ctrl = new hojaDeRuta.ListarHojaRuta_Controller();
            }
        });


        JButton btnChangePass = new JButton("Cambiar Contraseña");
        btnChangePass.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnChangePass.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnChangePass.setBounds(318, 360, 145, 50);
        contentPane.add(btnChangePass);
        btnChangePass.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                ChangePassword bo = new ChangePassword(userSes);
                bo.setTitle("Change Password");
                bo.setVisible(true);
            }
        });


        JButton btnSesion = new JButton("Cerrar Sesión");
        btnSesion.setForeground(Color.BLACK);
        btnSesion.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnSesion.setBackground(SystemColor.textInactiveText);
        btnSesion.setBounds(473, 360, 145, 50);
        contentPane.add(btnSesion);
        btnSesion.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnConsStock, "¿Desea continuar?");
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                }
                dispose();
                UserLogin_Logistica052023 obj = new UserLogin_Logistica052023();
                //obj.setTitle("Student-Login");
                obj.setVisible(true);
        	}
        });

        JButton btnInfProd = new JButton("Informe Producción");
        btnInfProd.setForeground(new Color(0, 0, 0));
        btnInfProd.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnInfProd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnInfProd.setBounds(26, 216, 290, 50);
        contentPane.add(btnInfProd);
        btnInfProd.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	InfoMovimPT_MVC nextMVC = new InfoMovimPT_MVC(userSes);
            	/*
            	 * MODULO EN DESARROLLO
            	 * */
            }
        });



    }
}