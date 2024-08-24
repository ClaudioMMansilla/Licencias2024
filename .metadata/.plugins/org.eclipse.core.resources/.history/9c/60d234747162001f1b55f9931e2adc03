package productoTerminado;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowStateListener;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JFrame;

import Atxy2k.CustomTextField.RestrictedTextField;
import LogisticaNew.Services;



@SuppressWarnings("serial")
public class Stock_OutputManual extends javax.swing.JFrame {

	String tablaProductos = "pruebas_2022.productos";
	//String tablaProductos = "testing.productos";
	private Services servicio = new Services();

    // Variables declaration - do not modify
    private static String userSes;
    private String movim_usuario = "Egreso";
    private javax.swing.JButton JBTCargar;
    private javax.swing.JButton JBTGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelMsjGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField tfield_cantidad;
    private javax.swing.JTextField tfield_id;
	private int codigo;
    // End of variables declaration

    public static void main(String args[]) {
    	JFrame.setDefaultLookAndFeelDecorated(true);
    	JDialog.setDefaultLookAndFeelDecorated(true);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                new Stock_OutputManual(userSes).setVisible(true);
            }
        });
    }



    public Stock_OutputManual(String userSes) {
        initComponents(userSes);

        setBounds(450, 20, 370, 260);
        //setSize(370, 255);
        //setLocationRelativeTo(null);
        setResizable(true);
        setTitle("");
        setDefaultCloseOperation(HIDE_ON_CLOSE);

    }

    private void initComponents(String userSes) {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfield_id = new javax.swing.JTextField();
        tfield_cantidad = new javax.swing.JTextField();
        JBTGuardar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelMsjGuardar = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        JBTCargar = new javax.swing.JButton();

    	addWindowListener(new Ventana()); //pongo la ventana en oyente
		EventoDeTeclado tecla=new EventoDeTeclado();
		addKeyListener(tecla);
		jPanel1.addKeyListener(tecla);

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("ORDEN DE EGRESO (manual)"));
        jPanel1.setMaximumSize(new java.awt.Dimension(250, 20));
        jPanel1.setPreferredSize(new java.awt.Dimension(360, 225));

        jLabel1.setText("ID Producto");
        jLabel2.setText("Descripci�n");
        jLabel3.setText("Cantidad");


		// libreria importada de internet - L132 to L135
		RestrictedTextField fieldIdRestringido = new RestrictedTextField(tfield_id);
		fieldIdRestringido.setLimit(4);
		fieldIdRestringido.setOnlyNums(true);
		// fin lib importada
        tfield_id.addKeyListener(tecla);
        tfield_id.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				if (codigo == 10) {
					JBTCargarActionPerformed(e);
				}
				else
				{
					System.out.println("saliendo del if en field.id"+ codigo);
				}
			}
		});


		// libreria importada de internet - L132 to L135
		RestrictedTextField fieldIdRestringido2 = new RestrictedTextField(tfield_cantidad);
		fieldIdRestringido2.setLimit(3);
		fieldIdRestringido2.setOnlyNums(true);
		// fin lib importada
        tfield_cantidad.addKeyListener(tecla);
        tfield_cantidad.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				if (codigo == 10) {
					try {
						JBTGuardarActionPerformed(userSes, e);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					System.out.println("saliendo del if en field.id"+ codigo);
				}
			}
		});


        JBTGuardar.setText("Guardar");
        JBTGuardar.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                try {
                	JBTGuardarActionPerformed(userSes, e);
				} catch (UnknownHostException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Comprobaci�n del sistema"));

        jLabelMsjGuardar.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelMsjGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabelMsjGuardar)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jLabel4.setText(" ");
        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 1, 12));
        jLabel4.setMaximumSize(new Dimension(50, 10));

        JBTCargar.setText("Cargar");
        JBTCargar.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBTCargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(JBTCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(tfield_id, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(tfield_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                                        .addComponent(JBTGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(2, 2, 2))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfield_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(JBTCargar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(JBTGuardar)
                    .addComponent(tfield_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>


    private void JBTGuardarActionPerformed(String userSes, java.awt.event.ActionEvent evt) throws UnknownHostException {

		Connection cn;
		Statement stm;
		ResultSet rs;

		String texto_id = tfield_id.getText();
		int id = Integer.parseInt(texto_id);
		String texto_cantidad = tfield_cantidad.getText();
		int cantidad_usuario = Integer.parseInt(texto_cantidad);

//		InetAddress identifPC = InetAddress.getLocalHost();
//		String identidad = identifPC.toString();

   		try {
   			cn = servicio.abrirConexion();
   			stm = cn.createStatement();
   			rs = stm.executeQuery("SELECT * FROM "+tablaProductos+" where idProducto ='"+id+"' ");
   			while(rs.next())
   			{

   				int cantidad_dbase = rs.getInt("stock");
	   			int stock_suma = (cantidad_dbase - cantidad_usuario);
		    	System.out.println("bucle while ejecutado");

   				if(tfield_id != null && tfield_cantidad != null &&texto_cantidad != null && cantidad_usuario > 0) {
   					String sql = "update "+tablaProductos+" set stock= '"+stock_suma+"', movimCantidad='"+cantidad_usuario+"', movimTipo='"+movim_usuario+"', movimUsuario='"+userSes+"', "
   							+ "idCliente ='', razonSocial ='', comentario='Egreso manual de stock' "
   							+ "where idProducto='"+id+"'";
   					int rowsAffected = stm.executeUpdate(sql);
   					System.out.println("Rows affected: " + rowsAffected);
   					System.out.println("SQL Ejecutado");
   					jLabelMsjGuardar.setText("Tu último ingreso realizado fue " + " ID:   " + id +"   |   "+" Cantidad:   "+ cantidad_usuario);
   					tfield_id.setText(null);
   					tfield_cantidad.setText(null);
   					jLabel4.setText(null);
   				}
   				else
   				{
   					jLabelMsjGuardar.setText("Error, actualización no realizada");
   				}
   			}

   		}catch (SQLException e){
   			System.out.println(e);
   		}
    }

    private void JBTCargarActionPerformed(java.awt.event.ActionEvent evt) {

		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

   		try {
   			cn = servicio.abrirConexion();
   			stm = cn.createStatement();

   			jLabel4.setText("Error, nos ha ingresado ningún ID");
   			String texto_id = tfield_id.getText();
   			int id = Integer.parseInt(texto_id);
   			rs = stm.executeQuery("SELECT * FROM "+tablaProductos+" where idProducto ='"+id+"'");
   			JBTCargar.setBackground(Color.BLUE);
   			JBTCargar.setForeground(Color.WHITE);
   			//jPanel2.setBackground(Color.BLUE);

   			while(rs.next()) {
   				//int IdProductos = rs.getInt("IdProductos");
   				String producto = rs.getString("producto");
   				//int cantidad = rs.getInt("cantidad");

   				if(tfield_id != null) {
   					jLabel4.setText(producto);

   					}
   				else {
   					jLabel4.setText("Error, no has ingresado ningún ID");

   					}
   			}}catch (SQLException e){
   				System.out.println(e);
   			}
    }


	class Ventana extends WindowAdapter { //extends refiere que hereda los atributos de clase WindowAdapter

		@Override
		public void windowIconified(java.awt.event.WindowEvent e) {
			System.out.println("windowIconified: cambio a estado minimizado");
		}
		@Override
		public void windowClosing(java.awt.event.WindowEvent e) {
			System.out.println("dispose ejecutado");
			dispose();

		}
		@Override
		public void windowDeiconified(java.awt.event.WindowEvent e) {
			System.out.println("windowDeiconified: cambio a estado normal");
		}
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


