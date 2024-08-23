package testCode;

import java.awt.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import modelos.Pedido;

public class TestCheckboxOnRows {
    public static void main(String[] args) {
        JFrame frame = new JFrame("CheckBox Column Example");

        Pedido p = new Pedido();
        p.setRazonSocial("Raul Petrosillo");
        
        // Datos de ejemplo
        Object[][] data = {
                {"Dato 1", false, false},
                {"Dato 2", true, false},
                {"Dato 3", false, true}
        };
        
        Object[][] data2 = new Object[1][4];


        data2[0][0] = p.getRazonSocial();
        data2[0][1] = true;
        data2[0][2] = false;
        
        // Nombres de las columnas
        String[] columnNames = {"Nombre", "Seleccionado", "Flete Origen?"};

        // Creamos un DefaultTableModel personalizado para incluir una columna de checkboxes
        DefaultTableModel model = new DefaultTableModel(data2, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Boolean.class : super.getColumnClass(columnIndex);
            }
        };

        // Creamos un JTable con el DefaultTableModel personalizado
        JTable table = new JTable(model);

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

        frame.add(new JScrollPane(table));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

