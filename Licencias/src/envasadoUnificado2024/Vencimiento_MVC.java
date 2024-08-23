package envasadoUnificado2024;

public class Vencimiento_MVC {

	public Vencimiento_MVC(String user, String planta) {

		Vencimiento_Model model = new Vencimiento_Model();

		Vencimiento_View view = new Vencimiento_View();
		view.setVisible(true);

		Vencimiento_Controller ctrl = new Vencimiento_Controller(view, model, planta, user);
		ctrl.iniciar();

	}
}