package View;

import Controller.CustomerDA;
import Controller.OrderDA;
import Controller.ProductDA;
import Model.Customer;
import Model.Order;
import Model.OrderDetails;
import Model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class OrderUI {
    private JPanel orderPanel;
    private JPanel viewPanel;
    private JPanel editPanel;
    private JScrollPane orderScrollPane;
    private JTable orderTable;
    JFrame infoFrame;
    OrderDA orderDA;
    CustomerDA custDA;
    ProductDA prodDA;

    public OrderUI() {
        init();
        orderDA = new OrderDA();
        custDA = new CustomerDA();
        prodDA = new ProductDA();
        table_update();
    }

    private void table_update() {
        ArrayList<Order> orderList = orderDA.getOrderList();

        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tm.addColumn("Order ID");
        tm.addColumn("Customer ID");
        tm.addColumn("Payment ID");
        tm.addColumn("Current Status");
        tm.addColumn("Order Date");

        orderTable.setModel(tm);
        for(Order o: orderList) {
            Vector<Object> rowObj = new Vector<>(3);
            rowObj.add(0, o.getOrderID());
            rowObj.add(1, o.getCustomerID());
            rowObj.add(2, o.getPaymentID());
            rowObj.add(3, o.getStatus());
            rowObj.add(4, o.getOrder_date());
            tm.addRow(rowObj);
        }
    }

    private void init() {
        orderPanel = new JPanel();
        orderPanel.setLayout(new GridBagLayout());
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        orderPanel.add(viewPanel, gbc);
        orderTable = new JTable();
        orderTable.setRowHeight(30);

        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    moreInfo((int) target.getValueAt(row,0));
                }
            }
        });

        orderScrollPane = new JScrollPane(orderTable);
        viewPanel.add(orderScrollPane);

        initEditPanel();
    }

    private void moreInfo(Integer orderID) {
        if(infoFrame != null) {
            infoFrame.dispose();
        }

        infoFrame = new JFrame("Order Details");
        infoFrame.setLayout(new GridBagLayout());

        JPanel dataPanel = new JPanel(new GridLayout(2,3));
        JPanel custPanel = new JPanel(new GridLayout(2,3));
        JPanel tablePanel = new JPanel(new GridLayout(1,1));
        JPanel btnPanel = new JPanel(new GridLayout(1,3));

        dataPanel.add(new JLabel("OrderID"));
        dataPanel.add(new JLabel("Order Status"));
        dataPanel.add(new JLabel("Order Date"));

        custPanel.add(new JLabel("First: "));
        custPanel.add(new JLabel("Last: "));
        custPanel.add(new JLabel("Address: "));

        JTextField custFirst = new JTextField();
        JTextField custLast = new JTextField();
        JTextField custAddress = new JTextField();

        custPanel.add(custFirst);
        custPanel.add(custLast);
        custPanel.add(custAddress);

        JTextField txtOrderId = new JTextField();
        JTextField txtOrderStatus = new JTextField();
        JTextField txtOrderDate = new JTextField();
        JTable detailTable = new JTable();
        JScrollPane detailPane = new JScrollPane(detailTable);

        dataPanel.add(txtOrderId);
        dataPanel.add(txtOrderStatus);
        dataPanel.add(txtOrderDate);

        tablePanel.add(detailPane);

        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dtm.addColumn("Item Name");
        dtm.addColumn("Qty");
        dtm.addColumn("Price");

        if(orderID == null) {
            //This is a new order
            System.out.println("New order not implemented");
        } else {
            //Existing order, grab information
            Order ord = orderDA.getOrder(orderID);
            ord.setOrderDetails(orderDA.getOrderDetails(orderID));
            //Grab better customer and product information from corresponding tables
            ord.setCustomer(custDA.getCustomer(ord.getCustomerID()));

            //Add order details to table
            detailTable.setModel(dtm);
            for(OrderDetails o: ord.getOrderDetails()) {
                Vector<Object> row = new Vector<>(3);
                row.add(0,prodDA.getProductName(o.getProductID()));
                row.add(1,o.getQty());
                row.add(2,(o.getQty() * prodDA.getProductPrice(o.getProductID())));
                dtm.addRow(row);
            }

            //Set values in UI
            txtOrderId.setText(String.valueOf(ord.getOrderID()));
            txtOrderStatus.setText(ord.getStatus());
            txtOrderDate.setText(ord.getOrder_date().toString());

            custFirst.setText(ord.getCustomer().getCustomerName_first());
            custLast.setText(ord.getCustomer().getCustomerName_last());
            custAddress.setText(ord.getCustomer().getFullAddress());
        }

        JPanel fillerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        JButton save = new JButton("Save");
        JButton delete = new JButton("Delete");

        save.addActionListener(e -> updateOrder(txtOrderId.getText()));
        delete.addActionListener(e -> deleteOrder(txtOrderId.getText()));

        buttonPanel.add(save);
        buttonPanel.add(delete);

        btnPanel.add(buttonPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        infoFrame.add(dataPanel,gbc);
        gbc.gridy++;
        infoFrame.add(custPanel,gbc);
        gbc.gridy++;
        infoFrame.add(new JSeparator(JSeparator.HORIZONTAL),gbc);
        gbc.gridy++;
        infoFrame.add(tablePanel,gbc);
        gbc.gridy++;
        infoFrame.add(btnPanel,gbc);

        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);

    }

    private void success(String message) {
        JOptionPane.showMessageDialog(null, message);
        infoFrame.dispose();
        table_update();
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void deleteOrder(String id) {
        System.out.println("Delete order not implemented");
    }

    private void updateOrder(String id) {
        System.out.println("Update order not implemented");
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
        orderPanel.add(editPanel, gbc);

        JButton newSupplier  = new JButton("New Order");
        newSupplier.setSize(25,25);
        newSupplier.addActionListener(e -> moreInfo(null));
        editPanel.add(newSupplier);
    }

    public JComponent getRootComponent() {
        return orderPanel;
    }

}
