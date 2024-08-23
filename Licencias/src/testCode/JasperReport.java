package testCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import reportes.InformeProduccion;

public class JasperReport {

	public static void main(String[] args) {
		try {
			Reporte();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void Reporte() throws JRException {

		ArrayList<InformeProduccion> arrayProductos = new ArrayList<>();

		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(arrayProductos);

		/* Map to hold Jasper report Parameters */
		Map<String, Object> parameters = new HashMap<>();
		//parameters.put("CollectionBeanParam", itemsJRBean);
		parameters.put("calendarFrom", itemsJRBean);

		//read jrxml file and creating jasperdesign object

		try {

			File path = new File(new File("")+"C:\\Users\\pc\\JaspersoftWorkspace\\MyReports\\informeProduccion2023.jrxml");

			InputStream input = new FileInputStream(path);

			JasperDesign jasperDesign = JRXmlLoader.load(input);

			/*compiling jrxml with help of JasperReport class*/
			net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			/* Using jasperReport object to generate PDF */
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // el ,false = hide_on_close
			jasperViewer.setVisible(true);
			jasperViewer.setTitle("Informe de Envasado");


		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}

	}
}
