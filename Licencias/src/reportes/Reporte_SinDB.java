package reportes;

import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte_SinDB {

	public static void main(String[] args) throws JRException {

    	//indico la ruta del objeto a cargar
    	JasperReport reporte2 = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\reporte2.jasper");

    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
    	HashMap parametros = new HashMap();
    	parametros.put("datos_0", "hola papito sos un bostero trolo cogido en Madrid");

    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
    	JasperPrint print = JasperFillManager.fillReport(
				reporte2, parametros,new JREmptyDataSource());

		JasperViewer jasperViewer = new JasperViewer(print, false); // el ,false = hide_on_close
		jasperViewer.setVisible(true);

	}

}
