package reportes;

import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte_SinDB2 {

	public static void main(String[] args) throws JRException {

    	JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\informe.jasper");


    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
    	HashMap parametros = new HashMap();
    	parametros.put("desde", "2020-05-01 00:00:00");

    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
    	JasperPrint print = JasperFillManager.fillReport(
				reporte, parametros,new JREmptyDataSource());

		JasperViewer jasperViewer = new JasperViewer(print, false); // el ,false = hide_on_close
		jasperViewer.setVisible(true);



  /*  	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
    	HashMap parametros = new HashMap();
		List lista = new ArrayList();
		lista.add("hola");
    	parametros.put("desde", lista);

    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
    	JasperPrint jasperPrintWindow = JasperFillManager.fillReport(
				reporte, parametros,new JRBeanCollectionDataSource(lista));


    	JasperPrint jasperPrintWindow = JasperFillManager.fillReport(
				"C:\\MyReports\\stock.jasper", null,
				Conexion.Conectar());

		JasperViewer jasperViewer = new JasperViewer(jasperPrintWindow, false); // el ,false = hide_on_close
		jasperViewer.setVisible(true); */
	}

}


/*
 *
 * 			List lista = new ArrayList();


	    	for (int fila = 0; fila<tablaLectura.getRowCount()-1; fila++) {
	    		PrintEnvasado listado = new PrintEnvasado(tablaLectura.getValueAt(fila, 0).toString(),
	    				tablaLectura.getValueAt(fila, 1).toString(),
	    				tablaLectura.getValueAt(fila, 2).toString(),
	    				tablaLectura.getValueAt(fila, 3).toString(),
	    				tablaLectura.getValueAt(fila, 4).toString(),
	    				tablaLectura.getValueAt(fila, 5).toString(),
	    				tablaLectura.getValueAt(fila, 6).toString());
	    				lista.add(listado);
	    	/*
	    	 	    		PrintEnvasado listado = new PrintEnvasado(String.valueOf(tablaLectura.getValueAt(fila, 0).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 1).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 2).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 3).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 4).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 5).toString()),
	    				String.valueOf(tablaLectura.getValueAt(fila, 6).toString()));
	    				lista.add(listado);



	    	JasperReport reporte2 = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\reporte2.jasper");

	    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
	    	Map parametros = new HashMap();

	    	parametros.put("datos_0", lista);

	    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
	    	JasperPrint print = JasperFillManager.fillReport(
					//reporte2, parametros,new JREmptyDataSource());
	    			reporte2, parametros,new JRBeanCollectionDataSource(lista));

			JasperViewer jasperViewer = new JasperViewer(print, false); // el ,false = hide_on_close
			jasperViewer.setVisible(true);

*/
