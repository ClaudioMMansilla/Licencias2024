package informeProduccion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import EventosGUI.EventoDeTeclado;
import Servicios.Services;
import fecha.Fecha;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import reportes.InformeProduccion;

public class InfoMovimPT_Controller {

	private final String db = "pruebas_2022.";
	private final String tableEnvBudines = "env_input_budines";
	private final String tableEnvPDulces = "env_input_pdulces";
	final String tablaProductos = "productos";
	final String tableRegistros = "reg_movim_productos";

	private InfoMovimPT_View view = new InfoMovimPT_View();
	private InfoMovimPT_Model model = new InfoMovimPT_Model();
	private final String usuario;

	public Map<Integer, Integer> mapProduccion = new HashMap<>();
	public Map<Integer, Integer> mapMercLiberada = new HashMap<>();
	public Map<Integer, Integer> mapMercVendida = new HashMap<>();
	public List<InformeProduccion> listItems;

	private double totalProduccion = 0F;
	private double totalVentas = 0F;


	public InfoMovimPT_Controller(InfoMovimPT_View view, InfoMovimPT_Model model, String usuario)
	{

		this.view = view;
		this.model = model;
		this.usuario = usuario;

		EventoDeTeclado tecla = new EventoDeTeclado();
		view.addKeyListener(tecla);

		view.btnImprimir.addActionListener(new ActionListener(){
	        @Override
			public void actionPerformed(ActionEvent e) {
	        	//sendToReport();
				reportEnvasado(listItems);
	        }
	    });


		view.rdbtnBudin.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent evt) {
        		try {
        			listItems = addObjectToList();
        			mapProduccion = injectDataToMap(querySQLSumAllDateIngreso(db, tableEnvBudines, "cajasInformadas"));
        			mapMercLiberada = injectDataToMap(querySQLSumAllDateEgreso(db, tableEnvBudines, "cajasValidadas"));
        			mapMercVendida = injectDataToMap(querySQLSumAllGroupBy(db, tableRegistros, "movimCantidad", "Egreso"));
	        		mapToSetters(mapProduccion, mapMercLiberada, mapMercVendida, listItems);
	        		sendDataToTable();
	        		iterateMap();
	        		setDataToTextfield(totalProduccion, totalVentas);
        		} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });

		view.rdbtnPDulce.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent evt) {
        		try {
        			listItems = addObjectToList();
        			mapProduccion = injectDataToMap(querySQLSumAllDateIngreso(db, tableEnvPDulces, "cajasInformadas"));
        			mapMercLiberada = injectDataToMap(querySQLSumAllDateEgreso(db, tableEnvPDulces, "cajasValidadas"));
        			mapMercVendida = injectDataToMap(querySQLSumAllGroupBy(db, tableRegistros, "movimCantidad", "Egreso"));
	        		mapToSetters(mapProduccion, mapMercLiberada, mapMercVendida, listItems);
	        		sendDataToTable();
	        		iterateMap();
	        		setDataToTextfield(totalProduccion, totalVentas);
        		} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });

		view.rdbtnTotal.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent evt) {
        		try {
        			listItems = addObjectToList();
                	mapProduccion = injectMultipleMapsDateIngreso(db, tableEnvBudines, tableEnvPDulces, "cajasInformadas");
                	mapMercLiberada = injectMultipleMapsDateEgreso(db, tableEnvBudines, tableEnvPDulces, "cajasValidadas");
                	mapMercVendida = injectDataToMap(querySQLSumAllGroupBy(db, tableRegistros, "movimCantidad", "Egreso"));
        			mapToSetters(mapProduccion, mapMercLiberada, mapMercVendida, listItems);
        			sendDataToTable();
            		iterateMap();
            		setDataToTextfield(totalProduccion, totalVentas);
        		} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });

		iniciar();

	}

	public void actionPerformed(ActionEvent e) {

	}

	public void iniciar() {
		this.view.setTitle("Movimiento de producto terminado");
		this.view.setLocationRelativeTo(null);
	}

	public String getCalendarFrom() {
		return Fecha.formatterDateBD((this.view.calendarFrom.getDate()));
	}

	public String getCalendarTo() {
		return Fecha.formatterDateBD((this.view.calendarTo.getDate()));
	}


	private int buscaProductoEnModelo(String codigo, DefaultTableModel modelo)
	{
		int renglon = 0;
		if (codigo != null && modelo != null)
		{
			for (renglon = 0; renglon <modelo.getRowCount(); renglon++)
			{
				String codigoEnRenglon = (String)modelo.getValueAt(renglon, 0);
				if (codigo.equals(codigoEnRenglon))
				{
					return renglon;
				}
			}
			renglon = -1;
		}
		return renglon;
	}


	public void sendDataToTable() {

		view.tablaLectura.removeAll();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Id");
		modelo.addColumn("Producto");
		modelo.addColumn("Producido");
		modelo.addColumn("Liberado");
		modelo.addColumn("Entregado");
		modelo.addColumn("Stock");
		view.tablaLectura.setModel(modelo);
		String [] datos = new String[6];

		//Instanciamos el TableRowSorter y lo añadimos al JTable
		TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
		view.tablaLectura.setRowSorter(elQueOrdena);

		//indico ancho de cada columna
		TableColumnModel columnModel = view.tablaLectura.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(5);
		columnModel.getColumn(1).setPreferredWidth(350);
		columnModel.getColumn(2).setPreferredWidth(15);
		columnModel.getColumn(3).setPreferredWidth(15);
		columnModel.getColumn(4).setPreferredWidth(15);
		columnModel.getColumn(5).setPreferredWidth(15);

		view.tablaLectura.setModel(modelo);

		for(int i=0;i<listItems.size()-1;i++)
		{
		   datos[0] = Integer.toString(listItems.get(i).getIdProducto());
		   datos[1] = listItems.get(i).getProducto();
		   datos[2] = Integer.toString(listItems.get(i).getProduccion());
		   datos[3] = Integer.toString(listItems.get(i).getMercLiberada());
		   datos[4] = Integer.toString(listItems.get(i).getMercVendida());
		   datos[5] = Integer.toString(listItems.get(i).getStock());

		   if( !(Fecha.parserStringToInt(datos[2]) ==0
				   && Fecha.parserStringToInt(datos[3]) ==0
				   && Fecha.parserStringToInt(datos[4]) ==0
				   && Fecha.parserStringToInt(datos[5]) ==0) ){
			   modelo.addRow(datos);
		   }
		   //modelo.addRow(datos);
		}

	}

	public void setDataToTextfield(double produccion, double ventas) throws SQLException {
		String rounded = String.format("%.0f", produccion);
		view.txtProduccion.setText(rounded);

		String rounded2 = String.format("%.0f", ventas);
		//rounded = String.format("%.0f", ventas);
		view.txtLogistica.setText(rounded2);
	}

	public void iterateMap(){
		double resultA = 0;
		double resultB = 0;
		for (InformeProduccion listItem : listItems) {
			resultA += listItem.getProduccion();
			resultB += listItem.getMercVendida();
		}
		totalProduccion = resultA;
		totalVentas = resultB;
	}

	public Map<Integer, Integer> injectDataToMap(String query) throws SQLException
	{
		Map<Integer, Integer> bufferMap = new HashMap<>();
		Connection cn = Services.abrirConexionStatic();
		ResultSet rs = null;
		PreparedStatement consultaIngreso = cn.prepareStatement(query);
		consultaIngreso.setString(1, getCalendarFrom());
		consultaIngreso.setString(2, getCalendarTo());
		rs = consultaIngreso.executeQuery();

		while(rs.next()) {
			bufferMap.put( rs.getInt(1), rs.getInt(2) );
		}
		cn.close();
		System.out.println("cn.close "+this.getClass());
		return bufferMap;
		//int mpSize = map.size();
		//System.out.println("Elements in "+map+" - Length: "+ mpSize);
	}

	public Map<Integer, Integer> voidInjectDataToMap(Map<Integer, Integer> map, String query) throws SQLException
	{
		Connection cn = Services.abrirConexionStatic();
		ResultSet rs = null;
		PreparedStatement consultaIngreso = cn.prepareStatement(query);
		consultaIngreso.setString(1, getCalendarFrom());
		consultaIngreso.setString(2, getCalendarTo());
		rs = consultaIngreso.executeQuery();

		while(rs.next()) {
			map.put( rs.getInt(1), rs.getInt(2) );
		}
		cn.close();
		System.out.println("cn.close "+this.getClass());
		return map;
		//int mpSize = map.size();
		//System.out.println("Elements in "+map+" - Length: "+ mpSize);
	}


	public ArrayList<InformeProduccion> addObjectToList()
	{
		ArrayList<InformeProduccion> arrayProductos = new ArrayList<>();
		ResultSet rs = Services.getResulSetStatic(queryProductos);
		try {
			while(rs.next()) {
				//map.put( rs.getInt(1), rs.getInt(2) );
				arrayProductos.add(new InformeProduccion(
						rs.getInt(1), rs.getString(2), 0, 0, 0, rs.getInt(3) ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrayProductos;
	}

	/**
	 * Inject data from DB to hashmaps, from hashmaps to List<>
	 * @param mapP: key id, value produccion from DB
	 * @param mapML: key id, value mercLiberada from DB
	 * @param mapMV: key id, value mercVendida from DB
	 * @param list of InformeProduccion obj
	 */
	public void mapToSetters(Map<Integer, Integer> mapP, Map<Integer, Integer> mapML,
			Map<Integer, Integer> mapMV, List<InformeProduccion> list)
	{
		int id = 0;
		Object bufferA;
		Object bufferB;
		Object bufferC;

		for (InformeProduccion listItem : listItems) {
			id = listItem.getIdProducto();
			bufferA = mapP.get(id);
			bufferB = mapML.get(id);
			bufferC = mapMV.get(id);

			if(bufferA != null)
			{
				listItem.setProduccion(Fecha.parserStringToInt(bufferA.toString()));
			}

			if(bufferB != null)
			{
				listItem.setMercLiberada(Fecha.parserStringToInt(bufferB.toString()));
			}

			if(bufferC != null)
			{
				listItem.setMercVendida(Fecha.parserStringToInt(bufferC.toString()));
			}

//			if( listItems.get(i).getProduccion() <1
//					&& listItems.get(i).getMercLiberada() <1
//					&& listItems.get(i).getMercVendida() <1 )
//			{
//				listItems.remove(i);
//			}
		}
	}
	/**
	 * Inject two map from DB, then call function combineMaps to generate a combined map
	 * @param mapA map one to read
	 * @param mapB map two to read
	 * @param tableA target DB table
	 * @param tableB target DB table
	 * @param dbColumn column to read in DB
	 * @return a new map with data combined
	 */
	private Map<Integer, Integer> injectMultipleMapsDateIngreso(String db, String tableA, String tableB, String dbColumn)
	{
		Map<Integer, Integer> bufferMapA = new HashMap<>();
		Map<Integer, Integer> bufferMapB = new HashMap<>();
		Map<Integer, Integer> bufferMap = new HashMap<>();
		try {
			bufferMapA = injectDataToMap(querySQLSumAllDateIngreso(db, tableA, dbColumn));
			bufferMapB = injectDataToMap(querySQLSumAllDateIngreso(db, tableB, dbColumn));
			bufferMap = combineMaps(bufferMapA, bufferMapB);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bufferMap;
	}

	/**
	 * Inject two map from DB, then call function combineMaps to generate a combined map
	 * @param mapA map one to read
	 * @param mapB map two to read
	 * @param tableA target DB table
	 * @param tableB target DB table
	 * @param dbColumn column to read in DB
	 * @return a new map with data combined
	 */
	private Map<Integer, Integer> injectMultipleMapsDateEgreso(String db, String tableA, String tableB, String dbColumn)
	{
		Map<Integer, Integer> bufferMapA = new HashMap<>();
		Map<Integer, Integer> bufferMapB = new HashMap<>();
		Map<Integer, Integer> bufferMap = new HashMap<>();
		try {
			bufferMapA = injectDataToMap(querySQLSumAllDateEgreso(db, tableA, dbColumn));
			bufferMapB = injectDataToMap(querySQLSumAllDateEgreso(db, tableB, dbColumn));
			bufferMap = combineMaps(bufferMapA, bufferMapB);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bufferMap;
	}

	/**
	 * Itera dos hashmap, si key == key entonces suma values. Else, agrega K,V al hashmap
	 * @param mapA hashmap a iterar para combinar
	 * @param mapB hashmap a iterar para combinar
	 * @return un nuevo hashmap combinado o bien, uno creado entre keys no repetidas
	 */
	private Map<Integer, Integer> combineMaps(Map<Integer, Integer> mapA, Map<Integer, Integer> mapB)
	{
		Map<Integer, Integer> bufferMap = new HashMap<>();
		for(int key : mapA.keySet())
		{
			if(mapB.containsKey(key))
			{
				int sum = mapA.get(key) + mapB.get(key);
				bufferMap.put(key, sum);
			}
			else
			{
				bufferMap.put(key, mapA.get(key));
			}
		}

		for(int key : mapB.keySet())
		{
			if(! mapA.containsKey(key))
			{
				bufferMap.put(key, mapB.get(key));
			}
		}
		return bufferMap;
	}


	public void reportEnvasado(List<InformeProduccion> listItemsParam) {

		if(listItemsParam != null) {

			for(Iterator<InformeProduccion> iterador = listItemsParam.iterator(); iterador.hasNext();) {
				InformeProduccion value = iterador.next();
				if(value.getProduccion() <1
						&& value.getMercLiberada() <1
						&& value.getMercVendida() <1
						&& value.getStock() <1)
				{
					iterador.remove();
				}
			}

	    	//indico la ruta del objeto a cargar
	    	JasperReport reporte;
			try {
				reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\Informe_Produccion.jasper");

				/* Convert List to JRBeanCollectionDataSource */
				JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItemsParam);

				String strTotalProduccion = String.format("%.0f", totalProduccion);
				String strTotalVentas = String.format("%.0f", totalVentas);

		    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
		    	Map<String, Object> parametros = new HashMap<>();
		    	parametros.put("dateFrom", getCalendarFrom());
		    	parametros.put("dateTo", getCalendarTo());
		    	parametros.put("totalProduccion", strTotalProduccion);
		    	parametros.put("totalVentas", strTotalVentas);
		    	parametros.put("CollectionBeanParam", itemsJRBean);


		    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
		    	JasperPrint jasperPrint = JasperFillManager.fillReport(
						reporte, parametros,
						new JREmptyDataSource());


				// primero genero pdf y luego proceso a disparar impresion en papel
				JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // false = hide_on_close
				jasperViewer.setVisible(true);
				JasperPrintManager.printReport( jasperPrint, true); // false = dispara impresion a impresora por default
																    //  true = abre ventana dialogo
			} catch (JRException e) {
				e.printStackTrace();
	   			JOptionPane.showMessageDialog(null,"Error generando reporte  \n\n "
	   					+ "SQLException : reports: reportEnvasado() \n" + e);
			}
		}
	}


	/*
	 * SENTENCIAS SQL PARA INYECTAR OBJETOS DESDE DB
	 * */
	private final String queryProductos = "SELECT idProducto, producto, stock FROM "+db+tablaProductos+" ;";

	private String querySQLSumAllDateIngreso(String tableDB, String tableParam, String columnName) {
		return "SELECT idProducto, SUM(ALL "+columnName+") as total FROM "+tableDB+tableParam+" "
				+ "WHERE dateIngreso >=? AND dateIngreso <=? GROUP BY idProducto;";
	}

	private String querySQLSumAllDateEgreso(String tableDB, String tableParam, String columnName) {
		return "SELECT idProducto, SUM(ALL "+columnName+") as total FROM "+tableDB+tableParam+" "
				+ "WHERE dateEgreso >=? AND dateEgreso <=? GROUP BY idProducto;";
	}

	private String querySQLSumAllGroupBy(String tableDB, String tableParam, String columnName, String filter) {
		return "SELECT idProducto, SUM(ALL "+columnName+") as total FROM "+tableDB+tableParam+" "
				+ "WHERE movimTipo ='"+filter+"' AND fecha >=? AND fecha <=? GROUP BY idProducto;";
	}


	private String querySQLSelect(String tableDB){
		return "SELECT idProducto, producto, stock FROM "+tableDB+" ;";
	}


}
