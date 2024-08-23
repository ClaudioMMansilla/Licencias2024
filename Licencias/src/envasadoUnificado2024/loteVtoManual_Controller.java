package envasadoUnificado2024;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loteVtoManual_Controller implements ActionListener{

	private loteVtoManual_View view = new loteVtoManual_View();
	public String[] loteVencimiento = new String[2];

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	public loteVtoManual_Controller(loteVtoManual_View view) {

		this.view = view;

		view.btn_volver.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {

            }
        });
	}


	public void iniciar() {
		view.setTitle("Ingreso de vencimiento y lote manual ");
		view.setLocationRelativeTo(null);
	}


}
