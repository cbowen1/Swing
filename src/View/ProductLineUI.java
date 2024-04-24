package View;

import Controller.ProductLineDA;
import Model.Product_Line;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class ProductLineUI {
    private JPanel plPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane plScrollPane;
    private JTable plTable;
    JFrame infoFrame;
    ProductLineDA plDA;


    public ProductLineUI() {
        init();
        plDA = new ProductLineDA();
        table_update();
    }

    private void table_update() {
        ArrayList<Product_Line> plList = plDA.getProductLineList();

        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tm.addColumn("ID");
        tm.addColumn("Name");
        tm.addColumn("Description");
        tm.addColumn("Unit Price");
        tm.addColumn("Supplier ID");

        plTable.setModel(tm);
        for(Product_Line pl: plList) {
            Vector<Object> rowObj = new Vector<>(5);
            rowObj.add(0, pl.getId());
            rowObj.add(1, pl.getName());
            rowObj.add(2, pl.getDesc());
            rowObj.add(3, pl.getUnitPrice());
            rowObj.add(4, pl.getSupplierId());
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
        infoFrame.setLayout(new GridLayout(4,2));

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
        plPanel.add(editPanel, gbc);

        JButton newProdLine = new JButton("New Product Line");
        newProdLine.setSize(25,25);
        newProdLine.addActionListener(e -> moreInfo(null));
        editPanel.add(newProdLine);
    }

    public JComponent getRootComponent() {
        return plPanel;
    }

}
