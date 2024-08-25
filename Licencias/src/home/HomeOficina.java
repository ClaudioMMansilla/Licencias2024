package home;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import hojaDeRuta.EmitirHojaRuta_Controller;

@SuppressWarnings("serial")
public class HomeOficina extends JFrame {

	private JPanel contentPane;
	private Color btnBGroundDefault = Color.LIGHT_GRAY;
	private Color btnBGroundOnMouse = Color.WHITE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					HomeOficina frame = new HomeOficina();
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
	public HomeOficina() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 195, 279);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
		setContentPane(contentPane);

		JButton btnStock = new JButton("STOCK");
		btnStock.setFont(new Font("Arial", Font.BOLD, 12));
		btnStock.setBounds(24, 10, 125, 20);
		btnStock.setBackground(btnBGroundDefault);
		contentPane.add(btnStock);
		btnStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productoTerminado.ConsultarStock.main(null);
			}
		});
		btnStock.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnStock.setBackground(btnBGroundOnMouse);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnStock.setBackground(btnBGroundDefault);
			}
		});

		JButton btnRecord = new JButton("MOVIMIENTOS");
		btnRecord.setFont(new Font("Arial", Font.BOLD, 12));
		btnRecord.setBackground(btnBGroundDefault);
		btnRecord.setBounds(24, 35, 125, 20);
		contentPane.add(btnRecord);
		btnRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				productoTerminado.MovimientosStock.main(null);
			}
		});
		btnRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRecord.setBackground(btnBGroundOnMouse);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnRecord.setBackground(btnBGroundDefault);
			}
		});

		JButton btnNewHR = new JButton("CREAR H. RUTA");
		btnNewHR.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewHR.setBackground(btnBGroundDefault);
		btnNewHR.setBounds(24, 60, 125, 20);
		contentPane.add(btnNewHR);
		btnNewHR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//EmitirHojaRuta_Controller hoja = new EmitirHojaRuta_Controller();
				EmitirHojaRuta_Controller hoja = new EmitirHojaRuta_Controller();
			}
		});
		
		btnNewHR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewHR.setBackground(btnBGroundOnMouse);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewHR.setBackground(btnBGroundDefault);
			}
		});

		JButton btnUpdateHR = new JButton("MODIFICAR H.R.");
		btnUpdateHR.setFont(new Font("Arial", Font.BOLD, 12));
		btnUpdateHR.setBackground(btnBGroundDefault);
		btnUpdateHR.setBounds(24, 85, 125, 20);
		contentPane.add(btnUpdateHR);
		btnUpdateHR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//new RecuperarHojaRuta_Controller().iniciar();
				chooseView.ChooseView_ModificarHRuta.main(null);
			}
		});
		btnUpdateHR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnUpdateHR.setBackground(btnBGroundOnMouse);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnUpdateHR.setBackground(btnBGroundDefault);
			}
		});

		JButton btnPrintHR = new JButton("IMPRIMIR H.R.");
		btnPrintHR.setFont(new Font("Arial", Font.BOLD, 12));
		btnPrintHR.setBackground(btnBGroundDefault);
		btnPrintHR.setBounds(24, 110, 125, 20);
		contentPane.add(btnPrintHR);
		btnPrintHR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hojaDeRuta.ImprimirHojaRuta.main(null);
			}
		});
		btnPrintHR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnPrintHR.setBackground(btnBGroundOnMouse);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnPrintHR.setBackground(btnBGroundDefault);
			}
		});

		JButton btnListHR = new JButton("LISTAR H.R.");
		btnListHR.setFont(new Font("Arial", Font.BOLD, 12));
		btnListHR.setBackground(btnBGroundDefault);
		btnListHR.setBounds(24, 135, 125, 20);
		contentPane.add(btnListHR);
		btnListHR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hojaDeRuta.ListarHojaRuta_Controller ctrl = new hojaDeRuta.ListarHojaRuta_Controller();
			}
		});
		btnListHR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnListHR.setBackground(btnBGroundOnMouse);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnListHR.setBackground(btnBGroundDefault);
			}
		});

		JButton btnFactory = new JButton("PRODUCCION");
		btnFactory.setFont(new Font("Arial", Font.BOLD, 12));
		btnFactory.setBackground(btnBGroundDefault);
		btnFactory.setBounds(24, 160, 125, 20);
		contentPane.add(btnFactory);
		btnFactory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFactory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnFactory.setBackground(btnBGroundOnMouse);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnFactory.setBackground(btnBGroundDefault);
			}
		});

		JButton btnRecFactory = new JButton("Admin HR");
		btnRecFactory.setFont(new Font("Arial", Font.BOLD, 12));
		btnRecFactory.setBackground(btnBGroundDefault);
		btnRecFactory.setBounds(24, 185, 125, 20);
		contentPane.add(btnRecFactory);
		btnRecFactory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnRecFactory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRecFactory.setBackground(btnBGroundOnMouse);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnRecFactory.setBackground(btnBGroundDefault);
			}
		});
	}
}
