package envasadoUnificado2024;

public class Domicilio_MVC {

	public Domicilio_MVC(String user) {

		Domicilio_model model = new Domicilio_model();

		Domicilio_view view = new Domicilio_view();
		view.setVisible(true);

		Domicilio_controller ctrl = new Domicilio_controller(view, model, user);
		ctrl.iniciar();

	}

}
