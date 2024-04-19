package View;

import Controller.CustomerDA;
import Model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class CustomerUI {
    private JPanel custPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane customerScrollPane;
    private JTable customerTable;
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


    public CustomerUI() {
        init();
        CustomerDA customerDA = new CustomerDA();
        ArrayList<Customer> custList = customerDA.getCustomerList();

        DefaultTableModel tm = new DefaultTableModel();
        tm.addColumn("ID");
        tm.addColumn("First Name");
        tm.addColumn("Last Name");

        customerTable.setModel(tm);
        for(Customer c: custList) {
            Vector<String> rowObj = new Vector<>(3);
            rowObj.add(0, c.getCustomerID());
            rowObj.add(1,c.getCustomerName_first());
            rowObj.add(2,c.getCustomerName_last());
            tm.addRow(rowObj);
        }

    }

    private void init() {
        custPanel = new JPanel();
        custPanel.setLayout(new GridBagLayout());
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
        custPanel.add(viewPanel, gbc);
        customerTable = new JTable();
        customerTable.setRowHeight(30);
        customerScrollPane = new JScrollPane(customerTable);
        viewPanel.add(customerScrollPane);

        initEditPanel();


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
        custPanel.add(editPanel, gbc);
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
        return custPanel;
    }

    public JTable getCustomerTable() {
        return customerTable;
    }

}
