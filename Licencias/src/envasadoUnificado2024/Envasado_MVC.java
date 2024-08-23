package envasadoUnificado2024;

public class Envasado_MVC {

	public Envasado_MVC(String usuario, String planta, int vidaUtil) {

		Envasado_Model model = new Envasado_Model();

		Envasado_View view = new Envasado_View();
		view.setVisible(true);


		Envasado_Controller ctrl = new Envasado_Controller(view, model, vidaUtil, planta, usuario);
		ctrl.iniciar();

	}

}
