package reportes;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte {

	public static void main(String[] args) throws JRException {
	//public static void metodo() throws JRException {

		// descarga dentro del mismo proyecto
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				"C:\\MyReports\\stock.jasper", null,
				ConexionLH.Conectar());
		JRPdfExporter exp = new JRPdfExporter();
		exp.setExporterInput(new SimpleExporterInput(jasperPrint));
		exp.setExporterOutput(new SimpleOutputStreamExporterOutput("stock.pdf"));
		SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
		exp.setConfiguration(conf);
		exp.exportReport();
		System.out.println("creacion de pdf iterada");

		// se muestra en una ventana aparte para su descarga
		JasperPrint jasperPrintWindow = JasperFillManager.fillReport(
				"C:\\MyReports\\stock.jasper", null,
				ConexionLH.Conectar());
		JasperViewer jasperViewer = new JasperViewer(jasperPrintWindow);
		jasperViewer.setVisible(true);
		System.out.println("jasperviewer iterado");

	}

/*
 		public  void Reporte() throws JRException {

			// String [] datos  = new String [8];

			//obtener la hora y salida por pantalla con formato:
			Date date = new Date();
			DateFormat formatDate = new SimpleDateFormat("dd:MM:yyyy HH:mm");
			String fecha = formatDate.format(date);

	    	JasperReport reporte2 = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\reporte2.jasper");

	    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
	    	HashMap parametros = new HashMap();

	    	int fila = 0;
	    	String dataje = "";
	    	for (fila = 0; fila<tablaLectura.getRowCount(); fila++) {
	    		dataje = dataje+String.valueOf(tablaLectura.getValueAt(fila, 0))+" "+
	    		String.valueOf(tablaLectura.getValueAt(fila, 1))+" "+
	    		String.valueOf(tablaLectura.getValueAt(fila, 2))+" "+
	    		String.valueOf(tablaLectura.getValueAt(fila, 3))+" "+
	    		String.valueOf(tablaLectura.getValueAt(fila, 4))+" "+
	    		String.valueOf(tablaLectura.getValueAt(fila, 5))+" "+
	    		String.valueOf(tablaLectura.getValueAt(fila, 6))+" ";

	    	}


	    	parametros.put("datos_0", dataje);

	    	parametros.put("datos_0", datos[0]);
	    	parametros.put("datos_1", datos[1]);
	    	parametros.put("datos_2", datos[2]);
	    	parametros.put("datos_3", datos[3]);
	    	parametros.put("datos_4", datos[4]);
	    	parametros.put("datos_5", datos[5]);
	    	parametros.put("datos_6", datos[6]);



	    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
	    	JasperPrint print = JasperFillManager.fillReport(
					reporte2, parametros,new JREmptyDataSource());
	    			//reporte2, parametros,new JRBeanCollectionDataSource());

			JasperViewer jasperViewer = new JasperViewer(print, false); // el ,false = hide_on_close
			jasperViewer.setVisible(true);
 */
}