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

    JFrame infoFrame;
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
        if(infoFrame != null) {
            infoFrame.dispose();
        }
        Supplier sup = supDA.getSupplier(supplierID);
        infoFrame = new JFrame("Supplier Information");
        infoFrame.setLayout(new GridLayout(6,2));
        JTextField txtSupplierID = new JTextField();
        JTextField txtSupplierName = new JTextField();
        JTextField txtSupplierWebsite = new JTextField();
        JTextField txtSupplierAddress = new JTextField();
        JTextField txtSupplierEmail = new JTextField();
        JTextField txtSupplierPhone = new JTextField();

        txtSupplierAddress.setColumns(50);

        infoFrame.add(new JLabel("ID:"));
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

        txtSupplierID.setText(Integer.toString(sup.getId()));
        txtSupplierName.setText(sup.getName());
        txtSupplierWebsite.setText(sup.getWebsite());
        txtSupplierAddress.setText(sup.getAddress());
        txtSupplierEmail.setText(sup.getEmail());
        txtSupplierPhone.setText(sup.getPhone());

        JPanel buttonPanel = new JPanel();
        JButton save = new JButton("Save");
        JButton delete = new JButton("Delete");

        save.addActionListener(e -> updateSupplier());
        delete.addActionListener(e -> deleteSupplier());

        buttonPanel.add(save);
        buttonPanel.add(delete);


        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }

    private void deleteSupplier() {
        System.out.print("Delete not implemented");
    }

    private void updateSupplier() {
        System.out.println("Update not implemented");
    }

    private void addSupplier() {
        System.out.println("Add supplier not implemented");
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
        newSupplier.addActionListener(e -> addSupplier());
        editPanel.add(newSupplier);
    }

    public JComponent getRootComponent() {
        return supPanel;
    }
}
