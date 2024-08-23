package envasadoUnificado2024;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import EventosGUI.EventoDeTeclado;
import Servicios.Services;
import fecha.Fecha;




public class Envasado_Controller implements ActionListener{
	private String dataBase = "";

	private Envasado_View view = new Envasado_View();
	private Envasado_Model model = new Envasado_Model();
	private Reports report = new Reports();

	private loteVtoManual_View loteVtoView = new loteVtoManual_View();
	private loteVtoManual_Controller loteVtoCtrl = new loteVtoManual_Controller(loteVtoView);

	private String planta = "null";
	private String usuario = "null";
	private int vidaUtil = 0;
//	private String tableDB = "null";


	public String[] loteVencimiento = new String[2]; // utilizado para return loteVtoManual_Controller
	private String [] dependency;

	public Envasado_Controller(
		Envasado_View view, Envasado_Model model,
		int vidaUtil, String planta, String usuario){

		this.view = view;
		this.model = model;
		this.planta = planta;
		this.usuario = usuario;
		this.vidaUtil = vidaUtil;
//		this.dataBase = dataBase;

		EventoDeTeclado tecla = new EventoDeTeclado();
		view.addKeyListener(tecla);

		view.field_id.addKeyListener(tecla);

		view.JBT_guardar.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	doInsertIntoDB();
            }
        });

		view.JBT_LtoVtoView.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	loteVtoManual_Controller();
            }
        });

		view.field_id.addActionListener(new ActionListener(){

			public void actionPerformed(KeyEvent e){
				tecla.codigo = e.getKeyCode();
			}

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (tecla.codigo == 10) {
					verificarId();
				}
			}
		});


    	loteVtoView.btn_volver.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
            	if( !(loteVtoView.field_ingresoLote.getText().equals("")
            			|| loteVtoView.calendar.getDate() == null))
            	{
            		loteVtoManual_Controller();
            		loteVtoView.dispose();
            	}
            	else {
           			JOptionPane.showMessageDialog(view, "Campos obligatorios son inválidos \n\n");
           			//loteVtoView.dispose();
            	}
            }
        });

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public void iniciar() {
		view.setTitle("Vencimiento ");
		view.setLocationRelativeTo(null);
		model.setPlanta(planta);
		model.setUsuarioIngreso(usuario);
		setTitleView();
		setFechaCalcVenc();
		setLote();

	}


	public void setTitleView() {
		String bufferPlanta = model.getPlanta();
		String bufferUsuario = model.getUsuarioIngreso();
		view.setTitle("Planta:  "+ bufferPlanta +"  |   Usuario:  "+bufferUsuario+" ");
	}


	public void setCantidadModel() {
		String buffer = view.field_cantidad.getText();

		if( !(buffer.equals("")) ) {
			model.setCajasInformadas(
					Fecha.parserStringToInt(buffer)
					);
		} else {
			model.setCajasInformadas(-1);
		}
	}


	public void setFechaCalcVenc() {
		String vencimiento = Fecha.calcularVencimiento(vidaUtil);
		model.setVencimiento(vencimiento);
		view.lbl_setearVenc.setText(model.getVencimiento());
	}

	public void setFechaCalcVenc(String vencimiento) {
		model.setVencimiento(vencimiento);
		view.lbl_setearVenc.setText(model.getVencimiento());
	}

	public void setLote() {
		String lote = Fecha.calcularLote();
		int bufferLote = Fecha.parserStringToInt(lote);
		model.setLote(bufferLote);
		view.lbl_setearLote.setText(lote);
	}

	public void setLote(String lote) {
		model.setLote(Fecha.parserStringToInt(lote));
		view.lbl_setearLote.setText(lote);
	}


	public void setProductoLabel() {
		String bufferString = model.getProducto();
		view.label6.setText(bufferString);
	}

	public void setFechaIngresoModel() {
		model.setFechaIngreso(Fecha.fechaIngreso());
	}

	public void setFlagDisponibleModel() {
		model.setFlagDisponible(1);
	}

	public void setFlagAjusteModel() {
		model.setFlagAjuste(0);
	}


	private String getTableDBModel() {
		return model.getTableDB();
	}

	public void verificarId() {
		//System.out.println("Data base target: "+dataBase);

		String field = view.field_id.getText();
		int bufferId = Fecha.parserStringToInt(field);
		String [] depcy = Services.validarProductoStatic(bufferId);

   		if (depcy[0]==null) { // valido textfield id sino hubo resultados en consulta
   			JOptionPane.showMessageDialog(null, "Código de producto inválido \n\n "
   					+ "Envasado_Controller: doInjection() ");

   			model.setIdProducto(-1);
   			model.setProducto(null);
   			model.setTableDB(null);
   			model.setTableDBCounter(null);
   			setProductoLabel();

   		} else {
   			dependency = depcy;
   			int id = Fecha.parserStringToInt(dependency[0]);
   			model.setIdProducto(id);
   			model.setProducto(dependency[1]); // descripcion producto
   			model.setTableDB(dependency[2]); // tabla target input env
   			model.setTableDBCounter(dependency[3]); // tabla target contador 
   			setProductoLabel();
   		}
	}


	private boolean checkBeforeInsertDB() {

		setCantidadModel();

		boolean check = false;
		int bufferCajas = model.getCajasInformadas();
		int bufferId = model.getIdProducto();

		if ( !(bufferId == -1
				|| bufferCajas <= 0
				|| view.field_id.getText().equals("")
				|| view.field_cantidad.getText().equals("") ))
		{
			check = true;
		}

		return check;
	}

	
	private void ejecutarQuerySQL() {
	    // Consulta SELECT para obtener el próximo número
	    String querySelect = "SELECT proximoNro FROM " + model.getTableDBCounter();

	    // Consulta UPDATE para incrementar el próximo número
	    String queryUpdate = "UPDATE " + model.getTableDBCounter() + " SET proximoNro = proximoNro + 1";

	    // Consulta INSERT utilizando PreparedStatement
	    String queryInsert = "INSERT INTO " + model.getTableDB() + " " +
	        "(fechaIngreso, usuarioIngreso, idProducto, planta, producto, lote, " +
	        "vencimiento, cajasInformadas, flagDisponible, flagAjuste, dateIngreso, numPallet) " +
	        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
	        // Obtener el próximo número y actualizar
	        try (Connection conn = Services.abrirConexionStatic();
	             PreparedStatement pstmtSelect = conn.prepareStatement(querySelect);
	             ResultSet rs = pstmtSelect.executeQuery()) {

	            if (rs.next()) {
	                model.setNumPallet(Integer.toString(rs.getInt("proximoNro")));
	            }

	            // Actualizar el próximo número
	            try (PreparedStatement pstmtUpdate = conn.prepareStatement(queryUpdate)) {
	                pstmtUpdate.executeUpdate();
	            }

	            // Realizar el INSERT
	            try (PreparedStatement pstmtInsert = conn.prepareStatement(queryInsert)) {
	                pstmtInsert.setString(1, model.getFechaIngreso());
	                pstmtInsert.setString(2, usuario);
	                pstmtInsert.setInt(3, model.getIdProducto());
	                pstmtInsert.setString(4, model.getPlanta());
	                pstmtInsert.setString(5, model.getProducto());
	                pstmtInsert.setString(6, Integer.toString(model.getLote()));
	                pstmtInsert.setString(7, model.getVencimiento());
	                pstmtInsert.setInt(8, model.getCajasInformadas());
	                pstmtInsert.setInt(9, model.getFlagDisponible());
	                pstmtInsert.setInt(10, model.getFlagAjuste());
	                pstmtInsert.setString(11, Fecha.calculateDateTime());
	                pstmtInsert.setString(12, model.getNumPallet());

	                pstmtInsert.executeUpdate();
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error en la consulta: " + e.getMessage());
	    }
	}

	
	
//	private void ejecutarQuerySQL(){
//
//		String querySelect = "SELECT proximoNro FROM "+model.getTableDBCounter()+" ";
//		String queryUpdate = "UPDATE "+model.getTableDBCounter()+" SET proximoNro = proximoNro + 1";
//
//		StringBuilder sb = new StringBuilder();
//		sb.append("INSERT into "+model.getTableDB()+" ");
//		sb.append("(fechaIngreso, usuarioIngreso, idProducto, ");
//		sb.append("planta, producto, lote, ");
//		sb.append("vencimiento, cajasInformadas, flagDisponible, ");
//		sb.append("flagAjuste, dateIngreso, numPallet) ");
//		sb.append("VALUES ( ");
//		sb.append(" "+model.getFechaIngreso()+", ");
//		sb.append(" "+usuario+", ");
//		sb.append(" "+model.getIdProducto()+", ");
//		sb.append(" "+model.getPlanta()+", ");
//		sb.append(" "+model.getProducto()+", ");
//		sb.append(" "+model.getLote()+", ");
//		sb.append(" "+model.getVencimiento()+", ");
//		sb.append(" "+model.getCajasInformadas()+", ");
//		sb.append(" "+model.getFlagDisponible()+", ");
//		sb.append(" "+model.getFlagAjuste()+", ");
//		sb.append(" "+Fecha.calculateDateTime()+", ");
//		sb.append(" "+model.getNumPallet()+" );");
//		
//		try {
//			PreparedStatement enviaConsulta;
//			enviaConsulta = Services.abrirConexionStatic().prepareStatement(querySelect);
//			ResultSet rs = enviaConsulta.executeQuery();
//			while(rs.next()) {
//				model.setNumPallet(Integer.toString(rs.getInt("proxNum")));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Error, selectNumGuiaFromDB: "+ e);
//		}
//		
////		String query = "INSERT into "+dataBase+tableDB+" "
////		+ "(fechaIngreso, usuarioIngreso, idProducto, planta, producto, lote, "
////		+ "vencimiento, cajasInformadas, flagDisponible, flagAjuste, dateIngreso, numPallet) "
////		+ "values ('"+model.getFechaIngreso()+"', '"+usuario+"', '"+model.getIdProducto()+"', "
////				+ "'"+model.getPlanta()+"', '"+model.getProducto()+"', '"+model.getLote()+"', "
////				+ "'"+model.getVencimiento()+"', '"+model.getCajasInformadas()+"', "
////				+ "'"+model.getFlagDisponible()+"', '"+model.getFlagAjuste()+"', '"+Fecha.calculateDateTime()+"',"
////				+ "'"+model.getNumPallet()+"')";
//
//		System.out.println("proxNumm: "+model.getNumPallet());
//		service.realizarInsertDB(sb.toString());
//		service.realizarInsertDB(queryUpdate);
//	}

//	private void ejecutarQuerySQL(){
//
//		this.tableDB = getTableDBModel();
//		String fechaIngreso = model.getFechaIngreso();
//		String usuarioIngreso = usuario;
//		int idProducto = model.getIdProducto();
//		String planta = model.getPlanta();
//		String producto = model.getProducto();
//		int lote = model.getLote();
//		String vencimiento = model.getVencimiento();
//		int cajasInformadas = model.getCajasInformadas();
//		int flagDisponible = model.getFlagDisponible();
//		int flagAjuste = model.getFlagAjuste();
//		String date = Fecha.calculateDateTime();
//
//		
//		String query = "INSERT into "+dataBase+tableDB+" "
//		+ "(fechaIngreso, usuarioIngreso, idProducto, planta, producto, lote, "
//		+ "vencimiento, cajasInformadas, flagDisponible, flagAjuste, dateIngreso) "
//		+ "values ('"+fechaIngreso+"', '"+usuarioIngreso+"', '"+idProducto+"', "
//				+ "'"+planta+"', '"+producto+"', '"+lote+"', "
//				+ "'"+vencimiento+"', '"+cajasInformadas+"', "
//				+ "'"+flagDisponible+"', '"+flagAjuste+"', '"+date+"')";
//
//		service.realizarInsertDB(query);
//	}
	

	private void doInsertIntoDB() {

		if(checkBeforeInsertDB()) {
			setFechaIngresoModel();
			setFlagDisponibleModel();
			setFlagAjusteModel();
			ejecutarQuerySQL();
			setInformacionIngresadaView();
			setNumPalletModel();
			report.reportEnvasado(model);

		} else {
			view.label6.setText("Alguno de los campos obligatorios es inválido");
		}

		view.field_cantidad.setText(null);
	}


	private void setNumPalletView() {
//		view.info_pallets.setText(
//				service.getNumPalletIntoDB(
//						model.getTableDB(),
//						model.getFechaIngreso())
//				);
		view.info_pallets.setText(model.getNumPallet());
	}


	private void setCantidadView() {
		view.info_cantidad.setText(
				Integer.toString(
						model.getCajasInformadas()
						));
	}


	private void setDescripcionView() {
		view.info_descripcion.setText(
				model.getProducto()
				);
	}


	private void setInformacionIngresadaView() {
		setNumPalletView();
		setCantidadView();
		setDescripcionView();
	}

	private void setNumPalletModel() {
		model.setNumPallet(view.info_pallets.getText());
	}


	public void setLoteVencimientoIntoArray() {
		loteVencimiento[0] = loteVtoView.field_ingresoLote.getText();
		loteVencimiento[1] = Fecha.formatterCalendarDate((loteVtoView.calendar.getDate()));
	}


	public String[] getLoteVencimientoIntoArray() {
		return loteVencimiento;
	}


	private void loteVtoManual_Controller() {

    	loteVtoCtrl.iniciar();
    	loteVtoView.setVisible(true);

    	setLoteVencimientoIntoArray();
    	setLote(loteVencimiento[0]);
    	setFechaCalcVenc(loteVencimiento[1]);
	}


}
