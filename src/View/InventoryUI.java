package View;

import Controller.InventoryDA;
import Controller.SupplierDA;
import Model.Inventory;
import Model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

public class InventoryUI {
    private JPanel invPanel;
    private JPanel viewPanel;

    private JPanel editPanel;
    private JScrollPane invScrollPane;
    private JTable invTable;
    JFrame infoFrame;
    Component parent;
    InventoryDA inventoryDA;

    public InventoryUI(Component parent) {
        this.parent = parent;
        init();
        inventoryDA = new InventoryDA();
        inventoryDA.setParent(this.getRootComponent());
        table_update();
    }

    protected void table_update() {
        ArrayList<Inventory> invList = inventoryDA.getOrderList();

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
        tm.addColumn("Supply Name");
        tm.addColumn("On Hand");
        tm.addColumn("Supplier ID");

        invTable.setModel(tm);
        for(Inventory o: invList) {
            Vector<Object> rowObj = new Vector<>(4);
            rowObj.add(0, o.getId());
            rowObj.add(1, o.getName());
            rowObj.add(2, o.getQty());
            rowObj.add(3, o.getSupplierID());
            tm.addRow(rowObj);
        }
    }

    private void init() {
        invPanel = new JPanel();
        invPanel.setLayout(new GridBagLayout());
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        invPanel.add(viewPanel, gbc);
        invTable = new JTable();
        invTable.setRowHeight(30);

        invTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    moreInfo((int) target.getValueAt(row,0));
                }
            }
        });

        invTable.setAutoCreateRowSorter(true);
        invScrollPane = new JScrollPane(invTable);
        viewPanel.add(invScrollPane);

        initEditPanel();

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
        invPanel.add(editPanel, gbc);

        JButton newSupplier  = new JButton("New Inventory");
        newSupplier.setSize(25,25);
        newSupplier.addActionListener(e -> moreInfo(null));
        editPanel.add(newSupplier);
    }

    private void moreInfo(Integer inventoryID) {
        if(infoFrame != null) {
            infoFrame.dispose();
        }

        infoFrame = new JFrame("Supply Information");
        infoFrame.setLayout(new GridLayout(5,2));
        JTextField txtInventoryID = new JTextField();
        JTextField txtInventoryName = new JTextField();

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);

        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);

        JFormattedTextField txtInventoryQty = new JFormattedTextField(formatter);

        //JOptionPane.showMessageDialog(this.getRootComponent(), txtInventoryQty);

        JLabel idLabel = new JLabel("ID");

        txtInventoryName.setColumns(50);

        infoFrame.add(idLabel);
        txtInventoryID.setEnabled(false);
        infoFrame.add(txtInventoryID);
        infoFrame.add(new JLabel("Name:"));
        infoFrame.add(txtInventoryName);
        infoFrame.add(new JLabel("Quantity:"));
        infoFrame.add(txtInventoryQty);

        SupplierDA supplierDA = new SupplierDA();
        ArrayList<Supplier> supList = supplierDA.getSupList();
        JComboBox<Supplier> optInventorySupplier = new JComboBox<>(supList.toArray(new Supplier[0]));
        infoFrame.add(new JLabel("Supplier:"));
        infoFrame.add(optInventorySupplier);

        if(inventoryID == null) {
            //This is a NEW inventory
        } else {
            //This is existing inventory
            Inventory inv = inventoryDA.getInventory(inventoryID);
            txtInventoryID.setText(String.valueOf(inv.getId()));
            txtInventoryName.setText(inv.getName());
            txtInventoryQty.setValue(inv.getQty());
            setComboBoxBySupplier(optInventorySupplier, supList, inv.getSupplierID());
        }

        JPanel fillerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        JButton save = new JButton("Save");
        JButton delete = new JButton("Delete");

        save.addActionListener(e -> updateInventory(txtInventoryID.getText(), txtInventoryName.getText(),(int) txtInventoryQty.getValue(),(Supplier) optInventorySupplier.getSelectedItem()));
        delete.addActionListener(e -> deleteInventory(txtInventoryID.getText()));

        buttonPanel.add(save);
        buttonPanel.add(delete);

        infoFrame.add(fillerPanel);
        infoFrame.add(buttonPanel);

        infoFrame.pack();
        infoFrame.setLocationRelativeTo(parent);
        infoFrame.setVisible(true);
    }

    private void deleteInventory(String id) {
        String response = inventoryDA.removeInventory(Integer.valueOf(id));
        if(response == "false") {
            JOptionPane.showMessageDialog(this.getRootComponent(), "ERROR! Inventory not removed");
        } else if(response == "true") {
            success("Success! Inventory removed successfully");
        }
    }

    private void success(String message) {
        JOptionPane.showMessageDialog(this.getRootComponent(), message);
        infoFrame.dispose();
        table_update();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(this.getRootComponent(), message);
    }

    private void updateInventory(String id, String name, int qty, Supplier sup) {
        if(name == null) {
            return;
        }
        Inventory inv = new Inventory(name, qty, sup.getId());
        if(id.isBlank()) {
            //Create new inventory item
            if(!inventoryDA.addInventory(inv)){
                error("ERROR! Inventory not added");
            } else {
                success("Success! Supplier added successfully");
            }
        } else {
            //Update existing inventory item
            inv.setId(Integer.valueOf(id));
            if(!inventoryDA.updateInventory(inv)) {
                error("ERROR! Inventory not updated");
            } else {
                success("Success! Inventory updated");
            }
        }
    }


    private void setComboBoxBySupplier(JComboBox<Supplier> combo,ArrayList<Supplier> supList, int supplierID) {
        if(supplierID == -1) {
            return;
        }
        for(Supplier s : supList) {
            if(s.getId() == supplierID) {
                combo.setSelectedItem(s);
                break;
            }
        }
    }

    public JComponent getRootComponent() {
        return invPanel;
    }


}
