package View;

import Controller.CustomerDA;
import Controller.SupplierDA;
import Model.Customer;
import Model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class SupplierUI {
    private JPanel supPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane supScrollPane;
    private JTable supTable;
    private JButton editButton;
    private JButton delButton;
    private JButton addButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox1;
    private JTextField textField6;
    SupplierDA supDA;


    public SupplierUI() {
        init();
        supDA = new SupplierDA();
        ArrayList<Supplier> supList = supDA.getSupList();

        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tm.addColumn("ID");
        tm.addColumn("Name");
        tm.addColumn("Website");
        supTable.setModel(tm);
        for(Supplier s: supList) {
            Vector<Object> rowObj = new Vector<>(3);
            rowObj.add(0, s.getId());
            rowObj.add(1, s.getName());
            rowObj.add(2, s.getWebsite());
            tm.addRow(rowObj);
        }

    }

    private void init() {
        supPanel = new JPanel();
        supPanel.setLayout(new GridBagLayout());
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout(0, 0));
        viewPanel.setBackground(new Color(-887852));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        supPanel.add(viewPanel, gbc);
        supTable = new JTable();
        supTable.setRowHeight(30);

        supTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    System.out.println("Double click detected @ row " + row);
                    System.out.println("SupplierID: " + target.getValueAt(row,0));
                    moreInfo((int) target.getValueAt(row,0));
                }
            }
        });
        supScrollPane = new JScrollPane(supTable);
        viewPanel.add(supScrollPane);

        initEditPanel();


    }

    private void moreInfo(int supplierID) {
        Supplier sup = supDA.getSupplier(supplierID);
        JFrame jFrame = new JFrame("Supplier Information");
        jFrame.setLayout(new GridLayout(6,2));
        JTextField txtSupplierId = new JTextField();
        JTextField txtSupplierName = new JTextField();
        JTextField txtSupplierAddress = new JTextField();
        JTextField txtSupplierEmail = new JTextField();
        JTextField txtSupplierPhone = new JTextField();
        JTextField textProductIDSupplier = new JTextField();

        jFrame.add(new JLabel("Supplier ID:"));
        jFrame.add(txtSupplierId);
        jFrame.add(new JLabel("Supplier Name:"));
        jFrame.add(txtSupplierName);
        jFrame.add(new JLabel("Address:"));
        jFrame.add(txtSupplierAddress);
        jFrame.add(new JLabel("Email:"));
        jFrame.add(txtSupplierEmail);
        jFrame.add(new JLabel("Phone:"));
        jFrame.add(txtSupplierPhone);
        jFrame.add(new JLabel("Product ID:"));
        jFrame.add(textProductIDSupplier);


        txtSupplierName.setText(sup.getName());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);


    }

    private void initEditPanel() {
        GridBagConstraints gbc;
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());
        editPanel.setBackground(new Color(-15076806));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        supPanel.add(editPanel, gbc);
        delButton = new JButton();
        delButton.setText("Delete");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        editPanel.add(delButton, gbc);
        addButton = new JButton();
        addButton.setText("Add");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        editPanel.add(addButton, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setBackground(new Color(-2737422));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        editPanel.add(panel1, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("UserID");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(label1, gbc);
        textField3 = new JTextField();
        textField3.setColumns(5);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(textField3, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("First");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(label2, gbc);
        textField1 = new JTextField();
        textField1.setColumns(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(textField1, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Last");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(label3, gbc);
        textField2 = new JTextField();
        textField2.setColumns(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(textField2, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Street");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(label4, gbc);
        textField4 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(textField4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("City");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(label5, gbc);
        textField5 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(textField5, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("State");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(label6, gbc);
        comboBox1 = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(comboBox1, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("Zip");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(label7, gbc);
        textField6 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(textField6, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        panel1.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        panel1.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 2;
        panel1.add(spacer3, gbc);
        editButton = new JButton();
        editButton.setText("Edit");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        editPanel.add(editButton, gbc);
    }

    public JComponent getRootComponent() {
        return supPanel;
    }


}
