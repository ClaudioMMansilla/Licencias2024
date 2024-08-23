package reportes;


import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import modelos.HojaDeRuta;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class PrintHojaRuta {

	public static void reportHojaDeRuta(HojaDeRuta objectList) {

		if(objectList != null) {

//			for(Iterator<InformeProduccion> iterador = listItemsParam.iterator(); iterador.hasNext();) {
//				InformeProduccion value = iterador.next();
//				if(value.getProduccion() <1
//						&& value.getMercLiberada() <1
//						&& value.getMercVendida() <1
//						&& value.getStock() <1)
//				{
//					iterador.remove();
//				}
//			}


	    	//indico la ruta del objeto a cargar
	    	JasperReport reporte;
			try {
				reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\PrintHojaDeRutaV02.jasper");

				/* Convert List to JRBeanCollectionDataSource */
//				JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(objectList.getPedidosList());



		    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
		    	Map<String, Object> parametros = new HashMap<>();
//		    	parametros.put("dateFrom", getCalendarFrom());
//		    	parametros.put("dateTo", getCalendarTo());
//		    	parametros.put("totalProduccion", strTotalProduccion);
		    	parametros.put("idHojaRutaParam", objectList.getHojaRutaID());
//		    	parametros.put("CollectionBeanParam", itemsJRBean);


//		    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
//		    	JasperPrint jasperPrint = JasperFillManager.fillReport(
//						reporte, parametros,
//						new JREmptyDataSource()); // objectList.getPedidosList().size())

		    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
		    	JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, Servicios.Services.abrirConexionStatic());


				// primero genero pdf y luego proceso a disparar impresion en papel
				JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // false = hide_on_close
				if(!jasperPrint.getPages().isEmpty()) {
					jasperViewer.setVisible(true);
				}
				JasperPrintManager.printReport( jasperPrint, true); // false = dispara impresion a impresora por default
																    //  true = abre ventana dialogo


			} catch (JRException e) {
				e.printStackTrace();
	   			JOptionPane.showMessageDialog(null,"Error generando reporte  \n\n "
	   					+ "SQLException : reports: reportEnvasado() \n" + e);
			}
		}
		else {System.out.println("listItemsParam IS NULL");}
	}


	public static void Print(String idString) throws JRException {
		int id;

		id = Integer.parseInt(idString);
		//indico la ruta del objeto a cargar
		JasperReport reporte;
		try {
			reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\PrintHojaDeRutaV02.jasper");

			/* Convert List to JRBeanCollectionDataSource */
			//				JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(objectList.getPedidosList());



			//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("idHojaRutaParam", id);

			//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, Servicios.Services.abrirConexionStatic());

			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // false = hide_on_close
			// primero genero pdf y luego proceso a disparar impresion en papel
			if(!jasperPrint.getPages().isEmpty()) {
				jasperViewer.setVisible(true);
			}
			//JasperPrintManager.printReport( jasperPrint, true); // false = dispara impresion a impresora por default
			//  true = abre ventana dialogo


		} catch (JRException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error generando reporte  \n\n " + e);
		}
	}
}
