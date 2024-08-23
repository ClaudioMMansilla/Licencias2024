package actualizarPallet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CambiarPallet_Controller implements ActionListener {

	private String[] dataBase = {"pruebas_2022.env_input_pdulces", "pruebas_2022.env_input_budines"};
	private CambiarPallet_View view = new CambiarPallet_View();
	private CambiarPallet_Model model = new CambiarPallet_Model();

	public CambiarPallet_Controller(CambiarPallet_View view, CambiarPallet_Model model, String user) {

		this.view = view;
		this.model = model;

		view.buttonA.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	model.setTableDB(dataBase[0]); // pan dulces
            	System.out.println(model.getTableDB());
            	view.dispose();
            	CambiarPallet_Update frameUpdate = new CambiarPallet_Update(user, model.getTableDB());
            	frameUpdate.setVisible(true);
            }
        });

		view.buttonB.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	model.setTableDB(dataBase[1]); // budines
            	System.out.println(model.getTableDB());
            	view.dispose();
            	CambiarPallet_Update frameUpdate = new CambiarPallet_Update(user, model.getTableDB());
            	frameUpdate.setVisible(true);
            }
        });

	}

	public void iniciar() {
		view.setTitle("MVC");
		view.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
