package hojaDeRuta;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import Atxy2k.CustomTextField.RestrictedTextField;
import envasadoUnificado2024.TextPrompt;
import net.sf.jasperreports.engine.JRException;
import javax.swing.JRadioButton;

public class ImprimirHojaRuta extends JFrame {

	private JPanel contentPane;
	private boolean flagHR;
	private boolean flagGT;
	private JTextField txtId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ImprimirHojaRuta frame = new ImprimirHojaRuta();
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
	public ImprimirHojaRuta() {
		setTitle("Imprimir ");
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 216, 132);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);


		txtId = new JTextField();
		txtId.setBounds(46, 56, 68, 26);
		contentPane.add(txtId);
		txtId.setColumns(10);
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(txtId);
		//fieldIdRestringido.setLimit(4);
		fieldIdRestringido.setOnlyNums(true);
		// fin lib importada
	    TextPrompt placeholder = new TextPrompt("Ingrese nro", txtId);
	    placeholder.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    placeholder.changeAlpha(0.75f);
        
		JRadioButton rdbtnHR = new JRadioButton("H. Ruta");
		rdbtnHR.setBounds(26, 17, 76, 23);
		contentPane.add(rdbtnHR);
		
		JRadioButton rdbtnGT = new JRadioButton("Guia Tte");
		rdbtnGT.setBounds(104, 17, 76, 23);
		contentPane.add(rdbtnGT);
	    
	    ButtonGroup group = new ButtonGroup();
        group.add(rdbtnHR);
        group.add(rdbtnGT);
        flagHR = false;
        flagGT = false;
        
        rdbtnHR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	flagHR = !flagHR;
            }
        });
        
        rdbtnGT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	flagGT = !flagGT;
            }
        });
		
		txtId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(flagHR && !flagGT) {
						reportes.PrintHojaRuta.Print(txtId.getText());		
					} else {
						reportes.PrintGuiatte.PrintGuia(txtId.getText());
					}
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnCargar = new JButton("");
		btnCargar.setIcon(new ImageIcon("C:\\MyReports\\images\\btnPrint.png"));
		btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnCargar.setBounds(130, 56, 29, 26);
		//btnCargar.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		contentPane.add(btnCargar);
		btnCargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(flagHR && !flagGT) {
						reportes.PrintHojaRuta.Print(txtId.getText());		
					} else {
						reportes.PrintGuiatte.PrintGuia(txtId.getText());
					}
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		

	}
}
