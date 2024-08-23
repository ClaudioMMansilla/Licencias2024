package actualizarPallet;

public class CambiarPallet_MVC {

	public CambiarPallet_MVC(String user) {

		CambiarPallet_Model model = new CambiarPallet_Model();

		CambiarPallet_View view = new CambiarPallet_View();
		view.setVisible(true);

		CambiarPallet_Controller ctrl = new CambiarPallet_Controller(view, model, user);
		ctrl.iniciar();

	}

}
