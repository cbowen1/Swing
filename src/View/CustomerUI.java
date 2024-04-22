package View;

import Controller.CustomerDA;
import Model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class CustomerUI {
    private JPanel custPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane customerScrollPane;
    private JTable customerTable;

    JFrame infoFrame;
    CustomerDA customerDA;

    public CustomerUI() {
        init();
        customerDA = new CustomerDA();
        table_update();
    }

    private void table_update() {
        ArrayList<Customer> custList = customerDA.getCustomerList();

        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tm.addColumn("ID");
        tm.addColumn("First Name");
        tm.addColumn("Last Name");

        customerTable.setModel(tm);
        for(Customer c: custList) {
            Vector<Object> rowObj = new Vector<>(3);
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

        customerTable.addMouseListener(new MouseAdapter() {
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
        customerScrollPane = new JScrollPane(customerTable);
        viewPanel.add(customerScrollPane);

        initEditPanel();


    }

    private void moreInfo(Integer customerID) {
        if(infoFrame != null) {
            infoFrame.dispose();
        }

        infoFrame = new JFrame("Customer Information");
        infoFrame.setLayout(new GridLayout(9,2));

        JTextField txtCustID = new JTextField();
        JTextField txtCustFirst = new JTextField();
        JTextField txtCustLast = new JTextField();
        JTextField txtCustEmail = new JTextField();
        JTextField txtCustStreet = new JTextField();
        JTextField txtCustCity = new JTextField();
        JTextField txtCustState = new JTextField();
        JTextField txtCustZip = new JTextField();

        JLabel idLabel = new JLabel("ID:");
        txtCustID.setEnabled(false);
        txtCustStreet.setColumns(25);

        infoFrame.add(idLabel);
        infoFrame.add(txtCustID);
        infoFrame.add(new JLabel("First Name:"));
        infoFrame.add(txtCustFirst);
        infoFrame.add(new JLabel("Last Name:"));
        infoFrame.add(txtCustLast);
        infoFrame.add(new JLabel("E-Mail:"));
        infoFrame.add(txtCustEmail);
        infoFrame.add(new JLabel("Street Address:"));
        infoFrame.add(txtCustStreet);
        infoFrame.add(new JLabel("City:"));
        infoFrame.add(txtCustCity);
        infoFrame.add(new JLabel("State:"));
        infoFrame.add(txtCustState);
        infoFrame.add(new JLabel("Zip:"));
        infoFrame.add(txtCustZip);

        if(customerID == null) {
            //This is a new user
        } else {
            //Get the current user and fill information in
            Customer cust = customerDA.getCustomer(customerID);
            txtCustID.setText(String.valueOf(cust.getCustomerID()));
            txtCustFirst.setText(cust.getCustomerName_first());
            txtCustLast.setText(cust.getCustomerName_last());
            txtCustEmail.setText(cust.getEmail());
            txtCustStreet.setText(cust.getStreet_address());
            txtCustState.setText(cust.getState());
            txtCustZip.setText(cust.getZip());
        }

        JPanel fillerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        JButton save = new JButton("Save");
        JButton delete = new JButton("Delete");

        save.addActionListener(e -> updateCustomer(txtCustID.getText(),txtCustFirst.getText(),txtCustLast.getText(), txtCustEmail.getText(),
                txtCustStreet.getText(), txtCustCity.getText(),txtCustState.getText(), txtCustZip.getText()));
        delete.addActionListener(e -> deleteCustomer(txtCustID.getText()));
        buttonPanel.add(save);
        buttonPanel.add(delete);

        infoFrame.add(fillerPanel);
        infoFrame.add(buttonPanel);
        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }

    private void initEditPanel() {
        GridBagConstraints gbc;
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = .5;
        gbc.fill = GridBagConstraints.BOTH;
        custPanel.add(editPanel, gbc);

        JButton newCustomer  = new JButton("New Customer");
        newCustomer.setSize(25,25);
        newCustomer.addActionListener(e -> moreInfo(null));
        editPanel.add(newCustomer);

    }

    public JComponent getRootComponent() {
        return custPanel;
    }
}
