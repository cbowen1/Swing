package View;

import Controller.CustomerDA;
import Controller.OrderDA;
import Model.Customer;
import Model.Order;

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

    public OrderUI() {
        init();
        orderDA = new OrderDA();
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
        infoFrame.setLayout(new GridLayout(5,2));

        JTextField txtOrderId = new JTextField();
        JTextField txtCustomerId = new JTextField();
        JTextField txtPaymentId = new JTextField();
        JTextField txtStatus = new JTextField();
        JTextField txtOrderDate = new JTextField();
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
