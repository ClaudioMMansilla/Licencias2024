package logisticaNew;

import reportes.InformeProduccion;

public class Reports {


	public void reportInformeProduccion(InformeProduccion model) {
//
//		if(model != null) {
//	    	//indico la ruta del objeto a cargar
//	    	JasperReport reporte;
//			try {
//				reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\rotuloEnvasado.jasper");
//
//
//		    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
//		    	Map<String, Object> parametros = new HashMap<String, Object>();
//		    	parametros.put("descripcion", model.getProducto());
//		    	parametros.put("lote", Integer.toString(model.getLote())); // parseo Int to String
//		    	parametros.put("planta", model.getPlanta());
//		    	parametros.put("operador", model.getUsuarioIngreso());
//		    	parametros.put("vencimiento", model.getVencimiento()); // fechaVencFormateada
//		    	parametros.put("pallet", model.getNumPallet());
//		    	parametros.put("cajas", Integer.toString(model.getCajasInformadas()));
//
//
//		    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
//		    	JasperPrint jasperPrint = JasperFillManager.fillReport(
//						reporte, parametros,
//						new JREmptyDataSource());
//
//				// Exporto en pdf luego de disparar la impresion
//				JRPdfExporter exp = new JRPdfExporter();
//				exp.setExporterInput(new SimpleExporterInput(jasperPrint));
//				exp.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\MyReports\\Pallets\\"+model.getNumPallet()+".pdf"));
//				SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
//				exp.setConfiguration(conf);
//				exp.exportReport();
//				System.out.println("creacion de pdf iterada");
//		    	// --------- cambio orden porque sino al cancelar impresion tampoco se genera pdf
//
//				// primero genero pdf y luego proceso a disparar impresion en papel
//				JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // false = hide_on_close
//				jasperViewer.setVisible(false);
//				JasperPrintManager.printReport( jasperPrint, false); // false = dispara impresion a impresora por default
//																    //  true = abre ventana dialogo
//			} catch (JRException e) {
//				e.printStackTrace();
//	   			JOptionPane.showMessageDialog(null,"Error generando reporte  \n\n "
//	   					+ "SQLException : reports: reportEnvasado() \n" + e);
//			}
//
//		}
	}

}
