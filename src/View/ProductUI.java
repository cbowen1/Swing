package View;

import Controller.ProductDA;
import Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class ProductUI {
    private JPanel prodPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane prodScrollPane;
    private JTable prodTable;
    JFrame infoFrame;
    ProductDA productDA;

    public ProductUI() {
        init();
        productDA = new ProductDA();
        ArrayList<Product> prodList = productDA.getOrderList();

        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tm.addColumn("Product ID");
        tm.addColumn("Line ID");
        tm.addColumn("Product Name");
        tm.addColumn("Unit Price");
        tm.addColumn("Qty");

        prodTable.setModel(tm);
        for(Product o: prodList) {
            Vector<Object> rowObj = new Vector<>(5);
            rowObj.add(0, o.getId());
            rowObj.add(1, o.getProductLineId());
            rowObj.add(2, o.getName());
            rowObj.add(3, o.getUnitPrice());
            rowObj.add(4, o.getQty());
            tm.addRow(rowObj);
        }

    }

    private void init() {
        prodPanel = new JPanel();
        prodPanel.setLayout(new GridBagLayout());
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
        prodPanel.add(viewPanel, gbc);
        prodTable = new JTable();
        prodTable.setRowHeight(30);

        prodTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    moreInfo((int) target.getValueAt(row,0));
                }
            }
        });

        prodScrollPane = new JScrollPane(prodTable);
        viewPanel.add(prodScrollPane);

        initEditPanel();
    }

    private void moreInfo(Integer productID) {
        if(infoFrame != null) {
            infoFrame.dispose();
        }

        infoFrame = new JFrame("Product Information");
        infoFrame.setLayout(new GridLayout(3,2));

        if(productID == null) {
            //New Product Creation
        } else {
            //Show existing product info
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
        prodPanel.add(editPanel, gbc);

        JButton newSupplier  = new JButton("New Product");
        newSupplier.setSize(25,25);
        newSupplier.addActionListener(e -> moreInfo(null));
        editPanel.add(newSupplier);
    }

    public JComponent getRootComponent() {
        return prodPanel;
    }
}
