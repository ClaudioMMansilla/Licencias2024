package envasadoUnificado2024;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import EventosGUI.EventoDeTeclado;


public class Vencimiento_Controller implements ActionListener {

	private Vencimiento_View view = new Vencimiento_View();
	private Vencimiento_Model model = new Vencimiento_Model();

	private String planta;
	private int vidaUtil;


	public Vencimiento_Controller(
			Vencimiento_View view, Vencimiento_Model model,
			String planta, String user) {

		this.view = view;
		this.model = model;
		this.planta = planta;

		EventoDeTeclado tecla = new EventoDeTeclado();
		view.addKeyListener(tecla);

		view.btn_volver.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	view.dispose();
            	Domicilio_MVC nextMVC = new Domicilio_MVC(user);
            }
        });

		view.btn_avanzar.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	view.dispose();
            	Envasado_MVC nextMVC = new Envasado_MVC(user, planta, vidaUtil);
            }
        });

		view.field_ingresoVto.addKeyListener(tecla);
		view.field_ingresoVto.addActionListener(new ActionListener(){

			public void actionPerformed(KeyEvent e){
				tecla.codigo = e.getKeyCode();
			}

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				if (tecla.codigo == 10) {
					setFechaCalcVenc();
				}
				else
				{
					System.out.println("saliendo del if en field.id "+ tecla.codigo);
				}
			}
		});

	}

	public void iniciar() {
		view.setTitle("Vencimiento ");
		view.setLocationRelativeTo(null);
		setPlantaEnLabel(planta);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void setPlantaEnLabel(String planta) {
		model.setPlanta(planta);
		view.lbl_detallePlanta.setText(model.getPlanta());
	}


	public void setFechaCalcVenc() {
		String bufferString = view.field_ingresoVto.getText();
		String vencimiento = Fecha.calcularVencimiento(bufferString);
		model.setVencimiento(vencimiento);
		this.vidaUtil = Fecha.parserStringToInt(bufferString);
		view.lbl_fechaCalculada.setText(model.getVencimiento());
	}

}


