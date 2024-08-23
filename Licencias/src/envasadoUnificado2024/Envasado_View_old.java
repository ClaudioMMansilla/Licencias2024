package envasadoUnificado2024;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowStateListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

//import javax.swing.border.TitledBorder;
import Atxy2k.CustomTextField.RestrictedTextField;
import conector.Conexion;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;


public class Envasado_View_old extends JFrame {

	// UNICAS VARIABLES GLOBALES, PARA REDIRECCIONAR CONEXION A BASE DE DATOS
//	String tablaInput = "pruebas_2022.env_input_budines";
//	String tablaProductos = "pruebas_2022.productos";
//	String tablaInput = "desarrollo.env_input_budines";

	int vidaUtil = 6;

	String tablaInput = "pruebas_2022.env_input_pdulces";
	String tablaProductos = "pruebas_2022.productos";
	private String domicilio = "Planta: 4945";

	private static String userSes;
	// variables para controlar fecha



	LocalDate fechaHoy = LocalDate.now();
	String fechaHoyFormateada = fechaHoy.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));

	LocalDate fechaVencimiento = LocalDate.now().plusMonths(vidaUtil);
	String fechaVencFormateada = fechaVencimiento.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
	//System.out.println("Vencimiento (formateado): " + formattedDateVenc);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	String formattedLocalDate = fechaVencimiento.format(formatter);



	int diaDelAnio = fechaHoy.getDayOfYear();   // int con el valor del dia
	String lote = Integer.toString(diaDelAnio); // parsea lote para imprimirlo por pantalla
	//System.out.println("Fecha Local: "+ localDate);
	//System.out.println("Dia del a�o: "+ dayOfYear);

	//LocalDate fechaAntes = LocalDate.now().plusMonths(vidaUtil);
	//String fechaAntesFormateada = fechaAntes.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));

	private JTextField field_id,
					   field_cantidad;

	private JButton JBT_guardar;

	// label del parte inferior, donde quedan en pantalla los datos ingresados
	private JLabel info_cantidad,
				   info_descripcion,
				   info_pallets;

	public JLabel label6, // imprime el error "campo id vacio"
				  label12, // opuesto a label6, aca se muestra la descripcion del producto
				  lbl_setearLote, // imprime el lote seteado
				  lbl_setearVenc, // imprime vencimiento seteado
				  lbl_setearPallet; // imprime nro de pallet seteado

	private int codigo;


	public String [] datos;
	public String [] pallet;

	public String stringPallet,
			      stringCantidad,
				  stringDescripProd;
	//cierro declaracion de variables


	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Envasado_View_old frame = new Envasado_View_old();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return
	 */
	public Envasado_View_old() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" REGISTRO "));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setDefaultLookAndFeelDecorated(true);
		setTitle("Planta:  Pan Dulces   |   M�dulo:  Envasado ");
        setBounds(30, 20, 900, 630);
        setResizable(false);
        contentPane.setBackground(Color.DARK_GRAY);

		EventoDeTeclado tecla=new EventoDeTeclado();
		addKeyListener(tecla);
        contentPane.addKeyListener(tecla);

		//Inicio creaci�n de componentes
		JLabel label1 = new JLabel("CARGA DE MERCADERIA A INFORME DE ENVASADO");
		label1.setBounds(25,26,617,23);
		label1.setFont(new Font ("Tw Cen MT", 1, 20));
		label1.setForeground(Color.white);
		contentPane.add(label1);

		JLabel label2 = new JLabel ("Id: ");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setBounds(25,60,63,50);
		label2.setFont(new Font ("Tw Cen MT",0,30));
		label2.setForeground(Color.white);
		contentPane.add(label2);

		JLabel label3 = new JLabel ("Producto: ");
		label3.setBounds(25,151,109,33);
		label3.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		label3.setForeground(Color.white);
		contentPane.add(label3);

		JLabel label4 = new JLabel ("Lote: ");
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		label4.setBounds(304,60,80,50);
		label4.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		label4.setForeground(Color.white);
		contentPane.add(label4);

		JLabel label5 = new JLabel ("Vto: ");
		label5.setHorizontalAlignment(SwingConstants.RIGHT);
		label5.setBounds(517,60,75,50);
		label5.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		label5.setForeground(Color.white);
		contentPane.add(label5);

		label6 = new JLabel (); //label que debe mostrar la descrip del producto
		label6.setBounds(25,184,857,49);
		label6.setFont(new Font("Arial", Font.BOLD, 26));
		label6.setForeground(Color.yellow);
		contentPane.add(label6);

		JLabel label7 = new JLabel ("CAJAS A INFORMAR: ");
		label7.setBounds(151,330,255,72);
		label7.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
		label7.setForeground(Color.white);
		contentPane.add(label7);

		field_id = new JTextField();
		field_id.setBounds(98,60,135,50);
		field_id.setFont(new java.awt.Font("Tw Cen MT", 0, 40));
		contentPane.add(field_id);
		// libreria importada de internet - L132 to L135
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(field_id);
		fieldIdRestringido.setLimit(4);
		fieldIdRestringido.setOnlyNums(true);
		// fin lib importada
		field_id.addKeyListener(tecla);
		field_id.addActionListener(new ActionListener()
		{
			public void actionPerformed(KeyEvent e)
			{
				codigo = e.getKeyCode();
			}
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				if (codigo == 10) {
					KeyIntro();
				}
				else
				{
					System.out.println("saliendo del if en field.id"+ codigo);
				}
			}
		});


		lbl_setearLote = new JLabel (); // imprime lote seteado
		lbl_setearLote.setBounds(398,61,80,50);
		lbl_setearLote.setFont(new Font("Tw Cen MT", Font.PLAIN, 35));
		lbl_setearLote.setForeground(Color.white);
		contentPane.add(lbl_setearLote);
		lbl_setearLote.setText(lote);


		lbl_setearVenc = new JLabel (); // imprime lote seteado
		lbl_setearVenc.setBounds(611,60,224,50);
		lbl_setearVenc.setFont(new Font("Tw Cen MT", Font.PLAIN, 35));
		lbl_setearVenc.setForeground(Color.white);
		contentPane.add(lbl_setearVenc);
		lbl_setearVenc.setText(formattedLocalDate); // fechaVencFormateada


		field_cantidad = new JTextField();
		field_cantidad.setBounds(441,330,150,72);
		field_cantidad.setFont(new java.awt.Font("Tw Cen MT", 0, 70));
		field_cantidad.setBackground(Color.white);
		contentPane.add(field_cantidad);
		// libreria importada de internet - L229 to L231
		RestrictedTextField fieldCantidadRestringido = new RestrictedTextField(field_cantidad);
		fieldCantidadRestringido.setLimit(3);
		fieldCantidadRestringido.setOnlyNums(true);
		// fin lib importada
		field_cantidad.addActionListener(new ActionListener(){
			public void actionPerformed(KeyEvent e){
				codigo = e.getKeyCode();
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (codigo == 10) {
					try {
						KeyIntro();
						JBTGuardar(userSes);

					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					System.out.println("saliendo del if en field.id"+ codigo);
				}
			}
			});


		JBT_guardar = new JButton("Guardar");
		JBT_guardar.setBounds(710,330,141,72);
		JBT_guardar.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
		contentPane.add(JBT_guardar);
		JBT_guardar.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
                try {
					KeyIntro();
					JBTGuardar(userSes);
				} catch (UnknownHostException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

		JPanel panelINF =new JPanel();
		panelINF.setBounds(25, 445, 836, 145);
		contentPane.add(panelINF);
		panelINF.setBorder(javax.swing.BorderFactory.createTitledBorder(" ULTIMO INGRESO "));
		panelINF.setLayout(null);
		panelINF.setBackground(Color.BLACK);
		//panelINF.setForeground(Color.RED);

		JLabel label11 = new JLabel ("Cantidad: ");
		label11.setBounds(549,20,118,41); //(138,239,70,15);
		label11.setFont(new Font("Tw Cen MT", 1, 24));
		label11.setForeground(Color.WHITE);
		panelINF.add(label11);

		JLabel label19 = new JLabel("Ultimo Pallet Liberado: ");
		label19.setHorizontalAlignment(SwingConstants.CENTER);
		label19.setForeground(Color.WHITE);
		label19.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
		label19.setBounds(23, 20, 275, 46);
		panelINF.add(label19);

		label12 = new JLabel (); //label que debe mostrar e
		label12.setBounds(15,164,820,49);
		label12.setFont(new Font("Arial",1,26));
		label12.setForeground(Color.red);
		contentPane.add(label12);

		info_cantidad = new JLabel ();
		info_cantidad.setBounds(692,20,118,41);
		info_cantidad.setFont(new Font("ARIAL",1,30));
		info_cantidad.setForeground(Color.CYAN);
		panelINF.add(info_cantidad);

		info_descripcion = new JLabel();
		info_descripcion.setForeground(Color.CYAN);
		info_descripcion.setFont(new Font("Arial", Font.BOLD, 26));
		info_descripcion.setBounds(23, 93, 803, 41);
		panelINF.add(info_descripcion);

		info_pallets = new JLabel();
		info_pallets.setForeground(Color.CYAN);
		info_pallets.setFont(new Font("Arial", Font.BOLD, 30));
		info_pallets.setBounds(323, 20, 102, 41);
		panelINF.add(info_pallets);

		//Cierro creaci�n de componentes
		dispose();
	}


	//seteo el preparedStatement
    private void KeyIntro() {
		System.out.println("entrando a KeyIntro");
    	Conexion conexion = new Conexion();
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

   		try {
   			System.out.println("entrando a KeyIntro() - try");
   			cn = conexion.Conectar();
   			stm = cn.createStatement();

			System.out.println("entrando a if");
			if(field_id.getText().equals("")) { // valido textfield id si esta vacio
	   			//label6.setForeground(Color.RED);
				label12.setText("ERROR, c�digo ID no fue ingresado");
				label6.setText(null);

	   		}
			else
			{
	   			System.out.println("entrando else");
				String texto_id = field_id.getText();
				int idLocal = Integer.parseInt(texto_id);
	   	   		rs = stm.executeQuery("SELECT * FROM "+tablaProductos+" where idProducto='"+idLocal+"'");
	   		    datos  = new String [2];

		   		while(rs.next())
		   		{
		   	   		System.out.println("Ejecutando en while");
		   			//producto = rs.getString("producto");
			   	   	datos[0] = rs.getString(1); //capturo idProductos de pruebas_2022.productos
		            datos[1] = rs.getString(2);//capturo producto de pruebas_2022.productos
		   			label6.setText(datos[1]);
		   			label12.setText(null);
		   		}

		   		if (datos[0]==null) // valido textfield id sino hubo resultados en consulta
		   		{
		   			label12.setForeground(Color.GREEN);
					label12.setText("ERROR, c�digo ID ingresado no existe en registro");
					label6.setText(null);
		   		}
	   		 }

			System.out.println("Bloque try ejecutado");
		} catch (SQLException e){
			System.out.println(e);
			}

    }

    private void JBTGuardar(String userSes) throws UnknownHostException, SQLException {
    	Conexion conexion = new Conexion();
		Connection cn;
		Statement stm;
		ResultSet rs;
		ResultSet rsDos = null;

		Date diaHoy = new Date();
		DateFormat horaFormatoHMS = new SimpleDateFormat("HH:mm:ss");
		String horaMinSeg = horaFormatoHMS.format(diaHoy);
		DateFormat fechaHoy_diaMesAnio = new SimpleDateFormat("dd/MM/yyyy");
		String diaMesAnio = fechaHoy_diaMesAnio.format(diaHoy);

		DateFormat horaFormatoHM = new SimpleDateFormat("HH:mm");
		String horaMinutos = horaFormatoHM.format(diaHoy);

		// concatenar diaMesAnio + horaMinSeg para la salida del query
		String fechaIngreso = diaMesAnio +" "+ horaMinutos;

		System.out.println("clase conexion instanciada");

		InetAddress identifPC;
		identifPC = InetAddress.getLocalHost();
		String pcUsuario = identifPC.toString();

		System.out.println("entrando en try catch");
   		try {
   			cn = conexion.Conectar();
   			stm = cn.createStatement();
   			rs = stm.executeQuery("SELECT * FROM "+tablaProductos);

   			if (datos[0]==null || field_id.getText().equals("") || field_cantidad.getText().equals("")) {
   				System.out.println("adentro de primer if");
	   			//info_hora.setText(null);
		   		info_cantidad.setText(null);
		   		//info_id.setText(null);
		   		//label6.setForeground(Color.RED);
		   		label12.setText("Alguno de los campos obligatorios es inv�lido");
		   		label6.setText(null);
			}
	   		else
	   		{
	   			System.out.println("adentro de else");

				String texto_id = field_id.getText();
				//int id = Integer.parseInt(texto_id);
				int id = Integer.parseInt(datos[0]);

				String descripProducto = datos[1];

   				stringCantidad = field_cantidad.getText();
				int cantidad = Integer.parseInt(stringCantidad);


	   			if (cantidad < 0 || datos[0] == null)
	   			{
					System.out.println("entrando a segundo if");
			   		//info_hora.setText(null);
				   	info_cantidad.setText(null);
				   	//info_id.setText(null);
				   	info_descripcion.setText(null);
				   	//info_scrap_unid.setText(null);
				   	info_pallets.setText(null);
				   	//label6.setForeground(Color.RED);
				   	label12.setText("Error, la cantidad ingresada no puede ser negativa");
			   		label6.setText(null);
				}
	   			else
	   			{

	   				int flagDisponible = 1;
	   				int flagAjuste = 0;

					String sql = "INSERT into "+tablaInput+" "
							+ "(fechaIngreso, usuarioIngreso, idProducto, planta, producto, lote, vencimiento, cajasInformadas, flagDisponible, flagAjuste) "
							+ "values ('"+fechaIngreso+"', '"+userSes+"', '"+id+"', '"+domicilio+"', '"+descripProducto+"', '"+diaDelAnio+"', '"+formattedLocalDate+"', '"+cantidad+"', '"+flagDisponible+"', '"+flagAjuste+"')";

					int rowsAffected = stm.executeUpdate(sql);
			   		System.out.println("Rows affected: " + rowsAffected);
				   	System.out.println("cantidad es: "+stringCantidad);


			   		field_cantidad.setText(null);
			   		info_cantidad.setText(stringCantidad);
			   		info_descripcion.setText(descripProducto);

			   		stringDescripProd = descripProducto;

			   		System.out.println("SQL Ejecutado");

					try
					{
			   			System.out.println("Try - catch para leer numPallet asignado");
			   			cn = conexion.Conectar();
			   			stm = cn.createStatement();

				   		// codigo 04.2022
				   		rsDos = stm.executeQuery("SELECT numPallet FROM "+tablaInput+" where fechaIngreso ='"+fechaIngreso+"' ");
				   		//rsDos = stm.executeQuery("SELECT * FROM '"+tablaInput+"'");
				   		//pallet = new String [1];

				   		System.out.println("while(rsDos.next())");
				   		while(rsDos.next())
				   		{
				   		System.out.println("Ejecutando en while");
				   		int pallet = rsDos.getInt("numPallet");
				   		stringPallet = Integer.toString(pallet);

				   		System.out.println("Ultimo pallet nro: "+pallet);
				   		//label6.setText(datos[1]);
				   		//label12.setText(null);
				   		info_pallets.setText(stringPallet);
				   		}

					} catch (SQLException e){
						System.out.println(e);
						} // Bloque: resulset para leer numero de pallet asignado

					try {
						Reporte03(userSes);
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 			} //else

	   		} //else

   		}catch (SQLException e){
   			System.out.println(e);
   			} // bloque try catch principal

   	}


	public  void Reporte03(String userSes) throws JRException {

    	//indico la ruta del objeto a cargar
    	JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\MyReports\\rotuloEnvasado.jasper");

    	//creo el mapeo para insertar el par�metro que sera enviado a Jasper Report
    	Map<String, Object> parametros = new HashMap<>();
    	parametros.put("descripcion", stringDescripProd);
    	parametros.put("lote", lote);
    	parametros.put("planta", domicilio);
    	parametros.put("operador", userSes);
    	parametros.put("vencimiento", formattedLocalDate); // fechaVencFormateada
    	parametros.put("pallet", stringPallet);
    	parametros.put("cajas", stringCantidad);


    	//el fillmanager necesita argumento (objeto jasper, el parametro mapeado, conector tipo Connection)
    	JasperPrint jasperPrint = JasperFillManager.fillReport(
				reporte, parametros,
				new JREmptyDataSource());

		// Exporto en pdf luego de disparar la impresion
		JRPdfExporter exp = new JRPdfExporter();
		exp.setExporterInput(new SimpleExporterInput(jasperPrint));
		exp.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\MyReports\\Pallets\\"+stringPallet+".pdf"));
		SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
		exp.setConfiguration(conf);
		exp.exportReport();
		System.out.println("creacion de pdf iterada");
    	// --------- cambio orden porque sino al cancelar impresion tampoco se genera pdf

		// primero genero pdf y luego proceso a disparar impresion en papel
		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // false = hide_on_close
		jasperViewer.setVisible(false);
		JasperPrintManager.printReport( jasperPrint, false); // false = dispara impresion a impresora por default
														    //  true = abre ventana dialogo

	}


	public class EventoDeTeclado implements KeyListener {

    	@Override
    	public void keyPressed(KeyEvent e) { // para escuchar tecla al presionar
    		codigo = e.getKeyCode();
    		System.out.println(codigo);
    	}

    	@Override
    	public void keyReleased(KeyEvent e) { // para escucha tecla al soltar

    	}

    	@Override
    	public void keyTyped(KeyEvent e) { // para escuchar que tecla fue presionada y soltada
    		char letra = e.getKeyChar();
    		System.out.println(letra);
    	}
    }

    class CambiaEstado implements WindowStateListener {
    	@Override
    	public void windowStateChanged(java.awt.event.WindowEvent e) {
    		System.out.println("el estado de la ventana ha cambiado");
    		System.out.println(e.getNewState());//recibo el c�digo que identifica el tipo de evento o�do
    	}
    }

}


