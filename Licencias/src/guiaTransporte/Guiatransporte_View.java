package guiaTransporte;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import modelos.GuiaTransporte;
import modelos.HojaDeRuta;
import modelos.Pedido;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

@SuppressWarnings("serial")
public class Guiatransporte_View extends JFrame {
	
    // Creamos un JTable con el DefaultTableModel personalizado
	public DefaultTableModel model;
	public JFrame frame;
    public JTable table;
    public HojaDeRuta roadmap;
    public Object[][] data;
    public JButton button; 
    private GuiaTransporte guiaTte;
    private ArrayList<GuiaTransporte> list;
    

    public Guiatransporte_View(Object[][] data3) {
    	data = data3;
        
        // Nombres de las columnas
        String[] columnNames = {"Cliente", "¿Emitir Guia?", "¿Flete sin cargo?"};
        
        // Creamos un DefaultTableModel personalizado para incluir una columna de checkboxes
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Boolean.class : super.getColumnClass(columnIndex);
            }
        };

        // Creamos un JTable con el DefaultTableModel personalizado
        table = new JTable(model);
        
        // Ajustamos el alto de todas las filas a 30 píxeles
        table.setRowHeight(20);
        
        // Ajustamos el ancho de la segunda columna a 100 píxeles
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(10);
        table.getColumnModel().getColumn(2).setPreferredWidth(35);

        
        // Creamos un renderizador de celdas para la columna de checkboxes
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected((Boolean) value);
                return checkBox;
            }
        });

        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected((Boolean) value);
                return checkBox;
            }
        });
        
        // Creamos un editor de celdas para la columna de checkboxes
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        // Creamos un panel para contener la tabla y el botón
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Creamos un botón
        button = new JButton("Grabar Guia de transporte");
        button.setBackground(Color.green);
        panel.add(button, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
    	
        frame = new JFrame("Emisión de Guias de Transporte");
        frame.setResizable(false);
        frame.setSize(300, 250);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    	
    }
    
    public void disposeView() {
    	frame.dispose();
    }
    
}



