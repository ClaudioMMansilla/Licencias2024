package informeProduccion;


public class InfoMovimPT_MVC {

	public InfoMovimPT_MVC(String usuario) {
		// TODO Auto-generated method stub
		InfoMovimPT_Model model = new InfoMovimPT_Model();

		InfoMovimPT_View view = new InfoMovimPT_View();
		view.setVisible(true);

		InfoMovimPT_Controller ctrl = new InfoMovimPT_Controller(view, model, usuario);
		ctrl.iniciar();

		System.out.println("InfoMovimPT_MVC");
	}

}
