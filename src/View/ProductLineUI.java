package View;

import Controller.ProductLineDA;
import Controller.SupplierDA;
import Model.Inventory;
import Model.Product_Line;
import Model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

public class ProductLineUI {
    private JPanel plPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane plScrollPane;
    private JTable plTable;
    JFrame infoFrame;
    ProductLineDA plDA;
    Component parent;

    public ProductLineUI(Component parent) {
        this.parent = parent;
        init();
        plDA = new ProductLineDA();
        table_update();
    }

    protected void table_update() {
        ArrayList<Product_Line> plList = plDA.getProductLineList();

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
        tm.addColumn("Unit Price");

        plTable.setModel(tm);
        for(Product_Line pl: plList) {
            Vector<Object> rowObj = new Vector<>(3);
            rowObj.add(0, pl.getId());
            rowObj.add(1, pl.getName());
            rowObj.add(2, pl.getUnitPrice());
            tm.addRow(rowObj);
        }
    }

    private void init() {
        plPanel = new JPanel();
        plPanel.setLayout(new GridBagLayout());
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        plPanel.add(viewPanel, gbc);
        plTable = new JTable();
        plTable.setRowHeight(30);

        plTable.setAutoCreateRowSorter(true);
        plTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    moreInfo((int) target.getValueAt(row,0));
                }
            }
        });

        plScrollPane = new JScrollPane(plTable);
        viewPanel.add(plScrollPane);

        initEditPanel();
    }

    private void moreInfo(Integer plID) {
        if(infoFrame != null) {
            infoFrame.dispose();
        }

        infoFrame = new JFrame("Product Line Information");
        infoFrame.setLayout(new GridLayout(6,2));

        JTextField txtProdLineID = new JTextField();
        JTextField txtProdLineName = new JTextField();
        JTextArea txtProdLineDesc = new JTextArea();

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        NumberFormatter formatter = new NumberFormatter(format);

        formatter.setMinimum(0.0);
        formatter.setAllowsInvalid(false);

        JFormattedTextField txtProdLinePrice = new JFormattedTextField(formatter);
        txtProdLinePrice.setValue(0.00);

        JLabel idLabel = new JLabel("ID:");
        txtProdLineName.setColumns(50);

        infoFrame.add(idLabel);
        txtProdLineID.setEnabled(false);
        infoFrame.add(txtProdLineID);
        infoFrame.add(new JLabel("Name:"));
        infoFrame.add(txtProdLineName);
        infoFrame.add(new JLabel("Desc:"));
        infoFrame.add(txtProdLineDesc);
        infoFrame.add(new JLabel("Unit Price:"));
        infoFrame.add(txtProdLinePrice);

        SupplierDA supplierDA = new SupplierDA();
        ArrayList<Supplier> supList = supplierDA.getSupList();
        JComboBox<Supplier> optSupplier = new JComboBox<>(supList.toArray(new Supplier[0]));
        infoFrame.add(new JLabel("Supplier:"));
        infoFrame.add(optSupplier);

        if(plID == null) {
        } else {
            //This is existing product line, fill information
            Product_Line pl = plDA.getProdLine(plID);
            txtProdLineID.setText(String.valueOf(pl.getId()));
            txtProdLineName.setText(pl.getName());
            txtProdLineDesc.setText(pl.getDesc());
            txtProdLinePrice.setValue(pl.getUnitPrice());
            setComboBoxBySupplier(optSupplier, supList, pl.getSupplierId());
        }

        JPanel fillerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        JButton save = new JButton("Save");
        JButton delete = new JButton("Delete");

        save.addActionListener(e -> updateProductLine(txtProdLineID.getText(), txtProdLineName.getText(),txtProdLineDesc.getText(),(double)txtProdLinePrice.getValue(),(Supplier) optSupplier.getSelectedItem()));
        delete.addActionListener(e -> deleteProductLine(txtProdLineID.getText()));

        buttonPanel.add(save);
        buttonPanel.add(delete);

        infoFrame.add(fillerPanel);
        infoFrame.add(buttonPanel);

        infoFrame.pack();
        infoFrame.setLocationRelativeTo(parent);
        infoFrame.setVisible(true);
    }

    private void updateProductLine(String id, String name,String desc, double price, Supplier sup) {
        if(name == null) {
            return;
        }
        Product_Line pl = new Product_Line(name, desc, price, sup.getId());
        if(id.isBlank()) {
            //Create new product line
            if(!plDA.addProductLine(pl)){
                error("ERROR! Product Line not added");
            } else {
                success("Success! Product Line added successfully");
            }
        } else {
            //Update existing product line
            pl.setId(Integer.valueOf(id));
            if(!plDA.updateProductLine(pl)) {
                error("ERROR! Product line not updated");
            } else {
                success("Success! Product line updated");
            }
        }
    }

    private void deleteProductLine(String id) {
        if(!plDA.removeProductLine(Integer.valueOf(id))) {
            JOptionPane.showMessageDialog(null, "ERROR! Product Line not removed");
        } else {
            success("Success! Product Line removed successfully");
        }
    }

    private void success(String message) {
        JOptionPane.showMessageDialog(null, message);
        infoFrame.dispose();
        table_update();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(null, message);
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
        plPanel.add(editPanel, gbc);

        JButton newProdLine = new JButton("New Product Line");
        newProdLine.setSize(25,25);
        newProdLine.addActionListener(e -> moreInfo(null));
        editPanel.add(newProdLine);
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
        return plPanel;
    }

}
