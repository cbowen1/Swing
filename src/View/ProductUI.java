package View;

import Controller.ProductDA;
import Controller.ProductLineDA;
import Controller.SupplierDA;
import Model.Product;
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

public class ProductUI {
    private JPanel prodPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane prodScrollPane;
    private JTable prodTable;
    JFrame infoFrame;
    ProductDA productDA;
    ProductLineDA prodLineDA;

    public ProductUI() {
        init();
        productDA = new ProductDA();
        prodLineDA = new ProductLineDA();
        table_update();
    }

    protected void table_update() {
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
        infoFrame.setLayout(new GridLayout(6,2));

        JTextField txtProdId = new JTextField();
        JTextField txtProdName = new JTextField();

        NumberFormat curFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        NumberFormatter curFormatter = new NumberFormatter(curFormat);

        curFormatter.setMinimum(0.0);
        curFormatter.setAllowsInvalid(false);

        JFormattedTextField txtUnitPrice = new JFormattedTextField(curFormatter);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);

        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        JFormattedTextField txtQty = new JFormattedTextField(formatter);
        JLabel idLabel = new JLabel("ID:");

        infoFrame.add(idLabel);
        infoFrame.add(txtProdId);
        infoFrame.add(new JLabel("Name:"));
        infoFrame.add(txtProdName);
        infoFrame.add(new JLabel("Unit Price:"));
        infoFrame.add(txtUnitPrice);
        infoFrame.add(new JLabel("QTY:"));
        infoFrame.add(txtQty);

        SupplierDA supplierDA = new SupplierDA();
        ArrayList<Supplier> supList = supplierDA.getSupList();
        ArrayList<Product_Line> prodLineList = prodLineDA.getProductLineList();
        JComboBox<Product_Line> prodLineComboBox = new JComboBox<>(prodLineList.toArray(new Product_Line[0]));
        infoFrame.add(new JLabel("Product Line:"));
        infoFrame.add(prodLineComboBox);

        if(productID == null) {
            //New Product Creation
            //TODO: New Product
            txtProdId.setEnabled(false);
            System.out.println("New Product not implemented");
        } else {
            txtProdId.setEnabled(false);
            Product prod = productDA.getProduct(productID);
            //Show existing product info
            txtProdId.setText(String.valueOf(prod.getId()));
            txtProdName.setText(prod.getName());
            txtQty.setValue(prod.getQty());
            txtUnitPrice.setValue(prod.getUnitPrice());
            setComboBoxBySupplier(prodLineComboBox, prodLineList, prod.getProductLineId());

        }

        JPanel fillerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        JButton save = new JButton("Save");
        JButton delete = new JButton("Delete");

        //TODO: save
        //TODO: delete
        //save.addActionListener(e -> updateProductLine(txtProdLineID.getText(), txtProdLineName.getText(),txtProdLineDesc.getText(),(double)txtProdLinePrice.getValue(),(Supplier) optSupplier.getSelectedItem()));
        //delete.addActionListener(e -> deleteProductLine(txtProdLineID.getText()));

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
        prodPanel.add(editPanel, gbc);

        JButton newSupplier  = new JButton("New Product");
        newSupplier.setSize(25,25);
        newSupplier.addActionListener(e -> moreInfo(null));
        editPanel.add(newSupplier);
    }

    public JComponent getRootComponent() {
        return prodPanel;
    }

    private void setComboBoxBySupplier(JComboBox<Product_Line> combo,ArrayList<Product_Line> plList, int plID) {
        if(plID == -1) {
            return;
        }
        for(Product_Line pl : plList) {
            if(pl.getId() == plID) {
                combo.setSelectedItem(pl);
                break;
            }
        }
    }
}
