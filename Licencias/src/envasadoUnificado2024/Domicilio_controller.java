package envasadoUnificado2024;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Domicilio_controller implements ActionListener {

	private String[] domicilio = {"PAN DULCE", "BUDINES"};
	private Domicilio_view view = new Domicilio_view();
	private Domicilio_model model = new Domicilio_model();

	public Domicilio_controller(Domicilio_view view, Domicilio_model model, String user) {

		this.view = view;
		this.model = model;

		view.buttonA.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	model.setPlanta(domicilio[0]);
            	System.out.println(model.getPlanta());
            	view.dispose();
            	Vencimiento_MVC nextMVC = new Vencimiento_MVC(user, model.getPlanta());
            }
        });

		view.buttonB.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	model.setPlanta(domicilio[1]);
            	System.out.println(model.getPlanta());
            	view.dispose();
            	Vencimiento_MVC nextMVC = new Vencimiento_MVC(user, model.getPlanta());
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
