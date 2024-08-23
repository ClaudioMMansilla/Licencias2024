package envasadoUnificado2024;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import Atxy2k.CustomTextField.RestrictedTextField;


public class loteVtoManual_View extends JFrame {

	private JPanel contentPane;

	public JTextField field_ingresoLote;
	public JButton btn_volver;
	public JDateChooser calendar = new JDateChooser();


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					loteVtoManual_View frame = new loteVtoManual_View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public loteVtoManual_View() {

		setTitle("Lote y Vencimiento manual ");

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(450, 190, 350, 250);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lbl_lote = new JLabel("Ingrese Lote: ");
        lbl_lote.setFont(new Font("Tahoma", Font.BOLD, 12));
        lbl_lote.setBounds(72, 43, 98, 25);
        contentPane.add(lbl_lote);

        JLabel lbl_venc = new JLabel("Ingrese Vencimiento: ");
        lbl_venc.setFont(new Font("Tahoma", Font.BOLD, 12));
        lbl_venc.setBounds(24, 97, 146, 25);
        contentPane.add(lbl_venc);

        calendar.setDateFormatString("dd/MM/yyyy");
        calendar.setFont(new Font("Tahoma", Font.BOLD, 12));
        calendar.setBounds(180, 100, 118, 25);
        contentPane.add(calendar);

        field_ingresoLote = new JTextField();
        field_ingresoLote.setBounds(180,40,50,30);
        field_ingresoLote.setFont(new java.awt.Font("Tahoma", 0, 18));
		contentPane.add(field_ingresoLote);
		// libreria importada de internet - L132 to L135
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(field_ingresoLote);
		fieldIdRestringido.setLimit(3);
		fieldIdRestringido.setOnlyNums(true);
	    TextPrompt placeholder = new TextPrompt("123", field_ingresoLote);
	    placeholder.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    placeholder.changeAlpha(0.75f);

	    btn_volver = new JButton("Finalizar");
	    btn_volver.setBounds(209, 161, 89, 30);
	    contentPane.add(btn_volver);


	}

}
