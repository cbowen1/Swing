package View;

import Controller.CustomerDA;
import Controller.SupplierDA;
import Model.Customer;
import Model.Supplier;
import jdk.jshell.spi.ExecutionControl;

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
    JFrame infoFrame;
    SupplierDA supDA;
    Component parent;

    public SupplierUI(Component parent) {
        this.parent = parent;
        init();
        supDA = new SupplierDA();
        table_update();
    }

    protected void table_update() {
        ArrayList<Supplier> supList = supDA.getSupList();

        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int col) {
                Class retVal = Object.class;
                if(getRowCount() > 0) {
                    retVal = getValueAt(0, col).getClass();
                }
                return  retVal;
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
        supTable.setAutoCreateRowSorter(true);
        supTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    moreInfo((int) target.getValueAt(row,0));
                }
            }
        });
        supScrollPane = new JScrollPane(supTable);
        viewPanel.add(supScrollPane);

        initEditPanel();
    }

    private void moreInfo(Integer supplierID) {
        if(infoFrame != null) {
            infoFrame.dispose();
        }

        infoFrame = new JFrame("Supplier Information");
        infoFrame.setLayout(new GridLayout(7,2));
        JTextField txtSupplierID = new JTextField();
        JTextField txtSupplierName = new JTextField();
        JTextField txtSupplierWebsite = new JTextField();
        JTextField txtSupplierAddress = new JTextField();
        JTextField txtSupplierEmail = new JTextField();
        JTextField txtSupplierPhone = new JTextField();
        JLabel idLabel = new JLabel("ID:");

        txtSupplierAddress.setColumns(50);

        infoFrame.add(idLabel);
        txtSupplierID.setEnabled(false);
        infoFrame.add(txtSupplierID);
        infoFrame.add(new JLabel("Name:"));
        infoFrame.add(txtSupplierName);
        infoFrame.add(new JLabel("Website:"));
        infoFrame.add(txtSupplierWebsite);
        infoFrame.add(new JLabel("Address:"));
        infoFrame.add(txtSupplierAddress);
        infoFrame.add(new JLabel("Email:"));
        infoFrame.add(txtSupplierEmail);
        infoFrame.add(new JLabel("Phone:"));
        infoFrame.add(txtSupplierPhone);

        if(supplierID == null) {
            //Hide the ID tags so the user doesn't have to enter ID in, that will be done by the DB
            idLabel.setVisible(false);
            txtSupplierID.setVisible(false);
            txtSupplierID.setText(null);
        } else {
            Supplier sup = supDA.getSupplier(supplierID);
            txtSupplierID.setText(Integer.toString(sup.getId()));
            txtSupplierName.setText(sup.getName());
            txtSupplierWebsite.setText(sup.getWebsite());
            txtSupplierAddress.setText(sup.getAddress());
            txtSupplierEmail.setText(sup.getEmail());
            txtSupplierPhone.setText(sup.getPhone());
        }
        JPanel fillerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        JButton save = new JButton("Save");
        JButton delete = new JButton("Delete");

        save.addActionListener(e -> updateSupplier(txtSupplierID.getText(), txtSupplierName.getText(),txtSupplierWebsite.getText(),txtSupplierAddress.getText(),
                txtSupplierEmail.getText(), txtSupplierPhone.getText()));
        delete.addActionListener(e -> deleteSupplier(txtSupplierID.getText()));

        buttonPanel.add(save);
        buttonPanel.add(delete);

        infoFrame.add(fillerPanel);
        infoFrame.add(buttonPanel);
        infoFrame.pack();
        infoFrame.setLocationRelativeTo(parent);
        infoFrame.setVisible(true);
    }

    private boolean updateSupplier(String ID, String name, String website, String address, String email, String phone) {
        if(name == null) {
            return false;
        }
        Supplier supplier = new Supplier(name, website, address, email, phone);
        if(ID.isBlank()) {
            //Supplier Creation
            if(!supDA.addSupplier(supplier)){
                JOptionPane.showMessageDialog(this.getRootComponent(), "ERROR! Supplier not added");
            }else {
                success("Success! Supplier added successfully");
            }
        } else {
            //Supplier Update
            supplier.setId(Integer.valueOf(ID));
            if(!supDA.updateSupplier(supplier)) {
                JOptionPane.showMessageDialog(this.getRootComponent(), "ERROR! Supplier not updated");
            } else {
                success("Success! Supplier updated");
            }
        }
        return true;
    }

    private void success(String message) {
        JOptionPane.showMessageDialog(this.getRootComponent(), message);
        infoFrame.dispose();
        table_update();
    }

    private void deleteSupplier(String id) {
        if(!supDA.removeSupplier(Integer.valueOf(id))) {
            JOptionPane.showMessageDialog(this.getRootComponent(), "ERROR! Supplier not removed");
        } else {
            success("Success! Supplier removed successfully");
        }
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
        supPanel.add(editPanel, gbc);

        JButton newSupplier  = new JButton("New Supplier");
        newSupplier.setSize(25,25);
        newSupplier.addActionListener(e -> moreInfo(null));
        editPanel.add(newSupplier);
    }

    public JComponent getRootComponent() {
        return supPanel;
    }
}
